package edu.ser222.m01_03;

/**
 * CompletedOrderedList represents an implementation of an ordered list that builds on
 * CompletedList.
 *
 * @author Bella Sheridan, Acuna
 * @version Visual Studio Code
 */
public class CompletedOrderedList<T extends Comparable<T>> extends CompletedList<T>
         implements OrderedListADT<T> {

    //TODO: implement this!
    public CompletedOrderedList(){ //default constructor 
        super();
    }

    public void add(T element){


        @SuppressWarnings("unchecked")
        Comparable<T> comp = (Comparable<T>) element;

        //create new node 
        DoubleLinearNode<T> newNode = new DoubleLinearNode<>(element);
        
        //if list in empty new node becomes head and tail 
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
            count++;
            modChange++;
            return;
        }

        //insert at very front
        if (comp.compareTo(head.element) <= 0) {
            newNode.next = head;
            head.last = newNode;
            head = newNode;
            count++;
            modChange++;
            return;
        }

        //if in middle search until you find the correct spot 
        DoubleLinearNode<T> current = head.next;

        while(current != null && comp.compareTo(current.element) > 0){
            current = current.next; //move forward
        }

        if(current == null){ //if current is null 
            tail.next = newNode; //insert at tail
            newNode.last = tail;
            tail = newNode;
            count++;
            modChange++;
            return;
        }

        newNode.next = current;
        newNode.last = current.last;
        current.last.next = newNode;
        current.last = newNode;
        count++;
        modChange++;
    }
}
