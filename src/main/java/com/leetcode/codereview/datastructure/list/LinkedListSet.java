package com.leetcode.codereview.datastructure.list;

import com.leetcode.codereview.datastructure.Set;

public class LinkedListSet<E> implements Set<E> {
    private LinkedList <E> list;
    @Override
    public void add(E e) {
        if (!list.contains(e)){
            list.addFirst(e);
        }
    }

    @Override
    public void remove(E e) {
        list.removeLast();
    }

    @Override
    public boolean contains(E e) {
        return list.contains(e);
    }

    @Override
    public int getSize() {
        return list.getSize();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
}
