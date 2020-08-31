package org.example.exercise;

import lombok.extern.log4j.Log4j2;
import org.example.data.GenData;

import java.util.Arrays;

@Log4j2
public class HeapSort {

    private static <T> void swap(T[] data, int a, int b) {
        T t = data[a];
        data[a] = data[b];
        data[b] = t;
    }

    private static <T extends Comparable<T>> void adjustHeap(T[] data, int len, int curPos) {
        // 自顶向下调整堆
        int left = 2 * curPos + 1, right = 2 * curPos + 2, target = curPos;

        if (left < len && data[left].compareTo(data[target]) > 0) {
            target = left;
        }
        if (right < len && data[right].compareTo(data[target]) > 0) {
            target = right;
        }
        if (curPos != target) {
            swap(data, target, curPos);
            // 继续调整被更新的子节点
            adjustHeap(data, len, target);
        }
    }

    private static <T extends Comparable<T>> void initHeap(T[] data) {
        // 自底向上调整堆
        for (int idx = data.length / 2 - 1; idx >= 0; idx--) {
            adjustHeap(data, data.length, idx);
        }
    }

    private static <T extends Comparable<T>> void unpack(T[] data) {
        for (int idx = data.length - 1; idx >= 0; idx--) {
            swap(data, 0, idx);
            adjustHeap(data, idx, 0);
        }
    }

    public static <T extends Comparable<T>> void sort(T[] data) {
        // 1. 初始化堆
        initHeap(data);

        // 2. 拆堆
        unpack(data);
    }

    public static void main(String[] args) {
        Integer[] data = GenData.genIntArr(10);
        HeapSort.sort(data);
        log.info("data = {}", Arrays.toString(data));
    }
}
