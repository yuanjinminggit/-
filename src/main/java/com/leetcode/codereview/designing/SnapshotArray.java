package com.leetcode.codereview.designing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SnapshotArray {

    private int curSnapId;

    private final Map<Integer, List<int[]>> history = new HashMap<>();

    public SnapshotArray(int length) {

    }

    public void set(int index, int val) {
        history.computeIfAbsent(index, k -> new ArrayList<>()).add(new int[]{curSnapId, val});
    }

    public int snap() {
        return curSnapId++;
    }

    public int get(int index, int snap_id) {
        if (!history.containsKey(index)) {
            return 0;
        }
        List<int[]> h = history.get(index);
        int j = search(h, snap_id);
        return j < 0 ? 0 : h.get(j)[1];
    }

    private int search(List<int[]> h, int x) {
        int left = 0;
        int right = h.size() - 1;
        if (h.get(right)[0] < x) {
            return -1;
        }
        while (left < right) {
            int mid = (left + right + 1) / 2;
            if (h.get(mid)[0] <= x) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        if (h.get(left)[0] < x) {
            return -1;
        }
        return left;
    }
}