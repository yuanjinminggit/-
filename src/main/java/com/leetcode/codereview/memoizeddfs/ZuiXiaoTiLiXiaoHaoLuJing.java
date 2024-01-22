package com.leetcode.codereview.memoizeddfs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ZuiXiaoTiLiXiaoHaoLuJing {

    private int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int minimumEffortPath(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;
        boolean[][] vis = new boolean[m][n];
        dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dp[i], -1);
        }
        return dfsminimumEffortPath(heights, vis, m, n, 0, 0);
    }

    private int dfsminimumEffortPath(int[][] heights, boolean[][] vis, int m, int n, int x, int y) {
        if (x == m - 1 && y == n - 1) {
            return 0;
        }
        if (dp[x][y] != -1) {
            return dp[x][y];
        }
        vis[x][y] = true;
        int min = Integer.MAX_VALUE;
        for (int[] direction : directions) {
            int newx = x + direction[0];
            int newy = y + direction[1];
            if (newx >= 0 && newx < m && newy >= 0 && newy < n && !vis[newx][newy]) {
                int maxlen = Math.max(dfsminimumEffortPath(heights, vis, m, n, newx, newy), Math.abs(heights[x][y] - heights[newx][newy]));
                min = Math.min(min, maxlen);
            }
        }
        vis[x][y] = false;
        return dp[x][y] = min;
    }

    public int minCostClimbingStairs1(int[] cost) {
        int len = cost.length;
        mem = new int[len];
        Arrays.fill(mem, -1);
        return Math.min(dfsminCostClimbingStairs(cost, 0), dfsminCostClimbingStairs(cost, 1));
    }

    private int dfsminCostClimbingStairs(int[] cost, int idx) {
        if (idx >= cost.length) {
            return 0;
        }
        if (mem[idx] != -1) {
            return mem[idx];
        }
        return mem[idx] = Math.min(dfsminCostClimbingStairs(cost, idx + 1), dfsminCostClimbingStairs(cost, idx + 2)) + cost[idx];
    }

    private int mem[];

    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        mem = new int[n + 1];
        Arrays.fill(mem, -1);
        return dfsminCost(n, cost);
    }

    private int dfsminCost(int i, int[] cost) {
        if (i <= 1) {
            return 0;
        }
        if (mem[i] != -1) {
            return mem[i];
        }
        return mem[i] = Math.min(dfsminCost(i - 1, cost) + cost[i - 1], dfsminCost(i - 2, cost) + cost[i - 2]);
    }

    public int minStoneSum(int[] piles, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int pile : piles) {
            heap.offer(pile);
        }
        for (int i = 0; i < k; i++) {
            Integer poll = heap.poll();
            heap.offer((poll + 1) / 2);
        }
        int sum = 0;
        while (!heap.isEmpty()) {
            sum += heap.poll();
        }
        return sum;
    }

    private int[][] dp;

    public int minimumTime(List<Integer> nums1, List<Integer> nums2, int x) {
        int sum1 = nums1.stream().mapToInt(Integer::intValue).sum();
        if (sum1 <= x) {
            return 0;
        }
        int sum2 = nums2.stream().mapToInt(Integer::intValue).sum();

        Integer[][] nums = new Integer[nums1.size()][2];
        for (int i = 0; i < nums1.size(); i++) {
            int a = nums1.get(i), b = nums2.get(i);
            nums[i] = new Integer[]{a, b};
        }
        Arrays.sort(nums, (o1, o2) ->
                o1[1] - o2[1]
        );
        dp = new int[nums.length][nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            Arrays.fill(dp[i], -1);
        }
        // 一共i次操作，模拟每次i次操作减少的最大值
        for (int j = nums.length; j >= 0; j--) {
            dfsminimumTime(nums, nums.length - 1, j);
        }

        for (int i = 0; i <= nums.length; i++) {
            if (sum1 + sum2 * i - max(i) <= x) {
                return i;
            }
        }
        return -1;
    }

    // i表示前i个元素，j表示执行置0的次数
    private int dfsminimumTime(Integer[][] nums, int i, int j) {
        if (j < 0) {
            return Integer.MIN_VALUE / 2;
        }
        if (i < 0) {
            if (j == 0) {
                return 0;
            }
            return Integer.MIN_VALUE / 2;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        // 不选第i个元素置0
        int var1 = dfsminimumTime(nums, i - 1, j);
        // 选第i个元素置0
        int var2 = dfsminimumTime(nums, i - 1, j - 1) + nums[i][1] * j + nums[i][0];
        return dp[i][j] = Math.max(var1, var2);
    }

    private int max(int j) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < dp.length; i++) {
            max = Math.max(max, dp[i][j]);
        }
        return max;
    }

    public int splitArray(int[] nums, int k) {
        int left = 0, right = 0;
        for (int i = 0; i < nums.length; i++) {
            right += nums[i];
            if (left < nums[i]) {
                left = nums[i];
            }
        }
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (check(mid, nums, k)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return right;
    }

    private boolean check(int mid, int[] nums, int k) {
        int count = 1;
        int sum = 0;
        for (int num : nums) {
            if (sum + num > mid) {
                count++;
                sum = num;
            } else {
                sum += num;
            }
        }
        return count <= k;
    }

    public List<String> splitWordsBySeparator(List<String> words, char separator) {
        return words.stream().flatMap(word -> Arrays.stream(word.split(Pattern.quote(String.valueOf(separator))))).filter(word -> !word.isEmpty()).collect(Collectors.toList());
    }

    public long minimumRemoval(int[] beans) {
        Arrays.sort(beans);
        long sum = 0, mx = 0;
        int n = beans.length;
        for (int i = 0; i < n; i++) {
            sum += beans[i];
            mx = Math.max(mx, (long) (n - i) * beans[i]);
        }
        return sum - mx;
    }

    public int maximumNumberOfStringPairs(String[] words) {
        int n = words.length;
        int ans = 0;
        HashSet<Integer> seen = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (seen.contains(words[i].charAt(1) * 100 + words[i].charAt(0))) {
                ans++;
            }
            seen.add(words[i].charAt(0) * 100 + words[i].charAt(1));
        }
        return ans;
    }

    static final int N = 23;
    static final int M = 401;

    static final int MOD = 1000000007;
    int[][] d;

    String num;
    int min_sum;
    int max_sum;

    public int count(String num1, String num2, int min_sum, int max_sum) {
        d = new int[N][M];
        for (int i = 0; i < N; i++) {
            Arrays.fill(d[i], -1);
        }
        this.min_sum = min_sum;
        this.max_sum = max_sum;
        return (get(num2) - get(sub(num1) + MOD)) % MOD;
    }

    private int get(String num) {
        this.num = new StringBuffer(num).reverse().toString();
        return dfs(num.length() - 1, 0, true);
    }

    private int dfs(int i, int j, boolean limit) {
        if (j > max_sum) {
            return 0;
        }
        if (i == -1) {
            return j >= min_sum ? 1 : 0;
        }
        if (!limit && d[i][j] != -1) {
            return d[i][j];
        }
        int res = 0;
        int up = limit ? num.charAt(i) - '0' : 9;
        for (int x = 0; x <= up; x++) {
            res = (res + dfs(i - 1, j + x, limit && x == up)) % MOD;
        }
        if (!limit) {
            d[i][j] = res;
        }
        return res;
    }

    public String sub(String num) {
        char[] arr = num.toCharArray();
        int i = arr.length - 1;
        while (arr[i] == '0') {
            i--;
        }
        arr[i]--;
        i++;
        while (i < arr.length) {
            arr[i] = '9';
            i++;
        }
        return new String(arr);
    }
}
