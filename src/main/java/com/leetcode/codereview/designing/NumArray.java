package com.leetcode.codereview.designing;

class NumArray {

    SegmentTree st;
    int n;

    public NumArray(int[] nums) {
        this.n = nums.length;
        st = new SegmentTree(n);
        for (int i = 0; i < n; i++) {
            st.modify(0, 0, n - 1, i, nums[i]);
        }
    }

    public void update(int index, int val) {
        st.modify(0, 0, n - 1, index, val);
    }

    public int sumRange(int left, int right) {
        return st.query(0, 0, n - 1, left, right);
    }

    class SegmentTree {
        int[] sum;

        public SegmentTree(int n) {
            sum = new int[4 * n];
        }

        public void modify(int cur, int l, int r, int idx, int val) {
            if (l == r) {
                sum[cur] = val;
                return;
            }
            int mid = l + (r - l) / 2;
            if (idx <= mid) {
                modify(2 * cur + 1, l, mid, idx, val);
            }
            if (idx > mid) {
                modify(2 * cur + 2, mid + 1, r, idx, val);
            }
            sum[cur] = sum[cur * 2 + 1] + sum[cur * 2 + 2];
        }

        public int query(int cur, int l, int r, int ql, int qr) {
            if (l >= ql && r <= qr) {
                return sum[cur];
            }
            int res = 0;
            int mid = (r + l) / 2;
            if (ql <= mid) {
                res += query(cur * 2 + 1, l, mid, ql, qr);
            }
            if (qr > mid) {
                res += query(cur * 2 + 2, mid + 1, r, ql, qr);
            }
            return res;
        }
    }
}