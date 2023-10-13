package com.leetcode.codereview.divide;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Nixudui {
    public int reversePairs1(int[] nums) {
        int len = nums.length;
        if (len < 2) {
            return 0;
        }
        int[] tmp = new int[len];
        return reversePairs1(nums, 0, len - 1, tmp);
    }

    private int reversePairs1(int[] nums, int l, int r, int[] tmp) {
        if (l >= r) {
            return 0;
        }
        int mid = (l + r) / 2;
        int m = reversePairs1(nums, l, mid, tmp);
        int n = reversePairs1(nums, mid + 1, r, tmp);
        if (nums[mid] <= nums[mid + 1]) {
            return m + n;
        }
        int merge = merge(nums, l, r, mid, tmp);
        return m + n + merge;
    }

    private int merge(int[] nums, int l, int r, int mid, int[] tmp) {
        int i = l;
        int j = mid + 1;
        for (int k = l; k <= r; k++) {
            tmp[i] = nums[i];
        }
        int count = 0;
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
                count += mid - i + 1;
            }
        }
        return count;
    }


    public List<Integer> countSmaller1(int[] nums) {
        int len = nums.length;
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (nums[j] < nums[i]) {
                    ans[i]++;
                }
            }
        }

        return IntStream.of(ans).boxed().collect(Collectors.toList());
    }


    public List<Integer> countSmaller(int[] nums) {
        int len = nums.length;
        if (len == 1) {
            return Arrays.asList(0);
        }
        int[] indexTmp = new int[len];
        for (int i = 0; i < indexTmp.length; i++) {
            indexTmp[i] = i;
        }
        int[] tmp = new int[len];
        int[] ans = new int[len];
        countSmaller(nums, 0, len - 1, indexTmp, tmp, ans);
        return IntStream.of(ans).boxed().collect(Collectors.toList());
    }

    private void countSmaller(int[] nums, int l, int r, int[] indexTmp, int[] tmp, int[] ans) {
        if (l >= r) {
            return;
        }
        int mid = (l + r) / 2;
        countSmaller(nums, l, mid, indexTmp, tmp, ans);
        countSmaller(nums, mid + 1, r, indexTmp, tmp, ans);
        if (nums[indexTmp[mid]] <= nums[indexTmp[mid + 1]]) {
            return;
        }
        countmerge(nums, l, r, mid, indexTmp, tmp, ans);
    }

    private void countmerge(int[] nums, int l, int r, int mid, int[] indexTmp, int[] tmp, int[] ans) {
        for (int i = l; i <= r; i++) {
            tmp[i] = indexTmp[i];
        }
        int i = l;
        int j = mid + 1;
        for (int k = l; k <= r; k++) {
            if (i == mid + 1) {
                indexTmp[k] = tmp[j];
                j++;
            } else if (j == r + 1) {
                indexTmp[k] = tmp[i];
                i++;
                ans[indexTmp[k]] += r - mid;
            } else if (nums[tmp[i]] <= nums[tmp[j]]) {
                indexTmp[k] = tmp[i];
                i++;
                ans[indexTmp[k]] += j - mid - 1;
            } else {
                indexTmp[k] = tmp[j];
                j++;
            }
        }
    }

    public int reversePairs(int[] nums) {
        int len = nums.length;
        if (len < 2) {
            return 0;
        }
        int[] tmp = new int[len];
        return reversePairs(nums, 0, len - 1, tmp);
    }

    private int reversePairs(int[] nums, int l, int r, int[] tmp) {
        if (l >= r) {
            return 0;
        }
        int mid = (l + r) / 2;
        int m = reversePairs(nums, l, mid, tmp);
        int n = reversePairs(nums, mid + 1, r, tmp);
        int c = merge0(nums, l, r, mid, tmp);
        return m + n + c;
    }

    private int merge0(int[] nums, int l, int r, int mid, int[] tmp) {
        for (int i = l; i <= r; i++) {
            tmp[i] = nums[i];
        }
        int i = l;
        int j = mid + 1;
        int count = 0;
        while (i <= mid && j <= r) {
            if ((long) nums[i] > 2 * (long) nums[j]) {
                j++;
            } else {
                i++;
                count += (j - mid - 1);
            }
        }
        while (i <= mid) {
            i++;
            count += (r - mid);
        }
        i = l;
        j = mid + 1;
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
        return count;
    }

    public String frequencySort(String s) {
        int[] ints = new int[128];
        for (char c : s.toCharArray()) {
            ints[c]++;
        }
        char[] chars = s.toCharArray();
        frequencySort(chars, 0, chars.length - 1, ints);
        for (char aChar : chars) {
            System.out.println(aChar);
        }
        return new String(chars);
    }

    private void frequencySort(char[] chars, int l, int r, int[] ints) {
        if (l >= r) {
            return;
        }
        int[] partition = partition(chars, l, r, ints);
        frequencySort(chars, l, partition[0], ints);
        frequencySort(chars, partition[1], r, ints);
    }

    private int[] partition(char[] chars, int l, int r, int[] ints) {
        int pivot = ints[chars[l]];
//        [l+1,lt)<pivot
//        [lt,i)=pivot
//        (gt,r]<pivot
        int lt = l + 1;
        int gt = r;
        int i = l + 1;
        while (i <= gt) {
            if (ints[chars[i]] > pivot) {
                swap(chars, i, lt);
                lt++;
                i++;
            } else if (ints[chars[i]] == pivot) {
                i++;
            } else {
                swap(chars, i, gt);
                gt--;
            }
        }
        swap(chars, l, lt - 1);
        return new int[]{lt - 2, gt + 1};
    }

    private void swap(char[] nums, int i, int j) {
        if (i == j) {
            return;
        }
        char a = nums[i];
        nums[i] = nums[j];
        nums[j] = a;
    }


}
