package com.leetcode.codereview.queue;

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


    @Test
    public void test() {
        System.out.println(leastInterval(new char[]{'A', 'A', 'A', 'A', 'A', 'A', 'B', 'C', 'D', 'E', 'F', 'G'}, 2));
    }
}
