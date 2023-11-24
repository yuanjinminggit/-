package com.leetcode.codereview.other;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/*
 * 分组循环
 *
 * */
public class GroupingCycle {

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
            while (i < n && nums[i] <= threshold && nums[i] % 2 != nums[i - 1] % 2) {
                i++;
            }
            ans = Math.max(ans, i - start);
        }
        return ans;
    }


    public boolean checkZeroOnes(String s) {
        int n = s.length();
        int[] cnt = new int[2];
        int i = 0;
        while (i < n) {
            int start = i;
            ++i;
            while (i < n && s.charAt(i) == s.charAt(start)) {
                i++;
            }
            cnt[s.charAt(start) - '0'] = Math.max(cnt[s.charAt(start) - '0'], i - start);
        }
        return cnt[1] > cnt[0];
    }

    public String makeFancyString(String s) {
        int n = s.length();
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (i < n) {
            int start = i;
            sb.append(s.charAt(i));
            ++i;
            int cnt = 0;
            while (i < n && s.charAt(i) == s.charAt(start) && cnt < 1) {
                sb.append(s.charAt(i));
                i++;
                cnt++;
            }
            while (i < n && cnt == 1 && s.charAt(i) == s.charAt(start)) {
                i++;
            }
        }
        return sb.toString();
    }


    private static Map<String, String> map = new HashMap<String, String>() {{
        put("&quot;", "\"");
        put("&apos;", "'");
        put("&amp;", "&");
        put("&gt;", ">");
        put("&lt;", "<");
        put("&frasl;", "/");
    }};

    public String entityParser(String text) {
        int n = text.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; ) {
            if (text.charAt(i) == '&') {
                int j = i + 1;
                while (j < n && j - i < 6 && text.charAt(j) != ';') j++;
                String s = text.substring(i, Math.min(j + 1, n));
                if (map.containsKey(s)) {
                    sb.append(map.get(s));
                    i = j + 1;
                    continue;
                }
            }
            sb.append(text.charAt(i++));
        }
        return sb.toString();
    }

    @Test
    public void test() {
        makeFancyString("leeetcode");
    }

}
