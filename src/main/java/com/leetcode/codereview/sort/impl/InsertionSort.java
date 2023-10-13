package com.leetcode.codereview.sort.impl;

import com.leetcode.codereview.sort.Sort;
import com.leetcode.codereview.sort.util.RandomArrayUtils;
import org.testng.annotations.Test;

public class InsertionSort implements Sort {
    @Override
    public void sort(int[] nums) {
        this.insertionSort(nums);
    }

    private void insertionSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int key = nums[i];
            int k = i;
            while (k > 0 && nums[k - 1] > key) {
                nums[k] = nums[k - 1];
                k--;
            }
           nums[k] =key;
        }
    }

    @Test
    public void test() {
        int[] ints = RandomArrayUtils.testGenerateRandomArray(0, 1000, 10000, this::sort);
        RandomArrayUtils.print(ints);
    }

}
