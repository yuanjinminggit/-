package com.leetcode.codereview.graphic;

import com.leetcode.codereview.simpleConstruct.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DaoDaShouDuDeZuiXiaoYouHao {
    public long minimumFuelCost(int[][] roads, int seats) {
        int n = roads.length + 1;
        ArrayList<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] e : roads) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        dfs(0, -1, g, seats);
        return ans;
    }

    private long ans;

    private int dfs(int x, int fa, ArrayList<Integer>[] g, int seats) {
        int size = 1;
        for (Integer y : g[x]) {
            if (y != fa) {
                size += dfs(y, x, g, seats);
            }
        }
        if (x > 0) {
            ans += (size - 1) / seats + 1;
        }
        return size;
    }

    private int res;

    public int distributeCoins(TreeNode root) {
        dfs(root);
        return res;
    }

    private int[] dfs(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0};
        }
        int[] left = dfs(node.left);
        int[] right = dfs(node.right);
        int coins = left[0] + right[0] + node.val;
        int nodes = left[1] + right[1] + 1;
        res += Math.abs(coins - nodes);
        return new int[]{coins, nodes};
    }

    private List<Integer>[] g;
    private int[] price, cnt;
    private int end;

    public int minimumTotalPrice(int n, int[][] edges, int[] price, int[][] trips) {
        g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        cnt = new int[n];
        for (int[] t : trips) {
            end = t[1];
            dfs(t[0], -1);
        }
        this.price = price;
        int[] res = dp(0, -1);
        return Math.min(res[0], res[1]);
    }

    private int[] dp(int x, int fa) {
        int notHalve = price[x] * cnt[x];
        int halve = notHalve / 2;
        for (Integer y : g[x]) {
            if (y != fa) {
                int[] res = dp(y, x);
                notHalve += Math.min(res[0], res[1]);
                halve += res[0];
            }
        }
        return new int[]{notHalve, halve};
    }

    private boolean dfs(int x, int fa) {
        if (x == end) {
            cnt[x]++;
            return true;
        }
        for (Integer y : g[x]) {
            if (y != fa && dfs(y, x)) {
                cnt[x]++;
                return true;
            }
        }
        return false;
    }

    public int minReorder(int n, int[][] connections) {
        ArrayList<int[]>[] e = new ArrayList[n];
        Arrays.setAll(e, a -> new ArrayList<int[]>());
        for (int[] edge : connections) {
            e[edge[0]].add(new int[]{edge[1], 1});
            e[edge[1]].add(new int[]{edge[0], 0});
        }
        return dfs(0, -1, e);
    }

    private int dfs(int x, int parent, ArrayList<int[]>[] e) {
        int res = 0;
        for (int[] edge : e[x]) {
            if (edge[0] == parent) {
                continue;
            }
            res += edge[1] + dfs(edge[0], x, e);
        }
        return res;
    }


}
