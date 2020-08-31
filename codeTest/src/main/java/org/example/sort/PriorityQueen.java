package org.example.sort;

import lombok.extern.log4j.Log4j2;
import org.junit.Assert;

/**
 * 固定大小的优先级队列
 */
@Log4j2
public class PriorityQueen<V extends Comparable<V>> {
    private int cap;
    private int size;
    private Object[] queen;

    public PriorityQueen(int cap) {
        this.cap = cap;
        this.queen = new Object[cap];
    }

    public boolean isEmpty() {
        return this.size <= 0;
    }

    public void put(V v) {
        Assert.assertTrue("too data", this.size < this.cap);

        // 自底向上调整
        this.queen[this.size++] = v;
        this.adjustHeap2(this.size - 1);
    }

    public V pop() {
        if (this.size <= 0) {
            return null;
        }

        // 1. 获取对顶元素
        this.swap(0, --this.size);
        V data = this.toClass(this.queen[this.size]);
        this.queen[this.size] = null;

        // 2. 调整堆
        this.adjustHeap1(0);

        return data;
    }

    private void adjustHeap2(int curIdx) {
        int parent = (curIdx + 1) / 2 - 1;
        if (parent < 0) {
            return;
        }

        adjustHeap1(parent);
        adjustHeap2(parent);
    }

    private void adjustHeap1(int curIdx) {
        int left = 2 * curIdx + 1, right = 2 * curIdx + 2, target = curIdx;
        if (left < this.size && this.toClass(this.queen[left]).compareTo(this.toClass(this.queen[target])) > 0) {
            target = left;
        }
        if (right < this.size && this.toClass(this.queen[right]).compareTo(this.toClass(this.queen[target])) > 0) {
            target = right;
        }
        if (target != curIdx) {
            this.swap(target, curIdx);
            adjustHeap1(target);
        }
    }

    private void swap(int a, int b) {
        Object t = this.queen[a];
        this.queen[a] = this.queen[b];
        this.queen[b] = t;
    }

    private V toClass(Object v) {
        return (V) v;
    }

    public static void main(String[] args) throws InterruptedException {
        PriorityQueen<Integer> queen = new PriorityQueen<>(6);

        queen.put(1);
        queen.put(3);
        queen.put(2);
        queen.put(5);
        queen.put(4);
        queen.put(6);

        while (!queen.isEmpty()) {
            log.info("data = {}", queen.pop());
        }
    }
}
