package com.leetcode.codereview.memoizeddfs;

import org.testng.annotations.Test;

import java.util.ArrayList;
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

    public int coinChange(int[] coins, int amount) {
        this.coins = coins;
        int n = coins.length;
        memo = new int[n][amount + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        int ans = dfs(n - 1, amount);
        return ans < Integer.MAX_VALUE / 2 ? ans : -1;
    }

    private int dfs(int i, int c) {
        if (i < 0) return c == 0 ? 0 : Integer.MAX_VALUE / 2;
        if (memo[i][c] != -1) return memo[i][c];
        if (c < coins[i]) return memo[i][c] = dfs(i - 1, c);
        return memo[i][c] = Math.min(dfs(i - 1, c), dfs(i, c - coins[i]) + 1);
    }

    public int distinctIntegers(int n) {
        return n == 1 ? 1 : n - 1;
    }

    private int[] coins;
    private int[][] memo;

    public int change(int amount, int[] coins) {
        memo = new int[amount + 1][coins.length];
        for (int i = 0; i < amount + 1; i++) {
            Arrays.fill(memo[i], -1);
        }
        this.coins = coins;
        return dfsChange(amount, coins.length - 1);
    }

    private int dfsChange(int amount, int idx) {
        if (amount == 0) {
            return 1;
        }
        if (idx < 0) {
            return 0;
        }
        if (memo[amount][idx] != -1) {
            return memo[amount][idx];
        }
        int a = 0;
        int b = 0;
        if (amount >= coins[idx]) {
            a = dfsChange(amount - coins[idx], idx);
        }
        b = dfsChange(amount, idx - 1);
        return memo[amount][idx] = a + b;
    }

    private char[] s;
    private char[] t;

    private int[][] distance;

    public int minDistance(String word1, String word2) {
        s = word1.toCharArray();
        t = word2.toCharArray();
        distance = new int[s.length][t.length];
        for (int i = 0; i < s.length; i++) {
            Arrays.fill(distance[i], -1);
        }
        return minDistance(s.length - 1, t.length - 1);
    }

    private int minDistance(int i, int j) {
        if (i < 0 || j < 0) {
            return i < 0 ? j + 1 : i + 1;
        }
        if (distance[i][j] != -1) {
            return distance[i][j];
        }
        if (s[i] == t[j]) {
            return distance[i][j] = minDistance(i - 1, j - 1);
        }
        return distance[i][j] = (Math.min(Math.min(minDistance(i - 1, j), minDistance(i, j - 1)), minDistance(i - 1, j - 1)) + 1);
    }

    @Test
    public void test() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        List<Integer> integers = list.subList(0, 3);
        System.out.println(integers);
    }

    private static final int MX = 51;
    private static final int[][] coprime = new int[MX][MX];

    static {
        // 预处理
        // coprime[i]保存【1，mx）中与i互斥的所有元素
        for (int i = 1; i < MX; i++) {
            int k = 0;
            for (int j = 1; j < MX; j++) {
                if (gcd(i, j) == 1) {
                    coprime[i][k++] = j;
                }
            }
        }
    }

    public int[] getCoprimes(int[] nums, int[][] edges) {
        int n = nums.length;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        int[] valDepth = new int[MX];
        int[] valNodeId = new int[MX];
        dfs(0, -1, 1, g, nums, ans, valDepth, valNodeId);
        return ans;
    }

    private void dfs(int x, int fa, int depth, List<Integer>[] g, int[] nums, int[] ans, int[] valDepth, int[] valNodeId) {
        int val = nums[x];

        int maxDepth = 0;
        for (int j : coprime[val]) {
            if (j == 0) {
                break;
            }
            if (valDepth[j] > maxDepth) {
                maxDepth = valDepth[j];
                ans[x] = valNodeId[j];
            }
        }
        int tmpDepth = valDepth[val];
        int tmpNodeId = valNodeId[val];

        valDepth[val] = depth;
        valNodeId[val] = x;

        for (Integer y : g[x]) {
            if (y != fa) {
                dfs(y, x, depth + 1, g, nums, ans, valDepth, valNodeId);
            }
        }
        valDepth[val] = tmpDepth;
        valNodeId[val] = tmpNodeId;
    }

    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public String maximumBinaryString(String binary) {
        int n = binary.length();
        char[] s = binary.toCharArray();
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (s[i] == '0') {
                while (j <= i || (j < n && s[j] == '1')) {
                    j++;
                }
                if (j < n) {
                    s[j] = '1';
                    s[i] = '1';
                    s[i + 1] = '0';
                }
            }
        }
        return new String(s);
    }

    public int findChampion(int[][] grid) {
        int ans = 0;
        for (int i = 1; i < grid.length; i++) {
            if (grid[i][ans] == 1) {
                ans = i;
            }
        }
        return ans;
    }

    private List<Integer>[] g;
    private int[] ans, size;

    public int[] sumOfDistancesInTree(int n, int[][] edges) {
        g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        ans = new int[n];
        size = new int[n];
        dfs(0, -1, 0);
        reroot(0, -1);
        return ans;
    }

    private void dfs(int x, int fa, int depth) {
        ans[0] += depth;
        size[x] = 1;
        for (int y : g[x]) {
            if (y != fa) {
                dfs(y, x, depth + 1);
                size[x] += size[y];
            }
        }
    }

    private void reroot(int x, int fa) {
        for (int y : g[x]) {
            if (y != fa) {
                ans[y] = ans[x] + g.length - 2 * size[y];
                reroot(y, x);
            }
        }
    }

    private int[] cache;

    public int combinationSum4(int[] nums, int target) {
        cache = new int[target + 1];
        Arrays.fill(cache, -1);
        int a = dfscombinationSum4(nums, target);
        return Math.max(0, a);
    }

    private int dfscombinationSum4(int[] nums, int target) {
        if (target == 0) {
            return 1;
        }
        if (cache[target] != -1) {
            return cache[target];
        }
        int res = 0;
        for (int i : nums) {
            if (target >= i) {
                res += dfscombinationSum4(nums, target - i);
            }
        }
        return cache[target] = res;
    }

}
