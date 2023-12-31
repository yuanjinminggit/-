package com.leetcode.codereview.other;

import org.testng.annotations.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/*
 * 分组循环
 *
 * */
public class GroupingCycle {

    public int longestAlternatingSubarray(int[] nums, int threshold) {
        int n = nums.length;
        int ans = 0, i = 0;
        while (i < n) {
            if (nums[i] > threshold || nums[i] % 2 != 0) {
                i++;
                continue;
            }
            int start = i;
            i++;
            while (i < n && nums[i] <= threshold && nums[i] % 2 != nums[i - 1] % 2) {
                i++;
            }
            ans = Math.max(ans, i - start);
        }
        return ans;
    }

    public boolean checkZeroOnes(String s) {
        int n = s.length();
        int[] cnt = new int[2];
        int i = 0;
        while (i < n) {
            int start = i;
            ++i;
            while (i < n && s.charAt(i) == s.charAt(start)) {
                i++;
            }
            cnt[s.charAt(start) - '0'] = Math.max(cnt[s.charAt(start) - '0'], i - start);
        }
        return cnt[1] > cnt[0];
    }

    public String makeFancyString(String s) {
        int n = s.length();
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (i < n) {
            int start = i;
            sb.append(s.charAt(i));
            ++i;
            int cnt = 0;
            while (i < n && s.charAt(i) == s.charAt(start) && cnt < 1) {
                sb.append(s.charAt(i));
                i++;
                cnt++;
            }
            while (i < n && cnt == 1 && s.charAt(i) == s.charAt(start)) {
                i++;
            }
        }
        return sb.toString();
    }

    private static Map<String, String> map = new HashMap<String, String>() {{
        put("&quot;", "\"");
        put("&apos;", "'");
        put("&amp;", "&");
        put("&gt;", ">");
        put("&lt;", "<");
        put("&frasl;", "/");
    }};

    public String entityParser(String text) {
        int n = text.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; ) {
            if (text.charAt(i) == '&') {
                int j = i + 1;
                while (j < n && j - i < 6 && text.charAt(j) != ';') j++;
                String s = text.substring(i, Math.min(j + 1, n));
                if (map.containsKey(s)) {
                    sb.append(map.get(s));
                    i = j + 1;
                    continue;
                }
            }
            sb.append(text.charAt(i++));
        }
        return sb.toString();
    }

    public int dayOfYear(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int mouth = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8));
        int[] amount = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
            ++amount[1];
        }
        int ans = 0;
        for (int i = 0; i < mouth - 1; i++) {
            ans += amount[i];
        }
        return ans + day;
    }

    int[] a;
    int[][] mem;

    public int maxStudents(char[][] seats) {
        int m = seats.length, n = seats[0].length;
        a = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (seats[i][j] == '.') {
                    a[i] |= 1 << j;
                }
            }
        }
        mem = new int[m][1 << n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(mem[i], -1);
        }
        return dfs(m - 1, a[m - 1]);

    }

    private int dfs(int i, int j) {
        if (mem[i][j] != -1) {
            return mem[i][j];
        }
        if (i == 0) {
            if (j == 0) {
                return 0;
            }
            int lb = j & -j;
            return mem[i][j] = dfs(i, j & ~(lb * 3)) + 1;
        }
        int res = dfs(i - 1, a[i - 1]);
        for (int s = j; s > 0; s = (s - 1) & j) {
            if ((s & (s >> 1)) == 0) {
                int t = a[i - 1] & ~(s << 1 | s >> 1);
                res = Math.max(res, dfs(i - 1, t) + bitCount(s));
            }
        }
        return mem[i][j] = res;
    }

    private int bitCount(int s) {
        int count = 0;
        while (s > 0) {
            count += s & 1;
            s = s >> 1;
        }
        return count;
    }

    public long minCost(int[] nums, int x) {
        int n = nums.length;
        int[] f = new int[n];
        System.arraycopy(nums, 0, f, 0, n);
        long res = getSum(f);
        // 操作次数
        for (int k = 1; k < n; k++) {
            // 每个类型的成本
            for (int i = 0; i < n; i++) {
                f[i] = Math.min(f[i], nums[(i + k) % n]);
            }
            res = Math.min(res, (long) k * x + getSum(f));
        }
        return res;
    }

    private long getSum(int[] f) {
        long sum = 0;
        for (int i : f) {
            sum += i;
        }
        return sum;
    }

    private int cache[];
    private int[][] dp;
    private String s;

    public int minCut(String s) {
        int n = s.length();
        cache = new int[n];
        dp = new int[n][n];
        Arrays.fill(cache, -1);
        this.s = s;
        return dfs(0) - 1;
    }

    private int dfs(int idx) {
        if (idx == s.length()) {
            return 0;
        }
        if (cache[idx] != -1) {
            return cache[idx];
        }
        int min = Integer.MAX_VALUE;
        for (int i = idx; i < s.length(); i++) {
            if (isPalindrome(idx, i + 1) == 1) {
                min = Math.min(min, dfs(i + 1) + 1);
            }
        }
        return cache[idx] = min;
    }

    private int isPalindrome(int i, int j) {
        if (i >= j) {
            return dp[i][j] = 1;
        }
        if (dp[i][j] != 0) {
            return dp[i][j];
        }
        if (s.charAt(i) == s.charAt(j)) {
            return dp[i][j] = isPalindrome(i + 1, j - 1);
        }
        return dp[i][j] = -1;
    }

    public List<List<Integer>> findSubsequences(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Deque<Integer> path = new ArrayDeque<>();
        dfs(nums, 0, path, res);
        return res;
    }

    private void dfs(int[] nums, int begin, Deque<Integer> path, List<List<Integer>> res) {
        if (path.size() > 1) {
            res.add(new ArrayList<>(path));
        }
        HashSet<Integer> set = new HashSet<>();
        for (int i = begin; i < nums.length; i++) {
            if (set.contains(nums[i])) {
                continue;
            }
            if (path.isEmpty() || nums[i] >= path.peekLast()) {
                path.addLast(nums[i]);
                dfs(nums, i + 1, path, res);
                path.removeLast();

                set.add(nums[i]);
            }
        }
    }



    @Test
    public void test() {
        makeFancyString("leeetcode");
    }

}
