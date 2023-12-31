package com.leetcode.codereview.priorityqueue;

import org.testng.annotations.Test;

import java.util.*;

public class Renwudiaoduqi {

    public int leastInterval1(char[] tasks, int n) {
        if (n == 0) {
            return tasks.length;
        }
        Integer[] freq = new Integer[26];
        Arrays.fill(freq, 0);
        for (char task : tasks) {
            freq[task - 'A']++;
        }
        Integer[] array = Arrays.stream(freq).filter(v -> v != 0).sorted((a, b) -> b - a).toArray(Integer[]::new);
        return getTotal(n, array, array.length);

    }

    private int getTotal(int n, Integer[] array, int end) {
        if (end == 1) {
            return (array[0] - 1) * n + array[0];
        }
        if (n >= array.length - 1) {
            return getTotal(n, array, end - 1) + array[0] == array[end] ? 1 : 0;
        }
        if (end >= n) {
            for (int i = end - n; i <= end - 1; i++) {
                array[i] -= array[end];
            }
            array[0] = array[0] - array[end];
            if (array[0] < array[1]) {
                for (int i = 1; i < array.length; i++) {
                    if (array[i] > array[0]) {
                        array[i - 1] = array[i];
                    } else {
                        array[i - 1] = array[0];
                        break;
                    }
                }
            }
        }
        return getTotal(n, array, end - 1);

    }

    public int leastInterval(char[] tasks, int n) {
        int[] count = new int[26];
        for (char task : tasks) {
            count[task - 'A']++;
        }
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(26, Comparator.reverseOrder());
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                maxHeap.offer(count[i]);
            }
        }
        int res = 0;
        Queue<Integer> queue = new LinkedList<>();
        while (!maxHeap.isEmpty()) {
            for (int i = 0; i < n + 1; i++) {
                if (!maxHeap.isEmpty()) {
                    int front = maxHeap.poll();
                    if (front > 1) {
                        queue.offer(front - 1);
                    }
                }
                res++;
                if (maxHeap.isEmpty() && queue.isEmpty()) {
                    break;
                }
            }
            while (!queue.isEmpty()) {
                maxHeap.offer(queue.poll());
            }
        }
        return res;
    }

    public int[] maxSlidingWindow1(int[] nums, int k) {
        if (k == 1) {
            return nums;
        }
        PriorityQueue<int[]> heap = new PriorityQueue<int[]>((a, b) -> a[0] - b[0] == 0 ? a[1] - b[1] : b[0] - a[0]);
        for (int i = 0; i < k; i++) {
            heap.offer(new int[]{nums[i], i});
        }
        int[] res = new int[nums.length - k + 1];
        res[0] = heap.peek()[0];
        for (int i = k; i < nums.length; i++) {
            heap.offer(new int[]{nums[i], i});
            while (!heap.isEmpty() && heap.peek()[1] <= i - k) {
                heap.poll();
            }
            res[i - k + 1] = heap.peek()[0];
        }
        return res;
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        LinkedList<Integer> deque = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);
        }
        int[] ans = new int[n - k + 1];
        ans[0] = nums[deque.peekFirst()];
        for (int i = 0; i < n; i++) {
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            deque.offerLast(i);
            while (deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            ans[i - k + 1] = nums[deque.peekFirst()];
        }
        return ans;
    }

    class MedianFinder {
        PriorityQueue<Integer> queMin;
        PriorityQueue<Integer> queMax;

        public MedianFinder() {
            queMin = new PriorityQueue<>(Comparator.reverseOrder());
            queMax = new PriorityQueue<>();
        }

        public void addNum(int num) {
            if (queMin.isEmpty() || num <= queMin.peek()) {
                queMin.offer(num);
                if (queMax.size() + 1 < queMin.size()) {
                    queMax.offer(queMin.poll());
                }
            } else {
                queMax.offer(num);
                if (queMax.size() > queMin.size()) {
                    queMin.offer(queMax.poll());
                }
            }
        }

        public double findMedian() {
            if (queMin.size() > queMax.size()) {
                return queMin.peek();
            }
            return (queMin.peek() + queMax.peek()) / 2.0;
        }
    }

    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses, (a, b) -> a[1] - b[1]);
        PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.reverseOrder());
        int day = 0;
        for (int[] c : courses) {
            int duration = c[0], lastDay = c[1];
            if (day + duration <= lastDay) {
                day += duration;
                heap.offer(duration);
            } else if (!heap.isEmpty() && duration < heap.peek()) {
                day -= heap.poll() - duration;
                heap.offer(duration);
            }
        }
        return heap.size();
    }

    public List<List<Integer>> kSmallestPairs1(int[] nums1, int[] nums2, int k) {
        PriorityQueue<Minsum> heap = new PriorityQueue<>((a, b) -> b.sum - a.sum);
        int n = nums1.length;
        for (int i : nums1) {
            for (int j : nums2) {
                if (heap.size() <= Math.min(n, k)) {
                    heap.offer(new Minsum(i + j, Arrays.asList(i, j)));
                } else {
                    if (i + j < heap.peek().sum) {
                        heap.poll();
                        heap.offer(new Minsum(i + j, Arrays.asList(i, j)));
                    }
                }
            }
        }
        System.out.println(heap.size());
        ArrayList<List<Integer>> res = new ArrayList<>();
        while (!heap.isEmpty()) {
            res.add(heap.poll().list);
        }
        return res;
    }

    class Minsum {
        int sum;
        List<Integer> list;

        public Minsum(int sum, List<Integer> list) {
            this.sum = sum;
            this.list = list;
        }
    }

    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(k, (o1, o2) -> {
            return nums1[o1[0]] + nums2[o1[1]] - nums1[o2[0]] - nums2[o2[1]];
        });
        ArrayList<List<Integer>> ans = new ArrayList<>();
        int m = nums1.length;
        int n = nums2.length;
        for (int i = 0; i < Math.min(m, k); i++) {
            pq.offer(new int[]{i, 0});
        }
        while (k-- > 0 && !pq.isEmpty()) {
            int[] idxPair = pq.poll();
            List<Integer> list = new ArrayList<>();
            list.add(nums1[idxPair[0]]);
            list.add(nums2[idxPair[1]]);
            ans.add(list);
            if (idxPair[1] + 1 < n) {
                pq.offer(new int[]{idxPair[0], idxPair[1] + 1});
            }
        }
        return ans;
    }

    public int maximizeSum1(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int num : nums) {
            heap.offer(num);
        }
        int sum = 0;
        for (int i = 0; i < k; i++) {
            Integer num = heap.poll();
            sum += num;
            heap.offer(num + 1);
        }
        return sum;
    }

    public int maximizeSum(int[] nums, int k) {
        int m = Arrays.stream(nums).max().getAsInt();
        return (2 * m + k - 1) * k / 2;
    }

    public int minStoneSum(int[] piles, int k) {
        heapify(piles);
        while (k-- > 0 && piles[0] != 1) {
            piles[0] -= piles[0] / 2;
            sink(piles, 0);
        }
        int ans = 0;
        for (int pile : piles) {
            ans += pile;
        }
        return ans;
    }

    private void heapify(int[] h) {
        for (int i = h.length / 2 - 1; i >= 0; i--) {
            sink(h, i);
        }
    }

    private void sink(int[] h, int i) {
        int n = h.length;
        while (2 * i + 1 < n) {
            int j = 2 * i + 1;
            if (j + 1 < n && h[j + 1] > h[j]) {
                j++;
            }
            if (h[j] <= h[i]) {
                break;
            }
            swap(h, i, j);
            i = j;
        }
    }

    private void swap(int[] h, int i, int j) {
        int tmp = h[i];
        h[i] = h[j];
        h[j] = tmp;
    }

    public long maximumSumOfHeights(List<Integer> maxHeights) {
        long res = 0;
        int n = maxHeights.size();
        long[] pre = new long[n];
        long[] suf = new long[n];
        ArrayDeque<Integer> stack1 = new ArrayDeque<>();
        ArrayDeque<Integer> stack2 = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack1.isEmpty() && maxHeights.get(i) < maxHeights.get(stack1.peek())) {
                stack1.pop();
            }
            if (stack1.isEmpty()) {
                pre[i] = (long) (i + 1) * maxHeights.get(i);
            } else {
                pre[i] = pre[stack1.peek()] + (long) (i - stack1.peek()) * maxHeights.get(i);
            }
            stack1.push(i);
        }
        for (int i = n - 1; i >= 0; i--) {
            while (!stack2.isEmpty() && maxHeights.get(i) < maxHeights.get(stack2.peek())) {
                stack2.pop();
            }
            if (stack2.isEmpty()) {
                suf[i] = (long) (n - i) * maxHeights.get(i);
            } else {
                suf[i] = suf[stack2.peek()] + (long) (stack2.peek() - i) * maxHeights.get(i);
            }
            stack2.push(i);
        }
        for (int i = 0; i < n; i++) {
            res = Math.max(res, pre[i] + suf[i] - maxHeights.get(i));
        }
        return res;
    }

    private int[] predp;
    private int[] sufdp;

    public int minimumMountainRemovals(int[] nums) {
        int n = nums.length;

        predp = new int[n];
        sufdp = new int[n];
        Arrays.fill(predp, -1);
        Arrays.fill(sufdp, -1);
        getLISArray(nums, predp);
        int[] reversed = reverse(nums);
        getLISArray(reversed, sufdp);
        int[] suf = reverse(sufdp);
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (predp[i] > 1 && suf[i] > 1) {
                ans = Math.max(ans, predp[i] + suf[i] - 1);
            }
        }
        return n - ans;

    }

    public int[] reverse(int[] nums) {
        int n = nums.length;
        int[] reverse = new int[n];
        for (int i = 0; i < n; i++) {
            reverse[i] = nums[n - 1 - i];
        }
        return reverse;
    }

    private void getLISArray(int[] nums, int[] predp) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            dfsgetLISArray(i, predp, nums);
        }
    }

    private int dfsgetLISArray(int i, int[] predp, int[] nums) {
        if (predp[i] != -1) {
            return predp[i];
        }
        int max = 0;
        for (int j = 0; j < i; j++) {
            if (nums[j] < nums[i]) {
                max = Math.max(max, dfsgetLISArray(j, predp, nums));
            }
        }
        return predp[i] = max + 1;
    }

    public String dayOfTheWeek(int day, int month, int year) {
        String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        int[] nums = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int ans = 3;
        for (int i = 1971; i < year; i++) {
            boolean isLeap = (i % 4 == 0 && i % 100 != 0) || i % 400 == 0;
            ans += isLeap ? 366 : 365;
        }
        for (int i = 1; i < month; i++) {
            ans += nums[i - 1];
            if (i == 2 && ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)) {
                ans++;
            }
        }
        ans += day;
        return week[ans % 7];
    }

    @Test
    public void test() {
        System.out.println(leastInterval(new char[]{'A', 'A', 'A', 'A', 'A', 'A', 'B', 'C', 'D', 'E', 'F', 'G'}, 2));
    }
}
