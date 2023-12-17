package com.leetcode.codereview.sort;

import java.util.Arrays;

public class HeBingQuJian {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals,(a,b)->a[0]-b[0]);
        int preL;
        int preR;
        for (int[] interval : intervals) {
            int l = interval[0];
            int r = interval[1];

        }
    }
}
