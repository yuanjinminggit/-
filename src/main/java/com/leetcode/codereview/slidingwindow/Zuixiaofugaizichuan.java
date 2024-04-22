package com.leetcode.codereview.slidingwindow;

import org.testng.annotations.Test;
import org.testng.collections.Sets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

public class Zuixiaofugaizichuan {
    public String minWindow(String s, String t) {
        int[] tFreq = new int[128];
        for (char c : t.toCharArray()) {
            tFreq[c]++;
        }
        int distance = t.length();
        int minLen = s.length() + 1;
        int begin = 0;
        int left = 0;
        int right = 0;
        while (right < s.length()) {
            if (tFreq[s.charAt(right)] > 0) {
                distance--;
            }
            tFreq[s.charAt(right)]--;
            right++;
            while (distance == 0) {
                if (right - left < minLen) {
                    begin = left;
                    minLen = right - left;
                }
                if (tFreq[s.charAt(left)] == 0) {
                    distance++;
                }
                tFreq[s.charAt(left)]++;
                left++;
            }
        }
        return minLen == s.length() + 1 ? "" : s.substring(begin, begin + minLen);
    }

    public List<Integer> findAnagrams(String s, String p) {
        int sLen = s.length();
        ArrayList<Integer> res = new ArrayList<>();
        if (sLen == 0) {
            return res;
        }
        int pLen = p.length();
        int[] window = new int[128];
        int[] pattern = new int[128];

        int sCount = 0;
        int pCount = 0;
        for (char c : p.toCharArray()) {
            pattern[c]++;
        }
        for (int i = 0; i < 128; i++) {
            if (pattern[i] > 0) {
                pCount++;
            }
        }
        int left = 0;
        int right = 0;
        while (right < sLen) {
            if (pattern[s.charAt(right)] > 0) {
                window[s.charAt(right)]++;
                if (pattern[s.charAt(right)] == window[s.charAt(right)]) {
                    sCount++;
                }
            }
            right++;
            if (sCount == pCount) {
                if (right - left == pLen) {
                    res.add(left);
                }
                if (pattern[s.charAt(left)] > 0) {
                    window[s.charAt(left)]--;
                    if (window[s.charAt(left)] < pattern[s.charAt(left)]) {
                        sCount--;
                    }
                }
            }
        }
        return res;
    }

    private String[] words;

    public List<Integer> findSubstring(String s, String[] words) {
        this.words = new String[words.length];
        System.arraycopy(words, 0, this.words, 0, words.length);
        int length = words[0].length();
        HashSet<Integer> ans = new HashSet<>();
        dfsfindSubstring(s, 0, words, length, ans);
        return new ArrayList<>(ans);

    }

    private void dfsfindSubstring(String s, int idx, String[] words, int length, HashSet<Integer> ans) {
        boolean b = Arrays.stream(words).allMatch(v -> v.equals(""));
        if (b) {
            // 答案走到这里添加了几次，取决于走到这里有多少个分支
            ans.add(idx - words.length * length);
            return;
        }
        if (idx + length - 1 >= s.length()) {
            return;
        }

        String s1 = s.substring(idx, idx + length);
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(s1)) {
                words[i] = "";
                dfsfindSubstring(s, idx + length, words, length, ans);
                words[i] = s1;
            }
        }
        String[] newW = new String[words.length];
        System.arraycopy(this.words, 0, newW, 0, words.length);
        dfsfindSubstring(s, idx + 1, newW, length, ans);
    }

    public int characterReplacement(String s, int k) {
        int[] freq = new int[128];

        int left = 0;
        int right = 0;
        int count = 0;
        while (right < s.length()) {
            freq[s.charAt(right)]++;
            count = Math.max(count, freq[s.charAt(right)]);
            right++;
            if (right - left > k + count) {
                freq[s.charAt(left)]--;
                left++;
            }
        }
        return right - left;
    }

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
            while (i < n && nums[i] <= threshold && nums[i] % 2 != nums[i + 1] % 2) {
                i++;
            }
            ans = Math.max(ans, i - start);
        }
        return ans;
    }

    public int maxPower(String s) {
        char[] charArray = s.toCharArray();
        int length = charArray.length;
        int ans = 0, i = 0;
        while (i < length) {
            if (i + 1 < length && charArray[i] != charArray[i + 1]) {
                i++;
                continue;
            }
            int tmp = 0;
            char c = charArray[i];
            while (i < length && charArray[i] == c) {
                tmp++;
                i++;
            }
            ans = Math.max(tmp, ans);
        }
        return ans;
    }

    public String makeSmallestPalindrome(String s) {
        char[] charArray = s.toCharArray();
        int i = 0, j = s.length() - 1;
        while (i < j) {
            charArray[i] = charArray[j] = (char) Math.min(charArray[i], charArray[j]);
            i++;
            j--;
        }
        return new String(charArray);
    }

    public int[] secondGreaterElement(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Arrays.fill(res, -1);
        ArrayList<Integer> s = new ArrayList<>();
        ArrayList<Integer> t = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            while (t.size() > 0 && nums[t.get(t.size() - 1)] < nums[i]) {
                res[t.get(t.size() - 1)] = nums[i];
                t.remove(t.size() - 1);
            }

            int pos = s.size() - 1;
            while (pos >= 0 && nums[s.get(pos)] < nums[i]) {
                pos--;
            }
            for (int j = pos + 1; j < s.size(); j++) {
                t.add(s.get(j));
            }
            for (int j = s.size() - 1; j > pos; --j) {
                s.remove(j);
            }
            s.add(i);
        }
        return res;
    }

    public List<Integer> numOfBurgers(int a, int b) {
        int x = a - 2 * b;
        int y = 4 * b - a;
        if (x >= 0 && y >= 0 && x % 2 == 0 && y % 2 == 0) {
            return Arrays.asList(x / 2, y / 2);
        }
        return new ArrayList<>();
    }

    private int[][] memo;

    public int maxStudents(char[][] seats) {
        int m = seats.length;
        int n = seats[0].length;
        memo = new int[m][1 << n];
        for (int[] ints : memo) {
            Arrays.fill(ints, -1);
        }
        int[] a = new int[m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (seats[i][j] == '.') {
                    // 最终结果就是全部都倒过来
                    a[i] |= 1 << j;
                }
            }
        }
        return dfs(m - 1, a[m - 1], a);
    }

    private int dfs(int i, int j, int[] a) {
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        if (i == 0) {
            if (j == 0) {
                return 0;
            }
            int lb = j & -j;
            return memo[i][j] = dfs(i, j & ~(lb * 3), a) + 1;
        }
        // 第i排空着
        int res = dfs(i - 1, a[i - 1], a);
        // 枚举j的子集s
        for (int s = j; s > 0; s = (s - 1) & j) {
            if ((s & (s >> 1)) == 0) {
                int t = a[i - 1] & ~(s << 1 | s >> 1);
                res = Math.max(res, dfs(i - 1, t, a) + Integer.bitCount(s));
            }
        }
        return memo[i][j] = res;
    }

    public int isWinner(int[] player1, int[] player2) {
        int sum1 = getSum(player1);
        int sum2 = getSum(player2);
        return sum1 > sum2 ? 1 : sum1 == sum2 ? 0 : 2;
    }

    private int getSum(int[] player1) {
        int count = 0;
        int sum = 0;
        for (int i : player1) {
            if (count == 0) {
                sum += i;
            } else {
                count--;
                sum += 2 * i;
            }

            if (i == 10) {
                count = 2;
            }
        }
        return sum;
    }

    public int buyChoco(int[] prices, int money) {
        if (prices.length == 1) {
            return money;
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int price : prices) {
            heap.offer(price);
        }
        int sum = 0;
        for (int i = 0; i < 2; i++) {
            sum += heap.poll();
        }
        return money >= sum ? money - sum : money;
    }

    @Test
    public void test() {
        longestAlternatingSubarray(new int[]{3, 2, 5, 4}, 5);
    }

    @Test
    public void testMapMerge() {
        Map<Integer, Set<Integer>> map1 = new HashMap<>();
        map1.put(1, Sets.newHashSet(2, 3, 4));
        map1.put(2, Sets.newHashSet(7, 8, 9));

        Map<Integer, Set<Integer>> map2 = new HashMap<>();
        map2.put(1, Sets.newHashSet(4, 5, 6));

        map1.forEach(
                (key, value) ->
                        map2.merge(key, value,
                                (v1, v2) -> {
                                    Set<Integer> mergeSet = new HashSet<>();
                                    mergeSet.addAll(v1);
                                    mergeSet.addAll(v2);
                                    return mergeSet;
                                }));
        System.out.println(map2);
    }

    @Test
    public void test123() {
        ArrayList<Stuedent> a = new ArrayList<>();
        Map<Integer, List<String>> collect = a.stream().collect(
                Collectors.groupingBy(Stuedent::getId,
                        Collectors.mapping(Stuedent::getName,
                                Collectors.toList())));
        System.out.println(collect);
    }

    class Stuedent {
        private Integer id;
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public int minOperations(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int m = 1;
        for (int i = 1; i < n; i++) {
            if (nums[i] != nums[i - 1]) {
                nums[m++] = nums[i];
            }
        }
        int ans = 0;
        int left = 0;
        for (int i = 0; i < m; i++) {
            while (nums[left] < nums[i] - n + 1) {
                left++;
            }
            ans = Math.max(ans, i - left + 1);
        }
        return n - ans;
    }




}
