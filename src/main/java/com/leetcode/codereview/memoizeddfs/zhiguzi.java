package com.leetcode.codereview.memoizeddfs;

import com.leetcode.codereview.simpleConstruct.TreeNode;

import java.util.Arrays;
import java.util.LinkedList;

public class zhiguzi {


    private int MOD = 1000000000 + 7;

    public int numRollsToTarget1(int n, int k, int target) {
        if (target < n || target > n * k) {
            return 0;
        }
        mem = new int[n + 1][target + 1];
        for (int i = 0; i < n + 1; i++) {
            Arrays.fill(mem[i], -1);
        }
        return dfsnumRollsToTarget(n, target, k);
    }

    private int dfsnumRollsToTarget(int n, int target, int k) {
        if (n == 0) {
            return target == 0 ? 1 : 0;
        }
        if (mem[n][target] != -1) {
            return mem[n][target];
        }
        int res = 0;
        for (int i = 1; i <= k && i <= target; i++) {
            res = (res + dfsnumRollsToTarget(n - 1, target - i, k)) % MOD;
        }
        return mem[n][target] = res;
    }

    public int numRollsToTarget2(int n, int k, int target) {
        if (target < n || target > n * k) {
            return 0;
        }
        int[][] mem = new int[n + 1][target + 1];
        mem[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= target; j++) {
                for (int x = 1; x <= k && x <= j; x++) {
                    mem[i][j] = (mem[i][j] + mem[i - 1][j - x]) % MOD;
                }
            }
        }
        return mem[n][target];
    }


    public int numRollsToTarget(int n, int k, int target) {
        if (target < n || target > n * k) {
            return 0;
        }
        int[] mem = new int[target + 1];
        mem[0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = target; j >= 0; j--) {
                mem[j] = 0;
                for (int x = 1; x <= k && x <= j; x++) {
                    mem[j] = (mem[j] + mem[j - x]) % MOD;
                }
            }
        }
        return mem[target];
    }


    public int punishmentNumber1(int n) {
        int res = 0;
        for (int i = 0; i <= n; i++) {
            String s = Integer.toString(i * i);
            if (dfspunishmentNumber(s, 0, 0, i)) {
                res += i * i;
            }
        }
        return res;
    }

    private boolean dfspunishmentNumber(String s, int pos, int tot, int target) {
        if (pos == s.length()) {
            return target == tot;
        }
        int sum = 0;
        for (int i = pos; i < s.length(); i++) {
            sum += sum * 10 + s.charAt(i) - '0';
            if (sum + tot > target) {
                break;
            }
            if (dfspunishmentNumber(s, i + 1, sum + tot, target)) {
                return true;
            }
        }
        return false;
    }


    public int punishmentNumber3(int n) {
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            if (check(i * i, i)) {
                ans += i * i;
            }
        }
        return ans;
    }

    private static boolean check(int t, int x) {
        if (t == x) {
            return true;
        }
        int d = 10;
        while (t >= d && t % d <= x) {
            if (check(t / d, x - t % d)) {
                return true;
            }
            d *= 10;
        }
        return false;
    }


    static int[] f = new int[1010];

    static {
        for (int i = 1; i <= 1000; i++) {
            f[i] = f[i - 1];
            if (check(i * i, i)) {
                f[i] += i * i;
            }
        }
    }

    public int punishmentNumber(int n) {
        return f[n];
    }


    public int maxSatisfaction1(int[] satisfaction) {
        int n = satisfaction.length;
        mem = new int[n][n + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(mem[i], -1);
        }
        Integer[] array = Arrays.stream(satisfaction).boxed().sorted((a, b) -> b - a).toArray(Integer[]::new);

        return dfsmaxSatisfaction(n - 1, 1, array);
    }

    private int dfsmaxSatisfaction(int i, int cnt, Integer[] array) {
        if (i < 0) {
            return 0;
        }
        if (mem[i][cnt] != -1) {
            return mem[i][cnt];
        }
        return mem[i][cnt] = Math.max(array[i] * cnt + dfsmaxSatisfaction(i - 1, cnt + 1, array), dfsmaxSatisfaction(i - 1, cnt, array));
    }

    public int maxSatisfaction(int[] satisfaction) {
        Arrays.sort(satisfaction);
        int f = 0;
        int s = 0;
        for (int i = satisfaction.length - 1; i >= 0; i--) {
            s += satisfaction[i];
            if (s <= 0) {
                break;
            }
            f += s;
        }
        return f;
    }

    public int maxProfit1(int[] prices) {
        int min = Integer.MAX_VALUE;
        int max = 0;
        for (int price : prices) {
            min = Math.min(min, price);
            max = Math.max(max, price - min);
        }
        return max;
    }

    public int maxProfit2(int[] prices) {
        int sum = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] - prices[i - 1] > 0) {
                sum += prices[i] - prices[i - 1];
            }
        }
        return sum;
    }

    private int[][][] dp;

    public int maxProfit(int[] prices) {
        dp = new int[prices.length][2][3];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < 2; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        return dfsmaxProfit(prices, prices.length - 1, 2, 0);
    }

    private int dfsmaxProfit(int[] prices, int index, int k, int hold) {
        if (k < 0) {
            return Integer.MIN_VALUE / 2;
        }
        if (index < 0) {
            return hold == 1 ? Integer.MIN_VALUE / 2 : 0;
        }
        if (dp[index][hold][k] != -1) {
            return dp[index][hold][k];
        }
        if (hold == 0) {
            return dp[index][0][k] = Math.max(dfsmaxProfit(prices, index - 1, k - 1, 1) + prices[index],
                    dfsmaxProfit(prices, index - 1, k, 0));
        }
        return dp[index][1][k] = Math.max(dfsmaxProfit(prices, index - 1, k, 0) - prices[index],
                dfsmaxProfit(prices, index - 1, k, 1));
    }

    private int[][] mem;


    private int cache[];

    public int rob1(int[] nums) {
        int n = nums.length;
        cache = new int[n];
        Arrays.fill(cache, -1);
        return dfsrob1(n - 1, nums);
    }

    private int dfsrob1(int i, int[] nums) {
        if (i < 0) {
            return 0;
        }
        if (cache[i] != -1) {
            return cache[i];
        }
        return cache[i] = Math.max(dfsrob1(i - 2, nums) + nums[i], dfsrob1(i - 1, nums));
    }

    public int rob(TreeNode root) {
        int[] rs = dfsrob(root);
        return Math.max(rs[0], rs[1]);
    }

    private int[] dfsrob(TreeNode root) {
        if (root == null) {
            return new int[]{0, 0};
        }
        int[] left = dfsrob(root.left);
        int[] right = dfsrob(root.right);
        int rob = root.val + left[0] + right[0];
        int noRob = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        return new int[]{noRob, rob};
    }

    private int[][] directions = new int[][]{{-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}, {1, -2}, {2, -1}, {2, 1}, {1, 2}};

    public boolean checkValidGrid1(int[][] grid) {
        int n = grid.length, tg = 1;
        LinkedList<int[]> queue = new LinkedList<>();
        if (grid[0][0] == 0) {
            queue.offer(new int[]{0, 0});
        }
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int[] direction : directions) {
                int newx = direction[0] + cur[0];
                int newy = direction[1] + cur[1];
                if (newx < 0 || newx >= grid.length || newy < 0 || newy >= grid[0].length || tg != grid[newx][newy]) {
                    continue;
                }
                queue.offer(new int[]{newx, newy});
                tg++;
            }
        }
        return tg == n * n;
    }


     public boolean checkValidGrid(int[][] grid) {
         return dfscheckValidGrid(grid, 0, 0, 0, 0);
     }

     private boolean dfscheckValidGrid(int[][] grid, int x, int y, int sum, int cur) {
         if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length  || cur != grid[x][y]) {
             return false;
         }
         sum++;
         if (sum == grid.length * grid[0].length) {
             return true;
         }

         for (int[] direction : directions) {
             int newx = direction[0] + x;
             int newy = direction[1] + y;
             if (dfscheckValidGrid(grid, newx, newy, sum, cur + 1)) {
                 return true;
             }
         }

         return false;
     }



    private int [] stones;
    private int sum;
    public int lastStoneWeightII(int[] stones) {
        int sum = Arrays.stream(stones).sum();
        int n = stones.length;
        this.stones = stones;
        this.sum = sum;
        mem = new int[n][sum + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(mem[i], -1);
        }
        return dfslastStoneWeightII(n - 1, 0);
    }

    private int dfslastStoneWeightII(int i, int cur) {
        if (i < 0) {
            return Math.abs(sum - 2 * cur);
        }
        if (mem[i][cur] != -1) {
            return mem[i][cur];
        }
        int r1 = dfslastStoneWeightII(i - 1, cur);
        int r2 = dfslastStoneWeightII(i - 1, cur + stones[i]);
        return mem[i][cur] = Math.min(r1, r2);
    }


}

