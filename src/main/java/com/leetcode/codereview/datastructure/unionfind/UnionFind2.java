package com.leetcode.codereview.datastructure.unionfind;

public class UnionFind2 implements UF {

    private int[] parent;


    public UnionFind2(int size) {
        parent = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
        }
    }

    @Override
    public int getSize() {
        return parent.length;
    }

    @Override
    public Boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public void unionElements(int p, int q) {
        int proot = find(p);
        int qroot = find(q);
        if (proot == qroot) {
            return;
        }
        parent[proot] = qroot;
    }

    private int find(int p) {
        while (p != parent[p]) {
            p = parent[p];
        }
        return p;
    }
}
