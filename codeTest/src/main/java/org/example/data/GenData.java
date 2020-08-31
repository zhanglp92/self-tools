package org.example.data;

import lombok.extern.log4j.Log4j2;

import java.util.Random;

/**
 * 构造数据
 */
@Log4j2
public class GenData {
    private static Random random = new Random(System.currentTimeMillis());

    private static int maxNum = 20;

    public static Integer[] genIntArr(int n) {
        Integer[] arr = new Integer[n];
        while (--n >= 0) {
            arr[n] = random.nextInt(maxNum);
        }
        return arr;
    }

    public static Integer[][] genIntArr(int row, int col) {
        Integer[][] arr = new Integer[row][];
        for (int i = 0; i < row; i++) {
            arr[i] = genIntArr(col);
        }
        return arr;
    }

    public static String sprint(Integer[][] arr) {
        StringBuilder builder = new StringBuilder();
        for (Integer[] ints : arr) {
            for (int j = 0; j < ints.length; j++) {
                builder.append(String.format("%3d ", ints[j]));
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public static <T> String sprint(T[] arr, int s, int e) {
        StringBuilder builder = new StringBuilder();
        for (int idx = s; idx < e; idx++) {
            builder.append(arr[idx]);
            if (idx + 1 < e) {
                builder.append("-> ");
            }
        }
        return builder.toString();
    }

}
