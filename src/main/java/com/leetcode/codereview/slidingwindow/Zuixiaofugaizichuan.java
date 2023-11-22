package com.leetcode.codereview.slidingwindow;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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

    @Test
    public void test() {
        longestAlternatingSubarray(new int[]{3, 2, 5, 4}, 5);
    }

}
