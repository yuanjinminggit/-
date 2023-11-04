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



    @Test
    public void test() {
        System.out.println(leastInterval(new char[]{'A', 'A', 'A', 'A', 'A', 'A', 'B', 'C', 'D', 'E', 'F', 'G'}, 2));
    }
}
