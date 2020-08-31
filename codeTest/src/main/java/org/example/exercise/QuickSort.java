package org.example.exercise;

import lombok.extern.log4j.Log4j2;
import org.example.data.GenData;

import java.util.Arrays;

@Log4j2
public class QuickSort {

    private static <T> void swap(T[] data, int a, int b) {
        T t = data[a];
        data[a] = data[b];
        data[b] = t;
    }

    private static <T extends Comparable<T>> int cut2(T[] data, int left, int right) {
        int midIdx = left + 1;
        for (int idx = midIdx; idx <= right; idx++) {
            if (data[idx].compareTo(data[left]) < 0) {
                swap(data, idx, midIdx++);
            }
        }
        swap(data, left, --midIdx);
        return midIdx;
    }

    private static <T extends Comparable<T>> int cut(T[] data, int left, int right) {
        // 返回mid, 将小于data[mid]的调整到左侧, 其余调整到data[mid]右侧
        T dataBase = data[left];
        int i = left, j = right;

        while (i < j) {
            while (i < j && data[j].compareTo(dataBase) >= 0) {
                j--;
            }
            data[i] = data[j];

            while (i < j && data[i].compareTo(dataBase) <= 0) {
                i++;
            }
            data[j] = data[i];
        }

        data[i] = dataBase;
        return i;
    }

    private static <T extends Comparable<T>> void sortInner(T[] data, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = cut2(data, left, right);
        sortInner(data, left, mid - 1);
        sortInner(data, mid + 1, right);
    }

    public static <T extends Comparable<T>> void sort(T[] data) {
        sortInner(data, 0, data.length - 1);
    }

    public static void main(String[] args) {
        Integer[] data = GenData.genIntArr(10);
        sort(data);
        log.info("data = {}", Arrays.toString(data));
    }
}
