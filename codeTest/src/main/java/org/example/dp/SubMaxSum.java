package org.example.dp;

public class SubMaxSum {

    public static int subMaxSum(int[] data) throws Throwable {
        if (data == null || data.length <= 0) {
            throw new Throwable("less");
        }

        // 1. 初始
        int[] dp = new int[data.length];
        dp[0] = data[0];
        int max = dp[0];

        // 2. 状态转移 d[i] = max(d[i-1]+d[i], d[i])
        for (int idx = 1; idx < data.length; idx++) {
            int t1 = dp[idx - 1] + data[idx];
            dp[idx] = Math.max(t1, data[idx]);
            if (max < dp[idx]) {
                max = dp[idx];
            }
        }
        return max;
    }

    public static void main(String[] args) throws Throwable {
        System.out.println(subMaxSum(new int[]{3, -1, 8, -9, 2}));
//        System.out.println(subMaxSum(new int[]{}));
        System.out.println(subMaxSum(new int[]{3}));
    }
}
