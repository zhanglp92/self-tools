package org.example.data;

import lombok.extern.log4j.Log4j2;

import java.util.Random;

/**
 * 构造数据
 */
@Log4j2
public class GenData {
    private static Random random = new Random(System.currentTimeMillis());

    public static int[] genIntArr(int n) {
        int[] arr = new int[n];
        while (--n >= 0) {
            arr[n] = random.nextInt(100);
        }
        return arr;
    }
}
