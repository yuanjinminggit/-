package com.leetcode.codereview.datastructure.list;

import com.leetcode.codereview.datastructure.Queue;

public class LinkedListQueue<E> implements Queue<E> {
    private class Node {
        public E e;
        public Node next;

        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(E e) {
            this.e = e;
        }

        public Node() {
        }

        public String toString() {
            return e.toString();
        }
    }

    private Node head, tail;
    private int size;

    public LinkedListQueue() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void enqueue(E e) {
        if (tail == null) {
            tail = new Node(e);
            head = tail;
        } else {
            tail.next = new Node(e);
            tail = tail.next;
        }
        size++;

    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new RuntimeException();
        }
        Node del = this.head;
        head = head.next;
        del.next = null;
        if (head == null) {
            tail = null;
        }
        size--;
        return del.e;
    }

    @Override
    public E getFront() {
        if (isEmpty()){
            return null;
        }
        return head.e;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
