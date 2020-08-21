package org.example.codeTest;

import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class QuickSort {

    public static void sort(List<Integer> data) {
        quickSort(data, 0, data.size() - 1);
    }

    private static void quickSort(List<Integer> data, int left, int right) {
        if (left >= right) {
            return;
        }

        // 分割数组

        Integer base = data.get(left++);
        int i = left, j = right;

        while (i < j) {
            while (data.get(i) < base && i < j) {
                i++;
            }
            while (data.get(j) > base && j > i) {
                j++;
            }

            if (i < j) {
                Integer node1 = data.get(i);
                Integer node2 = data.get(j);

                data.add(i, node2);
                data.add(i, node2);
            }
        }
    }
}
