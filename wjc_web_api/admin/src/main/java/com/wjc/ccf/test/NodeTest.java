package com.wjc.ccf.test;

import java.util.*;

/**
 * @author wjc
 * @description
 * @date 2019/10/28
 */
public class NodeTest {
    public static class ListNode{
        int val;
        ListNode next;
        ListNode(int v){val = v;}
    }

    public static void main(String[] args) {

//        ListNode listNode = new ListNode(3);
//        listNode.next = new ListNode(4);
//        listNode.next.next = new ListNode(2);
//        ListNode listNode2 = new ListNode(4);
//        listNode2.next = new ListNode(6);
//        listNode2.next.next = new ListNode(1);
//        ListNode result = getSum2(listNode, listNode2);

//        ListNode listNode = new ListNode(2);
//        ListNode listNode2 = new ListNode(1);
//        ListNode result = mergeTwoLists(listNode, listNode2);

//        ListNode listNode = new ListNode(3);
//        ListNode listNode2 = new ListNode(2);
//        ListNode listNode3 = new ListNode(0);
//        ListNode listNode4 = new ListNode(-4);
//
//        listNode.next = listNode2;
//        listNode2.next = listNode3;
//        listNode3.next = listNode4;
//        boolean b = hasCycle(listNode);
//        System.out.println(b);

//        ListNode listNode = new ListNode(1);
//        listNode.next = new ListNode(1);
//        listNode.next.next = new ListNode(2);
//        listNode.next.next.next = new ListNode(3);
//        listNode.next.next.next.next = new ListNode(3);
//        ListNode result = deleteDuplicates(listNode);

//        ListNode listNode = new ListNode(1);
//        listNode.next = new ListNode(1);
//        listNode.next.next = new ListNode(1);
//        listNode.next.next.next = new ListNode(2);
//        listNode.next.next.next.next = new ListNode(3);
//        ListNode result = deleteDuplicates2(listNode);

//        ListNode listNode = new ListNode(1);
//        listNode.next = new ListNode(2);
//        listNode.next.next = new ListNode(3);
//        listNode.next.next.next = new ListNode(4);
//        listNode.next.next.next.next = new ListNode(5);
//        ListNode result = reverseKGroup(listNode, 2);

//        ListNode listNode = new ListNode(2);
//        listNode.next = new ListNode(4);
//        listNode.next.next = new ListNode(3);
//        ListNode listNode2 = new ListNode(5);
//        listNode2.next = new ListNode(6);
//        listNode2.next.next = new ListNode(4);
//        ListNode result = addTwoNumbers(listNode, listNode2);

        TreeNode node1 = new TreeNode(12);
        TreeNode node2 = new TreeNode(2);
        node1.left = null;
        TreeNode node3 = new TreeNode(-60);
        node1.right = node3;
        TreeNode node11 = new TreeNode(12);
        TreeNode node12 = new TreeNode(2);
        node11.left = null;
        TreeNode node13 = new TreeNode(72);
        node11.right = node13;
        boolean b = isSameTree(node1, node11);
        System.out.println(b);
//        while (result != null){
//            System.out.println(result.val);
//            result = result.next;
//        }
    }

    public static List<Integer> aa(List list, TreeNode node){
        if(node.left != null){
            TreeNode left = node.left;
            list.add(left.val);
        }
        if(node.right != null){
            list.add(node.right.val);
        }

        return list;
    }
    //二叉树层次遍历
    public static List<List<Integer>> levelOrder(TreeNode root) {

        if (root == null) {
            return null;
        }

        List<List<Integer>> list = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        list1.add(root.val);

        if(root.left != null){
            TreeNode left = root.left;
            list1.add(left.val);
        }
        if(root.right != null){
            list1.add(root.right.val);
        }

        list.add(list1);
        return list;
    }

    //判断相同树
    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null && q == null ){
            return true;
        }
        if(p != null && q != null && p.val == q.val){
            if(isSameTree(p.left, q.left) && isSameTree(p.right, q.right)){
                return true;
            }
        }else{
            return false;
        }
        return false;
    }

    public static ListNode reverseKGroup(ListNode head, int k) {

        ListNode head2 = head;
        int length = 0;
        while (head2.next != null){
            length++;
            head2 = head2.next;
        }
        if(length<k){
            return head;
        }

        ListNode temp = new ListNode(0);
        ListNode ln2 = temp;
        ListNode ln3 = ln2;
        ListNode lnk = new ListNode(0);

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < k; j++,i++) {
                temp.next = new ListNode(head.val);
                head = head.next;
                temp = temp.next;
            }
            ln3 = reverse(ln2);
            head = head.next;
        }
        return lnk;
    }

    public static ListNode reverse(ListNode listNode){
        ListNode prev = null;
        ListNode curr = listNode;
        while (curr != null){
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    public static ListNode deleteDuplicates2(ListNode head) {
        if(head == null){
            return null;
        }

        ListNode listNode = new ListNode(head.val);
        ListNode ln = listNode;
        while (head != null){
            if(head.next == null){
                listNode.next = new ListNode(head.val);
                break;
            }
            if(head.val != head.next.val){
                listNode.next = new ListNode(head.val);
                listNode = listNode.next;
                head = head.next;
            }else if(head.val == head.next.val && (head.next.next != null && head.next.next.val != head.val)){
                head = head.next.next;
                continue;
            }else if(head.val == head.next.val && head.next.next != null && head.next.next.val == head.val){
                head = head.next;
                continue;
            }else if(head.val == head.next.val && head.next.next == null){
                head = null;
                continue;
            }
        }

        return ln.next;
    }

    //删除链表中重复元素
    public static ListNode deleteDuplicates(ListNode head) {
        ListNode ln = head;
        while (head != null && head.next != null){
            if(head.val == head.next.val){
                head.next = head.next.next;
            }else {
                head = head.next;
            }
        }
        return ln;
    }

    //环形链表
    public static boolean hasCycle(ListNode head) {

        Set<ListNode> nodes = new HashSet<ListNode>();
        while (head != null){
            if(head.next == null){
                return false;
            }

            if(nodes.contains(head)){
                return true;
            }

            nodes.add(head);
            head = head.next;
        }

        return false;
//        if (head == null || head.next == null) {
//            return false;
//        }
//        ListNode slow = head;
//        ListNode fast = head.next;
//        while (slow != fast) {
//            if (fast == null || fast.next == null) {
//                return false;
//            }
//            slow = slow.next;
//            fast = fast.next.next;
//        }
//        return true;
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1 == null){
            return l2;
        }

        if(l2 == null){
            return l1;
        }

        if(l1.val < l2.val){
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        }else{
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode l3 = new ListNode(0);
        ListNode prev = l3;

        long a = 0;
        long b = 0;
        int count = 0;

        while (l1 != null || l2 != null){
            if(l1 != null){
                a += Math.pow(10, count)*l1.val;
                l1 = l1.next;
            }
            if(l2 != null){
                b += Math.pow(10, count)*l2.val;
                l2 = l2.next;
            }
            count++;
        }

        long c = a+b;
        while (c>9){
            prev.next = new ListNode((int)c%10);
            prev = prev.next;
            c /= 10;
        }
        prev.next = new ListNode((int)c);
        return l3.next;
    }

    public static ListNode getSum(ListNode l1, ListNode l2){
        ListNode l3 = new ListNode(0);
        ListNode prev = l3;

        while (l1 != null || l2 != null){
            int val = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val);
            int a = 0;
            if(val > 9){
                if(prev.next != null){
                    prev.next.val += 1;
                }else{
                    a = 1;
                }
            }
            if(l1 != null){
                l1 = l1.next == null ? null : l1.next;
            }
            if(l2 != null){
                l2 = l2.next == null ? null : l2.next;
            }
            ListNode ld = new ListNode(val > 9 ? val-10 : val);
            if(a == 1){
                ld.next = new ListNode(a);
            }
            if(prev.next != null){
                ld.next = prev.next;
            }
            prev.next = ld;
        }

        return l3.next;
    }

    public static ListNode getSum2(ListNode l1, ListNode l2){
        ListNode l3 = new ListNode(0); ListNode prev = l3;
        while (l1 != null && l2 != null){
            int val = l1.val + l2.val;
            if(val > 9){
                prev.val += 1;
            }
            l1 = l1.next;
            l2 = l2.next;
            ListNode ld = new ListNode(val > 9 ? val-10 : val);
            prev.next = ld;
            prev = prev.next;
        }

        return l3.next;
    }
}
