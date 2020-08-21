package org.example.codeTest2;

import lombok.extern.log4j.Log4j2;
import org.example.codeTest.Node;

@Log4j2
public class LinkSortCombine {

    // 链表分割成两部分(使用快慢指针的方式)
    private static Node cutLink(Node head) {
        if (head == null || head.next == null) {
            return null;
        }

        Node newHead = new Node(-1, head);
        Node fast, preSlow;
        for (preSlow = newHead, fast = head; fast != null && fast.next != null; preSlow = preSlow.next, fast = fast.next.next)
            ;
        Node slow = preSlow.next;
        preSlow.next = null;
        return slow;
    }

    public static Node sort(Node head) {

        // 1. 将链表分割成两部分进行合并
        Node right = cutLink(head);

        log.info("head = {}", head.sample());
        log.info("right = {}", right.sample());

        return null;
    }

    public static void main(String[] args) {
        Node head = Node.fakeData(1);

        System.out.println(head.sample());

        sort(head);
    }
}
