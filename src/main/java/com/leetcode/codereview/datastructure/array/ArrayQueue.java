package com.leetcode.codereview.datastructure.array;

import com.leetcode.codereview.datastructure.Queue;

public class ArrayQueue<E> implements Queue<E> {
    private Array<E> array;

    public ArrayQueue(int capacity) {
        array = new Array<>(capacity);
    }

    public ArrayQueue() {
        array = new Array<>();
    }

    @Override
    public void enqueue(E e) {
        array.addLast(e);
    }

    @Override
    public E dequeue() {
        return array.removeFirst();
    }

    @Override
    public E getFront() {
        return array.getfirst();
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("queue: size = %d,capacity = %d \n", array.getSize(), array.getCapacity()));
        sb.append("front [");
        for (int i = 0; i < array.getSize(); i++) {
            sb.append(array.get(i));
            if (i != array.getSize() - 1) {
                sb.append(',');
            }
        }
        sb.append("] tail");
        return sb.toString();
    }
}
