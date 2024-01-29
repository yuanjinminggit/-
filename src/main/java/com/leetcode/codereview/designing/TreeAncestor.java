package com.leetcode.codereview.designing;

class TreeAncestor {
    private int[][] pa;

    public TreeAncestor(int n, int[] parent) {
        int m = 32 - Integer.numberOfLeadingZeros(n);
        pa = new int[n][m];
        for (int i = 0; i < n; i++) {
            pa[i][0] = parent[i];
        }
        for (int i = 0; i < m - 1; i++) {
            for (int x = 0; x < n; x++) {
                int p = pa[x][i];
                pa[x][i + 1] = p == -1 ? -1 : pa[p][i];
            }
        }

    }

    public int getKthAncestor(int node, int k) {
        int m = 32 - Integer.numberOfLeadingZeros(k);
        for (int i = 0; i < m; i++) {
            if (((k >> i) & 1) > 0) {
                node = pa[node][i];
                if (node < 0) {
                    break;
                }
            }
        }
        return node;
    }
}