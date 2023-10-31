package com.leetcode.codereview.datastructure.unionfind;

public interface UF {

    int getSize();
    Boolean isConnected(int p, int q);

    void unionElements(int p, int q);
}
