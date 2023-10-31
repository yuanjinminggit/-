package com.leetcode.codereview.datastructure.unionfind;

public class UnionFind3 implements UF {

    private int[] parent;

    private int[] sz;


    public UnionFind3(int size) {
        parent = new int[size];
        sz = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            sz[i] = 1;
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
        if (sz[proot] < sz[qroot]) {
            parent[proot] = qroot;
            sz[qroot] += sz[proot];
        } else {
            parent[qroot] = proot;
            sz[proot] += sz[qroot];
        }
    }

    private int find(int p) {
        while (p != parent[p]) {
            p = parent[p];
        }
        return p;
    }
}
