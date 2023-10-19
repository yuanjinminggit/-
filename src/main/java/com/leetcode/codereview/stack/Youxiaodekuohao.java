package com.leetcode.codereview.stack;

import com.leetcode.codereview.simpleConstruct.TreeNode;

import java.util.*;

public class Youxiaodekuohao {
    public boolean isValid(String s) {
        char[] charArray = s.toCharArray();
        Deque<Character> stack = new LinkedList<>();
        for (char c : charArray) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                if (c == ')') {
                    if (stack.isEmpty() || stack.peek() != '(') {
                        return false;
                    }
                    stack.pop();
                } else if (c == ']') {
                    if (stack.isEmpty() || stack.peek() != '[') {
                        return false;
                    }
                    stack.pop();
                } else if (c == '}') {
                    if (stack.isEmpty() || stack.peek() != '{') {
                        return false;
                    }
                    stack.pop();
                }
            }
        }
        return true;
    }


    public String simplifyPath(String path) {
        String[] array = path.split("/");
        if (array.length == 0) {
            return "/";
        }
        Deque<String> stack = new LinkedList<>();
        for (String s : array) {
            if ("".equals(s) || ".".equals(s)) {
                continue;
            }
            if ("..".equals(s)) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
                continue;
            }
            stack.push(s);
        }
        StringBuilder sb = new StringBuilder();
        if (stack.isEmpty()) {
            sb.append("/");
        }
        while (!stack.isEmpty()) {
            sb.insert(0, stack.pop());
            sb.insert(0, "/");
        }
        return sb.toString();
    }


    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        Deque<TreeNode> stack = new LinkedList<>();
        ArrayList<Integer> res = new ArrayList<>();
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            TreeNode pop = stack.pop();
            res.add(pop.val);
            root = pop.right;
        }
        return res;
    }


    public List<Integer> preorderTraversal(TreeNode root) {
        Deque<TreeNode> stack = new LinkedList<>();
        ArrayList<Integer> res = new ArrayList<>();
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                res.add(root.val);
                stack.push(root);
                root = root.left;
            }
            TreeNode pop = stack.pop();
            root = pop.right;
        }
        return res;
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        Deque<TreeNode> stack = new LinkedList<>();
        ArrayList<Integer> res = new ArrayList<>();
        TreeNode prev = null;
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            // 出栈
            root = stack.pop();
            if (root.right == null || root.right == prev) {
                res.add(root.val);
                prev = root;
                root = null;
            } else {
                // 重新入栈，将来还会再次弹出
                stack.push(root);
                root = root.right;
            }
        }
        return res;
    }


    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new LinkedList<>();
        for (String token : tokens) {
            if (token.equals("+")) {
                Integer v1 = stack.pop();
                Integer v2 = stack.pop();
                stack.push(v1 + v2);
            } else if (token.equals("-")) {
                Integer v1 = stack.pop();
                Integer v2 = stack.pop();
                stack.push(v2 - v1);
            } else if (token.equals("*")) {
                Integer v1 = stack.pop();
                Integer v2 = stack.pop();
                stack.push(v1 * v2);
            } else if (token.equals("/")) {
                Integer v1 = stack.pop();
                Integer v2 = stack.pop();
                stack.push(v2 / v1);
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }


    class BSTIterator {

        private List<Integer> list;
        private Integer curIndex;

        public BSTIterator(TreeNode root) {
            list = new ArrayList<>();
            inorderDfs(root, list);
            curIndex = 0;
        }

        private void inorderDfs(TreeNode root, List<Integer> list) {
            if (root == null) {
                return;
            }
            inorderDfs(root.left, list);
            list.add(root.val);
            inorderDfs(root.right, list);
        }

        public int next() {
            Integer res = list.get(curIndex);
            curIndex++;
            return res;
        }

        public boolean hasNext() {
            return curIndex + 1 < list.size();
        }
    }


    public boolean isValidSerialization(String preorder) {
        int n = preorder.length();
        int i = 0;
        Deque<Integer> stack = new LinkedList<>();
        stack.push(1);
        while (i < n) {
            if (stack.isEmpty()) {
                return false;
            }
            if (preorder.charAt(i) == ',') {
                i++;
            } else if (preorder.charAt(i) == '#') {
                int top = stack.pop() - 1;
                if (top > 0) {
                    stack.push(top);
                }
                i++;
            } else {
                while (i < n && preorder.charAt(i) != ',') {
                    i++;
                }
                int top = stack.pop() - 1;
                if (top >= 0) {
                    stack.push(top + 2);
                }
            }
        }
        return stack.isEmpty();
    }


//    public class NestedIterator implements Iterator<Integer> {
//
//        private List<Integer> list;
//        private int curIndex;
//
//        public NestedIterator(List<NestedInteger> nestedList) {
//            list = new ArrayList<>();
//            curIndex = 0;
//            init(nestedList);
//        }
//
//        private void init(List<NestedInteger> nestedList) {
//            for (NestedInteger nestedInteger : nestedList) {
//                if (nestedInteger.isInteger()) {
//                    list.add(nestedInteger.getInteger());
//                } else {
//                    init(nestedInteger.getList());
//                }
//            }
//        }
//
//        @Override
//        public Integer next() {
//            Integer res = list.get(curIndex);
//            curIndex++;
//            return res;
//        }
//
//        @Override
//        public boolean hasNext() {
//            return curIndex < list.size();
//        }
//    }

    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Deque<Integer> stack = new LinkedList<>();
        int idx = 0;
        for (int num : pushed) {
            if (num == popped[idx]) {
                idx++;
                continue;
            }
            if (stack.isEmpty()) {
                stack.push(num);
            } else {
                while (!stack.isEmpty() && stack.peek() == popped[idx]) {
                    stack.pop();
                    idx++;
                }
                stack.push(num);
            }
        }

        while (idx < popped.length && !stack.isEmpty() && stack.pop() == popped[idx]) {
            idx++;
        }

        return idx == popped.length;
    }


    public int[] maxDepthAfterSplit(String seq) {
        int d = 0;
        int length = seq.length();
        int[] ans = new int[length];
        for (int i = 0; i < length; i++) {
            if (seq.charAt(i) == '(') {
                d++;
                ans[i] = d % 2;
            } else {
                ans[i] = d % 2;
                --d;
            }
        }
        return ans;
    }


    public int longestValidParentheses(String s) {
        Deque<Integer> stack = new LinkedList<>();
        int max = 0;
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    max = Math.max(max, i - stack.peek());
                }
            }
        }
        return max;
    }


    public int calculate1(String s) {
        Deque<String> stack = new LinkedList<>();
        char[] charArray = s.toCharArray();
        int i = 0;
        while (i < charArray.length) {
            Character c = charArray[i];
            if (c == ' ') {

            } else if (c == '(') {
                stack.push(c + "");
            } else if (c >= '0' && c <= '9') {
                StringBuilder sb = new StringBuilder();
                while (i < charArray.length && charArray[i] >= '0' && charArray[i] <= '9') {
                    sb.append(charArray[i]);
                    i++;
                }
                if (stack.isEmpty()) {
                    stack.push(sb.toString());
                } else {
                    if (stack.peek().equals("*") || stack.peek().equals("/") || stack.peek().equals("+") || stack.peek().equals("-")) {
                        stack.push(sb.toString());
                        popStack(stack);
                    } else {
                        stack.push(sb.toString());
                    }
                }

                if (i == charArray.length) {
                    break;
                }
                i--;
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                if ((stack.isEmpty() || stack.peek().equals("(")) && c == '-') {
                    StringBuilder sb = new StringBuilder("-");
                    i++;
                    while (i < charArray.length && charArray[i] >= '0' && charArray[i] <= '9') {
                        sb.append(charArray[i]);
                        i++;
                    }
                    stack.push(sb.toString());
                    if (i == charArray.length) {
                        break;
                    }
                    i--;
                } else {
                    stack.push(c + "");
                }
            } else {
                getSum(stack);
                inOrderCalculate(stack);

            }
            i++;
        }
        if (stack.size() >= 3) {
            popStack(stack);
        }
        return stack.size() == 2 ? Integer.parseInt("-" + stack.pop()) : Integer.parseInt(stack.pop());

    }

    private void inOrderCalculate(Deque<String> stack) {
        if (stack.size() == 1) {
            return;
        }
        String num = stack.pop();
        if (stack.peek().equals("-")) {
            String pop = stack.pop();
            if (stack.isEmpty() || stack.peek().equals("(")) {
                int i = -Integer.parseInt(num);
                stack.push(String.valueOf(i));
                return;
            }
            stack.push(pop);
        }
        stack.push(num);
        popStack(stack);
    }

    private void getSum(Deque<String> stack) {
        if (stack.isEmpty()) {
            return;
        }
        if (stack.size() == 1) {
            return;
        }
        String num = stack.pop();
        if (stack.peek().equals("-")) {
            String pop = stack.pop();
            if (stack.isEmpty() || stack.peek().equals("(")) {
                stack.push(pop);
                stack.push(num);
                return;
            }
            stack.push(pop);
        }
        if (stack.peek().equals("(")) {
            stack.pop();
            stack.push(num);
            return;
        } else {
            stack.push(num);
            popStack(stack);
            getSum(stack);
        }

    }

    private void popStack(Deque<String> stack) {
        while (!stack.isEmpty() && !stack.peek().equals("(")) {
            String int1 = stack.pop();
            String ch = stack.pop();
            String int2 = stack.pop();
            int ans = subCalculate(Integer.parseInt(int2), Integer.parseInt(int1), ch);
            if (stack.isEmpty()) {
                stack.push(String.valueOf(ans));
                break;
            } else if (stack.peek().equals("(")) {
                stack.push(String.valueOf(ans));
                break;
            } else {
                stack.push(String.valueOf(ans));
            }
        }
    }

    public int subCalculate(int a, int b, String c) {
        if (c.equals("*")) {
            return a * b;
        }
        if (c.equals("+")) {
            return a + b;
        }
        if (c.equals("-")) {
            return a - b;
        }
        if (c.equals("/")) {
            return a / b;
        }
        return 0;
    }


    public int calculate(String s) {
        Deque<Integer> ops = new LinkedList<>();
        ops.push(1);
        int sign = 1;
        int ret = 0;
        int n = s.length();
        int i = 0;
        while (i < n) {
            if (s.charAt(i) == ' ') {
                i++;
            } else if (s.charAt(i) == '+') {
                sign = ops.peek();
                i++;
            } else if (s.charAt(i) == '-') {
                sign = -ops.peek();
                i++;
            } else if (s.charAt(i) == '(') {
                ops.push(sign);
                i++;
            } else if (s.charAt(i) == ')') {
                ops.pop();
                i++;
            } else {
                long num = 0;
                while (i < n && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + s.charAt(i) - '0';
                    i++;
                }
                ret += sign * num;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        Youxiaodekuohao youxiaodekuohao = new Youxiaodekuohao();
        youxiaodekuohao.calculate1("-(-2)+4");
    }


}
