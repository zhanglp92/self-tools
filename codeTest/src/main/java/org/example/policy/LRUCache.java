package org.example.policy;

import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.Random;

/**
 * 最近最少淘汰机制
 */
@Log4j2
public class LRUCache<K, V> {
    /**
     * 最大数量, 和当前节点你数量
     */
    private int maxNodeCount;

    /**
     * 使用MAP辅助查找
     */
    private Map<K, Node<K, V>> assist;

    /**
     * 数据使用双向链表存储
     */
    private Node<K, V> head, tail;

    public LRUCache(int maxNodeCount) {
        this.maxNodeCount = maxNodeCount;
        this.assist = Maps.newHashMapWithExpectedSize(this.maxNodeCount);

        this.head = new Node<>(null, null);
        this.tail = new Node<>(null, null);

        this.tail.pre = this.head;
        this.head.next = this.tail;
    }

    public void put(K k, V v) throws Exception {
        Node<K, V> node = this.assist.get(k);
        if (node != null) {
            node.setV(v);
            move(node);
        } else {
            if (this.assist.size() >= this.maxNodeCount) removeNode();
            node = new Node<>(k, v);
            this.assist.put(k, node);
            insert(node);
        }
    }

    public V get(K k) {
        Node<K, V> node = this.assist.get(k);
        if (node == null) {
            return null;
        }
        move(node);
        return node.getV();
    }

    private void move(Node<K, V> node) {
        if (node == null) {
            return;
        }
        node.pre.next = node.next;
        node.next.pre = node.pre;
        insert(node);
    }

    private void insert(Node<K, V> node) {
        node.pre = this.head;
        node.next = this.head.next;
        this.head.next.pre = node;
        this.head.next = node;
    }

    private void removeNode() throws Exception {
        Node<K, V> node = this.tail.pre;
        if (node == head) {
            throw new Exception("can't del head.");
        }
        node.pre.next = tail;
        tail.pre = node.pre;

        this.assist.remove(node.getK());
    }

    public String sample() {
        StringBuilder builder = new StringBuilder();
        for (Node<K, V> pos = head.next; pos != tail; pos = pos.next) {
            builder.append(pos.getK()).append("->");
        }
        return builder.toString();
    }

    public static void main(String[] args) throws Exception {
        Random random = new Random(System.currentTimeMillis());

        LRUCache<Integer, Integer> cache = new LRUCache<>(5);
        for (int idx = 0; idx < 20; idx++) {
            cache.put(idx, idx);
            log.info("Link: {}", cache.sample());

            Integer res = cache.get(random.nextInt(20));
            log.info("Link: {}, res = {}", cache.sample(), res);
        }
    }
}
