package com.leetcode.codereview.backtrack;

import java.util.*;

public class Dancisousuo {

    Set<String> set = new HashSet<>();
    List<String> ans = new ArrayList<String>();
    char[][] board;
    private int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    int n, m;
    boolean[][] vis = new boolean[15][15];

    public List<String> findWords(char[][] board, String[] words) {
        this.board = board;
        m = board.length;
        n = board[0].length;
        for (String word : words) {
            set.add(word);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                vis[i][j] = true;
                sb.append(board[i][j]);
                dfs(i, j, sb);
                vis[i][j] = false;
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        return ans;
    }

    private void dfs(int i, int j, StringBuilder sb) {
        if (sb.length() > 10) return;
        if (set.contains(sb.toString())) {
            ans.add(sb.toString());
            set.remove(sb.toString());
        }
        for (int[] d : directions) {
            int dx = i + d[0], dy = j + d[1];
            if (dx < 0 || dx >= m || dy < 0 || dy >= n) {
                continue;
            }
            if (vis[dx][dy]) continue;
            vis[dx][dy] = true;
            sb.append(board[dx][dy]);
            dfs(dx, dy, sb);
            vis[dx][dy] = false;
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
