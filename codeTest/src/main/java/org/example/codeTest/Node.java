package org.example.codeTest;

import java.util.Random;

public class Node implements Comparable<Node> {
    public int val;
    public Node next;

    public Node(int val) {
        this.val = val;
    }

    public Node(int val, Node next) {
        this.val = val;
        this.next = next;
    }


    // 随机生成节点
    public static Node fakeData(int n) {
        Node head = new Node(-1);
        Node cur = head;

        Random random = new Random();
        for (int idx = 0; idx < n; idx++) {
            cur.next = new Node(random.nextInt(100));
            cur = cur.next;
        }
        return head.next;
    }

    public String sample() {
        StringBuilder builder = new StringBuilder();
        for (Node pos = this; pos != null; pos = pos.next) {
            builder.append(pos.val).append("-->");
        }
        return builder.toString();
    }

    @Override
    public int compareTo(Node o) {
        return this.val - o.val;
    }

    @Override
    public String toString() {
        return "Node{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }
}
