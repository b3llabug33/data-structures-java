package edu.ser222.m01_03;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * CompletedList represents an implementation of a list.
 *
 * @author Bella Sheridan, Acuna
 * @version visual studio code 
 */
public class CompletedList<T> implements ListADT<T>, Iterable<T> {

    
    protected int count; //amount in list 
    protected int modChange; //tracks changes to list
    protected DoubleLinearNode<T> head;
    protected DoubleLinearNode<T> tail;

    //TODO: implement this!
    protected static class DoubleLinearNode<T> {
        T element; //node
        DoubleLinearNode<T> last; //last node
        DoubleLinearNode<T> next; //next node

        DoubleLinearNode(T element){ //node constructor 
            this.element = element;
        }
    }

    public CompletedList(){ //empty list constructor 
        head = null;
        tail = null;
        count = 0;
        modChange = 0;
    }

    public T removeFirst(){
        if(count == 0){ //same strategy but with the last element 
            throw new NoSuchElementException("no");
        }

        T h = head.element; //store old head 
        head = head.next; //head is now the next element 
        if(head != null){ //if head isnt null then delete the last element 
            head.last = null;
        } else{ //if head is null then the tail is also null
            tail = null;
        }

        count--; //decrease count
        modChange++; //increase changes 
        return h; //return old head 

    }

    public T removeLast(){
        if(count == 0){ //same strategy but with the last element 
            throw new NoSuchElementException("no");
        }

        T t = tail.element;
        tail = tail.last;
        if(tail != null){
            tail.next = null;
        } else{
            head = null;
        }

        count--;
        modChange++;
        return t;
    }

    public T remove(T targetElement){
         if(count == 0){ //same strategy but with the last element 
            throw new NoSuchElementException("no");
        }

        DoubleLinearNode<T> current = head;
        while(current.next != null && !targetElement.equals(current.element)){ //if the next element isnt null and the element isnt the one being removed go to next 
            current = current.next;
        }

        if(current == null){ //if current is null
            throw new NoSuchElementException("null"); 
        }

        if(current == head){ //if current is head call remove first 
            return removeFirst();
        }

        if(current == tail){ //call remove last 
            return removeLast();
        }

        current.last.next = current.next; //if its in the middle fix the positioning of list 
        current.next.last = current.last;

        count--; //one less element 
        modChange++; //one more change 

        return current.element; 


    }

    public T first(){
        if(isEmpty()){
            throw new NoSuchElementException("list is empty");
        }
        return head.element; //return head 
    }

    public T last(){
        if(isEmpty()){
            throw new NoSuchElementException("list is empty");
        }
        return tail.element; //return tail 
    }

    public boolean contains(T target){
        DoubleLinearNode<T> current = head; //point to head

        while(current != null){ //cycle through list while not null 
            if(target.equals(current.element)){ //if target equals element true 
                return true;
            } else {
                current = current.next; //point to next
            }
        }
        return false; //false is got through the whole list
    }

    public boolean isEmpty(){ //check if empty 
        if(count == 0){
            return true;
        } else {
            return false;
        }
    }

    public int size(){
        return count;
    }

    public Iterator<T> iterator(){ //iterator 
        return new ListIterator(); }

    private class ListIterator implements Iterator<T>{
        private final int modChanged = modChange;
        private DoubleLinearNode<T> iter = head;

        public boolean hasNext(){
            return iter != null;
        }

        public T next(){
            if(!hasNext()){
                throw new NoSuchElementException(); //check if next exists 
            }

            if(modChange != modChanged){ //something is wrong 
                throw new ConcurrentModificationException();
            }

            T element = iter.element;
            iter = iter.next;

            return element;
        }
    
    }
    

    public String toString(){
       

        if (isEmpty()) {
        return "empty";
    }

     String string = ""; //create a string with bracket at start 

        DoubleLinearNode<T> current = head; 

        while(current != null){
            string = string + current.element; //add elements to string 

            if(current.next != null){ //add space if there is still elements after
                string = string + " ";
            }
            current = current.next; //advance
        }

        //string = string + "]"; //add end bracket when done 
        return string;
    }
    
    
}