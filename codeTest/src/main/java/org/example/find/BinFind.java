package org.example.find;


import lombok.extern.log4j.Log4j2;
import org.example.data.GenData;
import org.example.exercise.HeapSort;

@Log4j2
public class BinFind {

    private static <T extends Comparable<T>> int findInner(T[] data, T target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2, res = target.compareTo(data[mid]);
            if (res == 0) {
                return mid;
            } else if (res < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    private static <T extends Comparable<T>> int findInner2(T[] data, T target, int left, int right) {
        if (left > right) {
            return -1;
        }

        int mid = left + (right - left) / 2;
        int res = data[mid].compareTo(target);

        if (res == 0) {
            return mid;
        } else if (res > 0) {
            return findInner2(data, target, left, mid - 1);
        } else {
            return findInner2(data, target, mid + 1, right);
        }
    }

    public static <T extends Comparable<T>> int find(T[] data, T target) {
        return findInner(data, target, 0, data.length - 1);
    }

    public static void main(String[] args) {
        Integer[] data = GenData.genIntArr(10);
        HeapSort.sort(data);

        log.info("find = {}, data = {}", find(data, data[3]), data[3]);
    }
}
