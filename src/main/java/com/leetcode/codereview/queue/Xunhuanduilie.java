package com.leetcode.codereview.queue;

import java.util.*;

public class Xunhuanduilie {

    class MyCircularQueue {
        private int front;
        private int rear;
        private int capacity;
        private int[] elements;

        // 循环队列满的时候队列中永远空出一个元素,capacity为数组的长度
        // front 指向队列中第一个元素的位置
        // rear 指向队列尾部的下一个位置，即从队尾入队元素的位置
        public MyCircularQueue(int k) {
            capacity = k + 1;
            elements = new int[capacity];
            rear = front = 0;
        }

        public boolean enQueue(int value) {
            if (isFull()) {
                return false;
            }
            elements[rear] = value;
            rear = (rear + 1) % capacity;
            return true;
        }

        public boolean deQueue() {
            if (isEmpty()) {
                return false;
            }
            front = (front + 1) % capacity;
            return true;
        }

        public int Front() {
            if (isEmpty()) {
                return -1;
            }
            return elements[front];
        }

        public int Rear() {
            if (isEmpty()) {
                return -1;
            }
            // 有可能越界
            return elements[(rear - 1 + capacity) % capacity];
        }

        public boolean isEmpty() {
            return rear == front;
        }

        public boolean isFull() {
            return ((rear + 1) % capacity) == front;
        }
    }


    class MyCircularDeque {

        private int front;
        private int rear;
        private int capacity;
        private int elements[];

        public MyCircularDeque(int k) {
            capacity = k + 1;
            elements = new int[capacity];
            front = rear = 0;
        }

        public boolean insertFront(int value) {
            if (isFull()) {
                return false;
            }
            front = (front - 1 + capacity) % capacity;
            elements[front] = value;
            return true;
        }

        public boolean insertLast(int value) {
            if (isFull()) {
                return false;
            }
            elements[rear] = value;
            rear = (rear + 1) % capacity;
            return true;
        }

        public boolean deleteFront() {
            if (isEmpty()) {
                return false;
            }
            front = (front + 1) % capacity;
            return true;
        }

        public boolean deleteLast() {
            if (isEmpty()) {
                return false;
            }
            rear = (rear - 1 + capacity) % capacity;
            return true;
        }

        public int getFront() {
            if (isEmpty()) {
                return -1;
            }
            return elements[front];
        }

        public int getRear() {
            if (isEmpty()) {
                return -1;
            }
            return elements[(rear - 1 + capacity) % capacity];
        }

        public boolean isEmpty() {
            return rear == front;
        }

        public boolean isFull() {
            return (rear + 1) % capacity == front;
        }
    }


    class RecentCounter {
        private Queue<Integer> queue;

        public RecentCounter() {
            queue = new LinkedList<Integer>();
        }

        public int ping(int t) {
            queue.offer(t);
            while (!queue.isEmpty() && queue.peek() < t - 3000) {
                queue.poll();
            }
            return queue.size();
        }
    }

    public int findKthLargest2(int[] nums, int k) {
        PriorityQueue<Integer> minheap = new PriorityQueue<>(k, Comparator.comparingInt(a -> a));
        for (int i = 0; i < k; i++) {
            minheap.offer(nums[i]);
        }
        for (int i = k; i < nums.length; i++) {
            if (nums[i] > minheap.peek()) {
                minheap.poll();
                minheap.offer(nums[i]);
            }
        }
        return minheap.peek();
    }

    public int findKthLargest(int[] nums, int k) {
        int heapSize = nums.length;
        buildMaxHeap(nums, heapSize);
        for (int i = nums.length - 1; i >= nums.length + 1 - k; i--) {
            swap(nums, 0, i);
            maxHeapify(nums, 0, i);
        }
        return nums[0];
    }

    private void buildMaxHeap(int[] nums, int heapSize) {
        for (int i = heapSize / 2; i >= 0; i--) {
            maxHeapify(nums, i, heapSize);
        }
    }

    private void maxHeapify(int[] nums, int idx, int heapSize) {
        int l = 2 * idx + 1;
        int r = l + 1;
        int largest = l;

        if (l >= heapSize) {
            return;
        }
        if (r < heapSize && nums[r] > nums[l]) {
            largest = r;
        }
        if (nums[idx] < nums[largest]) {
            swap(nums, idx, largest);
            maxHeapify(nums, largest, heapSize);
        }
    }

    private void swap(int[] nums, int i, int j) {
        if (i == j) {
            return;
        }
        int a = nums[i];
        nums[i] = nums[j];
        nums[j] = a;
    }


    public int nthSuperUglyNumber1(int n, int[] primes) {
        if (n == 1) {
            return 1;
        }
        PriorityQueue<Long> queue = new PriorityQueue<>();
        HashSet<Long> set = new HashSet<>();
        set.add(1l);
        queue.offer(1l);
        int i = 0;
        while (i++ < n) {
            Long a = queue.poll();
            for (int prime : primes) {
                if (set.add(a * prime)) {
                    queue.offer(a * prime);
                }
            }
        }
        return queue.poll().intValue();
    }


    public int nthSuperUglyNumber(int n, int[] primes) {
        int pLen = primes.length;
        int[] indexes = new int[pLen];
        long[] dp = new long[n];
        dp[0] = 1;
        for (int i = 0; i < n; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = 0; j < pLen; j++) {
                dp[i] = Math.min(dp[i], dp[indexes[j]] * primes[j]);
            }
            for (int j = 0; j < pLen; j++) {
                if (dp[i] == dp[indexes[j]] * primes[j]) {
                    indexes[j]++;
                }
            }
        }
        return (int) dp[n - 1];
    }


    public int[] topKFrequent1(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
        int len = nums.length;
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(len, Comparator.comparingInt(o -> o[1]));
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            Integer num = entry.getKey();
            Integer value = entry.getValue();
            if (minHeap.size() < k) {
                minHeap.offer(new int[]{num, value});
            } else {
                if (value > minHeap.peek()[1]) {
                    minHeap.poll();
                    minHeap.offer(new int[]{num, value});
                }
            }
        }
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = minHeap.poll()[0];
        }
        return res;
    }

    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> freq = new HashMap<>();
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        ArrayList<int[]> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            list.add(new int[]{entry.getKey(), entry.getValue()});
        }
        int left = 0;
        int right = list.size() - 1;
        int target = k - 1;
        while (left <= right) {
            int pIndex = partition(list, left, right);
            if (pIndex < target) {
                left = pIndex + 1;
            } else if (pIndex > target) {
                right = pIndex - 1;
            } else {
                break;
            }
        }
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = list.get(i)[0];
        }
        return res;
    }

    private int partition(ArrayList<int[]> list, int left, int right) {
        int randomIndex = (int) (Math.random() * (right - left + 1) + left);
        Collections.swap(list, randomIndex, left);
        int pivot = list.get(left)[1];
        int j = left;
        for (int i = left + 1; i <= right; i++) {
            if (list.get(i)[1] >= pivot) {
                j++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, left, j);
        return j;
    }


    public String frequencySort1(String s) {
        int len = s.length();
        if (len == 0) {
            return s;
        }
        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        Comparator<Character> comparator = new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                if (map.get(o2).intValue() == map.get(o1).intValue()) {
                    return o1.compareTo(o2);
                }
                return map.get(o2) - map.get(o1);
            }
        };
        Character[] characters = new Character[len];
        for (int i = 0; i < characters.length; i++) {
            characters[i] = s.charAt(i);
        }
        Arrays.sort(characters, comparator);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(characters[i]);
        }
        return sb.toString();
    }


    public String frequencySort(String s) {
        int len = s.length();
        if (len == 0) {
            return s;
        }
        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            heap.offer(new int[]{entry.getKey(), entry.getValue()});
        }
        StringBuilder sb = new StringBuilder();
        while (!heap.isEmpty()) {
            int[] poll = heap.poll();
            int c = poll[0];
            int count = poll[1];
            for (int i = 0; i < count; i++) {
                sb.append((char) c);
            }
        }
        return sb.toString();
    }


    class KthLargest {
        private PriorityQueue<Integer> heap;
        private int k;

        public KthLargest(int k, int[] nums) {
            this.k = k;
            heap = new PriorityQueue<>(k);
            for (int num : nums) {
                add(num);
            }
        }

        public int add(int val) {
            heap.offer(val);
            if (heap.size() > k) {
                heap.poll();
            }
            return heap.peek();
        }
    }


    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[0] * b[0] + b[1] * b[1] - a[0] * a[0] - a[1] * a[1]);
        for (int i = 0; i < k; i++) {
            heap.offer(points[i]);
        }
        for (int i = k; i <= points.length; i++) {
            int[] peek = heap.peek();
            if (points[i][0] * points[i][0] + points[i][1] * points[i][1] < peek[0] * peek[0] + peek[1] * peek[1]) {
                heap.poll();
                heap.offer(points[i]);
            }
        }
        int[][] res = new int[k][2];
        for (int i = 0; i < k; i++) {
            res[i] = heap.poll();
        }
        return res;
    }


    public int lastStoneWeight(int[] stones) {
        if (stones.length == 1) {
            return stones[0];
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int stone : stones) {
            heap.offer(stone);
        }
        while (heap.size() >= 2) {
            Integer first = heap.poll();
            Integer second = heap.poll();
            int i = first - second;
            if (i == 0) {
                continue;
            }
            heap.offer(i);
        }
        if (heap.isEmpty()) {
            return 0;
        }
        return heap.peek();
    }


    public List<List<Integer>> groupThePeople(int[] groupSizes) {
        int max = Arrays.stream(groupSizes).max().getAsInt();
        List<Integer>[] buckets = new ArrayList[max + 1];
        Arrays.setAll(buckets, a -> new ArrayList<Integer>());
        for (int i = 0; i < groupSizes.length; i++) {
            buckets[groupSizes[i]].add(i);
        }
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i].isEmpty()) {
                continue;
            }
            int tmp = buckets[i].size() / i;
            int n = 0;
            while (tmp-- > 0) {
                List<Integer> list = new ArrayList<>();
                for (int j = 0; j < i; j++) {
                    list.add(buckets[i].get(n++));
                }
                ans.add(list);
            }
        }
        return ans;
    }




}
