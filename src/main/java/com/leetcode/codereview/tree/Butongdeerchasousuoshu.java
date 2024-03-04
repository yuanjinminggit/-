package com.leetcode.codereview.tree;

import com.leetcode.codereview.simpleConstruct.ListNode;
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

    public Node connect1(Node root) {
        return dfsconnect(root);
    }

    private Node dfsconnect(Node root) {
        if (root == null) {
            return null;
        }
        if (root.left != null) {
            root.left.next = root.right;
        }
        if (root.right != null && root.next != null) {
            root.right.next = root.next.left;
        }

        root.left = dfsconnect(root.left);
        root.right = dfsconnect(root.right);
        return root;
    }

    public Node connect2(Node root) {
        if (root == null) {
            return root;
        }
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                if (i < size - 1) {
                    node.next = queue.peek();
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return root;
    }

    public Node connect5(Node root) {
        if (root == null) {
            return root;
        }
        Node leftMost = root;
        while (leftMost.left != null) {
            Node head = leftMost;
            while (head != null) {
                head.left.next = head.right;

                if (head.next != null) {
                    head.right.next = head.next.left;
                }
                head = head.next;
            }
            leftMost = leftMost.left;
        }
        return root;
    }

    public Node connect4(Node root) {
        if (root == null) {
            return root;
        }
        LinkedList<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                if (i != size - 1) {
                    node.next = queue.peek();
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return root;
    }

    public List<Integer> rightSideView(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        dfsrightSideView(res, root, 0);
        return res;
    }

    private void dfsrightSideView(ArrayList<Integer> res, TreeNode root, int level) {
        if (root == null) {
            return;
        }
        if (level == res.size()) {
            res.add(root.val);
        }
        dfsrightSideView(res, root.right, level + 1);
        dfsrightSideView(res, root.left, level + 1);

    }

    public Node connect(Node root) {
        Node ans = root;
        if (root == null) return ans;
        Node cur = root;
        while (cur != null) {
            Node head = new Node(-1), tail = head;
            for (Node i = cur; i != null; i = i.next) {
                if (i.left != null) tail = tail.next = i.left;
                if (i.right != null) tail = tail.next = i.right;
            }
            cur = head.next;
        }
        return ans;
    }

    public int maxLevelSum(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int maxLevel = 0;
        int max = Integer.MIN_VALUE;
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            int tmp = 0;
            level++;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                tmp += node.val;
                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            if (tmp > max) {
                max = tmp;
                maxLevel = level;
            }
        }
        return maxLevel;
    }

    public int goodNodes(TreeNode root) {
        return dfsgoodNodes(root, Integer.MIN_VALUE);
    }

    private int dfsgoodNodes(TreeNode root, int maxVaule) {
        if (root == null) {
            return 0;
        }
        int count = 0;
        if (root.val > maxVaule) {
            maxVaule = root.val;
            count = 1;
        }
        return dfsgoodNodes(root.left, maxVaule) +
                dfsgoodNodes(root.right, maxVaule) + count;
    }

    private int maxLevel = 0;
    private int sum = 0;

    public int deepestLeavesSum(TreeNode root) {
        dfsdeepestLeavesSum(root, 0);
        return sum;
    }

    private void dfsdeepestLeavesSum(TreeNode root, int level) {
        if (root == null) {
            return;
        }
        if (level > maxLevel) {
            sum = root.val;
            maxLevel = level;
        } else if (level == maxLevel) {
            sum += root.val;
        }
        dfsdeepestLeavesSum(root.left, level + 1);
        dfsdeepestLeavesSum(root.right, level + 1);

    }

    private int ans;

    public int maxAncestorDiff(TreeNode root) {
        this.ans = 0;
        dfsmaxAncestorDiff(root, Integer.MAX_VALUE, Integer.MIN_VALUE);
        return ans;
    }

    private void dfsmaxAncestorDiff(TreeNode root, int minValue, int maxValue) {
        if (root == null) {
            return;
        }
        if (root.val < minValue) {
            minValue = root.val;
        }
        if (root.val > maxValue) {
            maxValue = root.val;
        }
        ans = Math.max(ans, root.val - minValue);
        ans = Math.max(ans, maxValue - root.val);
        dfsmaxAncestorDiff(root.left, minValue, maxValue);
        dfsmaxAncestorDiff(root.right, minValue, maxValue);

    }

    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        if (root == null) {
            return new ArrayList<>();
        }
        ArrayList<TreeNode> res = new ArrayList<>();
        HashSet<Integer> set = new HashSet<>();
        for (int i : to_delete) {
            set.add(i);
        }
        TreeNode node = dfsdelNodes(root, set, res);
        if (node != null) {
            res.add(node);
        }
        return res;
    }

    private TreeNode dfsdelNodes(TreeNode root, HashSet<Integer> set, ArrayList<TreeNode> res) {
        if (root == null) {
            return null;
        }

        root.left = dfsdelNodes(root.left, set, res);
        root.right = dfsdelNodes(root.right, set, res);
        TreeNode returnNode = root;
        if (set.contains(root.val)) {
            if (root.left != null) {
                res.add(root.left);
            }
            if (root.right != null) {
                res.add(root.right);
            }
            returnNode = null;
        }
        return returnNode;
    }

    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        dfssubtreeWithAllDeepest(root, 0);
        return deepNode;
    }

    TreeNode deepNode;
    int maxDep;

    private int dfssubtreeWithAllDeepest(TreeNode root, int level) {
        if (root == null) {
            maxDep = Math.max(maxDep, level);
            return level;
        }
        int left = dfssubtreeWithAllDeepest(root.left, level + 1);
        int right = dfssubtreeWithAllDeepest(root.right, level + 1);
        if (left == right && right == maxDep) {
            deepNode = root;
            return left;
        }
        return Math.max(left, right);
    }

    public boolean isSubPath1(ListNode head, TreeNode root) {
        return dfsisSubPath(head, root, head);
    }

    private boolean dfsisSubPath(ListNode headcur, TreeNode rootcur, ListNode head1) {
        if (headcur == null) {
            return true;
        }
        if (rootcur == null) {
            return false;
        }
        boolean flag = false;
        if (rootcur.val == headcur.val) {
            flag = dfsisSubPath(headcur.next, rootcur.left, head1) || dfsisSubPath(headcur.next, rootcur.right, head1);
        }
        if (flag) {
            return true;
        }
        flag = dfsisSubPath(head1, rootcur.left, head1) || dfsisSubPath(head1, rootcur.right, head1);
        return flag;
    }

    public boolean isSubPath(ListNode head, TreeNode root) {
        if (root == null) {
            return false;
        }
        return dfs(head, root) || isSubPath(head, root.left) || isSubPath(head, root.right);
    }

    private boolean dfs(ListNode head, TreeNode root) {
        if (head == null) {
            return true;
        }
        if (root == null) {
            return false;
        }
        if (head.val != root.val) {
            return false;
        }
        return dfs(head.next, root.left) || dfs(head.next, root.right);
    }

    public TreeNode replaceValueInTree(TreeNode root) {
        root.val = 0;
        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int sum = 0;
            int size = queue.size();
            for (TreeNode node : queue) {
                if (node.left != null) {
                    sum += node.left.val;

                }
                if (node.right != null) {
                    sum += node.right.val;

                }
            }
            while (size-- > 0) {
                TreeNode node = queue.poll();
                int sumOfxy = (node.left == null ? 0 : node.left.val) + (node.right == null ? 0 : node.right.val);
                if (node.left != null) {
                    node.left.val = sum - sumOfxy;
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    node.right.val = sum - sumOfxy;
                    queue.offer(node.right);
                }
            }
        }
        return root;
    }


}