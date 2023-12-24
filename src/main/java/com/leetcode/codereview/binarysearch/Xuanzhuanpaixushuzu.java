package com.leetcode.codereview.binarysearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

    public long repairCars(int[] ranks, int cars) {
        int min = Integer.MAX_VALUE;
        for (int rank : ranks) {
            min = Math.min(rank, min);
        }
        long left = 0, right = min * cars * cars;
        while (left < right) {
            long mid = left + (right - left) / 2;
            if (check(mid, ranks, cars)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return right;
    }

    private boolean check(long mid, int[] ranks, int cars) {
        int sum = 0;
        for (int rank : ranks) {
            sum += Math.sqrt(mid / rank);
        }
        return sum >= cars;
    }

    int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int minimumEffortPath(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;
        int left = 0;
        int right = 99999;
        while (left < right) {
            int mid = (left + right) / 2;
            Queue<int[]> queue = new LinkedList<>();
            queue.offer(new int[]{0, 0});
            boolean[] seen = new boolean[m * n];
            seen[0] = true;
            while (!queue.isEmpty()) {
                int[] cell = queue.poll();
                int x = cell[0];
                int y = cell[1];
                for (int i = 0; i < 4; i++) {
                    int nx = x + dirs[i][0];
                    int ny = y + dirs[i][1];
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && !seen[nx * n + ny] && Math.abs(heights[x][y] - heights[nx][ny]) <= mid) {
                        queue.offer(new int[]{nx, ny});
                        seen[nx * n + ny] = true;
                    }
                }
            }
            if (seen[m * n - 1]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return right;
    }

    public int findPeakElement1(int[] nums) {
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
            if (nums[mid + 1] > nums[mid]) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return l;
    }

    public int[] findPeakGrid(int[][] mat) {
        int left = 0, right = mat.length - 1;
        while (left < right) {
            int i = (left + right) >>> 1;
            int j = indexOfMax(mat[i]);
            if (mat[i][j] > mat[i + 1][j]) {
                right = i;
            } else {
                left = i + 1;
            }
        }
        return new int[]{left, indexOfMax(mat[left])};
    }

    private int indexOfMax(int[] a) {
        int idx = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > a[idx]) {
                idx = i;
            }
        }
        return idx;
    }

    public boolean isAcronym(List<String> words, String s) {
        if (s.length() != words.size()) {
            return false;
        }
        int idx = 0;
        for (String word : words) {
            if (word.charAt(0) == s.charAt(idx)) {
                idx++;
            } else {
                return false;
            }
        }
        return true;
    }

    public long minimumPerimeter(long neededApples) {
        long left = 1, right = 100001;
        while (left < right) {
            long mid = (left + right) / 2;
            if (2 * mid * (mid + 1) * (mid * 2 + 1) >= neededApples) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left*4;
    }
}