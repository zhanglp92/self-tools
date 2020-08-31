package org.example.dp;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JumpStep {

    /**
     * 求n个台阶共有多少种走法, 每次1/2步
     */
    public static int countWay(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return countWay(n - 1) + countWay(n - 2);
    }

    public static void main(String[] args) {
        log.info("step count = {}", countWay(6));
    }
}
