package com.leetcode.codereview.designing;

import java.util.Map;
import java.util.TreeMap;

class RangeModule {

    TreeMap<Integer,Integer> intervals;
    public RangeModule() {
        intervals = new TreeMap<Integer,Integer>();
    }
    
    public void addRange(int left, int right) {
        Map.Entry<Integer, Integer> entry = intervals.higherEntry(left);
        if (entry!=intervals.firstEntry()){

        }
    }
    
    public boolean queryRange(int left, int right) {

    }
    
    public void removeRange(int left, int right) {

    }
}