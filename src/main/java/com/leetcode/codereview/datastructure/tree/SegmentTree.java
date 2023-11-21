package com.leetcode.codereview.datastructure.tree;

import com.leetcode.codereview.datastructure.Merger;

/*
 * 线段树
 * */
public class SegmentTree<E> {
    private E[] data;
    private E[] tree;
    private Merger<E> merger;

    public SegmentTree(E[] arr, Merger<E> merger) {
        data = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++) {
            data[i] = arr[i];
        }
        tree = (E[]) new Object[4 * arr.length];
        this.merger = merger;
        buildSegmentTree(0, 0, data.length - 1);

    }

    private void buildSegmentTree(int treeIndex, int l, int r) {
        if (l > r) {
            return;
        }
        if (l == r) {
            tree[treeIndex] = data[l];
            return;
        }
        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);

        int mid = (l + r) / 2;
        buildSegmentTree(leftTreeIndex, l, mid);
        buildSegmentTree(rightTreeIndex, mid + 1, r);

        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    // 闭区间
    public E query(int queryL, int queryR) {
        return query(0, 0, data.length - 1, queryL, queryR);
    }

    private E query(int treeIndex, int l, int r, int queryL, int queryR) {
        if (l >= queryL && r <= queryR) {
            return tree[treeIndex];
        }
        int mid = l + (r - l) / 2;
        int leftIndex = leftChild(treeIndex);
        int rightIndex = rightChild(treeIndex);
        if (queryL >= mid + 1) {
            return query(rightIndex, mid + 1, r, queryL, queryR);
        } else if (queryR <= mid) {
            return query(leftIndex, l, mid, queryL, queryR);
        }
        E leftR = query(leftIndex, l, mid, queryL, mid);
        E rightR = query(rightIndex, mid + 1, r, mid + 1, queryR);
        return merger.merge(leftR, rightR);

    }

    public void set(int index, E e) {
        data[index] = e;
        set(0, 0, tree.length - 1, index, e);
    }

    private void set(int treeIndex, int l, int r, int index, E e) {
        if (l == r) {
            tree[treeIndex] = e;
            return;
        }
        int mid = l + (r - l) / 2;
        int leftIndex = leftChild(treeIndex);
        int rightIndex = rightChild(treeIndex);
        if (index >= mid + 1) {
            set(rightIndex, mid + 1, r, index, e);
        } else {
            set(leftIndex, l, mid, index, e);
        }
        tree[treeIndex] = merger.merge(tree[leftIndex], tree[rightIndex]);
    }

    private int rightChild(int treeIndex) {
        return treeIndex * 2 + 2;
    }

    private int leftChild(int treeIndex) {
        return treeIndex * 2 + 1;
    }

    public int getSize() {
        return data.length;
    }

    public E get(int index) {
        return data[index];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < tree.length; i++) {
            if (tree[i] != null) {
                sb.append(tree[i]);
            } else {
                sb.append("null");
            }
            if (i == tree.length - 1) {
                break;
            }
            sb.append(",");
        }
        sb.append(']');
        return sb.toString();
    }

    public static void main(String[] args) {
        Integer[] nums = {-2, 0, 3, -5, 2, -1};
        SegmentTree<Integer> segmentTree = new SegmentTree<>(nums, new Merger<Integer>() {
            @Override
            public Integer merge(Integer a, Integer b) {
                return a + b;
            }
        });
        System.out.println(segmentTree);
        System.out.println(segmentTree.query(0, 2));
    }

}
