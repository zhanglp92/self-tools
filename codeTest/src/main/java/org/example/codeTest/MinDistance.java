package org.example.codeTest;

import lombok.extern.log4j.Log4j2;
import org.example.data.GenData;

/**
 * 最短距离
 */
@Log4j2
public class MinDistance {

    static private class DpNode {
        int val;
        // ↑↓←→
        String pre = ".";

        public DpNode(int val) {
            this.val = val;
        }

        public DpNode(int val, String pre) {
            this.val = val;
            this.pre = pre;
        }
    }

    public static String sprint(DpNode[][] arr) {
        StringBuilder builder = new StringBuilder();
        for (DpNode[] ints : arr) {
            StringBuilder tmp = new StringBuilder();
            for (DpNode node : ints) {
                builder.append(String.format(" %s ", node.pre));
                tmp.append(String.format("%3d", node.val));
            }
            builder.append("\t").append(tmp.toString()).append("\n");
        }
        return builder.toString();
    }

    public static int dp(Integer[][] distance) {
        int row = distance.length, col = distance[0].length;
        DpNode[][] dp = new DpNode[row][];
        for (int idx = 0; idx < row; idx++) {
            dp[idx] = new DpNode[col];
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = new DpNode(distance[i][j]);
                } else if (i == 0) {
                    dp[i][j] = new DpNode(dp[i][j - 1].val + distance[i][j], "→");
                } else if (j == 0) {
                    dp[i][j] = new DpNode(dp[i - 1][j].val + distance[i][j], "↓");
                } else {
                    if (dp[i][j - 1].val < dp[i - 1][j].val) {
                        dp[i][j] = new DpNode(dp[i][j - 1].val + distance[i][j], "→");
                    } else {
                        dp[i][j] = new DpNode(dp[i - 1][j].val + distance[i][j], "↓");
                    }
                }
            }
        }

        log.info("dp = \n{}\n", sprint(dp));
        return dp[row - 1][col - 1].val;
    }

    public static void main(String[] args) {
        Integer[][] data = GenData.genIntArr(3, 3);
        log.info("data = \n{}\n,  distance = {}", GenData.sprint(data), dp(data));

        /*
         *  .  →  → 	 11 25 35
         *  ↓  →  → 	 18 25 44
         *  ↓  →  → 	 19 25 42
         * */
    }
}
