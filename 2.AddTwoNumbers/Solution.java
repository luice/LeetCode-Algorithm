/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int num = (l1.val + l2.val) % 10;
        int jw = (l1.val + l2.val) / 10;
        ListNode result = new ListNode(num);
        ListNode currentNode = result;
        l1 = l1.next;
        l2 = l2.next;
        while (l1 != null && l2 != null) {
            int sum = l1.val + l2.val + jw;
            num = sum % 10;
            jw = sum / 10;
            currentNode.next = new ListNode(num);
            currentNode = currentNode.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        ListNode remain = null;
        if (l1 != null)
            remain = l1;
        else if (l2 != null)
            remain = l2;
        while (remain != null) {
            int sum = remain.val + jw;
            num = sum % 10;
            jw = sum / 10;
            currentNode.next = new ListNode(num);
            currentNode = currentNode.next;
            remain = remain.next;
        }
        if (jw != 0) {
            currentNode.next = new ListNode(jw);
        }
        return result;
    }
}