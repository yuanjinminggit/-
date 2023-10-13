package com.leetcode.codereview.binarysearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Xuanzhuanpaixushuzu {
    public int findMin(int[] nums) {
        int l = 0;
        int r = nums.length - 1;
        while (l < r) {
            int mid = (l + r) / 2;
            if (nums[mid] < nums[nums.length - 1]) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    public int findMin1(int[] nums) {
        int l = 0;
        int r = nums.length - 1;
        while (l < r) {
            int mid = (l + r) / 2;
            if (nums[mid] < nums[nums.length - 1]) {
                r = mid;
            } else if (nums[mid] == nums[nums.length - 1]) {
                int tmp = mid;
                while (tmp + 1 <= r && nums[tmp] == nums[tmp + 1]) {
                    tmp++;
                }
                if (tmp + 1 == r) {
                    r = mid;
                    continue;
                }
                if (nums[tmp + 1] > nums[tmp]) {
                    l = tmp;
                    continue;
                }
                if (nums[tmp + 1] < nums[tmp]) {
                    return tmp + 1;
                }
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    public int search1(int[] nums, int target) {
        int l = 0;
        int len = nums.length;
        if (nums[len - 1] == target) {
            return len - 1;
        }
        int key = nums[len - 1];
        int r = len - 2;
        while (l < r) {
            int mid = (l + r) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < key) {
                if (target < key) {
                    if (nums[mid] > target) {
                        r = mid - 1;
                    } else {
                        l = mid + 1;
                    }
                } else {
                    r = mid - 1;
                }
            } else {
                if (target < key) {
                    l = mid + 1;
                } else {
                    if (nums[mid] > target) {
                        r = mid - 1;
                    } else {
                        l = mid + 1;
                    }
                }
            }
        }
        if (nums[l] == target) {
            return l;
        }
        return -1;
    }


    public boolean search(int[] nums, int target) {
        int l = 0;
        int len = nums.length;
        if (target == nums[len - 1]) {
            return true;
        }
        int r = len - 2;
        int key = nums[len - 1];
        while (l < r) {
            int mid = (l + r) / 2;
            if (nums[mid] == target) {
                return true;
            }
            if (nums[mid] == key) {
                int tmp = mid;
                while (tmp <= r && nums[tmp] == key) {
                    tmp++;
                }
                if (tmp > r) {
                    r = mid - 1;
                    continue;
                }
                l = tmp;
            } else if (nums[mid] < key) {
                if (target < key) {
                    if (nums[mid] > target) {
                        r = mid - 1;
                    } else {
                        l = mid + 1;
                    }
                } else {
                    r = mid - 1;
                }
            } else {
                if (target < key) {
                    l = mid + 1;
                } else {
                    if (nums[mid] > target) {
                        r = mid - 1;
                    } else {
                        l = mid + 1;
                    }
                }
            }
        }
        return nums[l] == target;
    }


    public int findPeakElement(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        int l = 0;
        int len = nums.length;
        if (nums[len - 1] > nums[len - 2]) {
            return len - 1;
        }
        int r = len - 2;
        while (l < r) {
            int mid = (l + r) / 2;
            if (nums[mid + 1] >= nums[mid]) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return l;
    }


    public int hIndex(int[] citations) {
        int len = citations.length;
        int r = len - 1;
        int l = 0;
        while (l < r) {
            int mid = (l + r) / 2;
            if (citations[mid] >= len - mid) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        if (citations[len - 1] == 0) {
            return 0;
        }
        return len - l;
    }


    public int triangleNumber(int[] nums) {
        int len = nums.length;
        Arrays.sort(nums);
        int count = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] == 0) {
                continue;
            }
            for (int j = i + 1; j < nums.length - 1; j++) {
                int sum = nums[i] + nums[j];
                int l = j + 1;
                int r = len - 1;
                if (nums[l] >= sum) {
                    continue;
                }
                while (l < r) {
                    int mid = (l + r + 1) / 2;
                    if (nums[mid] < sum) {
                        l = mid;
                    } else {
                        r = mid - 1;
                    }
                }
                count += r - j;
            }
        }
        return count;
    }

    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int right = binarySearch0(arr, x);
        int left = right - 1;
        while (k-- > 0) {
            if (left < 0) {
                right++;
            } else if (right == arr.length) {
                left--;
            } else if (x - arr[left] <= arr[right] - x) {
                left--;
            } else {
                right++;
            }
        }
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = left + 1; i < right; i++) {
            ans.add(arr[i]);
        }
        return ans;
    }

    private int binarySearch0(int[] arr, int x) {
        int l = 0, r = arr.length - 1;
        while (l < r) {
            int mid = (l + r) / 2;
            if (arr[mid] >= x) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }
}