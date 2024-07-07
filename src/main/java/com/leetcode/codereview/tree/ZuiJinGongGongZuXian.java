package com.leetcode.codereview.tree;

import com.alibaba.fastjson.JSON;
import com.leetcode.codereview.simpleConstruct.Node;
import com.leetcode.codereview.simpleConstruct.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
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

    public int maximumCount(int[] nums) {
        int a = 0;
        int b = 0;
        for (int num : nums) {
            if (num > 0) {
                a++;
            }
            if (num < 0) {
                b++;
            }
        }
        return Math.max(a, b);
    }

    public int findChampion(int n, int[][] edges) {
        boolean[] isWeak = new boolean[n];
        for (int[] e : edges) {
            isWeak[e[1]] = true;
        }
        int ans = -1;
        for (int i = 0; i < n; i++) {
            if (isWeak[i]) {
                continue;
            }
            if (ans != -1) {
                return -1;
            }
            ans = i;
        }
        return ans;
    }

    private TreeNode startNode;
    private final Map<TreeNode, TreeNode> fa = new HashMap<>();

    public int amountOfTime(TreeNode root, int start) {
        dfs(root, null, start);
        return maxDepth(startNode, startNode);
    }

    private int maxDepth(TreeNode node, TreeNode from) {
        if (node == null) {
            return -1;
        }
        int res = -1;

        if (node.left != from) {
            res = Math.max(res, maxDepth(node.left, node));
        }
        if (node.right != from) {
            res = Math.max(res, maxDepth(node.right, node));
        }
        if (fa.get(node) != from) {
            res = Math.max(res, maxDepth(fa.get(node), node));
        }
        return res + 1;
    }

    private void dfs(TreeNode node, TreeNode from, int start) {
        if (node == null) {
            return;
        }
        fa.put(node, from);
        if (node.val == start) {
            startNode = node;
        }
        dfs(node.left, node, start);
        dfs(node.right, node, start);
    }

    private int nodeId, size;

    public int minMalwareSpread(int[][] graph, int[] initial) {
        int n = graph.length;
        boolean[] vis = new boolean[n];
        boolean[] isInitial = new boolean[n];
        int mn = Integer.MAX_VALUE;
        for (int x : initial) {
            isInitial[x] = true;
            mn = Math.min(mn, x);
        }
        int ans = -1;
        int maxSize = 0;
        for (int x : initial) {
            if (vis[x]) {
                continue;
            }
            nodeId = -1;
            size = 0;
            dfs(x, graph, vis, isInitial);
            if (nodeId >= 0 && (size > maxSize || size == maxSize && nodeId < ans)) {
                ans = nodeId;
                maxSize = size;
            }
        }
        return ans < 0 ? mn : ans;
    }

    private void dfs(int x, int[][] graph, boolean[] vis, boolean[] isInitial) {
        vis[x] = true;
        size++;
        if (nodeId != -2 && isInitial[x]) {
            nodeId = nodeId == -1 ? x : -2;
        }
        for (int y = 0; y < graph[x].length; y++) {
            if (graph[x][y] == 1 && !vis[y]) {
                dfs(y, graph, vis, isInitial);
            }
        }
    }

    public int[] findOriginalArray(int[] changed) {
        Arrays.sort(changed);
        int[] ans = new int[changed.length / 2];
        int ansIdx = 0;
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int x : changed) {
            if (!cnt.containsKey(x)) {
                if (ansIdx == ans.length) {
                    return new int[0];
                }
                ans[ansIdx++] = x;
                cnt.merge(x * 2, 1, Integer::sum);
            } else {
                int c = cnt.merge(x, -1, Integer::sum);
                if (c == 0) {
                    cnt.remove(x);
                }
            }
        }
        return ans;
    }

    public int garbageCollection(String[] garbage, int[] travel) {
        HashMap<Character, Integer> distance = new HashMap<>();
        int res = 0, curDis = 0;
        for (int i = 0; i < garbage.length; i++) {
            res += garbage[i].length();
            if (i > 0) {
                curDis += travel[i - 1];
            }
            for (char c : garbage[i].toCharArray()) {
                distance.put(c, curDis);
            }
        }
        return res + distance.values().stream().reduce(0, Integer::sum);
    }

    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 四方向

    public int orangesRotting(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int fresh = 0;
        List<int[]> q = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    fresh++;
                } else if (grid[i][j] == 2) {
                    q.add(new int[]{i, j});
                }
            }
        }
        int ans = -1;
        while (!q.isEmpty()) {
            ans++;
            List<int[]> tmp = q;
            q = new ArrayList<>();
            for (int[] pos : tmp) {
                for (int[] d : DIRECTIONS) {
                    int i = pos[0] + d[0];
                    int j = pos[1] + d[1];
                    if (i >= 0 && i < m && j >= 0 && j < n && grid[i][j] == 1) {
                        fresh--;
                        grid[i][j] = 2;
                        q.add(new int[]{i, j});
                    }
                }
            }
        }
        return fresh > 0 ? -1 : Math.max(ans, 0);
    }

    public int minimumRounds(int[] tasks) {
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int t : tasks) {
            cnt.merge(t, 1, Integer::sum);
        }
        int ans = 0;
        for (Integer c : cnt.values()) {
            if (c == 1) {
                return -1;
            }
            ans += (c + 2) / 3;
        }
        return ans;
    }

    public long numberOfWeeks(int[] milestones) {
        if (milestones.length == 1) {
            return 1;
        }
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> {
            return b[1] - a[1];
        });

        for (int i = 0; i < milestones.length; i++) {
            heap.offer(new int[]{i, milestones[i]});
        }
        int count = 0;
        while (!heap.isEmpty()) {
            int[] end = heap.poll();
            int[] second = heap.poll();
            if (end[1] - second[1] > 0) {
                heap.offer(new int[]{end[0], end[1] - second[1]});
            }
            count += 2 * second[1];
            if (heap.size() == 1) {
                count++;
                break;
            }
        }
        return count;
    }

    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        int n = difficulty.length;
        int[][] jobs = new int[n][2];
        for (int i = 0; i < n; i++) {
            jobs[i][0] = difficulty[i];
            jobs[i][1] = profit[i];
        }
        Arrays.sort(jobs, (a, b) -> a[0] - b[0]);
        Arrays.sort(worker);
        int ans = 0, j = 0, maxProfit = 0;
        for (int w : worker) {
            while (j < n && jobs[j][0] <= w) {
                maxProfit = Math.max(maxProfit, jobs[j++][1]);
            }
            ans += maxProfit;
        }
        return ans;
    }

    public int getWinner(int[] arr, int k) {
        int mx = arr[0];
        int win = 0;
        for (int i = 0; i < arr.length && win < k; i++) {
            if (arr[i] > mx) {
                mx = arr[i];
                win = 0;
            }
            win++;
        }
        return mx;
    }

    public int theMaximumAchievableX(int num, int t) {
        return num + 2 * t;
    }

    public int maxDivScore(int[] nums, int[] divisors) {
        int count = 0;
        int max = 0;
        int ans = 0;
        for (int divisor : divisors) {
            count = 0;
            for (int num : nums) {
                if (num % divisor == 0) {
                    count++;
                }
            }
            if (count == max) {
                ans = ans == 0 ? divisor : Math.min(ans, divisor);
            } else if (count > max) {
                ans = divisor;
                max = count;
            }
        }
        return ans;
    }

    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
        int n = s.length();
        int[] sum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            int bit = 1 << (s.charAt(i) - 'a');
            sum[i + 1] = sum[i] ^ bit;
        }
        List<Boolean> ans = new ArrayList<>(queries.length);
        for (int[] q : queries) {
            int left = q[0], right = q[1], k = q[2];
            int m = Integer.bitCount(sum[right + 1] ^ sum[left]);
            ans.add(m / 2 <= k);
        }
        return ans;
    }

    public List<List<Integer>> findWinners(int[][] matches) {
        HashMap<Integer, Integer> lossCount = new HashMap<>();
        for (int[] m : matches) {
            if (!lossCount.containsKey(m[0])) {
                lossCount.put(m[0], 0);
            }
            lossCount.merge(m[1], 1, Integer::sum);
        }
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            ans.add(new ArrayList<Integer>());
        }
        for (Map.Entry<Integer, Integer> entry : lossCount.entrySet()) {
            Integer cnt = entry.getValue();
            if (cnt < 2) {
                ans.get(cnt).add(entry.getKey());
            }
        }
        Collections.sort(ans.get(0));
        Collections.sort(ans.get(1));
        return ans;
    }

    public int[] findIndices1(int[] nums, int indexDifference, int valueDifference) {
        int n = nums.length;
        for (int i = 0; i < n - indexDifference; i++) {
            for (int j = i + indexDifference; j < n; j++) {
                if (Math.abs(nums[i] - nums[j]) >= valueDifference) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    public int[] findIndices(int[] nums, int indexDifference, int valueDifference) {
        int minIndex = 0, maxIndex = 0;
        for (int j = indexDifference; j < nums.length; j++) {
            int i = j - indexDifference;
            if (nums[i] < nums[minIndex]) {
                minIndex = i;
            }
            if (nums[j] - nums[minIndex] >= valueDifference) {
                return new int[]{minIndex, j};
            }
            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
            if (nums[maxIndex] - nums[j] >= valueDifference) {
                return new int[]{maxIndex, j};
            }
        }
        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("MARKETING", 20);
        map.put("MERCHANT_SALES", 30);
        map.put("MALL", 20);
        map.put("RISK_CONTROL", 20);
        String jsonString = JSON.toJSONString(map);
        System.out.println(jsonString);
    }

    public int paintWalls(int[] cost, int[] time) {
        int n = cost.length;
        int[][] memo = new int[n][2 * n + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dfs(n - 1, 0, cost, time, memo);
    }

    private int dfs(int i, int j, int[] cost, int[] time, int[][] memo) {
        if (j > i) {
            return 0;
        }
        if (i < 0) {
            return Integer.MAX_VALUE / 2;
        }
        int k = j + memo.length;
        if (memo[i][k] != -1) {
            return memo[i][k];
        }
        int res1 = dfs(i - 1, j + time[i], cost, time, memo);
        int res2 = dfs(i - 1, j - 1, cost, time, memo);
        return memo[i][k] = Math.min(res1, res2);
    }

    private int[][] matrix;
    private int m;
    private int n;

    private List<Integer> order;

    public List<Integer> spiralOrder(int[][] matrix) {
        this.matrix = matrix;
        this.n = matrix.length;
        this.m = matrix[0].length;
        order = new ArrayList<>();
        dfsSpiralOrder(0, 0, m, n);
        return order;
    }

    private void dfsSpiralOrder(int x, int y, int m, int n) {
        if (m == 0 || n == 0) {
            return;
        }
        if (n == 1) {
            for (int i = 0; i < m; i++) {
                order.add(matrix[x][y + i]);
            }
            return;
        }
        if (m == 1) {
            for (int i = 0; i < n; i++) {
                order.add(matrix[x + i][y]);
            }
            return;
        }
        // 横者走
        for (int i = 0; i < m; i++) {
            order.add(matrix[x][y + i]);
        }
        // 竖着走
        for (int i = 1; i < n; i++) {
            order.add(matrix[x + i][y + m - 1]);
        }
        // 横着往回走
        for (int i = 1; i < m; i++) {
            order.add(matrix[x + n - 1][y + m - 1 - i]);
        }
        // 往上走
        for (int i = 1; i < n - 1; i++) {
            order.add(matrix[x + n - 1 - i][y]);
        }
        dfsSpiralOrder(x + 1, y + 1, m - 2, n - 2);
    }

    private static final int[][] DIRS = {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};

    public boolean checkMove(char[][] board, int rMove, int cMove, char color) {
        int m = board.length;
        int n = board[0].length;
        for (int[] dir : DIRS) {

            int x = rMove + dir[0];
            int y = cMove + dir[1];

            if (x < 0 || x >= m || y < 0 || y >= n || board[x][y] != (color ^ 'B' ^ 'W')) {
                continue;
            }
            while (true) {
                x += dir[0];
                y += dir[1];
                if (x < 0 || x >= m || y < 0 || y >= n || board[x][y] == '.') {
                    break;
                }
                if (board[x][y] == color) {
                    return true;
                }
            }
        }
        return false;
    }

    public long countAlternatingSubarrays(int[] nums) {
        long ans = 0;
        int cnt = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] != nums[i - 1]) {
                cnt++;
            } else {
                cnt = 1;
            }
            ans += cnt;
        }
        return ans;
    }

    public int[][] modifiedMatrix(int[][] matrix) {
        for (int j = 0; j < matrix[0].length; j++) {
            int mx = 0;
            for (int[] row : matrix) {
                mx = Math.max(mx, row[j]);
            }
            for (int[] row : matrix) {
                if (row[j] == -1) {
                    row[j] = mx;
                }
            }
        }
        return matrix;
    }

    public int maximumPrimeDifference(int[] nums) {
        int i = 0;
        while (!isPrime(nums[i])) {
            i++;
        }
        int j = nums.length - 1;
        while (!isPrime(nums[j])) {
            j--;
        }
        return j - i;

    }

    private boolean isPrime(int n) {
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return n >= 2;
    }

    List<Integer>[] match;
    boolean[] vis;
    int num;

    public int countArrangement(int n) {
        vis = new boolean[n + 1];
        match = new List[n + 1];
        for (int i = 0; i <= n; i++) {
            match[i] = new ArrayList<Integer>();
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i % j == 0 || j % i == 0) {
                    match[i].add(j);
                }
            }
        }
        backtrack(1, n);
        return num;
    }

    private void backtrack(int index, int n) {
        if (index == n + 1) {
            num++;
            return;
        }
        for (Integer x : match[index]) {
            if (!vis[x]) {
                vis[x] = true;
                backtrack(index + 1, n);
                vis[x] = false;
            }
        }
    }

}