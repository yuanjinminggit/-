package com.leetcode.codereview.memoizeddfs;

import java.util.Arrays;

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

    private int[][] mem;

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


}
