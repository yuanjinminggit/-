package com.leetcode.codereview.datastructure.unionfind;

public class UnionFind6 implements UF {

    private int[] parent;

    private int[] rank;


    public UnionFind6(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 1;
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
        if (rank[proot] < rank[qroot]) {
            parent[proot] = qroot;
        } else if (rank[proot] > rank[qroot]) {
            parent[qroot] = proot;
        } else {
            parent[qroot] = proot;
            rank[proot]++;
        }
    }

    private int find(int p) {
        if (p != parent[p]) {
            // 所有节点直接指向根节点
            parent[p] = find(parent[p]);
        }
        return p;
    }
}
