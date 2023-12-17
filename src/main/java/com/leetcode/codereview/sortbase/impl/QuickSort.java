package com.leetcode.codereview.sortbase.impl;

import com.leetcode.codereview.sortbase.Sort;
import com.leetcode.codereview.sortbase.util.RandomArrayUtils;
import org.testng.annotations.Test;

import java.util.Random;

public class QuickSort implements Sort {

    private static final int INSERTIONSORT_THRESHOLD = 7;

    @Override
    public void sort(int[] nums) {
        this.quickSort(nums);
    }

    public int[] quickSort(int[] nums) {
        threePathQuickSort2(nums, 0, nums.length - 1);
        return nums;
    }

    private void quickSort(int[] nums, int l, int r) {
        if (l >= r) {
            return;
        }
        int pivotIndex = twoPathPartition(nums, l, r);
        quickSort(nums, l, pivotIndex - 1);
        quickSort(nums, pivotIndex + 1, r);
    }

    // 造成递归树倾斜，而且顺序数组，性能差
    private int partition(int[] nums, int l, int r) {
        int pivot = nums[l];
//        [l+1,j]维护<=pivot
//        (j,i)>pivot
//        int j = l;
//        for (int i = l + 1; i <= r; i++) {
//            if (nums[i] <= pivot) {
//                j++;
//                swap(nums, i, j);
//            }
//        }


//      [l+1,j)<=pivot
//      [j,i)>pivot
        int j = l + 1;
        for (int i = l + 1; i <= r; i++) {
            if (nums[i] <= pivot) {
                swap(nums, i, j);
                j++;
            }
        }
        swap(nums, l, j - 1);
        return j - 1;
    }

    private final static Random random = new Random(System.currentTimeMillis());

    private int randomPartition(int[] nums, int l, int r) {
        int random = QuickSort.random.nextInt(r - l + 1) + l;
        swap(nums, random, l);
        return this.partition(nums, l, r);
    }

    // 将与pivot相等的元素平均分到数组两边
    private int twoPathPartition(int[] nums, int l, int r) {
        int random = QuickSort.random.nextInt(r - l + 1) + l;
        swap(nums, random, l);
        int pivot = nums[l];
//        [l+1,le)<=pivot
//        (ge,r]>=pivot
        int le = l + 1;
        int ge = r;
        while (true) {
            while (le <= ge && nums[le] < pivot) {
                le++;
            }
            while (le <= ge && nums[ge] > pivot) {
                ge--;
            }
            if (le >= ge) {
                break;
            }
            swap(nums, le, ge);
            le++;
            ge--;
        }
        swap(nums, l, ge);
        return ge;
    }


    private void threePathQuickSort(int[] nums, int l, int r) {
        if (l >= r) {
            return;
        }
        if (r - l + 1 <= INSERTIONSORT_THRESHOLD) {
            insertionSort(nums, l, r);
            return;
        }
        int random = QuickSort.random.nextInt(r - l + 1) + l;
        swap(nums, random, l);
        int pivot = nums[l];
//        [l+1,lt)
//        [lt,i)
//        (gt,r]

        int lt = l + 1;
        int gt = r;
        int i = l + 1;
        while (i <= gt) {
            if (nums[i] < pivot) {
                swap(nums, i, lt);
                lt++;
                i++;
            } else if (nums[i] == pivot) {
                i++;
            } else {
                swap(nums, i, gt);
                gt--;
            }
        }
        swap(nums, l, lt - 1);
        threePathQuickSort(nums, l, lt - 2);
        threePathQuickSort(nums, gt + 1, r);

    }

    private void insertionSort(int[] nums, int l, int r) {
        for (int i = l + 1; i <= r; i++) {
            int key = nums[i];
            int j = i;
            while (j > 0 && nums[j - 1] > key) {
                nums[j] = nums[j - 1];
                j--;
            }
            nums[j] = key;
        }
    }


    private void threePathQuickSort2(int[] nums, int l, int r) {
        if (l >= r) {
            return;
        }
        int random = QuickSort.random.nextInt(r - l + 1) + l;
        swap(nums, random, l);
        int pivot = nums[l];
//        [l+1,lt]
//        (lt,i)
//        [gt,r]

        int lt = l;
        int gt = r + 1;
        int i = l + 1;
        while (i <= gt) {
            if (nums[i] < pivot) {
                lt++;
                swap(nums, i, lt);
                i++;
            } else if (nums[i] == pivot) {
                i++;
            } else {
                gt--;
                swap(nums, i, gt);
            }
        }
        swap(nums, l, lt);
        threePathQuickSort(nums, l, lt - 1);
        threePathQuickSort(nums, gt, r);

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
