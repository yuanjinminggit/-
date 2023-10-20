package com.leetcode.codereview.queue;

import java.util.LinkedList;
import java.util.Queue;

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
            while (!queue.isEmpty()&&queue.peek()<t-3000){
                queue.poll();
            }
            return queue.size();
        }
    }


}
