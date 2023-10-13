package com.leetcode.codereview.sort.impl;

import com.leetcode.codereview.sort.Sort;
import com.leetcode.codereview.sort.util.RandomArrayUtils;
import org.testng.annotations.Test;

public class SelectSort implements Sort {
    @Override
    public void sort(int[] nums) {
        this.selectSort(nums);
    }

    private void selectSort(int[] nums) {
        int len = nums.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                if (nums[j] < nums[i]) {
                    swap(nums, i, j);
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
        int[] ints = RandomArrayUtils.testGenerateRandomArray(0, 100000, 100000, this::sort);
        RandomArrayUtils.print(ints);
    }
}
