package com.leetcode.codereview.sort.impl;

import com.leetcode.codereview.sort.Sort;
import com.leetcode.codereview.sort.util.RandomArrayUtils;
import org.testng.annotations.Test;

public class DivideSort implements Sort {

    private static final int INSERTIONSORT_THRESHOLD = 7;

    @Override
    public void sort(int[] nums) {
        this.divideSort(nums);
    }

    private void divideSort(int[] nums) {
        int len = nums.length;
        int[] tmp = new int[len];
        divideSort(nums, 0, len - 1, tmp);
    }

    private void divideSort(int[] nums, int l, int r, int[] tmp) {
        if (l >= r) {
            return;
        }
        if (r - l + 1 <= INSERTIONSORT_THRESHOLD) {
            insertionSort(nums, l, r);
            return ;
        }
        int mid = l + (r - l) / 2;
        divideSort(nums, l, mid, tmp);
        divideSort(nums, mid + 1, r, tmp);
        // 优化一
        if (nums[mid] <= nums[mid + 1]) {
            return;
        }
        merge(nums, l, r, mid, tmp);
    }

    private void insertionSort(int[] nums, int l, int r) {
        for (int i = l + 1; i <= r; i++) {
            int key = nums[i];
            int j = i;
            while (j > l && nums[j - 1] > key) {
                nums[j] = nums[j-1];
                j--;
            }
            nums[j] =key;
        }
    }

    private void merge(int[] nums, int l, int r, int mid, int[] tmp) {
        for (int i = l; i <= r; i++) {
            tmp[i] = nums[i];
        }
        int i = l;
        int j = mid + 1;
        for (int k = l; k <= r; k++) {
            if (i == mid + 1) {
                nums[k] = tmp[j];
                j++;
            } else if (j == r + 1) {
                nums[k] = tmp[i];
                i++;
            } else if (tmp[i] <= tmp[j]) {
                nums[k] = tmp[i];
                i++;
            } else {
                nums[k] = tmp[j];
                j++;
            }
        }
    }

    @Test
    public void test() {
        int[] ints = RandomArrayUtils.testGenerateRandomArray(0, 100000, 100000, this::sort);
        RandomArrayUtils.print(ints);
    }
}
