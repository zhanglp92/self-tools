package org.example.sort;

import lombok.extern.log4j.Log4j2;
import org.example.data.GenData;

import java.util.Arrays;

/**
 * 多路数据合并
 */
@Log4j2
public class Combine {

    public static class ArrayNode implements Comparable<ArrayNode> {
        Integer[] data;
        int curIdx;

        public ArrayNode(Integer[] data) {
            this.data = data;
        }

        private Integer curData() {
            return data[curIdx];
        }

        public boolean isEnd() {
            return curIdx >= data.length;
        }

        public Integer pop() {
            return data[curIdx++];
        }

        @Override
        public int compareTo(ArrayNode o) {
            return o.curData().compareTo(curData());
        }
    }

    public static Integer[] combine(Integer[]... dataList) {
        PriorityQueen<ArrayNode> queen = new PriorityQueen<>(dataList.length);
        int size = 0;

        // 1. 初始化
        for (Integer[] arr : dataList) {
            size += arr.length;
            queen.put(new ArrayNode(arr));
        }

        // 2. 排序
        Integer[] result = new Integer[size];
        for (int idx = 0; !queen.isEmpty(); idx++) {
            ArrayNode node = queen.pop();
            result[idx] = node.pop();
            if (!node.isEnd()) {
                queen.put(node);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Integer[] data = combine(
                HeapSort.sort(GenData.genIntArr(10)),
                HeapSort.sort(GenData.genIntArr(10)),
                HeapSort.sort(GenData.genIntArr(10))
        );

        log.info("len = {}, data = {}", data.length, Arrays.toString(data));
    }
}
