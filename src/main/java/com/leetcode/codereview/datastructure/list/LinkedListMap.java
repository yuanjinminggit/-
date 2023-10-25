package com.leetcode.codereview.datastructure.list;

import com.leetcode.codereview.datastructure.Map;

public class LinkedListMap<K, V> implements Map<K, V> {


    private class Node {
        public K key;
        public V value;
        public Node next;


        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Node() {
        }

        public String toString() {
            return key.toString() + ":" + value.toString();
        }
    }

    private Node dummyHead;
    private int size;

    public LinkedListMap() {
        dummyHead = new Node();
        size = 0;
    }

    @Override
    public void add(K key, V value) {
        if (!contains(key)){
            Node node = new Node(key, value);
            node.next = dummyHead.next;
            dummyHead.next = node;
            size++;
        }
    }

    private Node getNode(K key) {
        Node cur = dummyHead.next;
        while (cur!=null){
            if (cur.key.equals(key)){
                return cur;
            }
            cur = cur.next;
        }
        return null;
    }

    @Override
    public boolean contains(K key) {
        return getNode(key)!=null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        return node ==null?null:node.value;
    }

    @Override
    public void set(K key, V newvalue) {
        Node node = getNode(key);
        if (node!=null){
            node.value = newvalue;
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public V remove(K key) {
        return null;
    }

}
