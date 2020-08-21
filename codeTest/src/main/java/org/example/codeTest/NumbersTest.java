package org.example.codeTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class NumbersTest {

    private static void print(Stack<Integer> numbers) {
        StringBuilder builder = new StringBuilder();
        for (Integer number : numbers) {
            builder.append(number).append(" ");
        }
        System.out.println(builder.toString());
    }

    public static boolean find(Stack<Integer> numbers, int begin, int end, int target) {
        if (target == 0) {
            print(numbers);
        } else if (target > 0) {
            for (int idx = begin; idx <= end; idx++) {
                numbers.add(idx);
                if (!find(numbers, idx + 1, end, target - idx)) {
                    numbers.pop();
                }
            }
            return false;
        }
        List<Integer> arr = new ArrayList<>();

        numbers.pop();
        return true;
    }

    public static int find(Integer[] arr, int target) {
        int start = 0;
        int end = arr.length - 1;

        while (start <= end) {
            int middle = (end + start) / 2;
            if (target == arr[middle]) {
                return middle;
            } else if (target < arr[middle]) {
                end = middle - 1;
            } else {
                start = middle + 1;
            }
        }
        return -1;
    }

    public static void find(int n, int m) {
        find(new Stack<>(), 1, n, m);
    }

    public static void main(String[] args) {
//        find(5, 5);
        Integer[] arr = {1, 2, 3, 4, 5, 7, 8, 9, 13};
        System.out.println(find(arr, 2));
    }


}
