package com.leetcode.codereview.graphic;

import java.util.*;

public class Dancijielong {

    public int ladderLength1(String beginWord, String endWord, List<String> wordList) {
        HashSet<String> wordSet = new HashSet<>(wordList);
        if (wordSet.size() == 0 || !wordSet.contains(endWord)) {
            return 0;
        }
        wordSet.remove(beginWord);

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        HashSet<String> visited = new HashSet<>();
        visited.add(beginWord);

        int step = 1;

        while (!queue.isEmpty()) {
            int currentSize = queue.size();
            for (int i = 0; i < currentSize; i++) {
                String currentWord = queue.poll();
                if (changeWordEveryOneLetter(currentWord, endWord, queue, visited, wordSet)) {
                    return step + 1;
                }
            }
            step++;
        }
        return 0;
    }


    private boolean changeWordEveryOneLetter(String currentWord, String endWord, Queue<String> queue, HashSet<String> visited, HashSet<String> wordSet) {
        char[] charArray = currentWord.toCharArray();
        for (int i = 0; i < endWord.length(); i++) {
            char originalChar = charArray[i];
            for (char k = 'a'; k <= 'z'; k++) {
                if (k == originalChar) {
                    continue;
                }
                charArray[i] = k;
                String nextWord = String.valueOf(charArray);
                if (wordSet.contains(nextWord)) {
                    if (nextWord.equals(endWord)) {
                        return true;
                    }
                    if (!visited.contains(nextWord)) {
                        queue.add(nextWord);
                        visited.add(nextWord);
                    }
                }
            }
            charArray[i] = originalChar;
        }
        return false;
    }


    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        HashSet<String> wordSet = new HashSet<>(wordList);
        if (wordSet.size() == 0 || !wordSet.contains(endWord)) {
            return 0;
        }
        HashSet<String> visited = new HashSet<>();
        HashSet<String> beginVisited = new HashSet<>();
        beginVisited.add(beginWord);
        HashSet<String> endVisited = new HashSet<>();
        endVisited.add(endWord);
        int step = 1;
        while (!beginVisited.isEmpty() && !endVisited.isEmpty()) {
            if (beginVisited.size() > endVisited.size()) {
                HashSet<String> temp = beginVisited;
                beginVisited = endVisited;
                endVisited = temp;
            }
            HashSet<String> nextLevelVisited = new HashSet<>();

            for (String word : beginVisited) {
                if (changeWordEveryOneLetter(word, endVisited, visited, wordSet, nextLevelVisited)) {
                    return step + 1;
                }
            }
            beginVisited = nextLevelVisited;
            step++;
        }
        return 0;
    }

    private boolean changeWordEveryOneLetter(String word, HashSet<String> endVisited, HashSet<String> visited, HashSet<String> wordSet, HashSet<String> nextLevelVisited) {
        char[] charArray = word.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            char originalChar = charArray[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (originalChar == c) {
                    continue;
                }
                charArray[i] = c;
                String nextWord = String.valueOf(charArray);
                if (wordSet.contains(nextWord)) {
                    if (endVisited.contains(nextWord)) {
                        return true;
                    }
                    if (!visited.contains(nextWord)) {
                        nextLevelVisited.add(nextWord);
                        visited.add(nextWord);
                    }
                }
            }
            charArray[i] = originalChar;
        }
        return false;
    }

    public int maximumInvitations(int[] favorite) {
        int n = favorite.length;
        int[] degree = new int[n];
        for (int f : favorite) {
            degree[f]++;
        }
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (degree[i] == 0) {
                queue.offer(i);
            }
        }
        ArrayList<Integer>[] rg = new ArrayList[n];
        Arrays.setAll(rg, g -> new ArrayList());
        while (!queue.isEmpty()) {
            Integer x = queue.poll();
            int y = favorite[x];
            rg[y].add(x);
            if (--degree[y] == 0) {
                queue.offer(y);
            }
        }
        int maxCircle = 0, maxChain = 0;
        for (int i = 0; i < n; i++) {
            if (degree[i] == 0) {
                continue;
            }
            degree[i] = 0;
            int circleSz = 1;
            for (int x = favorite[i]; x != i; x = favorite[x]) {
                degree[x] = 0;
                ++circleSz;
            }
            if (circleSz == 2) {
                maxChain += dfs(i, rg) + dfs(favorite[i], rg);
            } else {
                maxCircle = Math.max(maxCircle, circleSz);
            }
        }
        return Math.max(maxChain, maxCircle);
    }

    private int dfs(int x, ArrayList<Integer>[] rg) {
        int maxDepth = 1;
        for (Integer y : rg[x]) {
            maxDepth = Math.max(maxDepth, dfs(y, rg) + 1);
        }
        return maxDepth;
    }

    public int collectTheCoins(int[] coins, int[][] edges) {
        int n = coins.length, m = n - 1;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, v -> new ArrayList<>());
        int[] degree = new int[n];
        for (int[] edge : edges) {
            int x = edge[0];
            int y = edge[1];
            g[x].add(y);
            g[y].add(x);
            degree[x]++;
            degree[y]++;
        }
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (degree[i] == 1 && coins[i] == 0) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            Integer x = queue.poll();
            --m;
            for (Integer y : g[x]) {
                if (--degree[y] == 1 && coins[y] == 0) {
                    queue.offer(y);
                }
            }
        }


        for (int i = 0; i < n; i++) {
            if (degree[i] == 1 && coins[i] == 1) {
                queue.offer(i);
            }
        }
        m -= queue.size();
        while (queue.size() > 0) {
            Integer x = queue.poll();
            for (Integer y : g[x]) {
                if (--degree[y] == 1) {
                    --m;
                }
            }
        }
        return Math.max(0, m * 2);
    }

    public List<Boolean> checkIfPrerequisite(int n, int[][] prerequisites, int[][] queries) {
        boolean[][] f = new boolean[n][n];
        for (int[] p : prerequisites) {
            f[p[0]][p[1]] = true;
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    f[k][j] |= f[k][i] && f[i][j];
                }
            }
        }
        ArrayList<Boolean> list = new ArrayList<>();
        for (int[] q : queries) {
            list.add(f[q[0]][q[1]]);
        }
        return list;
    }

    public int minTrioDegree(int n, int[][] edges) {
        int[][] g = new int[n][n];
        int[] degree = new int[n];
        for (int[] edge : edges) {
            int x = edge[0] - 1;
            int y = edge[1] - 1;
            g[x][y] = g[y][x] = 1;
            ++degree[x];
            ++degree[y];
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (g[i][j] == 1) {
                    for (int k = j + 1; k < n; k++) {
                        if (g[i][k] == 1 && g[j][k] == 1) {
                            ans = Math.min(ans, degree[i] + degree[j] + degree[k] - 6);
                        }
                    }
                }
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
