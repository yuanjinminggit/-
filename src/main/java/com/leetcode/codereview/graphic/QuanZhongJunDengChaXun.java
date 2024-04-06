package com.leetcode.codereview.graphic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class QuanZhongJunDengChaXun {
    int n;
    List<Integer[]>[] g;

    public int[] minOperationsQueries(int n, int[][] edges, int[][] queries) {
        this.n = n;
        build(edges);
        int m = queries.length;
        int[] res = new int[m];
        for (int i = 0; i < m; i++) {
            int[] query = queries[i];
            List<Integer> ws = getRoute(query);
            res[i] = getRes(ws);
        }
        return res;
    }

    private int getRes(List<Integer> list) {
        int mxCnt = -1;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (Integer x : list) {
            map.put(x, map.getOrDefault(x, 0) + 1);
            mxCnt = Math.max(mxCnt, map.get(x));
        }
        int res = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == mxCnt) {
                mxCnt = -1;
            } else {
                res += entry.getValue();
            }
        }
        return res;
    }

    private List<Integer> getRoute(int[] q) {
        int u = q[0];
        int v = q[1];
        int[] to = bfs(u, v);
        List<Integer> res = new ArrayList<>();
        for (int i = v; i != u; i = to[i]) {
            for (Integer[] nt : g[i]) {
                if (nt[0] == to[i]) {
                    res.add(nt[1]);
                    break;
                }
            }
        }
        return res;
    }

    private int[] bfs(int st, int en) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(st);
        int[] vis = new int[n], to = new int[n];
        vis[st] = 1;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            if (cur == en) {
                break;
            }
            for (Integer[] nt : g[cur]) {
                int y = nt[0];
                if (vis[y] == 1) continue;
                to[y] = cur;
                vis[y] = 1;
                queue.add(y);
            }
        }
        return to;
    }

    private void build(int[][] edges) {
        g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1], w = edge[2];
            g[x].add(new Integer[]{y, w});
            g[y].add(new Integer[]{x, w});
        }
    }

    public boolean isPossible(int[] nums) {
        HashMap<Integer, PriorityQueue<Integer>> map = new HashMap<>();
        for (int x : nums) {
            if (!map.containsKey(x)) {
                map.put(x, new PriorityQueue<>());
            }
            if (map.containsKey(x - 1)) {
                int prevLength = map.get(x - 1).poll();
                if (map.get(x - 1).isEmpty()) {
                    map.remove(x - 1);
                }
                map.get(x).offer(prevLength + 1);
            } else {
                map.get(x).offer(1);
            }
        }
        Set<Map.Entry<Integer, PriorityQueue<Integer>>> entrySet = map.entrySet();
        for (Map.Entry<Integer, PriorityQueue<Integer>> entry : entrySet) {
            PriorityQueue<Integer> heap = entry.getValue();
            if (heap.peek() < 3) {
                return false;
            }
        }
        return true;
    }

    public int countSquares(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int m = matrix.length, n = matrix[0].length;
        int[][] f = new int[m][n];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    f[i][j] = matrix[i][j];
                } else if (matrix[i][j] == 0) {
                    f[i][j] = 0;
                } else {
                    f[i][j] = Math.min(Math.min(f[i][j - 1], f[i - 1][j]), f[i - 1][j - 1]) + 1;
                }
                ans += f[i][j];
            }
        }
        return ans;
    }

}
