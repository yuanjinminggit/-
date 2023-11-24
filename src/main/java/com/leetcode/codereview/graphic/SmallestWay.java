package com.leetcode.codereview.graphic;

import java.util.*;

public class SmallestWay {

    public int networkDelayTime(int[][] times, int n, int k) {
//        int[][] dis = floyd(times, n);
//        int max = Integer.MIN_VALUE;
//        for (int i = 1; i <= n; i++) {
//            max = Math.max(max, dis[k][i]);
//        }
//        return max == Integer.MAX_VALUE / 2 ? -1 : max;

//        int[] dis = spfa(times, n, k);
//        if (dis == null) {
//            return -1;
//        }
//        int res = 0;
//        for (int i = 1; i <= n; i++) {
//            res = Math.max(res, dis[i]);
//        }
//        return res == Integer.MAX_VALUE / 2 ? -1 : res;
        return dijkstra(times, n, k);
    }

    private int[][] floyd(int[][] edges, int n) {
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

    // 松弛： 缩短两个点之间的距离（更新出最短路）
    // 每一次循环，对图上的所有边进行一次松弛操作，当一次循环中，没有进行松弛操作时，终止循环
    // 每一次至少确定一段最短路，最多有n-1路径构成最短路

    // 判断是否存在负环
    // 会无休止进行松弛，当循环第n次，依然可以进行松弛，此时途中存在负环
    private int[] bellmanFord(int[][] edges, int n, int s) {
        int[] dis = new int[n + 1];
        Arrays.fill(dis, Integer.MAX_VALUE / 2);
        dis[s] = 0;
        for (int i = 1; i <= n; i++) {
            for (int[] e : edges) {
                int u = e[0], v = e[1], w = e[2];
                if (dis[u] + w < dis[v]) {
                    dis[v] = dis[u] + w;
                }
            }
        }
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            if (dis[u] + w < dis[v]) {
                return null;
            }
        }
        return dis;
    }

    // bellmanford 存在大量无用的松弛曹锁，只需要取松弛上一次被松弛的点所连的边
    private int[] spfa(int[][] edges, int n, int s) {
        List<int[]>[] g = new ArrayList[n + 1];
        Arrays.setAll(g, v -> new ArrayList<>());
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            g[u].add(new int[]{v, w});
        }
        int[] dis = new int[n + 1];
        Arrays.fill(dis, Integer.MAX_VALUE / 2);
        dis[s] = 0;
        boolean[] inQueue = new boolean[n + 1];
        int[] cnt = new int[n + 1];
        Queue<Integer> q = new LinkedList<>();
        q.offer(s);
        inQueue[s] = true;
        cnt[s]++;
        while (!q.isEmpty()) {
            Integer u = q.poll();
            inQueue[u] = false;
            for (int[] x : g[u]) {
                int v = x[0], w = x[1];
                if (dis[u] + w < dis[v]) {
                    dis[v] = dis[u] + w;
                    if (!inQueue[v]) {
                        q.offer(v);
                        inQueue[v] = true;
                        cnt[v]++;
                        if (cnt[v] > n) {
                            return null;
                        }
                    }
                }
            }
        }
        return dis;
    }

    // 贪心策略

    private int dijkstra(int[][] edges, int n, int s) {
        int[][] g = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            Arrays.fill(g[i], Integer.MAX_VALUE / 2);
        }
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            g[u][v] = w;
        }
        int[] dis = new int[n + 1];
        Arrays.fill(dis, Integer.MAX_VALUE / 2);
        dis[s] = 0;
        boolean[] vis = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            int u = -1;
            for (int j = 1; j <= n; j++) {
                if (!vis[j] && (u == -1 || dis[j] < dis[u])) {
                    u = j;
                }
            }
            vis[u] = true;
            for (int v = 1; v <= n; v++) {
                dis[v] = Math.min(dis[v], dis[u] + g[u][v]);
            }
        }

        int res = 0;
        for (int i = 1; i <= n; i++) {
            res = Math.max(res, dis[i]);
        }
        return res == Integer.MAX_VALUE / 2 ? -1 : res;
    }

    private int[][][] memo;

    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] w = new int[n][n];
        for (int[] row : w) {
            Arrays.fill(row, Integer.MAX_VALUE / 2);
        }
        for (int[] e : edges) {
            int x = e[0], y = e[1], wt = e[2];
            w[x][y] = w[y][x] = wt;
        }
        memo = new int[n][n][n];
        int ans = 0;
        int minCnt = n;
        for (int i = 0; i < n; i++) {
            int cnt = 0;
            for (int j = 0; j < n; j++) {
                if (i != j && dfs(n - 1, i, j, w) <= distanceThreshold) {
                    cnt++;
                }
            }
            if (cnt <= minCnt) {
                minCnt = cnt;
                ans = i;
            }
        }
        return ans;
    }

    private int dfs(int k, int i, int j, int[][] w) {
        if (k < 0) {
            return w[i][j];
        }
        if (memo[k][i][j] != 0) {
            return memo[k][i][j];
        }
        return memo[k][i][j] = Math.min(dfs(k - 1, i, j, w), dfs(k - 1, i, k, w) + dfs(k - 1, k, j, w));
    }


    private int m;
    private int n;

    public int maximumMinutes(int[][] grid) {
        m = grid.length;
        n = grid[0].length;
        this.grid = grid;
        List<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0});
        int[] maxValue = bfs(queue);
        int manToTarget = maxValue[0], manToleft = maxValue[1], mantoUp = maxValue[2];
        if (manToTarget < 0) return -1;
        ArrayList<int[]> fire = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    fire.add(new int[]{i, j});
                }
            }
        }
        int[] fireValue = bfs(fire);
        int fireToTarget = fireValue[0], fireToleft = fireValue[1], firetoUp = fireValue[2];
        if (fireToTarget < 0) return (int) 1e9;
        int res = fireToTarget - manToTarget;
        if (res < 0) return -1;
        if (manToleft + res < fireToleft || mantoUp + res < firetoUp) return res;
        return res - 1;
    }


    private int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private int[][] grid;

    private int[] bfs(List<int[]> st) {
        int[][] time = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(time[i], -1);
        }

        LinkedList<int[]> queue = new LinkedList<>();
        for (int[] arr : st) {
            int x = arr[0], y = arr[1];
            time[x][y] = 0;
            queue.offer(new int[]{x, y});
        }

        for (int t = 1; queue.size() > 0; ++t) {
            int sz = queue.size();
            while (sz-- > 0) {
                int[] cur = queue.poll();
                int x = cur[0], y = cur[1];
                for (int[] direction : directions) {
                    int nx = x + direction[0];
                    int ny = y + direction[1];
                    if (nx < 0 || nx >= m || ny < 0 || ny >= n || time[nx][ny] >= 0 || grid[nx][ny] > 0) {
                        continue;
                    }
                    time[nx][ny] = t;
                    queue.offer(new int[]{nx, ny});
                }
            }
        }
        return new int[]{time[m - 1][n - 1], time[m - 1][n - 2], time[m - 2][n - 1]};
    }


}