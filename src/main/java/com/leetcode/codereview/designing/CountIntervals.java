package com.leetcode.codereview.designing;

public class CountIntervals {
    CountIntervals left;
    CountIntervals right;
    int l, r, cnt;

    public CountIntervals() {
        l = 1;
        r = (int) 1e9;
    }

    public CountIntervals(int l, int r) {
        this.l = l;
        this.r = r;
    }

    public void add(int L, int R) {
        if (cnt == r - l + 1) {
            return;
        }
        if (L <= l && R >= r) {
            cnt = r - l + 1;
            return;
        }
        int mid = (l + r) / 2;
        if (left == null) {
            left = new CountIntervals(l, mid);
        }
        if (right == null) {
            right = new CountIntervals(mid + 1, r);
        }
        if (L <= mid) {
            left.add(L, R);
        }
        if (R > mid) {
            right.add(L, R);
        }
        cnt = left.cnt + right.cnt;
    }

    public int Count() {
        return cnt;
    }
}
