package com.leetcode.codereview.datastructure.tree;

public class SegmentTreeDynamic {
    class Node {
        Node left, right;
        int val, add;
    }

    private int N = (int) 1e9;
    private Node root = new Node();

    // 在区间 [start, end] 中更新区间 [l, r] 的值，将区间 [l, r] ➕ val
    // 对于上面的例子，应该这样调用该函数：update(root, 0, 4, 2, 4, 1)
    public void update(Node node, int start, int end, int l, int r, int val) {
        if (l <= start && end <= r) {
            // 区间节点加上更新值
            // 注意：需要✖️该子树所有叶子节点
            node.val += (end - start + 1) * val;
            // 添加懒惰标记
            // 对区间进行「加减」的更新操作，懒惰标记需要累加，不能直接覆盖
            node.add += val;
            return;
        }
        int mid = (start + end) >> 1;
        pushDown(node, mid - start + 1, end - mid);
        if (l <= mid) {
            update(node.left, start, mid, l, r, val);
        }
        if (r > mid) {
            update(node.right, mid + 1, end, l, r, val);
        }
        pushUp(node);
    }

    // leftNum 和 rightNum 表示左右孩子区间的叶子节点数量
// 因为如果是「加减」更新操作的话，需要用懒惰标记的值✖️叶子节点的数量
    private void pushDown(Node node, int leftNum, int rightNum) {
        if (node.left == null) {
            node.left = new Node();
        }
        if (node.right == null) {
            node.right = new Node();
        }
        if (node.add == 0) {
            return;
        }
        node.left.val += node.add * leftNum;
        node.right.val += node.add * rightNum;
        node.left.add += node.add;
        node.right.add += node.add;
        node.add = 0;
    }

    private void pushUp(Node node) {
        node.val = node.left.val + node.right.val;
    }

    // 在区间 [start, end] 中查询区间 [l, r] 的结果，即 [l ,r] 保持不变
// 对于上面的例子，应该这样调用该函数：query(root, 0, 4, 2, 4)
    public int query(Node node, int start, int end, int l, int r) {
        if (l <= start && end <= r) {
            return node.val;
        }
        int mid = (start + end) >> 1, ans = 0;
        pushDown(node, mid - start + 1, end - mid);
        if (l <= mid) {
            ans += query(node.left, start, mid, l, r);
        }
        if (r > mid) {
            ans += query(node.right, mid + 1, end, l, r);
        }
        return ans;
    }
}