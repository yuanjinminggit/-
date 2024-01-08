package com.leetcode.codereview.backtrack;

import com.leetcode.codereview.simpleConstruct.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

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
        dfscombinationSum(candidates, target, 0, ans, path, visited);
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

    public List<Integer> splitIntoFibonacci1(String num) {
        List<Integer> res = new ArrayList<>();
        int len = num.length();
        char[] charArray = num.toCharArray();
        dfs(charArray, 0, len, res);
        return res;
    }

    private boolean dfs(char[] charArray, int begin, int len, List<Integer> res) {
        if (begin == len) {
            return res.size() > 2;
        }
        int num = 0;
        for (int i = 0; i < len; i++) {
            num = num * 10 + (charArray[i] - '0');
            if (num < 0) {
                return false;
            }
            if (res.size() < 2 || res.get(res.size() - 2) + res.get(res.size() - 1) == num) {
                res.add(num);
                if (dfs(charArray, i + 1, len, res)) {
                    return true;
                }
                res.remove(res.size() - 1);
            }
            if (i == begin && charArray[i] == '0') {
                return false;
            }
        }
        return false;
    }

    public List<Integer> splitIntoFibonacci(String num) {
        ArrayList<Integer> list = new ArrayList<>();
        backtrack(list, num, num.length(), 0, 0, 0);
        return list;
    }

    private boolean backtrack(ArrayList<Integer> list, String num, int length, int index, int sum, int prev) {
        if (index == length) {
            return list.size() >= 3;
        }
        long curLong = 0;
        for (int i = index; i < length; i++) {
            if (i > index && num.charAt(index) == '0') {
                break;
            }
            curLong = curLong * 10 + num.charAt(i) - '0';
            if (curLong > Integer.MAX_VALUE) {
                break;
            }
            int curr = (int) curLong;
            if (list.size() >= 2) {
                if (curr < sum) {
                    continue;
                } else if (curr > sum) {
                    break;
                }
            }
            list.add(curr);
            if (backtrack(list, num, length, i + 1, prev + curr, curr)) {
                return true;
            } else {
                list.remove(list.size() - 1);
            }
        }
        return false;
    }

    public List<String> generateParenthesis(int n) {
        ArrayList<String> res = new ArrayList<>();
        backtrack(n, n, res, "");
        return res;
    }

    private void backtrack(int left, int right, ArrayList<String> res, String s) {
        if (left == 0 && right == 0) {
            res.add(s);
            return;
        }
        if (left > 0) {
            backtrack(left - 1, right, res, s + "(");
        }
        if (left > right && right > 0) {
            backtrack(left, right - 1, res, s + ")");
        }
    }

    public List<String> restoreIpAddresses(String s) {
        ArrayList<String> res = new ArrayList<>();
        backtrack(s, res, "", 0, 0);
        return res;
    }

    private void backtrack(String s, ArrayList<String> res, String tmp, Integer idx, Integer length) {
        if (length > 4) {
            return;
        }
        if (idx >= s.length() && length == 4) {
            res.add(tmp);
            return;
        }
        for (int i = idx; i < s.length(); i++) {
            if (valid(idx, i, s)) {
                backtrack(s, res, tmp.isEmpty() ? tmp + s.substring(idx, i + 1) : tmp + "." + s.substring(idx, i + 1), i + 1, length + 1);
            }
        }
    }

    private boolean valid(Integer idx, int i, String s) {
        if (s.charAt(idx) == '0') {
            return i <= idx;
        }
        if (i - idx >= 4) {
            return false;
        }
        int num = Integer.parseInt(s.substring(idx, i + 1));
        if (num >= 0 && num <= 255) {
            return true;
        }
        return false;
    }

    public List<String> readBinaryWatch1(int turnedOn) {
        ArrayList<String> ans = new ArrayList<>();
        for (int h = 0; h < 12; h++) {
            for (int m = 0; m < 60; m++) {
                if (bitCount(h) + bitCount(m) == turnedOn) {
                    ans.add(h + ":" + (m < 10 ? "0" : "") + m);
                }
            }
        }
        return ans;
    }

    private int bitCount(int num) {
        int count = 0;
        while (num > 0) {
            count += num & 1;
            num = num >>> 1;
        }
        return count;
    }

    private List<String> res = new ArrayList<>();
    private int[] hourArr = {8, 4, 2, 1};
    private int[] minuteArr = {32, 16, 8, 4, 2, 1};

    public List<String> readBinaryWatch(int num) {
        if (num > 10 || num < 0) {
            return res;
        }
        for (int i = 0; i <= num; i++) {
            List<Integer> hourCombination = findCombination(hourArr, i);
            List<Integer> minuteCombination = findCombination(minuteArr, num - i);
            for (int j = 0; j < hourCombination.size(); j++) {
                if (hourCombination.get(j) > 11) {
                    continue;
                }
                for (int k = 0; k < minuteCombination.size(); k++) {
                    if (minuteCombination.get(k) > 59) {
                        continue;
                    }
                    res.add(hourCombination.get(j) + ":" + (minuteCombination.get(k) < 10 ? "0" + minuteCombination.get(k) : minuteCombination.get(k)));
                }
            }
        }
        return res;
    }

    private List<Integer> findCombination(int[] arr, int count) {
        ArrayList<Integer> res = new ArrayList<>();
        findCombination(arr, count, 0, new Stack<Integer>(), res);
        return res;
    }

    private void findCombination(int[] arr, int count, int start, Stack<Integer> pre, ArrayList<Integer> res) {
        if (pre.size() == count) {
            res.add(sum(pre));
        }
        for (int i = start; i < arr.length; i++) {
            pre.push(arr[i]);
            findCombination(arr, count, i + 1, pre, res);
            pre.pop();
        }
    }

    private int sum(Stack<Integer> pre) {
        int sum = 0;
        for (int i = 0; i < pre.size(); i++) {
            sum += pre.get(i);
        }
        return sum;
    }

    public List<String> letterCasePermutation(String s) {
        ArrayList<String> res = new ArrayList<>();
        backtrack(s, res, 0, "");
        return res;
    }

    private void backtrack(String s, ArrayList<String> res, int idx, String tmp) {
        if (s.length() == tmp.length()) {
            res.add(tmp);
            return;
        }
        char c = s.charAt(idx);
        backtrack(s, res, idx + 1, tmp + c);
        if (Character.isLetter(c)) {
            if (c >= 'a') {
                backtrack(s, res, idx + 1, tmp + (char) (c - 32));
            } else {
                backtrack(s, res, idx + 1, tmp + (char) (c + 32));
            }
        }
    }

    public int numTilePossibilities(String tiles) {
        int[] count = new int[26];
        char[] charArray = tiles.toCharArray();
        for (char c : charArray) {
            count[c - 'A']++;
        }
        return dfs(count);

    }

    private int dfs(int[] count) {
        int res = 0;
        for (int i = 0; i < 26; i++) {
            if (count[i] == 0) {
                continue;
            }
            res++;
            count[i]--;
            res += dfs(count);
            count[i]++;
        }
        return res;
    }

    private int rows;

    private int cols;

    private int[][] image;

    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        int originColor = image[sr][sc];
        if (originColor == newColor) {
            return image;
        }
        this.rows = image.length;
        this.cols = image[0].length;
        this.image = image;
        dfs(sr, sc, originColor, newColor);
        return image;
    }

    private void dfs(int i, int j, int originColor, int newColor) {
        image[i][j] = newColor;
        for (int[] direction : DIRECTIONS) {
            int newX = i + direction[0];
            int newY = j + direction[1];
            if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && image[newX][newY] == originColor) {
                dfs(newX, newY, originColor, newColor);
            }
        }
    }

    public boolean exist(char[][] board, String word) {
        int h = board.length, w = board[0].length;
        boolean[][] visited = new boolean[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                boolean flag = check(board, visited, i, j, word, 0);
                if (flag) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean check(char[][] board, boolean[][] visited, int i, int j, String s, int k) {
        if (board[i][j] != s.charAt(k)) {
            return false;
        } else if (k == s.length() - 1) {
            return true;
        }
        visited[i][j] = true;
        boolean result = false;
        for (int[] dir : DIRECTIONS) {
            int newi = i + dir[0];
            int newj = j + dir[1];
            if (newi >= 0 && newi < board.length && newj >= 0 && newj < board[0].length) {
                if (!visited[newi][newj]) {
                    boolean flag = check(board, visited, newi, newj, s, k + 1);
                    if (flag) {
                        result = true;
                        break;
                    }
                }
            }
        }
        visited[i][j] = false;
        return result;
    }

    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public void solve(char[][] board) {
        int rows = board.length;
        if (rows == 0) {
            return;
        }
        int cols = board[0].length;
        if (cols == 0) {
            return;
        }
        // 第一列和最后一列
        for (int i = 0; i < rows; i++) {
            if (board[i][0] == 'O') {
                dfs(i, 0, rows, cols, board);
            }
            if (board[i][cols - 1] == 'O') {
                dfs(i, cols - 1, rows, cols, board);
            }
        }
        // 第一行和最后一行
        for (int j = 1; j < cols - 1; j++) {
            if (board[0][j] == 'O')
                dfs(0, j, rows, cols, board);
            if (board[rows - 1][j] == 'O')
                dfs(rows - 1, j, rows, cols, board);
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
                if (board[i][j] == '-') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    private void dfs(int i, int j, int rows, int cols, char[][] board) {
        if (inArea(i, j, rows, cols) && board[i][j] == 'O') {
            board[i][j] = '-';
            for (int[] direction : DIRECTIONS) {
                int newX = i + direction[0];
                int newY = j + direction[1];
                dfs(newX, newY, rows, cols, board);
            }
        }
    }

    private boolean inArea(int x, int y, int rows, int cols) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        if (n1 == 0) {
            return 0;
        }
        int s1cnt = 0, index = 0, s2cnt = 0;
        HashMap<Integer, int[]> recall = new HashMap<>();
        int[] preLoop = new int[2];
        int[] inLoop = new int[2];
        while (true) {
            ++s1cnt;
            for (int i = 0; i < s1.length(); i++) {
                char ch = s1.charAt(i);
                if (ch == s2.charAt(index)) {
                    index += 1;
                    if (index == s2.length()) {
                        ++s2cnt;
                        index = 0;
                    }
                }
            }
            if (s1cnt == n1) {
                return s2cnt / n2;
            }
            if (recall.containsKey(index)) {
                int[] value = recall.get(index);
                int s1cntPrime = value[0];
                int s2cntPrime = value[1];
                preLoop = new int[]{s1cntPrime, s2cntPrime};
                inLoop = new int[]{s1cnt - s1cntPrime, s2cnt - s2cntPrime};
                break;
            } else {
                recall.put(index, new int[]{s1cnt, s2cnt});
            }
        }
        int ans = preLoop[1] + (n1 - preLoop[0]) / inLoop[0] * inLoop[1];
        int rest = (n1 - preLoop[0]) % inLoop[0];
        for (int i = 0; i < rest; i++) {
            for (int j = 0; j < s1.length(); j++) {
                char ch = s1.charAt(j);
                if (ch == s2.charAt(index)) {
                    ++index;
                    if (index == s2.length()) {
                        ++ans;
                        index = 0;
                    }
                }
            }
        }
        return ans / n2;
    }
}