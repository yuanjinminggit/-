package com.leetcode.codereview.sortbase.impl;

import com.leetcode.codereview.sortbase.Sort;
import com.leetcode.codereview.sortbase.util.RandomArrayUtils;
import org.testng.annotations.Test;

public class HeapSort implements Sort {
    @Override
    public void sort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        for (int i = n - 1; i >= 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    private void heapify(int[] arr, int n, int i) {
        int largetest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && arr[left] > arr[largetest]) {
            largetest = left;
        }
        if (right < n && arr[right] > arr[left]) {
            largetest = right;
        }
        if (largetest != i) {
            swap(arr, largetest, i);
            heapify(arr, n, largetest);
        }
    }

    private void swap(int[] nums, int i, int j) {
        if (i == j) {
            return;
        }
        int a = nums[i];
        nums[i] = nums[j];
        nums[j] = a;
    }

    @Test
    public void test() {
        int[] ints = RandomArrayUtils.testGenerateRandomArray(0, 100000000, 1000000, this::sort);
//        RandomArrayUtils.print(ints);
    }
}
