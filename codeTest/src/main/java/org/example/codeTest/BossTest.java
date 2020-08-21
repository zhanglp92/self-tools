package org.example.codeTest;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BossTest {

    static class NotNum extends Exception {
        public NotNum(String message) {
            super(message);
        }
    }

    static class MoreInt extends Exception {
        public MoreInt(String message) {
            super(message);
        }
    }

    private static int parseInt(byte c) throws NotNum {
        if (c < '0' || c > '9') {
            throw new NotNum(String.format("%c", c));
        }
        return c - '0';
    }

    public static int parseInt(String str) throws NotNum {
        if (str == null || str.isEmpty()) {
            throw new NotNum(String.format("not number %s", str));
        }

        int pos = 0, singed = 1, res = 0;
        byte[] arr = str.getBytes();

        // 处理符号
        if (arr[pos] == '-') {
            singed = -1;
            pos++;
        } else if (arr[pos] == '+') {
            pos++;
        }

        for (int idx = pos; idx < arr.length; idx++) {
            // 判断是否超出int最大/小 MoreInt
            res = res * 10 + parseInt(arr[idx]);
        }
        return res * singed;
    }


    public static void main(String[] args) {
        String str = null;
        try {
            str = "10";
            log.info("res = {}", parseInt(str));
        } catch (NotNum e) {
            log.info("source = {}, e=", str, e);
        }

        try {
            str = "10x";
            log.info("res = {}", parseInt(str));
        } catch (NotNum e) {
            log.info("source = {}, e=", str, e);
        }

        try {
            str = "-124534";
            log.info("res = {}", parseInt(str));
        } catch (NotNum e) {
            log.info("source = {}, e=", str, e);
        }

        try {
            str = null;
            log.info("res = {}", parseInt(str));
        } catch (NotNum e) {
            log.info("source = {}, e=", str, e);
        }

        try {
            str = "";
            log.info("res = {}", parseInt(str));
        } catch (NotNum e) {
            log.info("source = {}, e=", str, e);
        }
    }
}



/*

1.
hive表结构为
  CREATE TABLE db.action (
date_time STRING COMMENT ‘时间’,
uid INT COMMENT ‘用户id’,
action STRING COMMENT ‘行为’
) PARTITIONED BY (
  ds STRING,
  hour STRING
) COMMENT ‘用户的行为表’
ds格式为”2020-01-01“
统计2020-01-01当天，有’add’行为大于2次的用户，各个尾号的数量统计

  select num, count from (
        distinct 取UID尾号 as num from db.action where date_time = '2020-01-01' and action = 'add'
  ) where count > 1;

 */