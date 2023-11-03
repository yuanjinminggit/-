package com.leetcode.codereview.tree;

import com.leetcode.codereview.simpleConstruct.TreeNode;

import java.util.*;

public class Butongdeerchasousuoshu {

    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new LinkedList<>();
        }
        return generateTrees(1, n);
    }

    private List<TreeNode> generateTrees(int start, int end) {
        LinkedList<TreeNode> allTrees = new LinkedList<>();
        if (start > end) {
            allTrees.add(null);
            return allTrees;
        }
        for (int i = start; i <= end; i++) {
            List<TreeNode> leftTrees = generateTrees(start, i - 1);
            List<TreeNode> rightTrees = generateTrees(i + 1, end);
            for (TreeNode leftTree : leftTrees) {
                for (TreeNode rightTree : rightTrees) {
                    TreeNode curTree = new TreeNode(i);
                    curTree.left = leftTree;
                    curTree.right = rightTree;
                    allTrees.add(curTree);
                }
            }
        }
        return allTrees;
    }

    public boolean isValidBST(TreeNode root) {
        return dfsisValidBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean dfsisValidBST(TreeNode root, int l, int r) {
        if (root == null) {
            return true;
        }
        if (root.val <= l || root.val >= r) {
            return false;
        }
        return dfsisValidBST(root.left, l, root.val) && dfsisValidBST(root.right, root.val, r);
    }


    public boolean containsNearbyDuplicate1(int[] nums, int k) {
        int len = nums.length;
        Integer[] idx = new Integer[len];
        for (int i = 0; i < idx.length; i++) {
            idx[i] = i;
        }
        Arrays.sort(idx, (a, b) -> nums[a] - nums[b]);
        for (int i = 1; i < idx.length; i++) {
            if (nums[idx[i]] - nums[idx[i - 1]] == 0) {
                if (idx[i] - idx[i - 1] <= k) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean containsNearbyDuplicate2(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            int num = nums[i];
            if (map.containsKey(num) && i - map.get(num) <= k) {
                return true;
            }
            map.put(num, i);
        }
        return false;
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (nums.length <= 1) {
            return false;
        }
        k = Math.min(k, nums.length - 1);
        HashSet<Integer> set = new HashSet<>();
        int length = nums.length;
        for (int i = 0; i <= k; i++) {
            if (!set.add(nums[i])) {
                return true;
            }
        }
        for (int i = k + 1; i < length; i++) {
            set.remove(nums[i - k - 1]);
            if (!set.add(nums[i])) {
                return true;
            }
        }
        return false;
    }


    public boolean containsNearbyAlmostDuplicate1(int[] nums, int indexDiff, int valueDiff) {
        int n = nums.length;
        TreeSet<Long> set = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            if (i > indexDiff) {
                set.remove((long) nums[i - indexDiff - 1]);
            }
            Long ceiling = set.ceiling((long) nums[i] - (long) valueDiff);
            if (ceiling != null && ceiling <= (long) nums[i] + (long) valueDiff) {
                return true;
            }
            set.add((long) nums[i]);

        }
        return false;
    }


    //暴力解法
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        int len = nums.length;
        if (len == 0 || k <= 0 || t < 0) {
            return false;
        }
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (Math.abs(j - i) <= k && Math.abs((long) nums[j] - (long) nums[i]) <= k) {
                    return true;
                }
            }
        }
        return false;
    }

    private int res;
    private int count;

    public int kthSmallest(TreeNode root, int k) {
        this.count = k;
        dfskthSmallest(root, k);
        return res;
    }

    private void dfskthSmallest(TreeNode root, int k) {
        if (root == null) {
            return;
        }
        dfskthSmallest(root.left, k);
        count--;
        if (count == 0) {
            res = root.val;
            return;
        }
        dfskthSmallest(root.right, k);
    }


    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == p || root == q) {
            return root;
        }
        if (root.val < p.val && root.val < q.val) {
            return lowestCommonAncestor(root.right, p, q);
        } else if (root.val > p.val && root.val > q.val) {
            return lowestCommonAncestor(root.left, p, q);
        }
        return root;
    }


    public class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            dfsserialize(root, sb);
            return sb.substring(0, sb.length() - 1);
        }

        private void dfsserialize(TreeNode root, StringBuilder sb) {
            if (root == null) {
                return;
            }
            sb.append(root.val);
            sb.append(",");
            dfsserialize(root.left, sb);
            dfsserialize(root.right, sb);
        }


        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            int len = data.length();
            if (len == 0) {
                return null;
            }
            String[] split = data.split(",");
            return deserialize(split, 0, split.length - 1);
        }

        private TreeNode deserialize(String[] split, int left, int right) {
            if (left > right) {
                return null;
            }
            TreeNode root = new TreeNode(Integer.parseInt(split[left]));
            int index = right + 1;
            for (int i = left + 1; i <= right; i++) {
                if (Integer.parseInt(split[i]) > root.val) {
                    index = i;
                    break;
                }
            }
            root.left = deserialize(split, left + 1, index - 1);
            root.right = deserialize(split, index, right);
            return root;
        }

    }


    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else {
            if (root.left == null && root.right == null) {
                return null;
            }
            if (root.left == null || root.right == null) {
                return root.left == null ? root.right : root.left;
            }
            TreeNode successor = findMin(root.right);
            root.right = deleteNode(root.right, successor.val);
            successor.right = root.right;
            successor.left = root.left;
            root.left = root.right = root = null;
            return successor;
        }
        return root;
    }

    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }


    public TreeNode increasingBST(TreeNode root) {
        LinkedList<Integer> list = new LinkedList<>();
        inorderDfs(root, list);
        return buildincreasingBST(list);
    }

    private TreeNode buildincreasingBST(LinkedList<Integer> list) {
        if (list.isEmpty()) {
            return null;
        }
        Integer val = list.poll();
        TreeNode root = new TreeNode(val);
        root.left = null;
        root.right = buildincreasingBST(list);
        return root;
    }

    private void inorderDfs(TreeNode root, LinkedList<Integer> list) {
        if (root == null) {
            return;
        }
        inorderDfs(root.left, list);
        list.offer(root.val);
        inorderDfs(root.right, list);
    }


    public TreeNode bstFromPreorder(int[] preorder) {
        return dfsbstFromPreorder(preorder, 0, preorder.length - 1);
    }

    private TreeNode dfsbstFromPreorder(int[] preorder, int l, int r) {
        if (l > r) {
            return null;
        }
        int val = preorder[l];
        TreeNode root = new TreeNode(val);
        int left = l + 1;
        int right = r;
        while (left < right) {
            System.out.println(left + ":" + right);
            int mid = (left + right - 1) / 2;
            if (preorder[mid] <= val) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        if (l + 1 >= preorder.length || preorder[l + 1] >= val) {
            left = l;
        }
        root.left = dfsbstFromPreorder(preorder, l + 1, left);
        root.right = dfsbstFromPreorder(preorder, left + 1, r);
        return root;
    }


    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    public Node connect(Node root) {

    }
}