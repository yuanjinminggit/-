package com.leetcode.codereview.array;

import java.util.Arrays;
import java.util.PriorityQueue;

public class ZhengChuShuZu {
    public int[] divisibilityArray(String word, int m) {
        int n = word.length();
        int[] res = new int[n];
        long cur = 0L;
        char[] charArray = word.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            cur = (cur * 10 + charArray[i] - '0') % m;
            if (cur % m == 0) {
                res[i] = 1;
            } else {
                res[i] = 0;
            }
        }
        return res;
    }

    public int numberOfEmployeesWhoMetTarget(int[] hours, int target) {
        int count = 0;
        for (int hour : hours) {
            if (hour >= target) {
                count++;
            }
        }
        return count;
    }

    public int[][] diagonalSort(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[] a = new int[Math.min(m, n)];
        for (int k = 1 - n; k < m; k++) {
            int left = Math.max(k, 0);
            int right = Math.min(k + n, m);
            for (int i = left; i < right; i++) {
                a[i - left] = mat[i][i - k];
            }
            Arrays.sort(a, 0, right - left);
            for (int i = left; i < right; i++) {
                mat[i][i - k] = a[i - left];
            }
        }
        return mat;
    }

    public int[] decrypt1(int[] code, int k) {
        int n = code.length;
        int[] a = new int[n];
        if (k == 0) {
            return a;
        } else if (k > 0) {
            for (int i = 0; i < n; i++) {
                for (int j = 1; j <= k; j++) {
                    a[i] += code[(i + j) % n];
                }
            }
        } else {
            k = -k;
            for (int i = 0; i < n; i++) {
                for (int j = 1; j <= k; j++) {
                    a[i] += code[(i - j + n) % n];
                }
            }
        }
        return a;
    }

    public int[] decrypt(int[] code, int k) {
        int n = code.length;
        int[] ans = new int[n];
        int r = k > 0 ? k + 1 : n;
        k = Math.abs(k);
        int s = 0;
        for (int i = r - k; i < r; i++) {
            s += code[i];
        }
        for (int i = 0; i < n; i++) {
            ans[i] = s;
            s += code[r % n] - code[(r - k) % n];
            r++;
        }
        return ans;
    }

    int n;
    int[] mem;
    int[][] jobs;// [startTime,endTime,profit]

    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        n = startTime.length;
        mem = new int[n];
        Arrays.fill(mem, -1);
        jobs = new int[n][];
        for (int i = 0; i < n; i++) {
            jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }
        Arrays.sort(jobs, (a, b) -> a[1] - b[1]);
        return dfs(n - 1);
    }

    private int dfs(int i) {
        if (i < 0) {
            return 0;
        }
        if (mem[i] != -1) {
            return mem[i];
        }
        int res = dfs(i - 1);
        int j = binarySearch(i - 1, jobs[i][0]);
        res = Math.max(res, dfs(j) + jobs[i][2]);
        return mem[i] = res;
    }

    private int binarySearch(int r, int tg) {
        int l = 0;
        if (jobs[l][1] > tg) {
            return -1;
        }
        while (l < r) {
            int mid = l + (r - l - 1) / 2;
            if (jobs[mid][1] <= tg) {
                l = mid;
            } else {
                r = mid - 1;
            }
        }
        return l;
    }

    public double average(int[] salary) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int sum = 0;
        for (int i : salary) {
            max = Math.max(i, max);
            min = Math.min(i, min);
            sum += i;
        }
        return (double) (sum - max - min) / (salary.length - 2);
    }

    public double mincostToHireWorkers(int[] quality, int[] wage, int k) {
        int n = quality.length;
        Integer[] id = new Integer[n];
        Arrays.setAll(id, i -> i);
        Arrays.sort(id, (i, j) -> wage[i] * quality[j] - wage[j] * quality[i]);

        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        int sumQ = 0;
        for (int i = 0; i < k; i++) {
            pq.offer(quality[id[i]]);
            sumQ += quality[id[i]];
        }
        double ans = sumQ * (1.0 * wage[id[k - 1]] / quality[id[k - 1]]);
        for (int i = k; i < n; i++) {
            int q = quality[id[i]];
            if (q < pq.peek()) {
                sumQ -= pq.poll() - q;
                pq.offer(q);
                ans = Math.min(ans, sumQ * ((double) wage[id[i]] / q));
            }
        }
        return ans;
    }

    public long totalCost(int[] costs, int k, int candidates) {
        int n = costs.length;
        long ans = 0;
        if (candidates * 2 + k > n) {
            Arrays.sort(costs);
            for (int i = 0; i < k; i++) {
                ans += costs[i];
            }
            return ans;
        }
        PriorityQueue<Integer> pre = new PriorityQueue<>();
        PriorityQueue<Integer> suf = new PriorityQueue<>();
        for (int i = 0; i < candidates; i++) {
            pre.offer(costs[i]);
            suf.offer(costs[n - 1 - i]);
        }

        int i = candidates;
        int j = n - 1 - candidates;

        while (k-- > 0) {
            if (pre.peek() <= suf.peek()) {
                ans += pre.poll();
                pre.offer(costs[i++]);
            } else {
                ans += suf.poll();
                suf.offer(costs[j--]);
            }
        }
        return ans;
    }

    public int numberOfEmployeesWhoMetTarget1(int[] hours, int target) {
        int ans = 0;
        for (int hour : hours) {
            if (hour >= target) {
                ans++;
            }
        }
        return ans;
    }

    public int[] findColumnWidth(int[][] grid) {
        int n = grid[0].length;
        int[] ans = new int[n];
        for (int j = 0; j < n; j++) {
            int mn = 0;
            int mx = 0;
            for (int[] row : grid) {
                mn = Math.min(mn, row[j]);
                mx = Math.max(mx, row[j]);
            }
            int len = 1;
            for (int x = Math.max(mx / 10, -mn); x > 0; x /= 10) {
                len++;
            }
            ans[j] = len;
        }
        return ans;
    }

    public int distanceTraveled(int mainTank, int additionalTank) {
        int ans = 0;
        while (mainTank >= 5) {
            ans += 50;
            if (additionalTank > 0) {
                additionalTank--;
                mainTank++;
            }
            mainTank -= 5;
        }
        return ans += mainTank * 10;
    }

}
