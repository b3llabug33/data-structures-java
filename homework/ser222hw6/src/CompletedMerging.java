package edu.ser222.m02_02;


/**
 * Implements various divide and conquer algorithms.
 *
 * Last updated 4/2/2022.
 *
 * Completion time: 2 and a half hours ish 
 *
 * @author Sheridan, Acuna, Sedgewick and Wayne
 * @verison 02
 */
import java.util.Random;

public class CompletedMerging implements MergingAlgorithms {

    @Override
    public <T extends Comparable> Queue<T> mergeQueues(Queue<T> q1, Queue<T> q2) {

        Queue<T> q3 = new ListQueue<>(); //create queue for result 

        while(!q1.isEmpty() && !q2.isEmpty()){ //while both queues still have more 
            T a = q1.peek(); //first item in each queue
            T b = q2.peek();

            if(a.compareTo(b) <= 0){
                q3.enqueue(q1.dequeue()); //if q1 is smaller move to q3 
            } else {
               q3.enqueue(q2.dequeue()); //else move q2 to q3
            }
        }

        while(!q1.isEmpty()){
            q3.enqueue(q1.dequeue()); //if q1 has leftover items add to q3
        }

        while(!q2.isEmpty()){
            q3.enqueue(q2.dequeue()); //if q2 has leftover items add to q3
        }
        
        return q3; //return merged result
    }

    @Override
    public void sort(Comparable[] a) {
        
        Comparable[] sorted = mergesort(a); //call mergesort 

        for(int i = 0; i < a.length; i++){ //copy sorted values back into original array 
            a[i] = sorted[i];
        }

    }

    @Override
    public Comparable[] mergesort(Comparable[] a) {
        
        if(a.length <= 1){ //already sorted if less than or equal to 1
            return a;
        }

        int middle = a.length / 2; //middle index

        Comparable[] left = new Comparable[middle]; //left array
        Comparable[] right = new Comparable[a.length - middle]; //right array 

        for(int i = 0; i < middle; i++){ //left half gets copied into left
            left[i] = a[i];
        }

        for(int i = middle; i < a.length; i++) { //right half into right
            right[i - middle] = a[i]; 
        }

        left = mergesort(left); //recursivly sort halfs
        right = mergesort(right);

        return merge(left, right); //merge sorted halfs

    }

    @Override
    public Comparable[] merge(Comparable[] a, Comparable[] b) {
        
        Comparable[] c = new Comparable[a.length + b.length]; //create array for result

        int i = 0; //a pointer
        int j = 0; //b pointer
        int k = 0; //c pointer

        while(i < a.length && j < b.length){ //compare elements until one array finishes

            if(a[i].compareTo(b[j]) <= 0){
                c[k] = a[i];
                i++;
            } else {
                c[k] = b[j];
                j++;
            }
            k++;
        }

        while(i < a.length){ //copy extra from a
            c[k] = a[i];
            i++;
            k++;
        }

        while(j < b.length){ //copy extra from b
            c[k] = b[j];
            j++;
            k++;
        }

        return c;

    }

    @Override
    public void shuffle(Object[] a) {
        
        Object[] shuffled = shuffleRecursive(a);

        for(int i = 0; i < a.length; i++){ //copy shuffled result back in og array
            a[i] = shuffled[i];
    } }
     
    //splits array in 2 halfs, then shuffles halfs recursivly then merges randomly
    private Object[] shuffleRecursive(Object[] a){
        if(a.length <= 1){
            return a; //too short to be shuffled
        }

        int middle = a.length / 2; //middle

        Object[] left = new Object[middle]; //array for left half
        Object[] right = new Object[a.length - middle]; //array for right half

        for(int i = 0; i < middle; i++){ //copy left into left
            left[i] = a[i]; 
        }

        for(int i = middle; i < a.length; i++) { 
            right[i - middle] = a[i];  
          }

        left = shuffleRecursive(left); //recursivly shuffle left half
        right = shuffleRecursive(right); //right half

        return mergeShuffle(left, right); //merge together 
        }

        private Object[] mergeShuffle(Object[] a, Object[] b){
            Random random = new Random(); //create random object

            Object[] c = new Object[a.length + b.length]; //will have all items from both arrays

            int i = 0; //a pointer
            int j = 0; //b pointer
            int k = 0; //c pointer

            while(i < a.length && j < b.length){ //keep mergind while both arrays still have elements 
                if(random.nextBoolean()){ //choose randomly from arrays 
                    c[k] = a[i]; //take from a
                    i++; //move pointer
                } else {
                    c[k] = b[j]; //take from b
                    j++;
                }

                k++; //result array always moves
            }

            while(i < a.length){ //if arrays have elements leftover copy them
                c[k] = a[i]; //copy
                i++; //move pointer
                k++; //move result pointer
            }

            while(j < b.length){ //if arrays have elements leftover copy them
                c[k] = b[j]; //copy
                j++; //move pointer
                k++; //move result pointer
            }

            return c;
        }
    /**
     * entry point for sample output.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Queue<String> q1 = new ListQueue<>(); q1.enqueue("E"); q1.enqueue("L"); q1.enqueue("O"); q1.enqueue("R"); q1.enqueue("T");
        Queue<String> q2 = new ListQueue<>(); q2.enqueue("A"); q2.enqueue("E"); q2.enqueue("M"); q2.enqueue("P"); q2.enqueue("S"); q2.enqueue("X");
        Queue<Integer> q3 = new ListQueue<>(); q3.enqueue(5); q3.enqueue(12); q3.enqueue(15); q3.enqueue(17); q3.enqueue(20);
        Queue<Integer> q4 = new ListQueue<>(); q4.enqueue(1); q4.enqueue(4); q4.enqueue(12); q4.enqueue(13); q4.enqueue(16); q4.enqueue(18);

        MergingAlgorithms ma = new CompletedMerging();

        //Q1 - sample test cases
        Queue merged1 = ma.mergeQueues(q1, q2);
        System.out.println(merged1.toString());
        Queue merged2 = ma.mergeQueues(q3, q4);
        System.out.println(merged2.toString());
        
        //Q2 - sample test cases
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        ma.sort(a);
        assert isSorted(a);
        show(a);
        
        //Q3 - sample test cases
        String[] b = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        ma.shuffle(b);
        show(b);
        
        ma.shuffle(b);
        show(b);
    }

    //below are utilities functions, please do not change them.
        
    //sorting helper from text
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    //sorting helper from text
    private static void show(Comparable[] a) {
        for (Comparable a1 : a)
            System.out.print(a1 + " ");

        System.out.println();
    }
    
    //sorting helper from text
    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1]))
                return false;
        
        return true;
    }
}