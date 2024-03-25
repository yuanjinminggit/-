package com.leetcode.codereview.designing;

import java.util.HashMap;
import java.util.Map;

class FrequencyTracker {
    private final Map<Integer, Integer> cnt = new HashMap<>();
    private final Map<Integer, Integer> freq = new HashMap<>();

    public FrequencyTracker() {

    }

    private void update(int number, int delta) {
        int c = cnt.merge(number, delta, Integer::sum);
        freq.merge(c - delta, -1, Integer::sum);
        freq.merge(c, 1, Integer::sum);
    }

    public void add(int number) {
        update(number, 1);
    }

    public void deleteOne(int number) {
        if (freq.getOrDefault(number, 0) > 0) {
            update(number, -1);
        }
    }

    public boolean hasFrequency(int frequency) {
        return freq.getOrDefault(frequency, 0) > 0;
    }
}