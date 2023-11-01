package com.leetcode.codereview.greedy;

import java.util.Arrays;

public class Qiegedangao {
    public int maxArea(int h, int w, int[] ho, int[] ve) {
        int MOD = (int) 1e9 + 7;
        Arrays.sort(ho);
        Arrays.sort(ve);
        long maxH = getMax(h, ho);
        long maxW = getMax(w, ve);
        return (int) (maxH * maxW % MOD);
    }

    private long getMax(int h, int[] ho) {
        long maxH = 0;
        for (int i = 1; i < ho.length; i++) {
            maxH = Math.max(ho[i] - ho[i - 1], maxH);
        }
        maxH = Math.max(maxH, ho[0]);
        maxH = Math.max(maxH, h - ho[ho.length - 1]);
        return maxH;
    }
}
