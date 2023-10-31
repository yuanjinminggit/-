package com.leetcode.codereview.tree;

import com.leetcode.codereview.simpleConstruct.TreeNode;

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

}