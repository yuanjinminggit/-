package com.leetcode.codereview.graphic;

import java.util.Arrays;

public class SmallestWay {

    public int networkDelayTime(int[][] times, int n, int k) {
        int[][] dis = floyd(times, n);
        int max = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            max = Math.max(max, dis[k][i]);
        }
        return max == Integer.MAX_VALUE / 2 ? -1 : max;
    }

    int[][] floyd(int[][] edges, int n) {
        int[][] dis = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            Arrays.fill(dis[i], Integer.MAX_VALUE / 2);
        }
        for (int[] edge : edges) {
            dis[edge[0]][edge[1]] = edge[2];
        }
        for (int i = 1; i <= n; i++) {
            dis[i][i] = 0;
        }
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    dis[i][j] = Math.min(dis[i][j], dis[i][k] + dis[k][j]);
                }
            }
        }
        return dis;
    }
}
