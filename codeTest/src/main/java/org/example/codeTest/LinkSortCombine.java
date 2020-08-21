package org.example.codeTest;

import lombok.extern.log4j.Log4j2;

/**
 * 链表排序归并
 */
@Log4j2
public class LinkSortCombine {

    public static Node sortList(Node root) {
        if (root == null || root.next == null) {
            return root;
        }

        Node mid = root, midPos = null;
        for (Node pos = root; pos != null && pos.next != null; ) {
            midPos = mid;
            mid = mid.next;
            pos = pos.next.next;
        }
        if (midPos != null) {
            midPos.next = null;
        }
        return twoWayMerge(sortList(root), sortList(mid));
    }

    public static Node twoWayMerge(Node n1, Node n2) {
        Node head = new Node(-1);
        Node pos = head;

        while (n1 != null && n2 != null) {
            if (n1.compareTo(n2) < 0) {
                pos.next = n1;
                n1 = n1.next;
            } else {
                pos.next = n2;
                n2 = n2.next;
            }
            pos = pos.next;
        }
        pos.next = n1 == null ? n2 : n1;
        return head.next;
    }
}
