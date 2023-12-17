package com.leetcode.codereview.sortbase.impl;

import com.leetcode.codereview.sortbase.Sort;
import com.leetcode.codereview.sortbase.util.RandomArrayUtils;
import org.testng.annotations.Test;

public class BubbleSort implements Sort {

    @Override
    public void sort(int[] nums) {
        this.bubbleSort(nums);
    }

    private void bubbleSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            for (int j = 1; j < nums.length - i; j++) {
                if (nums[j] < nums[j - 1]) {
                    swap(nums, j, j - 1);
                }
            }
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
        int[] ints = RandomArrayUtils.testGenerateRandomArray(0, 1000, 10000, this::sort);
        RandomArrayUtils.print(ints);
    }
}
