package com.baymax.testapplication;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        Node node = new Node("1");
        node.next=new Node("2");
        node.next.next=new Node("3");
        node.next.next.next=new Node("4");
        node.next.next.next.next=new Node("5");
        node.next.next.next.next.next=new Node("6");
        println(node);

        System.out.println("--------------------------");

        println(flipLinkedList(null,node));
    }

    private Node flipLinkedList(Node node) {
        //单链表为空或只有一个节点，直接返回原单链表
        if (node == null || node.next == null){
            return node;
        }
//        下一个node
        Node nextNode=null ;
//        当前node
        Node curNode=node;
//        上一个node
        Node preNode=null;

        while (curNode!=null){
            //取出下一个
            nextNode=curNode.next;
            //把当前node的下一个node 指向上一个node
            curNode.next=preNode;
            //移动指针使得prenode为当前curnode
            preNode=curNode;
            //移动指针使得当前curnode为nextNode
            curNode=nextNode;
        }
        return preNode;
    }

    private Node flipLinkedList(Node preNode ,Node curNode) {
//        当前curNode为空 说明preNode已经为最后一个Node 直接返回
        if (curNode==null){
            return preNode;
        }
//        拿到下一个node
        Node nextNode=curNode.next ;
//        当前curNode.next指向前一个Node
        curNode.next=preNode;
//        移动 preNode 到 curNode
        preNode=curNode;
//        移动 curNode 到 nextNode
        curNode=nextNode;
//        递归调用自己
        return flipLinkedList(preNode,curNode);
    }
    private void println(Node node) {
        if (node==null)return;
        System.out.println(node.data);
        println(node.next);
    }

}