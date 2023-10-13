package com.leetcode.codereview.slidingwindow;

import java.util.ArrayList;
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

//    public List<Integer> findSubstring(String s, String[] words) {
//
//    }

}
