package com.leetcode.codereview.greedy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Qiegedangao {
    public int maxArea(int h, int w, int[] ho, int[] ve) {
        int MOD = (int) 1e9 + 7;
        Arrays.sort(ho);
        Arrays.sort(ve);
        long maxH = getMax(h, ho);
        long maxW = getMax(w, ve);
        return (int) (maxH * maxW % MOD);
    }

    private long getMax(int h, int[] ho) {
        long maxH = 0;
        for (int i = 1; i < ho.length; i++) {
            maxH = Math.max(ho[i] - ho[i - 1], maxH);
        }
        maxH = Math.max(maxH, ho[0]);
        maxH = Math.max(maxH, h - ho[ho.length - 1]);
        return maxH;
    }

    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] left = new int[n];
        for (int i = 0; i < n; i++) {
            if (i > 0 && ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 1;
            }
        }
        int right = 0, ret = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (i < n - 1 && ratings[i] > ratings[i + 1]) {
                right++;
            } else {
                right = 1;
            }
            ret += Math.max(left[i], right);
        }
        return ret;
    }

    // 暴力解法
    public int maxPoints1(int[][] ps) {
        int n = ps.length;
        int ans = 1;
        for (int i = 0; i < n; i++) {
            int[] x = ps[i];
            for (int j = i + 1; j < n; j++) {
                int[] y = ps[j];
                int cnt = 2;
                for (int k = j + 1; k < n; k++) {
                    int[] p = ps[k];
                    int s1 = (y[1] - x[1]) * (p[0] - y[0]);
                    int s2 = (p[1] - y[1]) * (y[0] - x[0]);
                    if (s1 == s2) {
                        cnt++;
                    }
                }
                ans = Math.max(ans, cnt);
            }
        }
        return ans;
    }

    public int findGCD(int[] nums) {
        int min = 1001;
        int max = 1;
        for (int num : nums) {
            min = Math.min(num, min);
            max = Math.max(num, max);
        }
        return getGCD(max, min);
    }

    private int getGCD(int max, int min) {
        if (max < min) {
            return getGCD(min, max);
        }
        if (max % min == min) {
            return min;
        }

        return getGCD(max % min, min);
    }

    public int maxPoints(int[][] points) {
        int n = points.length;
        if (n <= 2) {
            return n;
        }
        int ret = 0;
        for (int i = 0; i < n; i++) {
            if (ret >= n - i || ret > n / 2) {
                break;
            }
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int j = i + 1; j < n; j++) {
                int x = points[i][0] - points[j][0];
                int y = points[i][1] - points[j][1];
                if (x == 0) {
                    y = 1;
                } else if (y == 0) {
                    x = 1;
                } else {
                    if (y < 0) {
                        x = -x;
                        y = -y;
                    }
                    int gcdXY = gcd(Math.abs(x), Math.abs(y));
                    x /= gcdXY;
                    y /= gcdXY;
                }
                int key = y + x * 20001;
                map.put(key, map.getOrDefault(key, 0) + 1);
            }
            int maxn = 0;
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int num = entry.getValue();
                maxn = Math.max(maxn, num + 1);
            }
            ret = Math.max(ret, maxn);
        }
        return ret;
    }

    private int gcd(int a, int b) {
        return b != 0 ? gcd(b, a % b) : a;
    }


    public int maxProduct(int[] nums) {
        int n = nums.length;
        int max = Integer.MIN_VALUE;
        mem = new int[n][2];
        for (int i = 0; i < n; i++) {
            Arrays.fill(mem[i], Integer.MIN_VALUE / 2);
        }
        for (int i = 0; i < n; i++) {
            max = Math.max(max, dfsmaxProduct(i, nums, 1));
        }
        return max;
    }


    private int[][] mem;

    private int dfsmaxProduct(int i, int[] nums, int flag) {
        if (i == 0) {
            return nums[0];
        }
        if (mem[i][flag] != Integer.MIN_VALUE / 2) {
            return mem[i][flag];
        }
        if (nums[i] >= 0) {
            if (flag == 1) {
                return mem[i][flag] = Math.max(dfsmaxProduct(i - 1, nums, 1) * nums[i], nums[i]);
            } else {
                return mem[i][flag] = Math.min(nums[i], dfsmaxProduct(i - 1, nums, 0) * nums[i]);
            }
        } else {
            if (flag == 1) {
                return mem[i][flag] = Math.max(nums[i], dfsmaxProduct(i - 1, nums, 0));
            }
            return mem[i][flag] = Math.min(nums[i], dfsmaxProduct(i - 1, nums, 1) * nums[i]);
        }
    }

    public boolean haveConflict(String[] event1, String[] event2) {
        return event1[1].compareTo(event2[0]) < 0 || event2[1].compareTo(event1[0]) < 0;
    }


}
