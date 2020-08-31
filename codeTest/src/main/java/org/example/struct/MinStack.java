package org.example.struct;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

/**
 * 最小栈
 */
@Log4j2
public class MinStack<V extends Comparable<V>> {
    private class Node {
        V v;
        Node next;

        Node(V v, Node next) {
            this.v = v;
            this.next = next;
        }
    }

    private class ReferenceNode {
        int referenceCount = 1;
        Node v;
        ReferenceNode next;

        public ReferenceNode(Node v, ReferenceNode next) {
            this.v = v;
            this.next = next;
        }
    }

    // 原始栈
    private Node stack = new Node(null, null);
    // 最小栈引用计数
    private ReferenceNode minStack = new ReferenceNode(null, null);

    public MinStack<V> push(@NonNull V v) {
        stack.next = new Node(v, stack.next);
        if (minStack.next != null && minStack.next.v.v.compareTo(v) <= 0) {
            minStack.next.referenceCount++;
        } else {
            minStack.next = new ReferenceNode(stack.next, minStack.next);
        }
        return this;
    }

    public boolean isEmpty() {
        return stack.next == null;
    }

    public V pop() {
        if (isEmpty()) {
            return null;
        }
        Node res = stack.next;
        stack.next = res.next;

        if (--minStack.next.referenceCount <= 0) {
            minStack.next = minStack.next.next;
        }
        return res.v;
    }

    public V min() {
        if (isEmpty()) {
            return null;
        }
        return minStack.next.v.v;
    }

    public int ref() {
        if (isEmpty()) {
            return -1;
        }
        return minStack.next.referenceCount;
    }

    public static void main(String[] args) {
        MinStack<Integer> stack = new MinStack<>();
        stack.push(4).push(5).push(1).push(-1).push(-4).push(2).push(5);

        while (!stack.isEmpty()) {
            log.info("min(ref:{}) = {}, cur = {}", stack.ref(), stack.min(), stack.pop());
        }
    }
}
