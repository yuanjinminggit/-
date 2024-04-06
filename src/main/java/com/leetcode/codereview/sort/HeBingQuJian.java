package com.leetcode.codereview.sort;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    static final int MOD = (int) 1e9 + 7;

    public int countWays(int[][] ranges) {
        Arrays.sort(ranges, (a, b) -> a[0] - b[0]);
        int n = ranges.length;
        int res = 1;
        for (int i = 0; i < n; ) {
            int r = ranges[i][1];
            int j = i + 1;
            while (j < n && ranges[j][0] <= r) {
                r = Math.max(r, ranges[j][1]);
                j++;
            }
            res = res * 2 % MOD;
            i = j;
        }
        return res;
    }

    public String finalString(String s) {
        char[] charArray = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] != 'i') {
                sb.append(charArray[i]);
            } else {
                sb.reverse();
            }
        }
        return sb.toString();
    }

    private void test1(List<Integer> list) {
        list = new ArrayList<>(list);
        list.add(2);
    }

    @Test
    public void test() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        test1(list);
        System.out.println(list);
    }
}
