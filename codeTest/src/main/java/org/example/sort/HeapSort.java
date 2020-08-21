package org.example.sort;

import lombok.extern.log4j.Log4j2;
import org.example.data.GenData;

/**
 * 牢记一点即可, 根节点始终大于(小于)子节点
 * <p>
 * 堆本身是不保证顺序的
 * <p>
 * 堆排序的过程其实就是不停的取堆顶元素的过程
 * <p>
 * 1. 初始化堆
 * 2. 拆堆
 * 3. 调整堆
 */

@Log4j2
public class HeapSort {

    private static int compare(int a, int b) {
        return a - b;
    }

    private static void swap(int[] arr, int a, int b) {
        int t = arr[a];
        arr[a] = arr[b];
        arr[b] = t;
    }

    /**
     * 调整k之后需要依次向下重新调整被修改的节点
     */
    private static void buildHeap(int[] arr, int k, int len) {
        int left = 2 * k + 1, right = 2 * k + 2;

        int target = k;
        if (left < len && compare(arr[target], arr[left]) > 0) {
            target = left;
        }
        if (right < len && compare(arr[target], arr[right]) > 0) {
            target = right;
        }

        if (target != k) {
            swap(arr, target, k);
            buildHeap(arr, target, len);
        }
    }

    /**
     * 初始化堆
     */
    private static void initHeap(int[] arr) {
        // 从底向上调整堆
        for (int idx = arr.length / 2; idx >= 0; idx--) {
            buildHeap(arr, idx, arr.length);
        }
    }

    /**
     * 堆顶元素和最后一个元素交换
     */
    private static void excrete(int[] arr) {
        for (int last = arr.length - 1; last >= 0; last--) {
            swap(arr, 0, last);
            buildHeap(arr, 0, last);
        }
    }

    public static void sort(int[] arr) {
        // 1. 建堆
        initHeap(arr);
        print(arr, arr.length);

        // 2. 拆堆
        excrete(arr);
    }

    private static String levelSpace(int n) {
        StringBuilder builder = new StringBuilder();
        for (int idx = 0; idx < n; idx++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    private static void print(int[] arr, int len) {
        // 1. 计算深度
        int depth = 0;
        for (int cnt = 1; cnt < len; cnt <<= 1) {
            depth++;
        }

        String space = levelSpace(depth);
        StringBuilder builder = new StringBuilder("\n").append(space).append(space).append(space);

        // 2. 打印树
        for (int idx = 0, levelCnt = 0, levelNum = 1, depthCnt = 0; idx < len; idx++, levelCnt++) {
            if (levelCnt >= levelNum) {
                levelCnt = 0;
                levelNum <<= 1;
                depthCnt++;

                space = levelSpace(depth - depthCnt);
                builder.append("\n").append(space).append(space).append(space);
            }
            builder.append(arr[idx]).append(space);
        }
        log.info(builder.toString());
    }

    public static void main(String[] args) {
        int[] data = GenData.genIntArr(10);

        sort(data);
        log.info("source = {}", data);
    }
}
