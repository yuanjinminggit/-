package com.leetcode.codereview.memoizeddfs;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Pisa {
    private int[] slices;


    public int maxSizeSlices(int[] slices) {
        this.slices = slices;
        int n = slices.length / 3;
        mem = new int[3 * n][n];
        for (int i = 0; i < 3 * n; i++) {
            Arrays.fill(mem[i], -1);
        }
        int first = slices[0];
        slices[0] = 0;
        int val1 = dfsmaxSizeSlices(3 * n - 1, n - 1);
        for (int i = 0; i < 3 * n; i++) {
            Arrays.fill(mem[i], -1);
        }
        slices[0] = first;
        slices[3 * n - 1] = 0;
        int val2 = dfsmaxSizeSlices(3 * n - 1, n - 1);
        return Math.max(val1, val2);
    }

    private int dfsmaxSizeSlices(int i, int j) {
        if (i < 0 || j < 0) {
            return 0;
        }
        if (mem[i][j] != -1) {
            return mem[i][j];
        }
        return mem[i][j] = Math.max(dfsmaxSizeSlices(i - 2, j - 1) + slices[i], dfsmaxSizeSlices(i - 1, j));
    }

    private final int MOD = (int) 1e9 + 7;
    private String[] pizza;
    private int m;
    private int n;
    private int[][][] cache;
    private MatixSum ms;

    public int ways(String[] pizza, int k) {
        m = pizza.length;
        n = pizza[0].length();
        this.pizza = pizza;
        cache = new int[k][m][n];
        ms = new MatixSum(pizza);
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < m; j++) {
                Arrays.fill(cache[i][j], -1);
            }
        }
        return dfsways(k - 1, 0, 0);
    }

    // 以(i，j)为左上角（m-1,n-1）为右下角的矩形，切割k次合理的方案数
    private int dfsways(int k, int i, int j) {
        if (k == 0) {
            return check(i, j, m, n) ? 1 : 0;
        }
        if (cache[k][i][j] != -1) {
            return cache[k][i][j];
        }
        int res = 0;
        for (int i2 = i + 1; i2 < m; i2++) {
            if (check(i, j, i2, n)) {
                res = (res + dfsways(k - 1, i2, j)) % MOD;
            }
        }
        for (int j2 = j + 1; j2 < n; j2++) {
            if (check(i, j, m, j2)) {
                res = (res + dfsways(k - 1, i, j2)) % MOD;
            }
        }
        return cache[k][i][j] = res;

    }

    private boolean check(int sx, int sy, int ex, int ey) {
//        for (int i = sx; i < ex; i++) {
//            for (int j = sy; j < ey; j++) {
//                if (pizza[i].charAt(j) == 'A') {
//                    return true;
//                }
//            }
//        }
//        return false;
        return ms.query(sx, sy, ex, ey) > 0;
    }

    class MatixSum {
        int[][] sum;

        public MatixSum(String[] pizza) {
            int m = pizza.length;
            int n = pizza[0].length();
            sum = new int[m + 1][n + 1];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    sum[i + 1][j + 1] = sum[i + 1][j] + sum[i][j + 1] - sum[i][j] + (pizza[i].charAt(j) == 'A' ? 1 : 0);
                }
            }
        }

        // 左臂右开
        public int query(int sx, int sy, int ex, int ey) {
            return sum[ex][ey] - sum[ex][sy] - sum[sx][ey] + sum[sx][sy];
        }
    }

    private int[] maxmem;
    private int[] minmem;

    public int maxAbsoluteSum1(int[] nums) {
        int n = nums.length;
        int max = Integer.MIN_VALUE;
        maxmem = new int[n];
        Arrays.fill(maxmem, Integer.MIN_VALUE / 2);
        for (int i = 0; i < n; i++) {
            int num = dfsmax(nums, i);
            max = Math.max(max, Math.abs(num));
        }
        minmem = new int[n];
        Arrays.fill(minmem, Integer.MAX_VALUE / 2);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int num = dfsmin(nums, i);
            min = Math.min(min, num);
        }
        return Math.max(max, -min);
    }

    private int dfsmin(int[] nums, int idx) {
        if (idx < 0) {
            return 0;
        }
        if (minmem[idx] != Integer.MAX_VALUE / 2) {
            return minmem[idx];
        }
        return minmem[idx] = Math.min(dfsmin(nums, idx - 1) + nums[idx], nums[idx]);
    }


    private int dfsmax(int[] nums, int idx) {
        if (idx < 0) {
            return 0;
        }
        if (maxmem[idx] != Integer.MIN_VALUE / 2) {
            return maxmem[idx];
        }
        return maxmem[idx] = Math.max(dfsmax(nums, idx - 1) + nums[idx], nums[idx]);
    }


    public int maxAbsoluteSum(int[] nums) {
        int s = 0, mx = 0, mn = 0;
        for (int num : nums) {
            s += num;
            if (s > mx) {
                mx = s;
            } else if (s < mn) {
                mn = s;
            }
        }
        return mx - mn;
    }

    public int maxSubArray(int[] nums) {
        int s = 0;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            s += num;
            max = Math.max(max, s - min);
            if (min > s) {
                min = s;
            }
        }
        return max;
    }

    public int minPathCost(int[][] grid, int[][] moveCost) {
        int ans = Integer.MAX_VALUE;
        mem = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            Arrays.fill(mem[i], -1);
        }
        for (int j = 0; j < grid[0].length; j++) {
            ans = Math.min(ans, dfsminPathCost(0, j, grid, moveCost));
        }
        return ans;
    }

    private int[][] mem;

    private int dfsminPathCost(int i, int j, int[][] grid, int[][] moveCost) {
        if (i == grid.length - 1) {
            return grid[i][j];
        }
        if (mem[i][j] != -1) {
            return mem[i][j];
        }
        int res = Integer.MAX_VALUE;
        for (int k = 0; k < grid[0].length; k++) {
            res = Math.min(res, dfsminPathCost(i + 1, k, grid, moveCost) + moveCost[grid[i][j]][k]);
        }
        return mem[i][j] = res + grid[i][j];
    }

    private String s;
    private int[] dp;

    public boolean wordBreak1(String s, List<String> wordDict) {
        this.s = s;
        HashSet<String> set = new HashSet<>(wordDict);
        dp = new int[s.length()];
        return dfswordBreak(s, set, 0);
    }


    private boolean dfswordBreak(String s, HashSet<String> set, int i) {
        if (i >= s.length()) {
            return true;
        }
        if (dp[i] != 0) {
            return dp[i] == 1;
        }
        for (int j = i; j < s.length(); j++) {
            String substring = s.substring(i, j + 1);
            if (set.contains(substring)) {
                if (dfswordBreak(s, set, j + 1)) {
                    dp[i] = 1;
                    return true;
                }
            }
        }
        dp[i] = -1;
        return false;
    }


    public List<String> wordBreak(String s, List<String> wordDict) {
        ArrayList<String> ans = new ArrayList<>();
        HashSet<String> set = new HashSet<>(wordDict);
        ArrayList<String> tmp = new ArrayList<>();
        dfs(s, set, 0, ans, tmp);
        return ans;
    }

    private void dfs(String s, HashSet<String> set, int i, ArrayList<String> ans, ArrayList<String> tmp) {
        if (i >= s.length()) {
            StringBuilder sb = new StringBuilder();
            for (String s1 : tmp) {
                sb.append(s1 + " ");
            }
            ans.add(sb.toString().trim());
            return;
        }

        for (int j = i; j < s.length(); j++) {
            String substring = s.substring(i, j + 1);
            if (set.contains(substring)) {
                tmp.add(substring);
                dfs(s, set, j + 1, ans, tmp);
                tmp.remove(tmp.size() - 1);
            }
        }
    }


    @Test
    public void test(){
        System.out.println("sdufsdh");
    }
}
