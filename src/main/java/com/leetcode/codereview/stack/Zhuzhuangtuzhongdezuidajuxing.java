package com.leetcode.codereview.stack;

import org.testng.annotations.Test;

import java.util.*;

public class Zhuzhuangtuzhongdezuidajuxing {

    /*枚举右边界*/
    public int largestRectangleArea1(int[] heights) {
        int max = heights[0];
        for (int i = 0; i < heights.length; i++) {
            int min = heights[i];
            for (int j = i; j < heights.length; j++) {
                min = Math.min(min, heights[j]);
                max = Math.max((j - i + 1) * min, max);
            }
        }
        return max;
    }

    /*枚举高*/
    public int largestRectangleArea3(int[] heights) {
        int max = 0;
        for (int i = 0; i < heights.length; i++) {
            int left, right;
            left = right = i;
            while (left - 1 >= 0 && heights[left - 1] >= heights[i]) {
                left--;
            }
            while (right + 1 < heights.length && heights[right + 1] >= heights[i]) {
                right++;
            }
            max = Math.max((right - left + 1) * heights[i], max);
        }
        return max;
    }

    public int largestRectangleArea(int[] heights) {
        int len = heights.length;
        if (len == 0) {
            return 0;
        }
        if (len == 1) {
            return heights[0];
        }
        int area = 0;
        int[] newHeights = new int[len + 2];
        for (int i = 0; i < len; i++) {
            newHeights[i + 1] = heights[i];
        }
        len += 2;
        heights = newHeights;
        Deque<Integer> stack = new LinkedList<>();
        stack.push(0);
        for (int i = 0; i < len; i++) {
            while (heights[i] < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                int width = i - stack.peek() - 1;
                area = Math.max(area, width * height);
            }
            stack.push(i);
        }

        return area;
    }

    public String removeDuplicateLetters(String s) {
        Deque<Character> stack = new LinkedList<>();
        int len = s.length();
        char[] charArray = s.toCharArray();
        int[] lastIndex = new int[26];
        for (int i = 0; i < len; i++) {
            lastIndex[charArray[i] - 'a'] = i;
        }
        boolean[] visited = new boolean[26];
        for (int i = 0; i < len; i++) {
            if (visited[charArray[i] - 'a']) {
                continue;
            }
            while (!stack.isEmpty() && stack.peek() > charArray[i] && lastIndex[stack.peek() - 'a'] > i) {
                Character top = stack.pop();
                visited[top - 'a'] = false;
            }
            stack.push(charArray[i]);
            visited[charArray[i] - 'a'] = true;
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.removeFirst());
        }
        return sb.reverse().toString();
    }

    public boolean find132pattern(int[] nums) {
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < len; j++) {
                if (nums[j] > nums[i]) {
                    for (int k = j + 1; k < len; k++) {
                        if (nums[k] < nums[j] && nums[k] > nums[i]) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public int[] nextGreaterElement1(int[] nums1, int[] nums2) {
        int[] ans = new int[nums1.length];
        Arrays.fill(ans, -1);
        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                if (nums1[i] == nums2[j]) {
                    for (int k = j + 1; k < nums2.length; k++) {
                        if (nums2[k] > nums2[j]) {
                            ans[i] = nums2[k];
                        }
                    }
                }
            }
        }
        return ans;
    }

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Deque<Integer> stack = new LinkedList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums2.length; i++) {
            int num = nums2[i];
            while (!stack.isEmpty() && stack.peek() < nums2[i]) {
                Integer pop = stack.pop();
                map.put(pop, num);
            }
            stack.push(num);
        }
        int[] res = new int[nums1.length];

        for (int i = 0; i < nums1.length; i++) {
            res[i] = map.getOrDefault(nums1[i], -1);
        }
        return res;
    }

    public int findUnsortedSubarray1(int[] nums) {
        int[] newInt = new int[nums.length];
        System.arraycopy(nums, 0, newInt, 0, nums.length);
        Arrays.sort(newInt);
        int start = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != newInt[i]) {
                start = i;
                break;
            }
        }
        int end = -1;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] != newInt[i]) {
                end = i;
                break;
            }
        }
        if (start == -1) {
            return 0;
        }
        return end - start + 1;
    }

    public int findUnsortedSubarray3(int[] nums) {
        int n = nums.length;
        int[] arr = nums.clone();
        Arrays.sort(arr);
        int i = 0, j = n - 1;
        while (i <= j && nums[i] == arr[i]) i++;
        while (i <= j && nums[j] == arr[j]) j--;
        return j - i + 1;
    }

    public int findUnsortedSubarray(int[] nums) {
        int n = nums.length;
        int i = 0, j = n - 1;
        while (i < j && nums[i] <= nums[i + 1]) i++;
        while (i < j && nums[j] >= nums[j - 1]) j--;
        int l = i, r = j;
        int min = nums[i], max = nums[j];
        for (int u = l; u <= r; u++) {
            if (nums[u] < min) {
                while (i >= 0 && nums[i] > nums[u]) i--;
                min = i >= 0 ? nums[i] : Integer.MIN_VALUE;
            }
            if (nums[u] > max) {
                while (j < n && nums[j] < nums[u]) j++;
                max = j < n ? nums[j] : max;
            }
        }
        return j == i ? 0 : j - i - 1;
    }

    public int[] dailyTemperatures1(int[] temperatures) {
        int len = temperatures.length;
        int[] res = new int[len];
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (temperatures[j] > temperatures[i]) {
                    res[i] = j - i;
                    break;
                }
            }
        }
        return res;
    }

    public int[] dailyTemperatures(int[] temperatures) {
        Deque<Integer> stack = new LinkedList<>();
        int[] res = new int[temperatures.length];
        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                Integer pop = stack.pop();
                res[pop] = i - pop;
            }
            stack.push(i);
        }
        return res;
    }

    public int maxWidthRamp1(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] >= nums[i]) {
                    max = Math.max(j - i, max);
                }
            }
        }
        return max;
    }

    public int maxWidthRamp(int[] nums) {
        int max = 0;
        int len = nums.length;
        Integer[] array = new Integer[len];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        Arrays.sort(array, (a, b) -> nums[a] - nums[b] == 0 ? a - b : nums[a] - nums[b]);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            min = Math.min(array[i], min);
            max = Math.max(array[i] - min, max);
        }
        return max;
    }

    private int[][] leftMin;

    public int sumSubarrayMins1(int[] arr) {
        int len = arr.length;
        leftMin = new int[len][len];
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j <= i; j++) {
                sum += dfsgetMin(arr, j, i);
                ;
            }
        }
        return sum;
    }

    private int dfsgetMin(int[] arr, int l, int r) {
        if (l == r) {
            return arr[l];
        }
        if (leftMin[l][r] != 0) {
            return leftMin[l][r];
        }
        if (arr[l] < arr[r]) {
            return leftMin[l][r] = Math.min(dfsgetMin(arr, l, r - 1), arr[l]);
        } else {
            return leftMin[l][r] = Math.min(dfsgetMin(arr, l + 1, r), arr[r]);
        }
    }

    public int sumSubarrayMins2(int[] arr) {
        int MOD = 1_000_000_007;
        int n = arr.length;
        Deque<Integer> stack = new LinkedList<>();
        // 计算左边第一个比当前元素小的下标
        int[] prev = new int[n];
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[i] <= arr[stack.peek()]) {
                stack.pop();
            }
            prev[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        Deque<Integer> stack2 = new LinkedList<>();
        int[] next = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            while (!stack2.isEmpty() && arr[i] < arr[stack2.peek()]) {
                stack2.pop();
            }
            next[i] = stack2.isEmpty() ? n : stack2.peek();
            stack2.push(i);
        }
        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans += (long) (i - prev[i]) * (next[i] - i) % MOD * arr[i] % MOD;
            ans %= MOD;
        }
        return (int) ans;
    }

    public int sumSubarrayMins(int[] arr) {
        int MOD = 1000000007;
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        long ans = 0;
        // 枚举左边界
        for (int i = 0; i < n; i++) {
            int min = arr[i];
            for (int j = i; j < n; j++) {
                min = Math.min(min, arr[j]);
                ans = (ans + min) % MOD;
            }
        }
        return (int) ans;
    }

    public int[] dailyTemperatures12(int[] temperatures) {
        int n = temperatures.length;
        int[] ans = new int[n];
        Deque<Integer> stack = new LinkedList<Integer>();
//        for (int i = n - 1; i >= 0; i--) {
//            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
//                stack.pop();
//            }
//            if (stack.isEmpty()) {
//                ans[i] = 0;
//            } else {
//                ans[i] = stack.peek() - i;
//            }
//            stack.push(i);
//        }

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                ans[stack.peek()] = i - stack.peek();
                stack.pop();
            }
            stack.push(i);
        }
        return ans;
    }

    public int[] finalPrices(int[] prices) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int[] ans = new int[prices.length];
        for (int i = 0; i < prices.length; i++) {
            while (!stack.isEmpty() && prices[i] <= prices[stack.peek()]) {
                ans[stack.peek()] = prices[stack.peek()] - prices[i];
                stack.pop();
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            ans[stack.peek()] = prices[stack.peek()];
            stack.pop();
        }
        return ans;
    }

    public int uniqueLetterString(String s) {
        HashMap<Character, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (!map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), new ArrayList<>());
                map.get(s.charAt(i)).add(-1);
            }
            map.get(s.charAt(i)).add(i);
        }
        int res = 0;
        for (List<Integer> value : map.values()) {
            value.add(s.length());
            int n = value.size();
            for (int i = 1; i < n - 1; i++) {
                res += (value.get(i) - value.get(i - 1)) * (value.get(i + 1) - value.get(i));
            }
        }
        return res;
    }

    private int MOD = (int) 1e9 + 7;

    public int sumSubarrayMins123(int[] nums) {
        int n = nums.length;
        int[] l = new int[n];
        ArrayDeque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            int cur = nums[i];
            while (!stack.isEmpty() && nums[stack.peek()] >= cur) {
                stack.pop();
            }

            l[i] = i - (stack.isEmpty() ? -1 : stack.peek()) - 1;
            stack.push(i);
        }
//        for (int i = 1; i < n; i++) {
//            int p = i - 1;
//            while (p >= 0 && nums[p] >= nums[i]) {
//                p--;
//            }
//            l[i] = i - p - 1;
//        }
        int[] r = new int[n];
//        for (int i = n - 2; i >= 0; --i) {
//            int p = i + 1;
//            while (p < n && nums[p] > nums[i]) ++p;
//            r[i] = p - i - 1;
//        }
        stack.clear();
        for (int i = n - 2; i >= 0; i--) {
            int cur = nums[i];
            while (!stack.isEmpty() && nums[stack.peek()] > cur) {
                stack.pop();
            }
            r[i] = (stack.isEmpty() ? n : stack.peek()) - i - 1;
            stack.push(i);
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            res = (int) (res + ((l[i] + r[i] + (long) l[i] * r[i] + 1) * nums[i]) % MOD) % MOD;
        }
        return res;
    }

    public long maximumSumOfHeights(List<Integer> maxHeights) {
        int max = 0;
        for (int i = 0; i < maxHeights.size(); i++) {
            int sum = getTopSum(i, maxHeights);
            max = Math.max(sum, max);
        }
        return max;
    }

    private int getTopSum(int i, List<Integer> maxHeights) {
        int sum = maxHeights.get(i);
        int max = sum;
        for (int j = i; j >= 1; j--) {
            if (maxHeights.get(j - 1) <= maxHeights.get(j)) {
                sum += maxHeights.get(j - 1);
                max = maxHeights.get(j - 1);
            } else {
                sum += max;
            }
        }
        max = maxHeights.get(i);
        for (int j = i; j < maxHeights.size() - 1; j++) {
            if (maxHeights.get(j + 1) <= maxHeights.get(j)) {
                sum += maxHeights.get(j + 1);
                max = maxHeights.get(j + 1);
            } else {
                sum += max;
            }
        }
        return sum;
    }

    @Test
    public void test() {
        maxWidthRamp(new int[]{6, 0, 8, 2, 1, 5});
    }
}
