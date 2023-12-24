package com.leetcode.codereview.sort;

import java.util.ArrayList;
import java.util.Arrays;

public class HeBingQuJian {
    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[0][2];
        }
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        ArrayList<int[]> merged = new ArrayList<>();
        for (int[] interval : intervals) {
            int l = interval[0];
            int r = interval[1];
            if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < l) {
                merged.add(new int[]{l, r});
            } else {
                merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], r);
            }
        }
        return merged.toArray(new int[merged.size()][]);
    }
}
