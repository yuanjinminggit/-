package com.leetcode.codereview.memoizeddfs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class ZuiXiaoTiLiXiaoHaoLuJing {

    private int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int minimumEffortPath(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;
        boolean[][] vis = new boolean[m][n];
        dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dp[i], -1);
        }
        return dfsminimumEffortPath(heights, vis, m, n, 0, 0);
    }

    private int[][] dp;

    private int dfsminimumEffortPath(int[][] heights, boolean[][] vis, int m, int n, int x, int y) {
        if (x == m - 1 && y == n - 1) {
            return 0;
        }
        if (dp[x][y] != -1) {
            return dp[x][y];
        }
        vis[x][y] = true;
        int min = Integer.MAX_VALUE;
        for (int[] direction : directions) {
            int newx = x + direction[0];
            int newy = y + direction[1];
            if (newx >= 0 && newx < m && newy >= 0 && newy < n && !vis[newx][newy]) {
                int maxlen = Math.max(dfsminimumEffortPath(heights, vis, m, n, newx, newy), Math.abs(heights[x][y] - heights[newx][newy]));
                min = Math.min(min, maxlen);
            }
        }
        vis[x][y] = false;
        return dp[x][y] = min;
    }

    public int minCostClimbingStairs1(int[] cost) {
        int len = cost.length;
        mem = new int[len];
        Arrays.fill(mem, -1);
        return Math.min(dfsminCostClimbingStairs(cost, 0), dfsminCostClimbingStairs(cost, 1));
    }

    private int dfsminCostClimbingStairs(int[] cost, int idx) {
        if (idx >= cost.length) {
            return 0;
        }
        if (mem[idx] != -1) {
            return mem[idx];
        }
        return mem[idx] = Math.min(dfsminCostClimbingStairs(cost, idx + 1), dfsminCostClimbingStairs(cost, idx + 2)) + cost[idx];
    }

    private int mem[];

    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        mem = new int[n + 1];
        Arrays.fill(mem, -1);
        return dfsminCost(n, cost);
    }

    private int dfsminCost(int i, int[] cost) {
        if (i <= 1) {
            return 0;
        }
        if (mem[i] != -1) {
            return mem[i];
        }
        return mem[i] = Math.min(dfsminCost(i - 1, cost) + cost[i - 1], dfsminCost(i - 2, cost) + cost[i - 2]);
    }

    public int minStoneSum(int[] piles, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int pile : piles) {
            heap.offer(pile);
        }
        for (int i = 0; i < k; i++) {
            Integer poll = heap.poll();
            heap.offer((poll + 1) / 2);
        }
        int sum = 0;
        while (!heap.isEmpty()) {
            sum += heap.poll();
        }
        return sum;
    }
}
