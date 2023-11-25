package com.leetcode.codereview.kmp;

public class KMP {
    public void prefixTable(char pattern[], int[] prefix, int n) {
        prefix[0] = 0;
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

    public void movePrefixTable(int prefix[], int n) {
        for (int i = n - 1; i > 0; i--) {
            prefix[i] = prefix[i - 1];
        }
        prefix[0] = -1;
    }

    public void kmpSearch(char[] txt, char[] pattern) {
        int n = pattern.length;
        int[] prefix = new int[n];
        prefixTable(pattern, prefix, n);
        movePrefixTable(prefix, n);
        int m = txt.length;
        // txt [i]  m
        // pattern [j]  n
        int i = 0, j = 0;
        while (i < m) {
            if (j == n - 1 && txt[i] == pattern[j]) {
                System.out.println("found pattern" + (i - j));
                j = prefix[j];
            }
            if (txt[i] == pattern[j]) {
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
    }
}
