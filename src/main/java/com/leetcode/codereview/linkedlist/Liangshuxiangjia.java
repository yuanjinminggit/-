package com.leetcode.codereview.linkedlist;

import com.leetcode.codereview.simpleConstruct.ListNode;
import com.leetcode.codereview.simpleConstruct.TreeNode;


public class Liangshuxiangjia {


    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carryBit = 0;
        int bitResult = 0;
        ListNode head = new ListNode(-1);
        ListNode move = head;
        while (l1 != null || l2 != null || carryBit != 0) {
            int i = l1 == null ? 0 : l1.val;
            int j = l2 == null ? 0 : l2.val;
            bitResult = (i + j + carryBit) % 10;
            carryBit = (i + j + carryBit) / 10;
            ListNode listNode = new ListNode(bitResult);
            move.next = listNode;
            move = listNode;
            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
        }
        return head.next;
    }


    public ListNode mergeTwoLists1(ListNode list1, ListNode list2) {
        ListNode dummyNode = new ListNode(-1);
        ListNode move = dummyNode;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                move.next = list1;
                list1 = list1.next;
            } else {
                move.next = list2;
                list2 = list2.next;
            }
            move = move.next;
        }

        if (list1 != null) {
            move.next = list1;
        } else {
            move.next = list2;
        }
        return dummyNode.next;
    }


    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }

        if (list1.val <= list2.val) {
            list1.next = mergeTwoLists(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoLists(list1, list2.next);
            return list2;
        }

    }


    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }
        return mergeKLists(lists, 0, lists.length - 1);
    }


    private ListNode mergeKLists(ListNode[] lists, int l, int r) {
        if (l >= r) {
            return lists[l];
        }
        int mid = (l + r) / 2;
        ListNode left = mergeKLists(lists, l, mid);
        ListNode right = mergeKLists(lists, mid + 1, r);
        return mergeTwoLists(left, right);
    }

    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode next = head.next;
        ListNode node = next.next;
        next.next = head;
        head.next = swapPairs(node);
        return head;
    }


    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode move = head;
        for (int i = 0; i < k - 1; i++) {
            if (move == null) {
                return head;
            }
            move = move.next;
        }
        if (move == null) {
            return head;
        }
        ListNode newHead = move.next;
        move.next = null;
        ListNode node = reverse0(head);
        node.next = reverseKGroup(newHead, k);
        return node;
    }

    private ListNode reverse0(ListNode head) {
        if (head.next == null) {
            return head;
        }
        ListNode node = reverse0(head.next);
        head.next.next = head;
        head.next = null;
        return node;
    }

    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || k == 0) {
            return head;
        }
        int len = getLength0(head);
        if (len == 1 || k % len == 0) {
            return head;
        }
        ListNode fast = head;
        ListNode slow = head;
        for (int i = 0; i < k % len; i++) {
            fast = fast.next;
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        ListNode newHead = slow.next;
        slow.next = null;
        fast.next = head;
        return newHead;
    }


    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        if (head.val == head.next.val) {
            return deleteDuplicates(head.next);
        } else {
            head.next = deleteDuplicates(head.next);
            return head;
        }
    }


    public ListNode partition(ListNode head, int x) {
        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;
        ListNode dummy2 = new ListNode(-2);
        ListNode cur2 = dummy2;
        while (head != null) {
            if (head.val < x) {
                cur.next = head;
                cur = cur.next;
            } else {
                cur2.next = head;
                cur2 = cur2.next;
            }
            head = head.next;
        }
        cur.next = null;
        cur2.next = null;
        cur.next = dummy2.next;
        return dummy.next;
    }


    public ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return null;
        }
        if (head.val == val) {
            return removeElements(head.next, val);
        }
        head.next = removeElements(head.next, val);
        return head;
    }


    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(-1);
        ListNode leftPreNode = dummy;
        for (int i = 0; i < left - 1; i++) {
            leftPreNode = leftPreNode.next;
        }
        ListNode rightNode = leftPreNode;
        for (int i = 0; i < right - left + 1; i++) {
            rightNode = rightNode.next;
        }
        ListNode after = rightNode.next;
        rightNode.next = null;
        ListNode leftNode = leftPreNode.next;
        ListNode pre = null;
        ListNode cur = leftNode;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        leftPreNode.next = pre;
        leftNode.next = after;
        return dummy.next;
    }

    public TreeNode sortedListToBST(ListNode head) {
        int length = getLength0(head);
        if (length == 0) {
            return null;
        }
        return buildSortedListToBST(head);
    }

    private TreeNode buildSortedListToBST(ListNode head) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return new TreeNode(head.val);
        }
        ListNode midPre = getMidPre(head);
        ListNode mid = midPre.next;
        ListNode right = mid.next;
        mid.next = null;
        midPre.next = null;
        TreeNode root = new TreeNode(mid.val);
        root.left = buildSortedListToBST(head);
        root.right = buildSortedListToBST(right);
        return root;
    }

    private ListNode getMidPre(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode fast = head;
        ListNode slow = head;
        ListNode pre = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            pre = slow;
            slow = slow.next;
        }
        return pre;
    }


    private int getLength0(ListNode head) {
        int length = 0;
        while (head != null) {
            head = head.next;
            length++;
        }
        return length;
    }


    public ListNode insertionSortList1(ListNode head) {
        if (head.next == null) {
            return head;
        }
        ListNode cur = head.next;
        ListNode newHead = cur.next;
        cur.next = null;
        head.next = null;
        return insertionSortList(head, cur, newHead);
    }

    private ListNode insertionSortList(ListNode head, ListNode cur, ListNode newHead) {
        if (cur == null) {
            System.out.println(head.val);
            return head;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode move = dummy;
        while (move.next != null) {
            if (move.next.val < cur.val) {
                move = move.next;
            } else {
                break;
            }
        }
        cur.next = move.next;
        move.next = cur;

        if (newHead == null) {
            return null;
        }
        ListNode nhead = newHead.next;
        newHead.next = null;
        System.out.println(dummy.next.val + "==" + newHead.val);
        return insertionSortList(dummy.next, newHead, nhead);
    }

    public ListNode insertionSortList(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode lastSorted = head, cur = head.next;
        while (cur != null) {
            if (lastSorted.val < cur.val) {
                lastSorted = lastSorted.next;
            } else {
                ListNode prev = dummy;
                while (prev.next.val <= cur.val) {
                    prev = prev.next;
                }
                lastSorted.next = cur.next;
                cur.next = prev.next;
                prev.next = cur;
            }
            cur = lastSorted.next;
        }
        return dummy.next;
    }


}
