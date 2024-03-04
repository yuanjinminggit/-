package com.leetcode.codereview.designing;

import java.util.TreeSet;

public class MyCalendar {
    TreeSet<int[]> booked;

    public MyCalendar() {
        booked = new TreeSet<>((a, b) -> a[0] - b[0]);
    }

    public boolean book(int start, int end) {
        if (booked.isEmpty()) {
            booked.add(new int[]{start, end});
            return true;
        }
        int[] tmp = {end, 0};
        int[] arr = booked.ceiling(tmp);
        int[] prev = arr == null ? booked.last() : booked.lower(arr);
        if (arr == booked.first() || prev != null && prev[1] <= start) {
            booked.add(new int[]{start, end});
            return true;
        }
        return false;
    }
}

//public class MyCalendar {
//
//
//    public MyCalendar() {
//
//    }
//
//    public boolean book(int start, int end) {
//
//    }
//
//    class Node {
//        Node left, right;
//        int val, add;
//    }
//
//    private int N = (int) 1e9;
//    private Node root = new Node();
//
//    public void update(Node node, int start, int end, int l, int r, int val) {
//        if (l <= start && end <= r) {
//            node.val += val;
//        }
//    }
//}