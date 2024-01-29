package com.leetcode.codereview.binarysearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
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
        return left * 4;
    }

    public int maxNumberOfAlloys(int n, int k, int budget, List<List<Integer>> composition, List<Integer> Stock, List<Integer> Cost) {
        int ans = 0;
        int mx = Collections.min(Stock) + budget;
        int[] stock = Stock.stream().mapToInt(i -> i).toArray();
        int[] cost = Cost.stream().mapToInt(i -> i).toArray();
        for (List<Integer> Comp : composition) {
            int[] comp = Comp.stream().mapToInt(i -> i).toArray();
            int left = ans, right = mx + 1;
            while (left < right) {
                int mid = left + (right - left + 1) / 2;
                boolean ok = true;
                long money = 0;
                for (int i = 0; i < n; i++) {
                    if (stock[i] < (long) comp[i] * mid) {
                        money += ((long) comp[i] * mid - stock[i]) * cost[i];
                        if (money > budget) {
                            ok = false;
                            break;
                        }
                    }
                }
                if (ok) {
                    left = mid;
                } else {
                    right = mid - 1;
                }
            }
            ans = left;
        }
        return ans;
    }


    public boolean canMeasureWater(int x, int y, int z) {
        if (z == 0) {
            return true;
        }
        if (x + y < z) {
            return false;
        }

        State initState = new State(0, 0);
        LinkedList<State> queue = new LinkedList<>();
        HashSet<State> visited = new HashSet<>();
        queue.offer(initState);
        visited.add(initState);
        while (!queue.isEmpty()) {
            State head = queue.poll();
            int curX = head.getX();
            int curY = head.getY();
            if (curX == z || curY == z || curY + curX == z) {
                return true;
            }
            List<State> nextStates = getNextStates(curX, curY, x, y);
            for (State nextState : nextStates) {
                if (!visited.contains(nextState)) {
                    queue.offer(nextState);
                    visited.add(nextState);
                }
            }
        }
        return false;
    }

    private List<State> getNextStates(int curX, int curY, int x, int y) {
        ArrayList<State> nextStates = new ArrayList<>(8);

        // 1. 倒满x
        State state1 = new State(x, curY);
        // 2.倒满y
        State state2 = new State(curX, y);

        // x清空
        State state3 = new State(0, curY);
        // y清空
        State state4 = new State(curX, 0);

        // x倒y
        State state5 = new State(curX - (y - curY), y);
        State state6 = new State(0, curY + curX);
        // y倒x
        State state7 = new State(x, curY - (x - curX));
        State state8 = new State(curX + curY, 0);
        // 加水条件
        if (curX < x) {
            nextStates.add(state1);
        }
        if (curY < y) {
            nextStates.add(state2);
        }
        // 倒掉条件
        if (curX > 0) {
            nextStates.add(state3);
        }
        if (curY > 0) {
            nextStates.add(state4);
        }
        // 倒水条件
        if (curX + curY > y) {
            nextStates.add(state5);
        }
        if (curX + curY > x) {
            nextStates.add(state7);
        }

        if (curX + curY < y) {
            nextStates.add(state6);
        }
        if (curX + curY < x) {
            nextStates.add(state8);
        }
        return nextStates;
    }

    private class State {
        private int x;
        private int y;

        public State(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "State{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            State state = (State) o;
            return x == state.x &&
                    y == state.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }


}