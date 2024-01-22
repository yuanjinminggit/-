package com.leetcode.codereview.linkedlist;

import com.leetcode.codereview.simpleConstruct.ListNode;

public class ShanChuChongFuYuanSu {
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        if (head.val != head.next.val) {
            head.next = deleteDuplicates(head.next);
        } else {
            while (head.next != null && head.val == head.next.val) {
                head.next = head.next.next;
            }
            return deleteDuplicates(head.next);
        }
        return head;
    }
}
