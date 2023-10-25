package com.leetcode.codereview.datastructure.array;

import com.leetcode.codereview.datastructure.Queue;
import org.testng.annotations.Test;

import java.util.Random;

public class LoopQueue<E> implements Queue<E> {
    private E[] data;
    private int front, tail;
    private int size;

    public LoopQueue(int capacity) {
        data = (E[]) new Object[capacity + 1];
        front = tail = 0;
        size = 0;
    }

    public LoopQueue() {
        this(10);
    }

    public int getCapacity() {
        return data.length - 1;
    }

    @Override
    public void enqueue(E e) {
        if ((tail + 1) % data.length == front) {
            reSize(getCapacity() * 2);
        }
        data[tail] = e;
        tail = (tail + 1) % data.length;
        size++;
    }

    private void reSize(int capacity) {
        E[] newData = (E[]) new Object[capacity + 1];
        for (int i = 0; i < size; i++) {
            newData[i] = data[(i + front) % data.length];
        }
        data = newData;
        front = 0;
        tail = size;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("cuo");
        }
        E ret = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;
        if (size == getCapacity() / 4 && getCapacity() / 2 != 0) {
            reSize(getCapacity() / 2);
        }
        return ret;
    }

    @Override
    public E getFront() {
        if (isEmpty()) {
            return null;
        }
        return data[front];
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return front == tail;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("loopQueue: size = %d,capacity = %d \n", getSize(), getCapacity()));
        sb.append("front [");
        for (int i = front; i != tail; i = (i + 1) % data.length) {
            sb.append(data[i]);
            if ((i + 1) % data.length != tail) {
                sb.append(',');
            }
        }
        sb.append("] tail");
        return sb.toString();
    }

//    public static void main(String[] args) {
//        LoopQueue<Integer> queue = new LoopQueue<>();
//        for (int i = 0; i < 10; i++) {
//            queue.enqueue(i);
//            System.out.println(queue);
//            if (i % 3 == 0) {
//                queue.dequeue();
//                System.out.println(queue);
//            }
//        }
//    }

    private double testQueue(Queue<Integer> q, int opCount) {
        long start = System.nanoTime();
        Random random = new Random();
        for (int i = 0; i < opCount; i++) {
            q.enqueue(random.nextInt(Integer.MAX_VALUE));
        }
        for (int i = 0; i < opCount; i++) {
            q.dequeue();
        }

        long end = System.nanoTime();
        return (end - start) / 1000000000.0;
    }

    @Test
    public void test() {
        int opCount = 100000;
        Queue<Integer> arrayQueue = new ArrayQueue<>();
        double v = testQueue(arrayQueue, opCount);
        System.out.println("arrayqueue" + v);


        Queue<Integer> loopQueue = new LoopQueue<>();
        double v2 = testQueue(loopQueue, opCount);
        System.out.println("loopQueue" + v2);

    }
}
