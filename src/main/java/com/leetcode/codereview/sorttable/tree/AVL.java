package com.leetcode.codereview.sorttable.tree;


import com.leetcode.codereview.sorttable.SortTable;

public class AVL<K extends Comparable<K>, V> implements SortTable<K, V> {
    static class AVLNode<K extends Comparable<K>, V> {
        public K key;
        public V value;
        public AVLNode<K, V> left;
        public AVLNode<K, V> right;

        public int height;

        public AVLNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.height = 1;
        }
    }

    public AVLNode<K, V> root;
    public int size;


    public AVL() {
        this.root = null;
        this.size = 0;
    }


    @Override
    public void put(K key, V value) {
        if (key == null) {
            return;
        }
        AVLNode<K, V> oldNode = findByKey(key);
        if (oldNode != null) {
            oldNode.value = value;
            return;
        }
        root = add(root, key, value);
        size++;
    }


    private AVLNode<K, V> add(AVLNode<K, V> cur, K key, V value) {
        if (cur == null) {
            AVLNode<K, V> node = new AVLNode<>(key, value);
            return node;
        }


        if (key.compareTo(cur.key) < 0) {
            cur.left = add(cur.left, key, value);
        } else {
            cur.right = add(cur.right, key, value);
        }
        updateHeight(cur);
        return maintain(cur);
    }


    // 站在爷爷的角度调整父节点的高度，父节点以下是平衡的
    private AVLNode<K, V> maintain(AVLNode<K, V> cur) {
        if (cur == null) {
            return null;
        }
        int leftHight = cur.left == null ? 0 : cur.left.height;
        int rightHight = cur.right == null ? 0 : cur.right.height;
        if (Math.abs(leftHight - rightHight) <= 1) {
            return cur;
        }


        if (leftHight > rightHight) {
            int leftleftHeight = cur.left != null && cur.left.left != null ? cur.left.left.height : 0;
            int leftrightHeight = cur.left != null && cur.left.right != null ? cur.left.right.height : 0;
            if (leftleftHeight >= leftrightHeight) {
                //LL
                cur = rightRotate(cur);
            } else {
                //LR
                cur.left = leftRotate(cur.left);
                cur = rightRotate(cur);
            }
        } else {
            int rightleftHeight = cur.right != null && cur.right.left != null ? cur.right.left.height : 0;
            int rightrightHeight = cur.right != null && cur.right.right != null ? cur.right.right.height : 0;
            if (rightrightHeight >= rightleftHeight) {
                //RR
                cur = leftRotate(cur);
            } else {
                //RL
                cur.right = rightRotate(cur.right);
                cur = leftRotate(cur);
            }
        }
        return cur;
    }


    private AVLNode<K, V> leftRotate(AVLNode<K, V> cur) {
        AVLNode<K, V> right = cur.right;
        cur.right = right.left;
        right.left = cur;
        updateHeight(cur);
        updateHeight(right);
        return right;
    }



    private AVLNode<K, V> rightRotate(AVLNode<K, V> cur) {
        AVLNode<K, V> left = cur.left;
        cur.left = left.right;
        left.right = cur;
        updateHeight(cur);
        updateHeight(left);
        return left;
    }


    private void updateHeight(AVLNode<K, V> cur) {
        if (cur == null) {
            return;
        }
        cur.height = Math.max(cur.left == null ? 0 : cur.left.height, cur.right == null ? 0 : cur.right.height) + 1;
    }


    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }
        AVLNode<K, V> node = findByKey(key);
        return node != null;
    }


    private AVLNode<K, V> findByKey(K key) {
        AVLNode<K, V> cur = this.root;
        while (cur != null) {
            if (key.compareTo(cur.key) == 0) {
                return cur;
            } else if (key.compareTo(cur.key) < 0) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return null;
    }


    @Override
    public void remove(K key) {
        if (key == null) {
            return;
        }
        if (containsKey(key)) {
            size--;
            root = delete(root, key);
        }
    }


    private AVLNode<K, V> delete(AVLNode<K, V> cur, K key) {
        if (key.compareTo(cur.key) < 0) {
            cur.left = delete(cur.left, key);
        } else if (key.compareTo(cur.key) > 0) {
            cur.right = delete(cur.right, key);
        } else {
            if (cur.left == null && cur.right == null) {
                cur = null;
            } else if (cur.left == null && cur.right != null) {
                cur = cur.right;
            } else if (cur.left != null && cur.right == null) {
                cur = cur.left;
            } else {
                AVLNode<K, V> next = cur.right;
                while (next.left != null) {
                    next = next.left;
                }
                cur.right = delete(cur.right, next.key);
                // 这里并没有改变next变量的指向
                next.left = cur.left;
                next.right = cur.right;
                cur = next;
            }
        }
        updateHeight(cur);
        return maintain(cur);
    }

    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }
        AVLNode<K, V> node = findByKey(key);
        return node == null ? null : node.value;
    }


    @Override
    public K ceilingKey(K key) {
        if (key == null) {
            return null;
        }
        return findNoSmall(key);
    }


    private K findNoSmall(K key) {
        AVLNode<K, V> cur = this.root;
        AVLNode<K, V> res = null;
        while (cur != null) {
            if (key.compareTo(cur.key) > 0) {
                cur = cur.right;
            } else if (key.compareTo(cur.key) < 0) {
                res = cur;
                cur = cur.left;
            } else {
                return cur.key;
            }
        }
        return res == null ? null : res.key;

    }


    @Override
    public K firstKey() {
        if (root == null) {
            return null;
        }
        AVLNode<K, V> cur = this.root;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur.key;
    }


    @Override
    public K lastKey() {
        if (root == null) {
            return null;
        }
        AVLNode<K, V> cur = this.root;
        while (cur.right != null) {
            cur = cur.right;
        }
        return cur.key;
    }


    @Override
    public K floorKey(K key) {
        if (key == null) {
            return null;
        }
        return findNoBig(key);
    }


    @Override
    public int size() {
        return this.size;
    }

    private K findNoBig(K key) {
        AVLNode<K, V> cur = this.root;
        AVLNode<K, V> res = null;
        while (cur != null) {
            if (key.compareTo(cur.key) > 0) {
                res = cur;
                cur = cur.right;
            } else if (key.compareTo(cur.key) < 0) {
                cur = cur.left;
            } else {
                return cur.key;
            }
        }
        return res == null ? null : res.key;
    }


    public void print() {
        print(root);

    }


    private void print(AVLNode<K, V> root) {
        if (root == null) {
            return;
        }
        print(root.left);
        System.out.print(root.key + " ");
        print(root.right);
    }


    private void checkHeight(AVLNode<K, V> cur) {
        if (cur == null) {
            return ;
        }
        int leftHight = cur.left == null ? 0 : cur.left.height;
        int rightHight = cur.right == null ? 0 : cur.right.height;
        if (Math.abs(leftHight - rightHight) > 1) {
            System.out.println("you are die");
            return ;
        }
        checkHeight(cur.left);
        checkHeight(cur.right);
    }


}
