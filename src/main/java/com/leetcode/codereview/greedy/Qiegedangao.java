package com.leetcode.codereview.greedy;

import org.testng.annotations.Test;

import java.util.*;

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

    public int maxProduct(String[] words) {
        HashSet<Character> set = new HashSet<Character>();
        int max = 0;
        for (int i = 0; i < words.length; i++) {
            set.clear();
            for (int k = 0; k < words[i].length(); k++) {
                set.add(words[i].charAt(k));
            }
            int l = words[i].length();

            for (int j = i + 1; j < words.length; j++) {
                boolean flag = false;
                for (int k = 0; k < words[j].length(); k++) {
                    if (set.contains(words[j].charAt(k))) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    max = Math.max(l * words[j].length(), max);
                }
                System.out.println(max);
            }
        }
        return max;
    }


    public List<String> findRepeatedDnaSequences(String s) {
        HashMap<String, Integer> map = new HashMap<>();
        ArrayList<String> res = new ArrayList<>();
        for (int i = 10; i < s.length(); i++) {
            String tmp = s.substring(i - 10, i);
            System.out.println(tmp.length());
            map.put(tmp, map.getOrDefault(tmp, 0) + 1);
            if (map.get(tmp) == 2) {
                res.add(tmp);
            }
        }
        return res;
    }

    public int findTheLongestBalancedSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int len = 0;
        int maxLen = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                len++;
            } else {
                int tmpLen = 0;
                while (i < s.length() && s.charAt(i) == '1') {
                    i++;
                    tmpLen++;
                }
                maxLen = Math.max(Math.min(tmpLen, len) * 2, maxLen);
                len = 0;
                i--;
            }
        }
        return maxLen;
    }


    public int vowelStrings(String[] words, int left, int right) {
        int len = 0;
        for (int i = left; i < right; i++) {
            int length = words[i].length();
            if ((words[i].charAt(0) == 'a' || words[i].charAt(0) == 'e' || words[i].charAt(0) == 'i' || words[i].charAt(0) == 'o' || words[i].charAt(0) == 'u') && (
                    words[i].charAt(length - 1) == 'a' || words[i].charAt(length - 1) == 'e' || words[i].charAt(length - 1) == 'i' || words[i].charAt(length - 1) == 'o' || words[i].charAt(length - 1) == 'u')
            ) {
                len++;
            }
        }
        return len;
    }

    public int minDeletion(int[] nums) {
        int n = nums.length, cnt = 0;
        for (int i = 0; i < n; i++) {
            if ((i - cnt) % 2 == 0 && i + 1 < n && nums[i] == nums[i + 1]) {
                cnt++;
            }
        }
        return (n - cnt) % 2 != 0 ? cnt + 1 : cnt;
    }


    public int[] successfulPairs1(int[] spells, int[] potions, long success) {
        int[] ans = new int[spells.length];
        int cnt = 0;
        for (int i = 0; i < spells.length; i++) {
            for (int j = 0; j < potions.length; j++) {
                if (spells[i] * potions[j] >= success) {
                    cnt++;
                }
            }
            ans[i] = cnt;
            cnt = 0;
        }
        return ans;
    }

    public int[] successfulPairs2(int[] spells, int[] potions, long success) {
        Integer[] idx = new Integer[spells.length];
        for (int i = 0; i < idx.length; i++) {
            idx[i] = i;
        }
        Arrays.sort(idx, (a, b) -> spells[a] - spells[b]);
        Arrays.sort(potions);
        int[] res = new int[spells.length];
        int tmp = 0;
        int preIdx = potions.length - 1;
        for (int i = 0; i < idx.length; i++) {
            for (int j = preIdx; j >= 0; j--) {
                if ((long) spells[idx[i]] * potions[j] >= success) {
                    preIdx = j - 1;
                    tmp++;
                } else {
                    break;
                }
            }
            res[idx[i]] = tmp;
        }
        return res;
    }


    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        Arrays.sort(potions);
        int n = spells.length, m = potions.length;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            long target = (success + spells[i] - 1) / spells[i];
            if (target <= potions[potions.length - 1]) {
                spells[i] = potions.length - binarySearch(potions, target);
            } else {
                spells[i] = 0;
            }
        }
        return spells;
    }

    private int binarySearch(int[] potions, long target) {
        int l = 0;
        int r = potions.length - 1;
        while (l < r) {
            int mid = (l + r) / 2;
            if (potions[mid] >= target) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return r;
    }

    public int[] maximumSumQueries(int[] nums1, int[] nums2, int[][] queries) {
        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int x = queries[i][0];
            int y = queries[i][1];
            int max = getMax(nums1, nums2, x, y);
            ans[i] = max;
        }
        return ans;
    }

    private int getMax(int[] nums1, int[] nums2, int x, int y) {
        int n = nums1.length;
        int max = -1;
        for (int i = 0; i < n; i++) {
            if (nums1[i] >= x && nums2[i] >= y) {
                max = Math.max(max, nums1[i] + nums2[i]);
            }
        }
        return max;
    }


    public int maximumSum1(int[] nums) {
        HashMap<Integer, PriorityQueue<Integer>> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int sum = getSum(nums[i]);
            PriorityQueue<Integer> heap = map.getOrDefault(sum, new PriorityQueue<Integer>(Comparator.reverseOrder()));
            heap.offer(nums[i]);
            map.put(sum, heap);
        }
        int max = 0;
        for (PriorityQueue<Integer> queue : map.values()) {
            if (queue.size() >= 2) {
                int t = queue.poll() + queue.poll();
                max = Math.max(t, max);
            }
        }
        return max;
    }

    private int getSum(int num) {
        int sum = 0;
        while (num != 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }


    public int maximumSum(int[] nums) {
        int[] val = new int[100];
        int ans = -1;
        for (int x : nums) {
            int t = x, cur = 0;
            while (t != 0) {
                cur += t % 10;
                t /= 10;
            }
            if (val[cur] != 0) ans = Math.max(ans, cur + val[cur]);
            val[cur] = Math.max(val[cur], x);
        }
        return ans;
    }

    int n;
    int[] nums;
    int k;
    private Data[][] memo;
    private long[] sum;

    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        this.n = nums.length;
        this.nums = nums;
        this.k = k;
        memo = new Data[nums.length][4];
        sum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }
        List<Integer> idx = dfs(0, 3).idx;
        Collections.reverse(idx);
        return idx.stream().mapToInt(v -> v).toArray();
    }

    private Data dfs(int i, int j) {
        if (n - i < k * j) {
            return new Data(new ArrayList<>(), Integer.MIN_VALUE / 2);
        }
        if (j == 0) {
            return new Data(new ArrayList<>(), 0);
        }
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        Data res = null;
        Data a = dfs(i + 1, j);
        Data b = dfs(i + k, j - 1);
        if (a.sum > b.sum + sum[i + k] - sum[i]) {
            res = new Data(new ArrayList<>(a.idx), a.sum);
        } else {
            ArrayList<Integer> list = new ArrayList<>(b.idx);
            list.add(i);
            res = new Data(list, b.sum + sum[i + k] - sum[i]);
        }
        return memo[i][j] = res;
    }


    class Data {
        List<Integer> idx;
        long sum;

        public Data(List<Integer> idx, long sum) {
            this.idx = idx;
            this.sum = sum;
        }
    }


    public int countPairs1(List<Integer> nums, int target) {
        int n = nums.size();
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (nums.get(i) + nums.get(j) < target) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public int countPairs2(List<Integer> nums, int target) {
        Collections.sort(nums);
        int ans = 0;
        for (int i = 0; i < nums.size(); i++) {
            int j = binarySearch0(nums, target - nums.get(i));
            ans += j > i ? j - i : 0;
        }
        return ans;
    }

    private int binarySearch0(List<Integer> nums, int i) {
        int l = 0, r = nums.size() - 1;
        while (l < r) {
            int mid = (l + r + 1) / 2;
            if (nums.get(mid) < i) {
                l = mid;
            } else {
                r = mid - 1;
            }
        }
        if (nums.get(0) >= i) {
            return -1;
        }
        return l;
    }


    public int countPairs(List<Integer> nums, int target) {
        Collections.sort(nums);
        int res = 0;
        for (int i = 0, j = nums.size() - 1; i < j; i++) {
            while (i < j && nums.get(i) + nums.get(j) >= target) {
                j--;
            }
            res += j - i;
        }
        return res;
    }


    public int maximumGap(int[] nums) {
        Arrays.sort(nums);
        int max = 0;
        for (int i = 1; i < nums.length; i++) {
            max = Math.max(nums[i] - nums[i - 1], max);
        }
        return max;
    }


    public int compareVersion1(String version1, String version2) {
        String[] s1 = version1.split("\\.");
        String[] s2 = version2.split("\\.");
        for (int i = 0; i < s1.length; i++) {
            int v1 = Integer.parseInt(s1[i]);
            if (i <= s2.length - 1) {

                int v2 = Integer.parseInt(s2[i]);
                if (v1 > v2) {
                    return 1;
                } else if (v1 < v2) {
                    return -1;
                }
            } else {
                if (v1 > 0) {
                    return 1;
                }
            }
        }

        if (s2.length > s1.length) {
            for (int i = s1.length; i < s2.length; i++) {
                if (Integer.parseInt(s2[i]) > 0) {
                    return -1;
                }
            }
        }
        return 0;
    }


    public int compareVersion(String version1, String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        for (int i = 0; i < v1.length || i < v2.length; i++) {
            int x = 0, y = 0;
            if (i < v1.length) {
                x = Integer.parseInt(v1[i]);
            }
            if (i < v2.length) {
                y = Integer.parseInt(v2[i]);
            }
            if (x > y) {
                return 1;
            }
            if (x < y) {
                return -1;
            }
        }
        return 0;
    }

    public String fractionToDecimal1(int numerator, int denominator) {
        long numeratorLong = (long) numerator;
        long denominatorLong = (long) denominator;
        if (numeratorLong % denominatorLong == 0) {
            return String.valueOf(numeratorLong / denominatorLong);
        }

        StringBuilder sb = new StringBuilder();
        if (numeratorLong < 0 ^ denominatorLong < 0) {
            sb.append("-");
        }

        numeratorLong = Math.abs(numeratorLong);
        denominatorLong = Math.abs(denominatorLong);
        long integerPart = numeratorLong / denominatorLong;
        sb.append(integerPart);
        sb.append('.');


        StringBuilder fractionPart = new StringBuilder();
        HashMap<Long, Integer> remainderIndexMap = new HashMap<>();
        long remainder = numeratorLong % denominatorLong;
        int index = 0;
        while (remainder != 0 && !remainderIndexMap.containsKey(remainder)) {
            remainderIndexMap.put(remainder, index);
            remainder *= 10;
            fractionPart.append(remainder / denominatorLong);
            remainder %= denominatorLong;
            index++;
        }

        if (remainder != 0) {
            int insertIndex = remainderIndexMap.get(remainder);
            fractionPart.insert(insertIndex, '(');
            fractionPart.append(')');
        }
        sb.append(fractionPart.toString());
        return sb.toString();
    }

    public String fractionToDecimal(int numerator, int denominator) {
        long a = numerator, b = denominator;
        if (a % b == 0) return String.valueOf(a / b);
        StringBuilder sb = new StringBuilder();
        if (a * b < 0) sb.append("-");
        a = Math.abs(a);
        b = Math.abs(b);
        sb.append(String.valueOf(a / b) + ".");
        a %= b;
        HashMap<Long, Integer> map = new HashMap<>();
        while (a != 0) {
            map.put(a, sb.length());
            a *= 10;
            sb.append(a / b);
            a %= b;
            if (map.containsKey(a)) {
                int u = map.get(a);
                return String.format("s%(s%)", sb.substring(0, u), sb.substring(u));
            }
        }
        return sb.toString();
    }

    public int titleToNumber(String columnTitle) {
        int length = columnTitle.length();
        char[] chars = columnTitle.toCharArray();
        int sum = 0;
        for (int i = 0; i < chars.length; i++) {
            int num = chars[i] - 'A' + 1;
            sum = sum * 26 + num;
        }
        return sum;
    }


    @Test
    public void test() {
        fractionToDecimal(4, 333);
    }


}
