package edu.ser222.m03_02;

/**
 * A binary search tree based implementation of a symbol table.
 * 
 * Completion time: about 3 hours 
 *
 * @author bella sheridan, Sedgewick, Acuna
 * @version 4/7/26
 */
import java.util.Collections;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class CompletedBST<Key extends Comparable<Key>, Value> implements BST<Key, Value> {
    private Node<Key, Value> root;

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null)
            return 0;
        else
            return x.N;
    }

    @Override
    public Value get(Key key) {
        Node<Key, Value> iter = root;

        while(iter != null) {
            int cmp = key.compareTo(iter.key);

            if (cmp < 0)
                iter = iter.left;
            else if (cmp > 0)
                iter = iter.right;
            else
                return iter.val;
        }

        return null;
    }

    private Value get(Node<Key, Value> x, Key key) {
        // Return value associated with key in the subtree rooted at x;
        // return null if key not present in subtree rooted at x.
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    @Override
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node<Key, Value> x, Key key, Value val) {
        if (x == null)
            return new Node(key, val, 1);

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else
            x.val = val;
        x.N = size(x.left) + size(x.right) + 1;

        return x;
    }

    @Override
    public Key min() {
        if(root == null)
            throw new NoSuchElementException();
        return min(root).key;
    }

    private Node<Key, Value> min(Node x) {
        if (x.left == null)
            return x;
        return min(x.left);
    }

    @Override
    public Key max() {
        if(root == null)
            throw new NoSuchElementException();
        return max(root).key;
    }

    private Node<Key, Value> max(Node x) {
    if (x.right == null) return x;
        return max(x.right);
    }

    @Override
    public Key floor(Key key) {
        if(root == null)
            throw new NoSuchElementException();

        Node<Key, Value> x = floor(root, key);
        if (x == null)
            return null;
        return x.key;
    }

    private Node<Key, Value> floor(Node<Key, Value> x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        Node<Key, Value> t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    @Override
    public Key select(int k) {
        return select(root, k).key;
    }

    private Node<Key, Value> select(Node x, int k) {
        if (x == null) return null;
        int t = size(x.left);
        if (t > k) return select(x.left, k);
        else if (t < k) return select(x.right, k-t-1);
        else return x;
    }

    @Override
    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node<Key, Value> x) {
        // Return number of keys less than x.key in the subtree rooted at x.
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);
    }

    @Override
    public void deleteMin() {
        if(root == null)
            throw new NoSuchElementException();
        root = deleteMin(root);
    }

    private Node<Key, Value> deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node<Key, Value> delete(Node<Key, Value> x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else
        {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public Iterable<Key> keys() {
        if (root == null)
            return new LinkedList<>();
        else
            return keys(min(), max());
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi)
    {
        Queue<Key> queue = new LinkedList<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node<Key, Value> x, Queue<Key> queue, Key lo, Key hi)
    {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.add(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    public Key ceiling(Key key) {
        //SKIP, UNNEEDED
        return null;
    }
    public Node getRoot() {
        return root;
    }

    public boolean contains(Key key) { //true if key is in the tree
        return get(key) != null; //the key exists if it returns something
    }

    public boolean isEmpty() { //true if tree is empty
        return size() == 0;
    }

    public void deleteMax()  { //deleted largest
        if (isEmpty()) {
        throw new NoSuchElementException("BST underflow");
    }
    root = deleteMax(root);
}
// helper for deleteMax
private Node<Key, Value> deleteMax(Node<Key, Value> x) {
    // if there is no right child, this is the max node
    // so replace it with left
    if (x.right == null) {
        return x.left;
    }

    x.right = deleteMax(x.right);

    // update size after deleted
    x.N = 1 + size(x.left) + size(x.right);
    return x;
    }

    public int size(Key lo, Key hi) { //returns how many keys are between high and low 
         if (lo.compareTo(hi) > 0) {
        return 0;
       }

    if (contains(hi)) { // if high is actually in the tree include it
        return rank(hi) - rank(lo) + 1;
    }

    return rank(hi) - rank(lo);  //otherwise just count everything before high
    }

    public void putFast(Key key, Value val) {
    if (root == null) { // if tree is empty new node becomes root
        root = new Node<Key, Value>(key, val, 1);
        return;
    }

    Node<Key, Value> current = root;
    LinkedList<Node<Key, Value>> path = new LinkedList<Node<Key, Value>>();

    while (true) {
        path.add(current); // save path to fix N later

        int cmp = key.compareTo(current.key);

        if (cmp < 0) {
            if (current.left == null) {
                current.left = new Node<Key, Value>(key, val, 1);
                break;
            }
            current = current.left;
        }
        else if (cmp > 0) {
            if (current.right == null) {
                current.right = new Node<Key, Value>(key, val, 1);
                break;
            }
            current = current.right;
        }
        else {
            current.val = val; // key already exists so just update value
            return;
        }
    }

    // fix subtree sizes from bottom up
    for (int i = path.size() - 1; i >= 0; i--) {
        Node<Key, Value> node = path.get(i);
        node.N = 1 + size(node.left) + size(node.right);
    }
    }

    public Value getFast(Key key) {
        Node<Key, Value> current = root;

        while (current != null) {
        int cmp = key.compareTo(current.key);

        if (cmp < 0) {
            current = current.left;
        }
        else if (cmp > 0) {
            current = current.right;
        }
        else {
            return current.val;
        }
    }

    return null;
    }

    public void balance() { //rebuilds binary search tree from sorted nodes
    LinkedList<Node<Key, Value>> nodes = new LinkedList<Node<Key, Value>>();
    inorder(root, nodes); // store nodes in sorted order
    root = buildBalanced(nodes, 0, nodes.size() - 1); // rebuild the tree so it is balanced
}

private void inorder(Node<Key, Value> x, LinkedList<Node<Key, Value>> nodes) { //inorder traversal
    if (x == null) {
        return;
    }
    inorder(x.left, nodes);
    nodes.add(x);
    inorder(x.right, nodes);
}

// builds a balanced BST from the sorted list
private Node<Key, Value> buildBalanced(LinkedList<Node<Key, Value>> nodes, int lo, int hi) {
    if (lo > hi) {
        return null;
    }

    int mid = (lo + hi) / 2;
    Node<Key, Value> x = nodes.get(mid);

    x.left = buildBalanced(nodes, lo, mid - 1);
    x.right = buildBalanced(nodes, mid + 1, hi);

    // update  size
    x.N = 1 + size(x.left) + size(x.right);

    return x;
    }

    public String displayLevel(Key key) { //print
         Node<Key, Value> start = root;

   
    while (start != null) {  // find the node with this key
        int cmp = key.compareTo(start.key);

        if (cmp < 0) {
            start = start.left;
        }
        else if (cmp > 0) {
            start = start.right;
        }
        else {
            break;
        }
    }

    if (start == null) { // if key was not found
        return "empty";
    }

    Queue<Node<Key, Value>> q = new LinkedList<Node<Key, Value>>();
    StringBuilder sb = new StringBuilder();

    q.add(start);

    while (!q.isEmpty()) {
        Node<Key, Value> current = q.remove();

        sb.append(current.val).append(" ");

        if (current.left != null) {
            q.add(current.left);
        }

        if (current.right != null) {
            q.add(current.right);
        }
    }

    return sb.toString().trim();
    }

    /**
     * entry point for testing.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BST<Integer, String> bst = new CompletedBST();
        
        bst.put(10, "TEN");
        bst.put(3, "THREE");
        bst.put(1, "ONE");
        bst.put(5, "FIVE");
        bst.put(2, "TWO");
        bst.put(7, "SEVEN");
        
        System.out.println("Before balance:");
        System.out.println(bst.displayLevel(10)); //root
        
        System.out.println("After balance:");
        bst.balance();
        System.out.println(bst.displayLevel(5)); //root
    }
}