package com.leetcode.codereview.datastructure;

public interface Map<K, V> {
    void add(K key, V value);

    boolean contains(K key);

    V get(K key);

    void set(K key, V newvalue);

    int getSize();

    boolean isEmpty();

    V remove(K key);
}
