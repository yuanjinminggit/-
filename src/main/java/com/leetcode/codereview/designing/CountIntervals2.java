package com.leetcode.codereview.designing;

import java.util.Map;
import java.util.TreeMap;

class CountIntervals2 {

    TreeMap<Integer, Integer> map = new TreeMap<>();
    int cnt = 0;

    public CountIntervals2() {

    }

    public void add(int left, int right) {
        Map.Entry<Integer, Integer> interval = map.floorEntry(right);
        // 左闭右开，只要map中的右端点大于等于待插入集合的左端点，都可以合并
        while (interval != null && interval.getValue() >= left) {
            Integer l = interval.getKey();
            Integer r = interval.getValue();
            left = Math.min(left, l);
            right = Math.max(right, r);
            cnt -= r - l + 1;
            map.remove(l);
            interval = map.floorEntry(right);
        }
        cnt += right - left + 1;
        map.put(left, right);
    }

    public int count() {
        return cnt;
    }
}