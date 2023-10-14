package com.leetcode.codereview.sorttable;

public interface SortTable<K extends Comparable<K>,V> {

    void put(K key, V value);

    V get(K key);

    void remove(K key);

    boolean containsKey(K key);

    K ceilingKey(K key);

    K firstKey();

    K lastKey();

    K floorKey(K key);

    int size();
}
