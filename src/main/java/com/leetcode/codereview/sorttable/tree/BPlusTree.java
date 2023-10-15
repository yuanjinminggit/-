package com.leetcode.codereview.sorttable.tree;

import com.leetcode.codereview.sorttable.SortTable;

import java.util.*;


/*
 *
 * 新增节点：
 *   分裂：
 *     叶子节点
 *     非叶子节点
 * 删除节点：
 *       借：
 *           叶子节点：
 *              向做兄弟借    调整父亲的key，后继节点
 *              向右兄弟借
 *           非叶子节点
 *              向做兄弟借   向父亲借关键字，向左兄弟接一个儿子，父亲寻找后继节点
 *              向右兄弟借
 *       合并：(右边向左边合并)
 *           叶子结点
 *               和做兄弟合并
 *               和右兄弟合并
 *           非叶子结点
 *               和做兄弟合并  合并父节点的key,合并兄弟节点，父亲寻找后继节点
 *               和右兄弟合并
 *
 *
 *
 * */
public class BPlusTree<K extends Comparable<K>, V> implements SortTable<K,V> {

    private BPlusTreeNode root;
    private BPlusTreeNode head;
    private BPlusTreeNode tail;
    private int size;
    private int height;
    private int degree;
    private int UPPER_BOUND;
    private int UNDER_BOUND;

    public BPlusTree(int degree) {
        if (degree < 3) {
            throw new ArithmeticException("参数异常");
        }
        this.degree = degree;
        this.UPPER_BOUND = degree - 1;
        this.UNDER_BOUND = this.UPPER_BOUND / 2;
    }

    abstract class BPlusTreeNode {
        protected List<K> keys;

        public abstract BPlusTreeNode put(K key, V value);

        // 严格大于
        protected int findUpperIndex(K key) {
            int l = 0;
            int r = keys.size() - 1;
            if (keys.get(keys.size() - 1).compareTo(key) <= 0) {
                return keys.size();
            }
            while (l < r) {
                int mid = (r + l) / 2;
                if (keys.get(mid).compareTo(key) <= 0) {
                    l = mid + 1;
                } else {
                    r = mid;
                }
            }
            return l;
        }

        protected int getMidIndex() {
            return (UPPER_BOUND + 1) / 2;
        }

        protected abstract K findLeafKey();

        protected abstract V get(K key);

        protected abstract K floorKey(K key);

        protected abstract K ceilingKey(K key);

        protected abstract DeleteInfo remove(K key);

        protected abstract void borrow(BPlusTreeNode leftBrother, boolean isLeft, K key);

        protected abstract void combine(BPlusTreeNode childNode, K parentKey);

        public abstract Map<K, V> rangeQuery(K start, K end);
    }

    /************************BPlusTreeDataNode*******************************/
    class BPlusTreeDataNode extends BPlusTreeNode {
        public List<V> data;
        public BPlusTreeDataNode pre;
        public BPlusTreeDataNode next;


        public BPlusTreeDataNode(List<K> keys, List<V> data) {
            this.data = data;
            this.keys = keys;
        }


        @Override
        public BPlusTreeNode put(K key, V value) {
            int index = findEqualKeyIndex(key);
            if (index != -1) {
                data.set(index, value);
                return null;
            }
            size++;
            int upperIndex = findUpperIndex(key);
            keys.add(upperIndex, key);
            data.add(upperIndex, value);
            if (keys.size() > UPPER_BOUND) {
                BPlusTreeNode spite = spite();
                return spite;
            }
            return null;
        }


        @Override
        protected K findLeafKey() {
            return keys.get(0);
        }


        @Override
        public V get(K key) {
            int equalKeyIndex = findEqualKeyIndex(key);
            if (equalKeyIndex != -1) {
                return data.get(equalKeyIndex);
            }
            return null;
        }


        @Override
        public K floorKey(K key) {
//            for (int i = keys.size() - 1; i >= 0; i--) {
//                if (keys.get(i).compareTo(key) <= 0) {
//                    return keys.get(i);
//                }
//            }

            int left = findFloorKeyIndex(key);

            if (keys.get(left).compareTo(key) <= 0) {
                return keys.get(left);
            }

            BPlusTreeDataNode pre = this.pre;
            if (pre == null) {
                return null;
            }
            return pre.keys.get(pre.keys.size() - 1);
        }


        private int findFloorKeyIndex(K key) {
            int left = 0;
            int right = keys.size() - 1;
            while (left < right) {
                int mid = (right + left + 1) / 2;
                if (keys.get(mid).compareTo(key) <= 0) {
                    left = mid;
                } else {
                    right = mid + 1;
                }
            }
            return left;
        }


        @Override
        public K ceilingKey(K key) {
//            for (int i = 0; i < keys.size(); i++) {
//                if (keys.get(i).compareTo(key) >= 0) {
//                    return keys.get(i);
//                }
//            }
            Integer ceilingKeyIndex = findCeilingKeyIndex(key);
            if (keys.get(ceilingKeyIndex).compareTo(key) >= 0) {
                return keys.get(ceilingKeyIndex);
            }
            BPlusTreeDataNode next = this.next;
            if (next == null) {
                return null;
            }
            return next.keys.get(0);
        }


        @Override
        public DeleteInfo remove(K key) {
            int equalKeyIndex = findEqualKeyIndex(key);
            if (equalKeyIndex == -1) {
                return new DeleteInfo(false, false);
            }
            size--;
            keys.remove(equalKeyIndex);
            data.remove(equalKeyIndex);

            return new DeleteInfo(true, keys.size() < UNDER_BOUND);

        }


        @Override
        protected void borrow(BPlusTreeNode brother, boolean isLeft, K key) {
            BPlusTreeDataNode brotherNode = (BPlusTreeDataNode) brother;
            if (isLeft) {
                this.keys.add(brotherNode.keys.remove(brotherNode.keys.size() - 1));
                this.data.add(brotherNode.data.remove(brotherNode.data.size() - 1));
            } else {
                this.keys.add(brotherNode.keys.remove(0));
                this.data.add(brotherNode.data.remove(0));
            }
        }


        /*
         * this 指的是做兄弟
         * childnode 当前被删除的节点
         * */
        @Override
        protected void combine(BPlusTreeNode childNode, K parentKey) {
            BPlusTreeDataNode brotherNode = (BPlusTreeDataNode) childNode;
            this.keys.addAll(brotherNode.keys);
            this.data.addAll(brotherNode.data);
            if (brotherNode.next == null) {
                tail = this;
            } else {
                brotherNode.next.pre = this;
            }
            this.next = brotherNode.next;
        }


        @Override
        public Map<K, V> rangeQuery(K start, K end) {
            HashMap<K, V> map = new HashMap<>();
            Integer startIndex = findCeilingKeyIndex(start);
            Integer endIndex = findCeilingKeyIndex(end);
            for (int i = startIndex; i < endIndex; i++) {
                map.put(keys.get(i), data.get(i));
            }
            BPlusTreeDataNode next = this.next;
            while (next != null) {
                for (int i = 0; i < next.keys.size(); i++) {
                    if (next.keys.get(i).compareTo(end) < 0) {
                        map.put(next.keys.get(i), next.data.get(i));
                    }else{
                        return map;
                    }
                }
                next = next.next;
            }
            return map;
        }


        private Integer findCeilingKeyIndex(K key) {
            int left = 0;
            int right = keys.size() - 1;
            while (left < right) {
                int mid = (left + right) / 2;
                if (keys.get(mid).compareTo(key) >= 0) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            return left;
        }


        private BPlusTreeNode spite() {
            int midIndex = getMidIndex();
            List<K> allkeys = this.keys;
            List<V> alldata = this.data;
            this.keys = allkeys.subList(0, midIndex);
            this.data = alldata.subList(0, midIndex);
            ArrayList<K> rightKeys = new ArrayList<>(allkeys.subList(midIndex, allkeys.size()));
            ArrayList<V> rightdata = new ArrayList<>(alldata.subList(midIndex, alldata.size()));

            BPlusTreeDataNode rightNode = new BPlusTreeDataNode(rightKeys, rightdata);
            if (this.next == null) {
                tail = rightNode;
            }
            rightNode.next = this.next;
            if (this.next != null) {
                this.next.pre = rightNode;
            }
            this.next = rightNode;
            rightNode.pre = this;
            return rightNode;
        }


        private int findEqualKeyIndex(K key) {
            int left = 0;
            int right = keys.size() - 1;
            while (left <= right) {
                int mid = (right + left) / 2;
                if (key.compareTo(keys.get(mid)) == 0) {
                    return mid;
                } else if (key.compareTo(keys.get(mid)) > 0) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            return -1;
        }
    }


    /***************************BPlusTreeIndexNode**********************************/
    class BPlusTreeIndexNode extends BPlusTreeNode {
        public List<BPlusTreeNode> children;


        public BPlusTreeIndexNode(List<K> keys, List<BPlusTreeNode> children) {
            this.keys = keys;
            this.children = children;
        }


        @Override
        public BPlusTreeNode put(K key, V value) {
            int upperIndex = findUpperIndex(key);
            BPlusTreeNode rightNode = children.get(upperIndex).put(key, value);
            if (rightNode == null) {
                return null;
            }
            // 先添加后继节点，并管理好儿子节点
            K newKey = rightNode.findLeafKey();
            int newKeyIndex = findUpperIndex(newKey);
            keys.add(newKeyIndex, newKey);
            children.add(newKeyIndex + 1, rightNode);
            // 在考虑分裂
            if (keys.size() > UPPER_BOUND) {
                BPlusTreeNode spite = spite();
                // 对于叶子节点，删除第一个节点
                spite.keys.remove(0);
                return spite;
            }
            return null;
        }


        private BPlusTreeNode spite() {
            int midIndex = getMidIndex();
            List<K> allkeys = this.keys;
            List<BPlusTreeNode> allchildren = this.children;
            this.keys = allkeys.subList(0, midIndex);
            this.children = allchildren.subList(0, midIndex + 1);
            ArrayList<K> rightKeys = new ArrayList<>(allkeys.subList(midIndex, allkeys.size()));
            ArrayList<BPlusTreeNode> rightchildren = new ArrayList<>(allchildren.subList(midIndex + 1, allchildren.size()));
            return new BPlusTreeIndexNode(rightKeys, rightchildren);
        }


        @Override
        protected K findLeafKey() {
            return children.get(0).findLeafKey();
        }


        @Override
        public V get(K key) {
            int upperIndex = findUpperIndex(key);
            return children.get(upperIndex).get(key);
        }


        @Override
        public K floorKey(K key) {
            int upperIndex = findUpperIndex(key);
            return children.get(upperIndex).floorKey(key);
        }


        @Override
        public K ceilingKey(K key) {
            int upperIndex = findUpperIndex(key);
            return children.get(upperIndex).ceilingKey(key);
        }


        @Override
        public DeleteInfo remove(K key) {
            int upperIndex = findUpperIndex(key);
            BPlusTreeNode childNode = children.get(upperIndex);
            DeleteInfo deleteInfo = childNode.remove(key);
            if (!deleteInfo.isDelete) {
                return deleteInfo;
            }

            if (deleteInfo.isUnder) {
                delete_maintain(childNode, upperIndex);
            } else {
                // 删除后没有发生合并和借节点的情况
                int parentKeyIndex = Math.max(upperIndex - 1, 0);

                if (childNode.getClass().equals(BPlusTreeDataNode.class) && upperIndex > 0) {
                    K leafKey = childNode.findLeafKey();

                    if (keys.get(parentKeyIndex).compareTo(leafKey) != 0) {
                        System.out.println("不相等，keys.get(parentKeyIndex) = " + keys.get(parentKeyIndex) + "leafKey = " + leafKey);
                        keys.set(parentKeyIndex, leafKey);
                    }
                } else if (childNode.getClass().equals(BPlusTreeIndexNode.class)) {
                    if (keys.get(parentKeyIndex).compareTo(key) == 0) {
                        K leafKey = childNode.findLeafKey();
                        keys.set(parentKeyIndex, leafKey);
                    }
                }
            }
            return new DeleteInfo(true, keys.size() < UNDER_BOUND);
        }


        // 父亲帮儿子调整（站在父亲的角度调整儿子，同时也调整自己，保证数据的正确性）
        private void delete_maintain(BPlusTreeNode childNode, int childIndex) {
            int parentKeyIndex = Math.max(childIndex - 1, 0);
            if (childIndex > 0 && children.get(childIndex - 1).keys.size() > UNDER_BOUND) {
                BPlusTreeNode leftBrother = children.get(childIndex - 1);
                // 将自己的关键字传下去
                K parentKey = keys.get(parentKeyIndex);
                childNode.borrow(leftBrother, true, parentKey);
                // 将该节点的值更新为后继节点的值
                K leafKey = childNode.findLeafKey();
                this.keys.set(parentKeyIndex, leafKey);
            } else if (childIndex < children.size() - 1 && children.get(childIndex + 1).keys.size() > UNDER_BOUND) {
                BPlusTreeNode rightBrother = children.get(childIndex + 1);

                parentKeyIndex = childIndex == 0 ? 0 : Math.min(keys.size() - 1, parentKeyIndex + 1);
                // 将自己的关键字传下去
                K parentKey = keys.get(parentKeyIndex);
                childNode.borrow(rightBrother, false, parentKey);
                // 将该节点的值更新为后继节点的值
                // 找的是要借的节点的后继节点
                K leafKey = rightBrother.findLeafKey();
                this.keys.set(parentKeyIndex, leafKey);
            } else {
                if (childIndex > 0) {
                    BPlusTreeNode leftBrother = children.get(childIndex - 1);
                    K parentKey = keys.get(parentKeyIndex);
                    leftBrother.combine(childNode, parentKey);
                    this.keys.remove(parentKeyIndex);
                    this.children.remove(childIndex);
                } else {
                    // 来到这里 childnode一定是第一个节点
                    BPlusTreeNode rightBrother = children.get(childIndex + 1);
                    childNode.combine(rightBrother, this.keys.get(0));
                    this.keys.remove(0);
                    // 删除右孩子
                    this.children.remove(1);
                }
            }
        }


        @Override
        public void borrow(BPlusTreeNode brother, boolean isLeft, K parentKey) {
            BPlusTreeIndexNode brotherNode = (BPlusTreeIndexNode) brother;
            if (isLeft) {
                // 向父节点借关键字
                this.keys.add(0, parentKey);
                this.children.add(0, brotherNode.children.remove(brotherNode.children.size() - 1));
                brother.keys.remove(brotherNode.keys.size() - 1);
            } else {
                this.keys.add(parentKey);
                this.children.add(brotherNode.children.remove(0));
                brother.keys.remove(0);
            }
        }

        @Override
        protected void combine(BPlusTreeNode childNode, K parentKey) {
            BPlusTreeIndexNode brotherNode = (BPlusTreeIndexNode) childNode;
            this.keys.add(parentKey);
            this.keys.addAll(brotherNode.keys);
            this.children.addAll(brotherNode.children);
        }

        @Override
        public Map<K, V> rangeQuery(K start, K end) {
            int upperIndex = findUpperIndex(start);
            return children.get(upperIndex).rangeQuery(start, end);
        }
    }


    class DeleteInfo {
        public boolean isDelete;
        public boolean isUnder;

        public DeleteInfo(boolean isDelete, boolean isUnder) {
            this.isDelete = isDelete;
            this.isUnder = isUnder;
        }
    }


    @Override
    public void put(K key, V value) {
        if (key == null) {
            return;
        }
        if (root == null) {
            root = new BPlusTreeDataNode(toList(key), toList(value));
            head = tail = root;
            size++;
            height = 1;
            return;
        }


        BPlusTreeNode newChildren = root.put(key, value);
        if (newChildren != null) {
            K leafKey = newChildren.findLeafKey();
            root = new BPlusTreeIndexNode(toList(leafKey), toList(root, newChildren));
            height++;
        }

    }


    public final <T> List<T> toList(T... t) {
        ArrayList<T> list = new ArrayList<>();
        Collections.addAll(list, t);
        return list;
    }


    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }
        return root.get(key);
    }


    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }
        return get(key) != null;
    }


    @Override
    public K ceilingKey(K key) {
        if (key == null || root == null) {
            return null;
        }
        return root.ceilingKey(key);

    }


    @Override
    public K firstKey() {
        return head.keys.get(0);
    }


    @Override
    public K lastKey() {
        return tail.keys.get(tail.keys.size() - 1);
    }


    @Override
    public K floorKey(K key) {
        if (key == null || root == null) {
            return null;
        }
        return root.floorKey(key);
    }


    @Override
    public int size() {
        return size;
    }


    public int height(){
        return height;
    }


    public Map<K, V> rangeQuery(K start, K end) {
        if (start == null || root == null || end == null) {
            return null;
        }
        return root.rangeQuery(start, end);
    }


    @Override
    public void remove(K key) {
        if (key == null || root == null) {
            return;
        }
        DeleteInfo deleteInfo = root.remove(key);
        if (!deleteInfo.isDelete) {
            return;
        }

        if (root.keys.isEmpty()) {
            if (root.getClass().equals(BPlusTreeDataNode.class)) {
                root = null;
            } else {
                root = ((BPlusTreeIndexNode) root).children.get(0);
            }
            height--;
        }
    }


    public String printAll() {
        return printAll(root);
    }


    private String printAll(BPlusTreeNode root) {
        LinkedList<BPlusTreeNode> queue = new LinkedList<>();
        queue.add(root);
        StringBuilder sb = new StringBuilder();
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            sb.append("\n");
            sb.append("第" + (++level) + "层");
            sb.append("\n");
            for (int i = 0; i < size; i++) {
                BPlusTreeNode cur = queue.poll();
                if (cur == null) {
                    sb.append("null");
                    continue;
                }
                sb.append(cur.keys).append(" ");
                if (cur.getClass().equals(BPlusTreeIndexNode.class) && ((BPlusTreeIndexNode) cur).children != null) {
                    queue.addAll(((BPlusTreeIndexNode) cur).children);
                }
            }

        }
        sb.append("总共节点的数量为" + size);
        return sb.toString();
    }


    public static void main(String[] args) {
        BPlusTree<Integer, Integer> bPlusTree = new BPlusTree<Integer, Integer>(5);
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int times = 100;
        int maxkey = 20;
        int maxvalue = 1000000;
        for (int i = 0; i < times; i++) {
            int key = (int) (Math.random() * maxkey);
            int val = (int) (Math.random() * maxvalue);
//            int key ,val;
//            key = val = i;
            if (Math.random() > 0.1) {
                bPlusTree.put(key, val);
                map.put(key, val);
//                System.out.println(bPlusTree.printAll());
            }


//            if (Math.random() > 0.3 && bPlusTree.size > 0) {
//                int floorKey = (int) (Math.random() * maxkey);
//                Map<Integer, Integer> map1 = bPlusTree.rangeQuery(floorKey, floorKey + 100);
//                System.out.println(map1);
//            }
        }
        for (int i = 0; i < times; i++) {
            if (Math.random() > 0.3 && bPlusTree.size > 0) {
                int floorKey = (int) (Math.random() * maxkey);
                System.out.println("删除" + floorKey);
                map.remove(floorKey);
                bPlusTree.remove(floorKey);
                System.out.println(bPlusTree.printAll());
            }
        }
//        System.out.println(bPlusTree.printAll());
//        bPlusTree.print();
        System.out.println(map.size());
        System.out.println(bPlusTree.size);
        System.out.println(new Date());
//        System.out.println(bPlusTree.printAll());
//        bPlusTree.checkHeight(bPlusTree.root);
    }
}
