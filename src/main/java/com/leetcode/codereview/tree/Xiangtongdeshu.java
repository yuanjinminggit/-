package com.leetcode.codereview.tree;

import com.leetcode.codereview.simpleConstruct.TreeNode;
import javafx.util.Pair;

import java.util.*;

public class Xiangtongdeshu {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    public boolean isSymmetric(TreeNode root) {
        return isSymmetric(root.left, root.right);
    }

    private boolean isSymmetric(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        return left.val == right.val && isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left);
    }

    public TreeNode buildTree1(int[] preorder, int[] inorder) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return dfsBuildPreorderTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, map);
    }

    private TreeNode dfsBuildPreorderTree(int[] preorder, int preL, int preR, int[] inorder, int inL, int inR, HashMap<Integer, Integer> map) {
        if (preL > preR) {
            return null;
        }
        if (preL == preR) {
            return new TreeNode(preorder[preL]);
        }
        TreeNode root = new TreeNode(preorder[preL]);
        Integer idx = map.get(preorder[preL]);

        root.left = dfsBuildPreorderTree(preorder, preL + 1, idx - inL + preL, inorder, inL, idx - 1, map);
        root.right = dfsBuildPreorderTree(preorder, idx - inL + preL + 1, preR, inorder, idx + 1, inR, map);
        return root;
    }

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return dfsBuildPostOrderTree(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1, map);
    }

    private TreeNode dfsBuildPostOrderTree(int[] inorder, int inL, int inR, int[] postorder, int poL, int poR, HashMap<Integer, Integer> map) {
        if (inL > inR) {
            return null;
        }
        if (poL == poR) {
            return new TreeNode(postorder[poR]);
        }
        TreeNode root = new TreeNode(postorder[poR]);
        Integer idx = map.get(postorder[poR]);
        root.left = dfsBuildPostOrderTree(inorder, inL, idx - 1, postorder, poL, idx - inL + poL - 1, map);
        root.right = dfsBuildPostOrderTree(inorder, idx + 1, inR, postorder, idx - inL + poL, poR - 1, map);
        return root;
    }

    public int sumNumbers(TreeNode root) {
        return dfssumNumbers(root, 0);
    }

    private int dfssumNumbers(TreeNode root, int presum) {
        if (root == null) {
            return 0;
        }
        int sum = presum * 10 + root.val;
        if (root.left == null && root.right == null) {
            return sum;
        } else {
            return dfssumNumbers(root.left, sum) + dfssumNumbers(root.right, sum);
        }
    }

    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> ans = new ArrayList<>();
        maxLevel = 0;
        ans.add(root.val);
        dfsrightSideView(root, ans, 0);
        return ans;
    }

    private int maxLevel;

    private void dfsrightSideView(TreeNode root, List<Integer> list, int level) {
        if (root == null) {
            return;
        }
        level++;

        if (root.right != null) {
            if (level > maxLevel) {
                maxLevel = level;
                list.add(root.right.val);
            }
            dfsrightSideView(root.left, list, level);
            dfsrightSideView(root.right, list, level);
        } else if (root.left != null) {
            if (level > maxLevel) {
                maxLevel = level;
                list.add(root.left.val);
            }
            dfsrightSideView(root.left, list, level);
        }
    }

    public int countNodes1(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        return countNodes1(root.left) + countNodes1(root.right) + 1;
    }

    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = countLevel(root.left);
        int right = countLevel(root.right);
        if (left == right) {
            return countNodes(root.right) + (1 << left);
        } else {
            return countNodes(root.left) + (1 << right);
        }
    }

    private int countLevel(TreeNode root) {
        int level = 0;
        while (root != null) {
            level++;
            root = root.left;
        }
        return level;
    }

    public TreeNode invertTree1(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = root.left;
        root.left = root.right;
        root.right = left;
        invertTree1(root.left);
        invertTree1(root.right);
        return root;
    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) {
            return root;
        }
        return left == null ? right : left;
    }

    public int[] findFrequentTreeSum(TreeNode root) {
        HashMap<Integer, Integer> map = new HashMap<>();
        dfsfindFrequentTreeSum(root, map);
        ArrayList<Integer> list = new ArrayList<>();
        int maxFreq = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int freq = entry.getValue();
            int key = entry.getKey();
            if (freq > maxFreq) {
                list.clear();
                maxFreq = freq;
                list.add(key);
            } else if (freq < maxFreq) {
                continue;
            } else {
                list.add(key);
            }
        }
        return list.stream().mapToInt(v -> v).toArray();
    }

    private int dfsfindFrequentTreeSum(TreeNode root, HashMap<Integer, Integer> map) {
        if (root == null) {
            return 0;
        }
        int left = dfsfindFrequentTreeSum(root.left, map);
        int right = dfsfindFrequentTreeSum(root.right, map);
        int sum = left + right + root.val;
        map.put(sum, map.getOrDefault(sum, 0) + 1);
        return sum;
    }

    public boolean isSubtree(TreeNode root, TreeNode subRoot) {

        return dfsIsSubTree(root, subRoot, root, subRoot);

    }

    private boolean dfsIsSubTree(TreeNode root, TreeNode subRoot, TreeNode leftRoot, TreeNode rightRoot) {
        if (root == null && subRoot == null) {
            return true;
        }
        if (root == null || subRoot == null) {
            return false;
        }
        boolean flag = false;
        if (root.val == subRoot.val) {
            flag = dfsIsSubTree(root.left, subRoot.left, leftRoot, rightRoot) && dfsIsSubTree(root.right, subRoot.right, leftRoot, rightRoot);
        }
        if (flag) {
            return true;
        }
        TreeNode node = rightRoot;
        if (root.val == node.val && root != leftRoot) {
            flag = dfsIsSubTree(root.left, node.left, leftRoot, rightRoot) && dfsIsSubTree(root.right, node.right, leftRoot, rightRoot);
        }
        if (flag) {
            return true;
        }
        return dfsIsSubTree(root.left, node, leftRoot, rightRoot) || dfsIsSubTree(root.right, node, leftRoot, rightRoot);
    }

    private Map<Integer, TreeNode> parents = new HashMap<>();

    public List<Integer> distanceK1(TreeNode root, TreeNode target, int k) {

        findParents(root);
        findAns(target, null, 0, k);
        return ans;
    }

    private void findAns(TreeNode node, TreeNode from, int depth, int k) {
        if (node == null) {
            return;
        }
        if (depth == k) {
            ans.add(node.val);
            return;
        }
        if (node.left != from) {
            findAns(node.left, node, depth + 1, k);
        }
        if (node.right != from) {
            findAns(node.right, node, depth + 1, k);
        }
        if (parents.get(node.val) != from) {
            findAns(parents.get(node.val), node, depth + 1, k);
        }
    }

    private void findParents(TreeNode root) {
        if (root.left != null) {
            parents.put(root.left.val, root);
            findParents(root.left);
        }
        if (root.right != null) {
            parents.put(root.right.val, root);
            findParents(root.right);
        }
    }

    int K;
    private List<Integer> ans = new ArrayList<>();

    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        this.K = k;
        if (K == 0) {
            ans.add(target.val);
            return ans;
        }
        dfs(root, target);
        return ans;
    }

    private int dfs(TreeNode root, TreeNode target) {
        if (root == null) {
            return -1;
        }
        if (root == target) {
            helper(root, 0);
            return 1;
        } else {
            // 说明左子树中找到了目标值
            int left = dfs(root.left, target);
            if (left >= 0) {
                if (left == K) {
                    ans.add(root.val);
                } else {
                    // 遍历右子树
                    helper(root.right, left + 1);
                }
                return left + 1;
            }
            // 说明右子树中找到了目标值
            int right = dfs(root.right, target);
            if (right >= 0) {
                if (right == K) {
                    ans.add(root.val);
                } else if (right < K) {
                    helper(root.left, right + 1);
                }
                return right + 1;
            }
            return -1;
        }

    }

    // 从根节点向下找
    private void helper(TreeNode root, int distance) {
        if (root == null) {
            return;
        }
        if (distance == K) {
            ans.add(root.val);
            return;
        }
        helper(root.left, distance + 1);
        helper(root.right, distance + 1);
    }

    public boolean flipEquiv1(TreeNode root1, TreeNode root2) {
        if (root1.val != root2.val) {
            return false;
        }
        return dfsflipEquiv(root1, root2);

    }

    private boolean dfsflipEquiv(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return true;
        }
        if (root1 == null || root2 == null) {
            return false;
        }
        if (root1.val != root2.val) {
            return false;
        }
        boolean flag = false;
        if (root1.left != null && root2.left != null) {
            if (root1.left.val == root2.left.val) {
                flag = dfsflipEquiv(root1.left, root2.left) && dfsflipEquiv(root1.right, root2.right);
            } else {
                flag = dfsflipEquiv(root1.left, root2.right) && dfsflipEquiv(root1.right, root2.left);
            }
        } else if (root1.left == null && root2.left == null) {
            flag = dfsflipEquiv(root1.right, root2.right);
        } else {
            flag = dfsflipEquiv(root1.left, root2.right) && dfsflipEquiv(root1.right, root2.left);
        }
        return flag;
    }

    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return true;
        }
        if (root1 == null || root2 == null || root1.val != root2.val) {
            return false;
        }
        return flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right) || flipEquiv(root1.right, root2.left) && flipEquiv(root1.left, root2.right);
    }

    private int one;

    public boolean isCousins(TreeNode root, int x, int y) {
        one = -1;
        return dfsisCousins(root, x, y, 0);
    }

    private boolean dfsisCousins(TreeNode root, int x, int y, int depth) {
        if (root == null) {
            return false;
        }
        if (root.val == x || root.val == y) {
            if (one == -1) {
                one = depth;
                return false;
            } else {
                return one == depth;
            }
        }
        if (root.left != null && root.right != null) {
            if (root.left.val == x && root.right.val == y || root.left.val == y && root.right.val == x) {
                return false;
            }
        }
        return dfsisCousins(root.left, x, y, depth + 1) ||
                dfsisCousins(root.right, x, y, depth + 1);
    }

    public int numTilePossibilities1(String tiles) {
        HashMap<Character, Integer> count = new HashMap<>();
        for (char t : tiles.toCharArray()) {
            count.put(t, count.getOrDefault(t, 0) + 1);
        }
        HashSet<Character> title = new HashSet<>(count.keySet());
        return dfsnumTilePossibilities(tiles.length(), count, title) - 1;
    }

    private int dfsnumTilePossibilities(int i, HashMap<Character, Integer> count, HashSet<Character> title) {
        if (i == 0) {
            return 1;
        }
        int res = 1;
        for (Character t : title) {
            if (count.get(t) > 0) {
                count.put(t, count.get(t) - 1);
                res += dfsnumTilePossibilities(i - 1, count, title);
                count.put(t, count.get(t) + 1);
            }
        }
        return res;
    }

    public int numTilePossibilities(String tiles) {
        int[] count = new int[26];
        char[] charArray = tiles.toCharArray();
        for (char c : charArray) {
            count[c - 'A']++;
        }
        return dfsPossibilities(count);
    }

    private int dfsPossibilities(int[] count) {
        int res = 0;
        for (int i = 0; i < 26; i++) {
            if (count[i] == 0) {
                continue;
            }
            // 当前count数组且以i为头是一种排列
            res++;
            count[i]--;
            res += dfsPossibilities(count);
            count[i]++;
        }
        return res;
    }

    public TreeNode sufficientSubset(TreeNode root, int limit) {

        int sum = dfssufficientSubset(root, limit, 0);
        return sum < limit ? null : root;
    }

    private int dfssufficientSubset(TreeNode root, int limit, int sum) {
        if (root.left == null && root.right == null) {
            sum += root.val;
            return sum;
        }
        sum += root.val;
        int left = Integer.MIN_VALUE;
        if (root.left != null) {
            left = dfssufficientSubset(root.left, limit, sum);
        }
        int right = Integer.MIN_VALUE;
        if (root.right != null) {
            right = dfssufficientSubset(root.right, limit, sum);
        }
        // 站在父亲的角度调整儿子节点
        if (left < limit && right < limit) {
            root.right = null;
            root.left = null;
            return Math.max(left, right);
        }
        if (left >= limit && right >= limit) {
            return Math.max(left, right);
        }
        if (left < limit) {
            root.left = null;
            return right;
        }
        root.right = null;
        return left;
    }

    public class Codec {
        public String serialize(TreeNode root) {
            if (root == null) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            serialize(root, sb);
            return sb.toString();
        }

        private void serialize(TreeNode root, StringBuilder sb) {
            if (root == null) {
                sb.append("null,");
                return;
            }
            sb.append(root.val + ",");
            serialize(root.left, sb);
            serialize(root.right, sb);
        }

        public TreeNode deserialize(String data) {
            if (data.equals("")) {
                return null;
            }
            String[] array = data.split(",");
            return deserialize(new LinkedList<String>(Arrays.asList(array)));
        }

        private TreeNode deserialize(LinkedList<String> data) {
            System.out.println(data);
            if (data.isEmpty()) {
                return null;
            }
            String poll = data.poll();
            if (poll.equals("null")) {
                return null;
            }
            int val = Integer.parseInt(poll);
            TreeNode root = new TreeNode(val);
            root.left = deserialize(data);
            root.right = deserialize(data);
            return root;
        }
    }

    public int[] smallestMissingValueSubtree(int[] parents, int[] nums) {
        int n = parents.length;
        int[] ans = new int[n];
        Arrays.fill(ans, 1);
        int node = -1;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 1) {
                node = i;
                break;
            }
        }
        if (node < 0) {
            return ans;
        }
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList());
        for (int i = 1; i < n; i++) {
            g[parents[i]].add(i);
        }
        HashSet<Integer> vis = new HashSet<>();
        int mex = 2;
        while (node >= 0) {
            dfs(node, g, vis, nums);
            while (vis.contains(mex)) {
                mex++;
            }
            ans[node] = mex;
            node = parents[node];
        }
        return ans;
    }

    private void dfs(int x, List<Integer>[] g, HashSet<Integer> vis, int[] nums) {
        vis.add(nums[x]);
        for (Integer son : g[x]) {
            if (!vis.contains(nums[son])) {
                dfs(son, g, vis, nums);
            }
        }
    }

    public TreeNode lcaDeepestLeaves1(TreeNode root) {
        TreeNode[] fa = new TreeNode[1001];
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean isCanNext = root.left != null && root.right != null;
        while (isCanNext) {
            int size = queue.size();
            isCanNext = false;
            while (size-- > 0) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    fa[node.left.val] = node;
                    queue.offer(node.left);
                    isCanNext |= node.left.left != null || node.left.right != null;
                }
                if (node.right != null) {
                    fa[node.right.val] = node;
                    queue.offer(node.right);
                    isCanNext |= node.right.left != null || node.right.right != null;
                }
            }
        }

        while (queue.size() > 1) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode node = queue.poll();
                if (fa[node.val] != queue.peekLast()) {
                    queue.offer(fa[node.val]);
                }
            }
        }
        return queue.peek();
    }

    public TreeNode lcaDeepestLeaves2(TreeNode root) {
        dfslcaDeepestLeaves(root, 0);
        return res;
    }

    int maxDepth = 0;
    TreeNode res;

    private int dfslcaDeepestLeaves(TreeNode root, int level) {
        if (root == null) {
            maxDepth = Math.max(level, maxDepth);
            return level;
        }
        int left = dfslcaDeepestLeaves(root.left, level + 1);
        int right = dfslcaDeepestLeaves(root.right, level + 1);
        if (left == right && right == maxDepth) {
            res = root;
        }
        return Math.max(left, right);
    }

    public TreeNode lcaDeepestLeaves(TreeNode root) {
        return dfs(root).getValue();
    }

    private Pair<Integer, TreeNode> dfs(TreeNode root) {
        if (root == null) {
            return new Pair<>(0, null);
        }
        Pair<Integer, TreeNode> left = dfs(root.left);
        Pair<Integer, TreeNode> right = dfs(root.right);
        if (left.getKey() > right.getKey()) {
            return new Pair<>(left.getKey() + 1, left.getValue());
        }

        if (left.getKey() < right.getKey()) {
            return new Pair<>(right.getKey() + 1, right.getValue());
        }
        return new Pair<>(left.getKey() + 1, root);
    }

    private final int LIMIT = 8000;
    Long[][] memo = new Long[LIMIT + 1][2];
    boolean[][] visited = new boolean[LIMIT + 1][2];

    public int minimumJumps(int[] forbidden, int a, int b, int x) {
        HashSet<Integer> forbid = new HashSet<>(forbidden.length);
        for (int i : forbidden) {
            forbid.add(i);
        }
        Long result = helper(0, x, forbid, a, b, 0);
        return result == Integer.MAX_VALUE ? -1 : result.intValue();
    }

    private Long helper(int cur, int target, HashSet<Integer> forbid, int a, int b, int back) {
        if (cur == target) {
            return 0l;
        }
        visited[cur][back] = true;
        long result = Integer.MAX_VALUE;
        if (cur + a <= LIMIT && !forbid.contains(cur + a) && !visited[cur + a][0]) {
            result = Math.min(result, 1 + helper(cur + a, target, forbid, a, b, 0));
        }
        if (cur - b >= 0 && back < 1 && !forbid.contains(cur - b) && !visited[cur - b][1]) {
            result = Math.min(result, 1 + helper(cur - b, target, forbid, a, b, 1));
        }
        return memo[cur][back] = result;
    }

    private int[] arr;
    private long[] mem;
    private Map<Integer, Integer> map;
    private int MOD = (int) 1e9 + 7;

    public int numFactoredBinaryTrees(int[] arr) {
        Arrays.sort(arr);
        int n = arr.length;
        map = new HashMap<Integer, Integer>();
        this.arr = arr;
        mem = new long[n];
        Arrays.fill(mem, -1l);

        long res = 0l;
        for (int i = 0; i < n; i++) {
            map.put(arr[i], i);
        }
        for (int i = 0; i < n; i++) {
            res = (res + dfsnumFactoredBinaryTrees(i)) % MOD;
        }
        return (int) (res % MOD);
    }

    private long dfsnumFactoredBinaryTrees(int i) {
        if (mem[i] != -1) {
            return mem[i];
        }
        long res = 1l;
        for (int j = 0; j < i; j++) {
            if (arr[i] % arr[j] == 0 && map.containsKey(arr[i] / arr[j])) {
                res = (res + dfsnumFactoredBinaryTrees(j) * dfsnumFactoredBinaryTrees(map.get(arr[i] / arr[j])) % MOD) % MOD;
            }
        }
        return mem[i] = res;
    }

    public int pseudoPalindromicPaths(TreeNode root) {
        int[] cnt = new int[10];
        return dfspseudoPalindromicPaths(root, cnt);
    }

    private int dfspseudoPalindromicPaths(TreeNode root, int[] cnt) {
        if (root == null) {
            return 0;
        }
        cnt[root.val]++;
        int res = 0;
        if (root.left == null && root.right == null) {
            if (isPseudoPalindrome(cnt)) {
                res = 1;
            }
        } else {
            res = dfspseudoPalindromicPaths(root.left, cnt) + dfspseudoPalindromicPaths(root.right, cnt);
        }
        cnt[root.val]--;
        return res;
    }

    private boolean isPseudoPalindrome(int[] cnt) {
        int odd = 0;
        for (int i : cnt) {
            if (i % 2 == 1) {
                odd++;
            }
        }
        return odd <= 1;
    }

    public TreeNode reverseOddLevels1(TreeNode root) {
        if (root == null) {
            return null;
        }
        dfsreverseOddLevels(root.left, root.right, true);
        return root;
    }

    private void dfsreverseOddLevels(TreeNode left, TreeNode right, boolean swap) {
        if (left == null) {
            return;
        }
        if (swap) {
            int tmp = left.val;
            left.val = right.val;
            right.val = tmp;
        }
        dfsreverseOddLevels(left.left, right.right, !swap);
        dfsreverseOddLevels(left.right, right.left, !swap);
    }

    public TreeNode reverseOddLevels(TreeNode root) {
        if (root == null) {
            return root;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean swap = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            ArrayList<TreeNode> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                list.add(node);
                if (node.left != null) {
                    queue.offer(node.left);
                    queue.offer(node.right);
                }
            }
            if (swap) {
                for (int i = 0, j = size - 1; i <= j; i++, j--) {
                    int tmp = list.get(i).val;
                    list.get(i).val = list.get(j).val;
                    list.get(j).val = tmp;
                }
            }
            swap = !swap;
        }
        return root;
    }

    public TreeNode replaceValueInTree(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        root.val = 0;
        while (!queue.isEmpty()) {
            Queue<TreeNode> queue2 = new ArrayDeque<>();
            int sum = 0;
            for (TreeNode fa : queue) {
                if (fa.left != null) {
                    queue2.offer(fa.left);
                    sum += fa.left.val;
                }
                if (fa.right != null) {
                    queue2.offer(fa.right);
                    sum += fa.right.val;
                }
            }
            for (TreeNode fa : queue) {
                int childSum = (fa.left != null ? fa.left.val : 0) +
                        (fa.right != null ? fa.right.val : 0);
                if (fa.left != null) {
                    fa.left.val = sum - childSum;
                }
                if (fa.right != null) {
                    fa.right.val = sum - childSum;
                }
            }
            queue = queue2;
        }
        return root;
    }

}