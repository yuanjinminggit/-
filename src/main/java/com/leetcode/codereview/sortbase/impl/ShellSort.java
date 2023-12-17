package com.leetcode.codereview.sortbase.impl;

import com.leetcode.codereview.sortbase.Sort;
import com.leetcode.codereview.sortbase.util.RandomArrayUtils;
import org.testng.annotations.Test;

public class ShellSort implements Sort {
    @Override
    public void sort(int[] nums) {
        this.shellSort(nums);
    }

    private void shellSort(int[] nums) {
        int len = nums.length;
        // 分组个数
        for (int group = len / 2; group > 0; group /= 2) {
            // 枚举起始点
            for (int i = 0; i <= group; i++) {
                // 插入排序
                for (int j = i + group; j < len; j += group) {
                    int key = nums[j];
                    int k = j;
                    while (k > i && nums[k - group] > key) {
                        nums[k] = nums[k - group];
                        k -= group;
                    }
                    nums[k] = key;
                }
            }
        }
    }

    @Test
    public void test() {
        int[] ints = RandomArrayUtils.testGenerateRandomArray(0, 100000, 100000, this::sort);
        RandomArrayUtils.print(ints);
    }

}
