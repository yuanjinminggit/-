package com.leetcode.codereview.kmp;

import org.testng.annotations.Test;

import java.util.Arrays;

public class Zuiduanhuiwenchuan {
    public String shortestPalindrome(String s) {
        if (s.equals("")) {
            return "";
        }
        int n = s.length();
        StringBuilder sb = new StringBuilder(s);
        String pattern = s + "#" + sb.reverse().toString();
        int i = getNext(pattern);
        String common = s.substring(0, i);
        String substring = sb.substring(0, n - i);
        return substring + common + new StringBuilder(substring).reverse().toString();
    }

    private int getNext(String pat) {
        char[] pattern = pat.toCharArray();
        int n = pat.length();
        int[] prefix = new int[n];
        int i = 1;
        int len = 0;
        while (i < n) {
            if (pattern[i] == pattern[len]) {
                len++;
                prefix[i] = len;
                i++;
            } else {
                if (len > 0) {
                    len = prefix[len - 1];
                } else {
                    prefix[i] = len;
                    i++;
                }
            }
        }
        return prefix[n - 1];
    }


    public int uniqueLetterString(String s) {
        int ans = 0, total = 0;
        int[] last0 = new int[26];
        int[] last1 = new int[26];
        Arrays.fill(last0, -1);
        Arrays.fill(last1, -1);
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'A';
            total += i - 2 * last0[c] + last1[c];
            ans += total;
            last1[c] = last0[c];
            last0[c] = i;
        }
        return ans;
    }


    public boolean repeatedSubstringPattern1(String s) {
        int n = s.length();
        for (int i = 1; i * 2 <= n; ++i) {
            if (n % i == 0) {
                boolean match = true;
                for (int j = i; j < n; j++) {
                    if (s.charAt(j) != s.charAt(j - i)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean repeatedSubstringPattern(String s) {
        char[] chars = s.toCharArray();
        int n = s.length();
        int[] prefix = new int[n];
        getPrefix(chars, prefix, n);
        movePrefix(prefix);
        return kmpSearch(s + s, prefix, chars);
    }


    private boolean kmpSearch(String s, int[] prefix, char[] pattern) {
        int i = 0, j = 0;
        int n = pattern.length;
        while (i < s.length()) {
            if (j == n - 1 && s.charAt(i) == pattern[j] && i != n - 1 && i != s.length() - 1) {
                return true;
            }
            if (j == n - 1 && s.charAt(i) == pattern[j]) {
                j = prefix[j];
                if (j == -1) {
                    i++;
                    j++;
                }
            }
            if (s.charAt(i) == pattern[j]) {
                i++;
                j++;
            } else {
                j = prefix[j];
                if (j == -1) {
                    i++;
                    j++;
                }
            }
        }
        return false;
    }

    private void getPrefix(char[] pattern, int[] prefix, int n) {
        int len = 0;
        int i = 1;
        while (i < n) {
            if (pattern[i] == pattern[len]) {
                len++;
                prefix[i] = len;
                i++;
            } else {
                if (len > 0) {
                    len = prefix[len - 1];
                } else {
                    prefix[i] = len;
                    i++;
                }

            }
        }
    }

    public void movePrefix(int[] prefix) {
        int n = prefix.length;
        for (int i = n - 1; i > 0; i--) {
            prefix[i] = prefix[i - 1];
        }
        prefix[0] = -1;
    }


    @Test
    public void test() {
        shortestPalindrome("abcd");
    }

}
