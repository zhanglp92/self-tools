package org.example.policy;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

/**
 * 跳表简单实现
 * <p>
 * 1. 由level层组成
 * 2. 每层都是一个链表
 * 3. 每个元素有两个指针:(1)指向下一个(2)指向下一层
 */
public class SkipListCache<K extends Comparable<K>, V> {
    private static Random random = new Random(System.currentTimeMillis());

    // 节点
    private class SkipListNode {
        K k;
        V v;
        SkipListNode next, down;

        public SkipListNode(K k, V v) {
            this.k = k;
            this.v = v;
        }

        public int compare(K k) {
            return this.k == null ? -1 : this.k.compareTo(k);
        }
    }

    private int level = 0;

    // 顶层(每层都有一个head)
    private SkipListNode top;

    // 初始化
    public SkipListCache(int level) {
        this.level = level;
        while (--level >= 0) {
            SkipListNode node = new SkipListNode(null, null);
            node.down = this.top;
            this.top = node;
        }
    }

    // 查找
    public V get(K k) {
        for (SkipListNode pos = top; pos != null; ) {
            int res = pos.compare(k);
            if (res == 0) {
                return pos.v;
            }

            if (pos.next == null) {
                if (pos.down == null) {
                    return null;
                }
                pos = pos.down;
            } else {
                pos = pos.next.compare(k) > 0 ? pos.down : pos.next;
            }
        }
        return null;
    }

    public void insert(K k, V v) {

        SkipListNode curNode = null;

        // 记录每层的前驱节点
        List<SkipListNode> path = Lists.newArrayList();

        for (SkipListNode pos = top; pos != null; ) {
            int res = pos.compare(k);
            if (res == 0) {
                curNode = pos;
                break;
            }
            if (pos.next == null) {
                path.add(pos);
                if (pos.down == null) {
                    break;
                }
                pos = pos.down;
            } else {
                if (pos.next.compare(k) > 0) {
                    path.add(pos);
                    pos = pos.down;
                } else {
                    pos = pos.next;
                }
            }
        }

        // 存在, 则修改curNode自上而下整个路上的节点
        if (curNode != null) {
            for (; curNode != null; curNode = curNode.down) {
                curNode.v = v;
            }
            return;
        }

        // 不存在, 则添加
        SkipListNode downTmp = null;
        for (int curLevel = this.level - 1, lev = randomLevel(); curLevel >= lev; curLevel--) {
            SkipListNode preNode = path.get(curLevel);
            SkipListNode t = preNode.next;
            preNode.next = new SkipListNode(k, v);
            preNode.next.next = t;
            preNode.next.down = downTmp;
            downTmp = preNode.next;
        }
    }

    private int randomLevel() {
        return Math.max(random.nextInt(this.level), 1);
    }
}
