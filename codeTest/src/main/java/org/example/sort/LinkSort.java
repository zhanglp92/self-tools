package org.example.sort;

import lombok.extern.log4j.Log4j2;
import org.example.codeTest.Node;

import java.util.PriorityQueue;

@Log4j2
public class LinkSort {

    private static Node cutList(Node head) {
        if (head == null || head.next == null) {
            return null;
        }

        // 使用快慢指针的方式进行截断(找前驱节点)
        Node fast = head.next;
        Node slow = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        Node res = slow.next;
        slow.next = null;
        return res;
    }

    private static Node combine(Node head1, Node head2) {
        if (head1 == null) {
            return head2;
        } else if (head2 == null) {
            return head1;
        }
        if (head1.compareTo(head2) < 0) {
            head1.next = combine(head1.next, head2);
            return head1;
        } else {
            head2.next = combine(head2.next, head1);
            return head2;
        }
    }

    /**
     * 迭代
     */
//    private static Node combine(Node head1, Node head2) {
//        Node newHead = new Node(-1, null);
//        Node p1 = head1, p2 = head2, p = newHead;
//        while (p1 != null && p2 != null) {
//            if (p1.val < p2.val) {
//                p = p.next = p1;
//                p1 = p1.next;
//            } else {
//                p = p.next = p2;
//                p2 = p2.next;
//            }
//        }
//        p.next = p1 != null ? p1 : p2;
//        return newHead.next;
//    }

    /**
     * 单链表归并排序
     */
    public static Node sort(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node right = cutList(head);
        return combine(sort(head), sort(right));
    }

    private static void addQueue(PriorityQueue<Node> queue, Node node) {
        if (node == null) {
            return;
        }
        queue.add(node);
    }

    /**
     * 使用优先级队列合并
     */
    public static Node combine(Node[] arr) {
        PriorityQueue<Node> queue = new PriorityQueue<>(arr.length);

        // 1. 首元素添加到Queue
        for (Node node : arr) {
            addQueue(queue, node);
        }

        // 2. 遍历
        Node newHead = new Node(-1, null);
        Node p = newHead;
        while (!queue.isEmpty()) {
            log.info("queue size1 = {}", queue.size());
            Node node = queue.poll();
            addQueue(queue, node.next);
            node.next = null;
            p = p.next = node;
            log.info("queue size2 = {}, node = {}, p = {}", queue.size(), node.hashCode(), p.hashCode());
        }
        return newHead.next;
    }

    /**
     * 多链表两两合并
     */
    public static Node combine(Node[] arr, int begin, int end) {
        if (end < begin || begin >= arr.length) {
            return null;
        } else if (begin == end) {
            return arr[begin];
        }

        int mid = begin + (end - begin) / 2;
        return combine(combine(arr, begin, mid), combine(arr, mid + 1, end));
    }

    public static void main(String[] args) {
        Node[] arr = new Node[]{
                sort(Node.fakeData(10)),
                sort(Node.fakeData(10)),
                sort(Node.fakeData(10))
        };
        log.info("sort1 = {}", combine(arr, 0, arr.length).sample());
        log.info("sort2 = {}", combine(arr).sample());
    }
}
