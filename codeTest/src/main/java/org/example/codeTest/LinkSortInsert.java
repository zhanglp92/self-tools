package org.example.codeTest;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

/**
 * 链表排序 -- 插入
 */
@Log4j2
public class LinkSortInsert {

    public static Node sort(Node head) {
        Node newHead = head;
        for (Node pos = head; pos != null; pos = pos.next) {
            newHead = move(head, pos);
        }
        return newHead;
    }

    /**
     * 插入, 1. 断开, 2. 插入
     */
    private static Node move(@NonNull Node head, Node insertNodePre) {
        if (insertNodePre == null || insertNodePre.next == null) {
            return head;
        }

        // 链表分成3部分
        Node aloneNode = insertNodePre.next;
        Node head2 = aloneNode.next;

        insertNodePre.next = null;
        aloneNode.next = null;

        head = insert(head, aloneNode);
        if (head2 == null) {
            return head;
        }

        // 链接head2
        Node pos;
        for (pos = head; pos.next != null; pos = pos.next) {
        }
        pos.next = head2;

        return head;
    }

    /**
     * 按照顺序插入
     */
    private static Node insert(Node head, Node node) {
        if (head == null) {
            return node;
        }
        if (node == null) {
            return head;
        }

        Node newHead = new Node(-1, head);
        for (Node pos = newHead; pos != null; pos = pos.next) {
            if (pos.next == null) {
                pos.next = node;
                break;
            } else if (pos.next.compareTo(node) > 0) {
                node.next = pos.next;
                pos.next = node;
                break;
            }
        }

        log.info("new head -- {}", newHead.next.sample());

        return newHead.next;
    }

    public static void main(String[] args) {
        Node head = Node.fakeData(10);
        log.info("source = {}", head.sample());

        head = sort(head);
        log.info("result = {}", head.sample());
    }
}
