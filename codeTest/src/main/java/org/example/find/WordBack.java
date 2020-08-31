package org.example.find;

import lombok.extern.log4j.Log4j2;

/**
 * 回文串
 */

@Log4j2
public class WordBack {

    private static int getPreIdx(String str, int end) {
        for (; end >= 0; end--) {
            if (Character.isDigit(str.charAt(end)) || Character.isLetter(str.charAt(end))) {
                return end;
            }
        }
        return end;
    }


    private static int getNextIdx(String str, int start) {
        for (; start < str.length(); start++) {
            if (Character.isDigit(str.charAt(start)) || Character.isLetter(str.charAt(start))) {
                return start;
            }
        }
        return start;
    }

    /**
     * 只检查字母数字
     */
    public static boolean isWordBack(String str) {
        int s = 0, e = str.length() - 1;
        while (true) {
            s = getNextIdx(str, ++s);
            e = getPreIdx(str, --e);
            if (s >= e) {
                return true;
            }
            if (str.charAt(s) != str.charAt(e)) {
                return false;
            }
        }
    }

    public static void main(String[] args) {
        String str;

        str = "";
        log.info("str = {}, wordBack = {}", str, isWordBack(str));

        str = "qwertyuiopoiuytrewq";
        log.info("str = {}, wordBack = {}", str, isWordBack(str));

        str = "11234554321";
        log.info("str = {}, wordBack = {}", str, isWordBack(str));

        str = "123#%*4554    321";
        log.info("str = {}, wordBack = {}", str, isWordBack(str));
    }
}
