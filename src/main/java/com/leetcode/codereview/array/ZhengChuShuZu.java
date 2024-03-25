package com.leetcode.codereview.array;

public class ZhengChuShuZu {
    public int[] divisibilityArray(String word, int m) {
        int n = word.length();
        int[] res = new int[n];
        long cur = 0L;
        char[] charArray = word.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            cur = (cur * 10 + charArray[i] - '0') % m;
            if (cur % m == 0) {
                res[i] = 1;
            } else {
                res[i] = 0;
            }
        }
        return res;
    }

}
