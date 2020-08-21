package org.example.codeTest;

import java.util.ArrayList;
import java.util.List;


/**
 * See https://www.jianshu.com/p/1ba925157f7d
 */
public class JVMTest {

    static class EndException extends Throwable {
        public EndException(String message) {
            super(message);
        }
    }

    private static Integer hasSameNum(int[] arrIndex, List<List<Integer>> numberList) throws EndException {
        Integer maxNum = null;

        for (int idx = 0; idx < numberList.size(); ) {
            if (numberList.get(idx).size() <= arrIndex[idx]) {
                throw new EndException(String.format("list[%d] is end, %d <= %d", idx, numberList.get(idx).size(), arrIndex[idx]));
            }

            Integer cur = numberList.get(idx).get(arrIndex[idx]);
            if (cur == null) {
                idx++;
            } else if (maxNum == null) {
                maxNum = cur;
                arrIndex[idx]++;
            } else if (maxNum < cur) {
                maxNum = cur;
                idx = 0;
            } else if (maxNum > cur) {
                arrIndex[idx]++;
            } else {
                idx++;
            }
        }
        return maxNum;
    }

    private static List<Integer> sameNum(List<List<Integer>> numbersList) {
        List<Integer> response = new ArrayList<>();
        int[] arrIndex = new int[numbersList.size()];
        while (true) {
            try {
                response.add(hasSameNum(arrIndex, numbersList));
            } catch (EndException e) {
                return response;
            }
        }
    }

    public static void main(String[] args) {
        List<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        a.add(4);
        a.add(6);

        List<Integer> b = new ArrayList<>();
        b.add(1);
        b.add(2);
        b.add(4);
        b.add(6);

        List<Integer> c = new ArrayList<>();
        c.add(1);
        c.add(4);
        c.add(6);
        c.add(9);
        c.add(10);

        List<List<Integer>> data = new ArrayList<>();
        data.add(a);
        data.add(b);
        data.add(c);
        System.out.println(JVMTest.sameNum(data));
    }
}
