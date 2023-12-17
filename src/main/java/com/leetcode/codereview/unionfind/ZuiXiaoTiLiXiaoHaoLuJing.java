package com.leetcode.codereview.unionfind;

import java.util.ArrayList;
import java.util.Arrays;

public class ZuiXiaoTiLiXiaoHaoLuJing {

    public int minimumEffortPath(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;
        ArrayList<int[]> edges = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int id = i * n + j;
                if (i > 0) {
                    edges.add(new int[]{id - n, id, Math.abs(heights[i][j] - heights[i - 1][j])});
                }
                if (j > 0) {
                    edges.add(new int[]{id - 1, id, Math.abs(heights[i][j] - heights[i][j - 1])});
                }
            }
        }
        edges.sort((edg1, edg2) -> edg1[2] - edg2[2]);
        UnionFind uf = new UnionFind(m * n);
        int ans = 0;
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1], v = edge[2];
            uf.unite(x, y);
            if (uf.connected(0, m * n - 1)){
                ans = v;
                break;
            }
        }
        return ans;
    }

    static class UnionFind {
        int[] parent;
        int[] size;
        int n;
        int setCount;

        public UnionFind(int n) {
            this.n = n;
            this.setCount = n;
            this.parent = new int[n];
            this.size = new int[n];
            Arrays.fill(size, 1);
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int findSet(int x) {

            if (x == parent[x]) {
                return x;
            }
            parent[x] = findSet(parent[x]);
            return parent[x];
        }

        public void unite(int x, int y) {
            x = findSet(x);
            y = findSet(y);
            if (x == y) {
                return ;
            }
            if (size[x] < size[y]) {
                unite(y, x);
                return ;
            }
            parent[y] = x;
            size[x] += size[y];
            --setCount;
        }

        public boolean connected(int x, int y) {
            x = findSet(x);
            y = findSet(y);
            return x == y;
        }
    }
}
