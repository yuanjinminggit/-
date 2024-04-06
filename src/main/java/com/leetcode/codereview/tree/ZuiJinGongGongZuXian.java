package com.leetcode.codereview.tree;

import com.leetcode.codereview.simpleConstruct.Node;
import com.leetcode.codereview.simpleConstruct.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ZuiJinGongGongZuXian {
    private TreeNode ans;

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        dfslowestCommonAncestor(root, p, q);
        return ans;
    }

    boolean dfslowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return false;
        }
        boolean left = dfslowestCommonAncestor(root.left, p, q);
        boolean right = dfslowestCommonAncestor(root.right, p, q);
        if (left && right || ((root == p || root == q) && (left || right))) {
            ans = root;
        }
        return left || right || root == p || root == q;
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stk = new LinkedList<>();
        while (root != null || !stk.isEmpty()) {
            while (root != null) {
                stk.push(root);
                root = root.left;
            }
            root = stk.pop();
            res.add(root.val);
            root = root.right;
        }
        return res;
    }

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stk = new LinkedList<>();
        while (root != null || !stk.isEmpty()) {
            while (root != null) {
                stk.push(root);
                res.add(root.val);
                root = root.left;
            }
            root = stk.pop();
            root = root.right;
        }
        return res;
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stk = new LinkedList<>();
        TreeNode prev = null;
        while (root != null || !stk.isEmpty()) {
            while (root != null) {
                stk.push(root);
                root = root.left;
            }
            root = stk.pop();
            if (root.right == null || root.right == prev) {
                res.add(root.val);
                prev = root;
                root = null;
            } else {
                stk.push(root);
                root = root.right;
            }
        }
        return res;
    }

    public List<List<Integer>> verticalTraversal(TreeNode root) {
        ArrayList<int[]> nodes = new ArrayList<>();
        dfs(root, 0, 0, nodes);
        Collections.sort(nodes, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] != o2[0]) {
                    return o1[0] - o2[0];
                } else if (o1[1] != o2[1]) {
                    return o1[1] - o2[1];
                } else {
                    return o1[2] - o2[2];
                }
            }
        });
        List<List<Integer>> ans = new ArrayList<>();
        int size = 0;
        int lastCol = Integer.MIN_VALUE;
        for (int[] tuple : nodes) {
            int col = tuple[0];
            int row = tuple[1];
            int val = tuple[2];
            if (col != lastCol) {
                ans.add(new ArrayList<>());
                size++;
                lastCol = col;
            }
            ans.get(size - 1).add(val);
        }
        return ans;
    }

    private void dfs(TreeNode root, int row, int col, List<int[]> nodes) {
        if (root == null) {
            return;
        }
        nodes.add(new int[]{col, row, root.val});
        dfs(root.left, row + 1, col - 1, nodes);
        dfs(root.right, row + 1, col + 1, nodes);
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        List<List<Integer>> ans = new ArrayList<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                level.add(node.val);
            }
            ans.add(level);
        }
        return ans;
    }

    public List<TreeNode> allPossibleFBT(int n) {
        ArrayList<TreeNode> fullBinaryTrees = new ArrayList<>();
        if (n % 2 == 0) {
            return fullBinaryTrees;
        }
        if (n == 1) {
            fullBinaryTrees.add(new TreeNode(0));
            return fullBinaryTrees;
        }
        for (int i = 1; i < n; i += 2) {
            List<TreeNode> leftSubtrees = allPossibleFBT(i);
            List<TreeNode> rightSubTrees = allPossibleFBT(n - i - 1);
            for (TreeNode leftSubtree : leftSubtrees) {
                for (TreeNode rightSubTree : rightSubTrees) {
                    TreeNode root = new TreeNode(0, leftSubtree, rightSubTree);
                    fullBinaryTrees.add(root);
                }
            }
        }
        return fullBinaryTrees;
    }

    public final TreeNode getTargetCopy(final TreeNode original, final TreeNode cloned, final TreeNode target) {
        if (original == null) {
            return null;
        }
        if (original == target) {
            return cloned;
        }
        TreeNode left = getTargetCopy(original.left, cloned.left, target);
        if (left != null) {
            return left;
        }
        return getTargetCopy(original.right, cloned.right, target);
    }

    public List<List<Integer>> getAncestors(int n, int[][] edges) {
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            g[e[0]].add(e[1]);
        }

        List<Integer>[] ans = new ArrayList[n];
        Arrays.setAll(ans, i -> new ArrayList<>());
        int[] vis = new int[n];
        Arrays.fill(vis, -1);
        for (int start = 0; start < n; start++) {
            dfs(start, start, g, vis, ans);
        }
        return Arrays.asList(ans);
    }

    private void dfs(int x, int start, List<Integer>[] g, int[] vis, List<Integer>[] ans) {
        vis[x] = start;
        for (int y : g[x]) {
            if (vis[y] != start) {
                ans[y].add(start);
                dfs(y, start, g, vis, ans);
            }
        }
    }

    private int res;

    public int maxAncestorDiff(TreeNode root) {
        dfs(root, root.val, root.val);
        return res;
    }

    private void dfs(TreeNode root, int min, int max) {
        if (root == null) {
            return;
        }
        min = Math.min(root.val, min);
        max = Math.max(max, root.val);
        res = Math.max(res, Math.max(root.val - min, max - root.val));
        dfs(root.left, min, max);
        dfs(root.right, min, max);
    }

    public List<List<Integer>> levelOrder(Node root) {
        if (root == null) {
            return new ArrayList<List<Integer>>();
        }
        List<List<Integer>> ans = new ArrayList<>();
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int cnt = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < cnt; i++) {
                Node cur = queue.poll();
                level.add(cur.val);
                for (Node child : cur.children) {
                    queue.offer(child);
                }
            }
            ans.add(level);
        }
        return ans;
    }

    public List<Integer> preorder(Node root) {
        List<Integer> ans = new ArrayList<>();
        dfs(root, ans);
        return ans;
    }

    private void dfs(Node root, List<Integer> ans) {
        if (root == null) {
            return;
        }
        for (Node child : root.children) {
            dfs(child, ans);
        }
        ans.add(root.val);
    }

    public List<Integer> postorder(Node root) {
        List<Integer> ans = new ArrayList<>();
        dfs(root, ans);
        return ans;
    }

    public long kthLargestLevelSum(TreeNode root, int k) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        List<Long> levelSumArray = new ArrayList<>();
        while (!queue.isEmpty()) {
            Long levelSum = 0l;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                levelSum += node.val;
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            levelSumArray.add(levelSum);
        }
        if (levelSumArray.size() < k) {
            return -1;
        }
        Collections.sort(levelSumArray);
        return levelSumArray.get(levelSumArray.size() - k);
    }

    public List<List<Integer>> closestNodes(TreeNode root, List<Integer> queries) {
        List<Integer> arr = new ArrayList<>();
        dfs(root, arr);
        List<List<Integer>> res = new ArrayList<>();
        for (Integer val : queries) {
            int maxVal = -1;
            int minVal = -1;
            int idx = binarySearch(arr, val);
            if (idx != arr.size()) {
                maxVal = arr.get(idx);
                if (arr.get(idx).intValue() == val) {
                    minVal = val;
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(minVal);
                    list.add(maxVal);
                    res.add(list);
                    continue;
                }
            }
            if (idx > 0) {
                minVal = arr.get(idx - 1);
            }
            ArrayList<Integer> list = new ArrayList<>();
            list.add(minVal);
            list.add(maxVal);
            res.add(list);
        }
        return res;
    }

    private int binarySearch(List<Integer> arr, int target) {
        int low = 0, high = arr.size();
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr.get(mid) >= target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    private void dfs(TreeNode root, List<Integer> arr) {
        if (root == null) {
            return;
        }
        dfs(root.left, arr);
        arr.add(root.val);
        dfs(root.right, arr);
    }

    public int rangeSumBST(TreeNode root, int low, int high) {
        if (root == null) {
            return 0;
        }
        int left = rangeSumBST(root.left, low, high);
        int right = rangeSumBST(root.right, low, high);
        return left + right + root.val >= low && root.val <= high ? root.val : 0;
    }

    public int countPrimes1(int n) {
        int ans = 0;
        for (int i = 2; i < n; i++) {
            ans += isPrime(i) ? 1 : 0;
        }
        return ans;
    }

    private boolean isPrime(int x) {
        for (int i = 2; i * i <= x; ++i) {
            if (x % 2 == 0) {
                return false;
            }
        }
        return true;
    }

    public int countPrimes(int n) {
        int[] isPrime = new int[n];
        Arrays.fill(isPrime, 1);
        int ans = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime[i] == 1) {
                ans += 1;
                if ((long) i * i < n) {
                    for (int j = i * i; j < n; j += 1) {
                        isPrime[j] = 0;
                    }
                }
            }
        }
        return ans;
    }

    private final static int MX = (int) 1e5;
    private final static boolean[] np = new boolean[MX + 1];

    static {
        np[1] = true;
        for (int i = 2; i * i <= MX; i++) {
            if (!np[i]) {
                for (int j = i * i; j <= MX; j += i) {
                    np[j] = true;
                }
            }
        }
    }

    public long countPaths(int n, int[][] edges) {
        List<Integer>[] g = new ArrayList[n + 1];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        long ans = 0;
        int[] size = new int[n + 1];
        List<Integer> nodes = new ArrayList<>();
        for (int x = 1; x <= n; x++) {
            if (np[x]) {
                continue;
            }
            int sum = 0;
            for (int y : g[x]) {
                if (!np[y]) {
                    continue;
                }
                if (size[y] == 0) {
                    nodes.clear();
                    dfs(y, -1, g, nodes);
                    for (Integer z : nodes) {
                        size[z] = nodes.size();
                    }
                }
                ans += (long) size[y] * sum;
                sum += size[y];
            }
            ans += sum;
        }
        return ans;
    }

    private void dfs(int x, int fa, List<Integer>[] g, List<Integer> nodes) {
        nodes.add(x);
        for (int y : g[x]) {
            if (y != fa && np[y]) {
                dfs(y, x, g, nodes);
            }
        }
    }

}