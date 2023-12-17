package com.leetcode.codereview.designing;

import java.util.Map;
import java.util.TreeMap;

class CountIntervals1 {
    private int count;
    private TreeMap<Integer, Integer> intervalTreeMap;

    public CountIntervals1() {
        this.count = 0;
        intervalTreeMap = new TreeMap<>();
    }

    public void add(int left, int right) {
        // 区间做笔友开
        right++;
        if (intervalTreeMap.size() == 0) {
            updateInterval(left, right);
            return;
        }
        if (left > intervalTreeMap.lastEntry().getValue() || right < intervalTreeMap.firstEntry().getKey()) {
            updateInterval(left, right);
            return;
        }
        Map.Entry<Integer, Integer> entry = intervalTreeMap.floorEntry(left);
        if (entry == null) {
            entry = intervalTreeMap.firstEntry();
        }
        for (; entry != null && right >= entry.getKey(); entry = intervalTreeMap.higherEntry(entry.getKey())) {
            if (entry.getKey() < left && left <= entry.getValue()) {
                left = entry.getKey();
                deleteInterval(entry.getKey(), entry.getValue());
            }
            if (entry.getKey() <= right && right < entry.getValue()) {
                right = entry.getValue();
                deleteInterval(entry.getKey(), entry.getValue());
            }
            if (left <= entry.getKey() && entry.getValue() <= right) {
                deleteInterval(entry.getKey(),entry.getValue());
            }
        }
        updateInterval(left,right);
    }

    public void deleteInterval(int left, int right) {
        if (intervalTreeMap.remove(left) != null) {
            this.count -= right - left;
        }
    }

    private void updateInterval(int left, int right) {
        intervalTreeMap.put(left, right);
        this.count += right - left;
    }

    public int count() {
        return this.count;
    }
}