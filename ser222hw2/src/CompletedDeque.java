package edu.ser222.m01_03;

import java.util.NoSuchElementException;

/**
 * This program provides an implementation of the Deque interface. Also provides a main that
 * demonstrates it.
 * 
 * @author Bella Sheridan, Acuna
 * @version visual studio latest version 
 */


public class CompletedDeque<Item> implements Deque<Item> {

    //TODO: implement all the methods
    
    //create the node class with next and previous so it can be edited at both ends 
    private class Node{
        Item data;
        Node next;
        Node prev;
    
    private Node(Item data){
        this.data = data;
        this.next = null;
        this.prev = null;
    } }

    private Node head; //front
    private Node tail; //back 
    private int size; //number of elements 

    //makes empty deque
    public CompletedDeque(){
        head = null; 
        tail = null;
        size = 0;
    }

    //adds element to front
    public void enqueueFront(Item element){
        Node n = new Node(element);

        //if theres nothing in the deque the first element is the head and the tail 
        if(head == null){
            head = n;
            tail = n;
        }
         else { //link new node to front of current head 
            n.next = head;
            head.prev = n;
            head = n;
            
        }
        size++; //increase size 
    }

    //adds element to back of list 
    public void enqueueBack(Item element){
        Node n = new Node(element);

        //if theres nothing in the deque the first element is the head and the tail 
        if(tail == null){ 
            head = n;
            tail = n; 
        } else { //link new node after tail
           tail.next = n;
           n.prev = tail;
           tail = n;
        }

       size++; //increase size 

    }

    public Item dequeueFront(){
        if(head == null){
            throw new NoSuchElementException("deque is empty");
        }

        Item number = head.data;
        if(size == 1){ //if the size is one removing an element will make the head and tail null
            head = null;
            tail = null;
        } else {
            head = head.next; //head is thing after head then delete head 
            head.prev = null;
        }
        size--; //decrease size 
        return number; //return old head 

    }

    public Item dequeueBack(){
        if(head == null){
            throw new NoSuchElementException("deque is empty");
        }
        Item number = tail.data;
        if(size == 1){
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return number;
    }

    public Item first(){ //return element at front of list 
        if(head == null){
            throw new NoSuchElementException("deque is empty");
        }
            return head.data;
        
    }

    public Item last(){ //return element at end of list 
        if(head == null){
            throw new NoSuchElementException("deque is empty");
        }
            return tail.data;
    
    }

    public boolean isEmpty(){ //check is deque is empty 
        if(size == 0){
            return true;
        }
        return false;
    }

    public int size(){ //return size 
        return size;
    }

    public String toString(){
        String text = ""; //create string
        if(size == 0){
            return "empty";
        }
        Node i = tail;
        while(i != null){ //tranverse through the nodes and add the data to string 
            text += i.data;
        if(i.prev != null){
            text += " "; //add spaces between data
        }
    
        i = i.prev; 
    }
    return text; //return the string 
    }
    /**
     * Program entry point for deque. 
     * @param args command line arguments
     */    
    public static void main(String[] args) {
        CompletedDeque<Integer> deque = new CompletedDeque<>();

        //standard queue behavior
        deque.enqueueBack(3);
        deque.enqueueBack(7);
        deque.enqueueBack(4);
        deque.dequeueFront();        
        deque.enqueueBack(9);
        deque.enqueueBack(8);
        deque.dequeueFront();
        System.out.println("size: " + deque.size());
        System.out.println("contents:\n" + deque.toString());   

        //deque features
        System.out.println(deque.dequeueFront());        
        deque.enqueueFront(1);
        deque.enqueueFront(11);                         
        deque.enqueueFront(3);                 
        deque.enqueueFront(5);         
        System.out.println(deque.dequeueBack());
        System.out.println(deque.dequeueBack());        
        System.out.println(deque.last());                
        deque.dequeueFront();
        deque.dequeueFront();        
        System.out.println(deque.first());        
        System.out.println("size: " + deque.size());
        System.out.println("contents:\n" + deque.toString());            
    }
} 
