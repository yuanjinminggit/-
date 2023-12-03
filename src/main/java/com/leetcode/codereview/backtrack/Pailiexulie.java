package com.leetcode.codereview.backtrack;

import com.leetcode.codereview.simpleConstruct.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pailiexulie {

    private boolean[] used;
    private int[] factorial;
    private int n;
    private int k;

    public String getPermutation(int n, int k) {
        this.n = n;
        this.k = k;
        calculateFactorial(n);
        used = new boolean[n + 1];
        StringBuilder path = new StringBuilder();
        dfs(0, path);
        return path.toString();
    }

    private void dfs(int index, StringBuilder path) {
        if (index == n) {
            return;
        }
        int cnt = factorial[n - 1 - index];
        for (int i = 1; i <= n; i++) {
            if (used[i]) {
                continue;
            }
            if (cnt < k) {
                k -= cnt;
                continue;
            }
            path.append(i);
            used[i] = true;
            dfs(index + 1, path);
            return;
        }
    }

    private void calculateFactorial(int n) {
        factorial = new int[n + 1];
        factorial[0] = 1;
        for (int i = 1; i <= n; i++) {
            factorial[i] = factorial[i - 1] * i;
        }
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        ArrayList<List<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();
        boolean[] visited = new boolean[candidates.length];
        dfscombinationSum(candidates, target, 0, ans, path,visited);
        return ans;
    }

    private void dfscombinationSum(int[] candidates, int target, int idx, List<List<Integer>> ans, ArrayList<Integer> path, boolean[] visited) {
        if (target == 0) {
            ans.add(new ArrayList<>(path));
            return;
        }
        for (int i = idx; i < candidates.length; i++) {
            if (target - candidates[i] >= 0) {
                if (i > 0 && candidates[i] == candidates[i - 1] && !visited[i - 1]) continue;
                path.add(candidates[i]);
                visited[i] = true;
                dfscombinationSum(candidates, target - candidates[i], i + 1, ans, path, visited);
                path.remove(path.size() - 1);
                visited[i] = false;
            }
        }
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        ArrayList<List<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();
        Arrays.sort(candidates);
        boolean[] visited = new boolean[candidates.length];
        dfscombinationSum(candidates, target, 0, ans, path, visited);
        return ans;
    }


    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        ArrayList<Integer> path = new ArrayList<>();
        ArrayList<List<Integer>> ans = new ArrayList<>();
        dfspathSum(root, targetSum, path, ans);
        return ans;
    }

    private void dfspathSum(TreeNode root, int targetSum, ArrayList<Integer> path, ArrayList<List<Integer>> ans) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            if (targetSum == root.val) {
                path.add(root.val);
                ans.add(new ArrayList<>(path));
                path.remove(path.size() - 1);
            }
            return;
        }
        path.add(root.val);
        dfspathSum(root.left, targetSum - root.val, path, ans);

        dfspathSum(root.right, targetSum - root.val, path, ans);
        path.remove(path.size() - 1);
    }


    private int[][] f;
    List<List<String>> ret = new ArrayList<List<String>>();
    List<String> ans = new ArrayList<String>();

    public List<List<String>> partition(String s) {
        int n = s.length();
        f = new int[n][n];
        dfspartition(s, 0, ans, ret);
        return ret;
    }

    private void dfspartition(String s, int idx, List<String> ans, List<List<String>> ret) {
        if (idx == s.length()) {
            ret.add(new ArrayList<>(ans));
            return;
        }
        for (int i = idx; i < s.length(); i++) {
            if (isPalindrome(s, idx, i)) {
                ans.add(s.substring(idx, i + 1));
                dfspartition(s, i + 1, ans, ret);
                ans.remove(ans.size() - 1);
            }
        }
    }

    private boolean isPalindrome(String s, int i, int j) {
        if (i >= j) {
            f[i][j] = 1;
            return true;
        }
        if (f[i][j] != 0) {
            return f[i][j] == 1;
        }
        if (s.charAt(i) == s.charAt(j)) {
            return isPalindrome(s, i + 1, j - 1);
        } else {
            f[i][j] = -1;
            return false;
        }
    }




}