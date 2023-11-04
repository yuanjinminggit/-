package com.leetcode.codereview.datastructure.tree;

import java.util.ArrayList;
import java.util.TreeMap;

public class AVLTree<K extends Comparable<K>, V> {
    private class Node {
        public K key;
        public V value;
        public Node left, right;
        public int height;

        public Node(K k, V v) {
            this.key = k;
            this.value = v;
            height = 1;
        }
    }

    private Node root;
    private int size;

    public AVLTree() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(K k, V v) {
        root = add(root, k, v);
    }

    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    public V get(K key) {
        return get(root, key);
    }

    private V get(Node root, K key) {
        if (root == null) {
            return null;
        }
        if (key.compareTo(root.key) < 0) {
            return get(root.left, key);
        } else if (key.compareTo(root.key) > 0) {
            return get(root.right, key);
        } else {
            return root.value;
        }
    }

    private Node add(Node node, K key, V value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }

        if (key.compareTo(node.key) < 0) {
            node.left = add(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = add(node.right, key, value);
        } else {
            node.value = value;
        }
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        int balanceFactor = getBalanceFactor(node);
        if (Math.abs(balanceFactor) > 1) {
            if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
                return rightRotate(node);
            }
            if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
                return leftRotate(node);
            }
            if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }

            if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        return node;
    }

    private Node leftRotate(Node node) {
        Node right = node.right;
        node.right = right.left;
        right.left = node;
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        right.height = Math.max(getHeight(right.left), getHeight(right.right)) + 1;
        return right;
    }

    private Node rightRotate(Node node) {
        Node left = node.left;
        node.left = left.right;
        left.right = node;

        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        left.height = Math.max(getHeight(left.left), getHeight(left.right)) + 1;
        return left;
    }

    public boolean isBST() {
        ArrayList<K> keys = new ArrayList<>();
        inOrder(root, keys);
        for (int i = 1; i < keys.size(); i++) {
            if (keys.get(i - 1).compareTo(keys.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(Node root) {
        if (root == null) {
            return true;
        }
        int balanceFactor = getBalanceFactor(root);
        if (Math.abs(balanceFactor) > 1) {
            return false;
        }
        return isBalanced(root.left) && isBalanced(root.right);
    }

    private void inOrder(Node root, ArrayList<K> keys) {
        if (root == null) {
            return;
        }
        inOrder(root.left, keys);
        keys.add(root.key);
        inOrder(root.right, keys);
    }

    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    public boolean contains(K k) {
        return contains(root, k);
    }

    private boolean contains(Node node, K e) {
        if (node == null) {
            return false;
        }
        if (e.compareTo(node.key) < 0) {
            return contains(node.left, e);
        } else if (e.compareTo(node.key) > 0) {
            return contains(node.right, e);
        } else {
            return true;
        }
    }

    public void removeMax() {
        if (root == null) {
            return;
        }
        removeMax(root);
    }

    private Node removeMax(Node node) {
        if (node.right == null) {
            Node left = node.left;
            node.left = null;
            size--;
            return left;
        }
        node.right = removeMax(node.right);
        return node;
    }

    public void remove(K key) {
        root = remove(root, key);
    }

    private Node remove(Node node, K key) {
        if (node == null) {
            return null;
        }
        Node retNode = node;
        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
        } else {
            if (node.left == null) {
                Node right = node.right;
                node.right = null;
                size--;
                retNode = right;
            } else if (node.right == null) {
                Node left = node.left;
                node.left = null;
                size--;
                retNode = left;
            } else {
                Node successor = minimum(node.right);
                successor.right = remove(node.right, successor.key);
                successor.left = node.left;
                node.left = node.right = null;
                retNode = successor;
            }

        }
        if (retNode == null) {
            return null;
        }
        retNode.height = 1 + Math.max(getHeight(retNode.left), getHeight(retNode.right));
        int balanceFactor = getBalanceFactor(retNode);
        if (Math.abs(balanceFactor) > 1) {
            if (balanceFactor > 1 && getBalanceFactor(retNode.left) >= 0) {
                return rightRotate(retNode);
            }
            if (balanceFactor < -1 && getBalanceFactor(retNode.right) <= 0) {
                return leftRotate(retNode);
            }
            if (balanceFactor > 1 && getBalanceFactor(retNode.left) < 0) {
                retNode.left = leftRotate(retNode.left);
                return rightRotate(retNode);
            }

            if (balanceFactor < -1 && getBalanceFactor(retNode.right) > 0) {
                retNode.right = rightRotate(retNode.right);
                return leftRotate(retNode);
            }
        }

        return retNode;

    }

    private Node removeMin(Node node) {
        if (node.left == null) {
            Node right = node.right;
            node.right = null;
            size--;
            return right;
        }
        node.left = removeMin(node.left);
        return node;
    }

    private Node minimum(Node right) {
        Node cur = right;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur;
    }

    public static void main(String[] args) {
        AVLTree<Integer, Integer> avlTree = new AVLTree<Integer, Integer>();
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int times = 1000;
        int maxkey = 10000;
        int maxvalue = 127;
        for (int i = 0; i < times; i++) {
            int key = (int) (Math.random() * maxkey);
            int val = (int) (Math.random() * maxvalue);
            if (Math.random() > 0.1) {
                avlTree.add(key, val);
                map.put(key, val);
//                System.out.println(bPlusTree.printAll());
            }


            if (Math.random() > 0.3 && map.size() > 0) {
                int floorKey = (int) (Math.random() * maxkey);
                System.out.println(map.get(floorKey) + ":" + avlTree.get(floorKey));
            }
        }

        for (int i = 0; i < times; i++) {
            if (Math.random() > 0.3 && avlTree.size > 0) {
                int floorKey = (int) (Math.random() * maxkey);
                map.remove(floorKey);
                avlTree.remove(floorKey);
            }
        }
        System.out.println(avlTree.isBalanced());
        System.out.println(avlTree.isBST());

        System.out.println(avlTree.size == map.size());
    }


}
