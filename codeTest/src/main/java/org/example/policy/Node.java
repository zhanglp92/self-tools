package org.example.policy;

import lombok.Getter;
import lombok.Setter;

/**
 * 缓存节点
 */
@Getter
@Setter
public class Node<K, V> {
    /**
     * 数据域
     */
    private K k;
    private V v;

    /**
     * 使用双向循环链表
     */
    public Node<K, V> pre, next;

    public Node(K k, V v) {
        this.k = k;
        this.v = v;
    }
}
