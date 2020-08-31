package org.example.find;

import lombok.extern.log4j.Log4j2;
import org.example.data.GenData;
import org.example.sort.HeapSort;

import java.util.PriorityQueue;

/**
 * 快排思想
 */
@Log4j2
public class FindK {

    public static int findK(Integer[] arr, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(k);
        for (int v : arr) {
            if (queue.size() < k) {
                queue.add(v);
            } else if (queue.peek() < v) {
                // 1. 删除堆顶, 2. 将堆尾元素移动到堆顶, 3. 自顶向下调整(找最大的自节点)
                queue.poll();
                // 1. 元素添加到堆尾, 2. 自底向上调整(找父节点)
                queue.add(v);
            }
        }
        return queue.peek();
    }

    public static void main(String[] args) {
        Integer[] arr = GenData.genIntArr(10);
        log.info("k(4) = {}", findK(arr, 4));

        HeapSort.sort(arr);
        log.info("source = {}", arr);
    }
}
