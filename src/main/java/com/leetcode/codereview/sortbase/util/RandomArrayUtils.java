package com.leetcode.codereview.sortbase.util;

import com.leetcode.codereview.sortbase.Sort;

import java.util.Random;

public class RandomArrayUtils {
    private static Random random = new Random(System.currentTimeMillis());

    public static int[] generateRandomArray(int left, int right, int length) {
        int[] nums = new int[length];
        for (int i = 0; i < length; i++) {
            nums[i] = randomInt(left, right);
        }
        return nums;
    }

    private static int randomInt(int left, int right) {
        int a = random.nextInt(right - left + 1) + left;
        return a;
    }

    public static void test(Sort sort, int[] array) {
        long start = System.currentTimeMillis();
        sort.sort(array);
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - start) + "豪秒");
    }

    public static int[] testGenerateRandomArray(int left, int right, int length, Sort sort) {
        int[] array = generateRandomArray(left, right, length);
        test(sort, array);
        return array;
    }

    public static void print(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (i != 0 && i % 20 == 0) {
                System.out.println();
            }
            System.out.print(array[i] + " ");
        }
    }
}
