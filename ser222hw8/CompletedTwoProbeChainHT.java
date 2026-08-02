package edu.ser222.m03_04;

/**
 * A symbol table implemented using a hashtable with chaining.
 * Does not support load balancing or resizing.
 * 
 * @author Bella Sheridan, Sedgewick and Wayne, Acuna
 */
import java.util.LinkedList;
import java.util.Queue;

public class CompletedTwoProbeChainHT<Key, Value> implements TwoProbeChainHT<Key, Value> {

    // default table size
    private static final int DEFAULT_M = 997;

    // size and number of value pairs
    private int M;
    private int N;

    // one object that stores both key and value together
    private static class Entry<Key, Value> {
        Key key;
        Value value;

        Entry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    // each array spot holds a linked list 
    private LinkedList<Entry<Key, Value>>[] entries;

    public CompletedTwoProbeChainHT() {
        this.M = DEFAULT_M;
        this.N = 0;
        this.entries = (LinkedList<Entry<Key, Value>>[]) new LinkedList[M];

        // make an empty linked list at every index
        for (int i = 0; i < M; i++) {
            entries[i] = new LinkedList<Entry<Key, Value>>();
        }
    }

    @Override
    public int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    @Override
    public int hash2(Key key) {
        return (((key.hashCode() & 0x7fffffff) % M) * 31) % M;
    }

    @Override
    public void put(Key key, Value val) {
        int index1 = hash(key);
        int index2 = hash2(key);

        //check if the key already exists in chain 1
        for (Entry<Key, Value> entry : entries[index1]) {
            if (entry.key.equals(key)) {
                entry.value = val;
                return;
            }
        }

        // then check chain 2
        // if hash1 and hash2 are the same this still works fine
        for (Entry<Key, Value> entry : entries[index2]) {
            if (entry.key.equals(key)) {
                entry.value = val;
                return;
            }
        }

        // if key is new insert into the shorter chain
        if (entries[index1].size() <= entries[index2].size()) {
            entries[index1].add(new Entry<Key, Value>(key, val));
        } else {
            entries[index2].add(new Entry<Key, Value>(key, val));
        }

        N++;
    }

    @Override
    public Value get(Key key) {
        int index1 = hash(key);
        int index2 = hash2(key);

        for (Entry<Key, Value> entry : entries[index1]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }

        for (Entry<Key, Value> entry : entries[index2]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }

        return null;
    }

    @Override
    public void delete(Key key) {
        int index1 = hash(key);
        int index2 = hash2(key);

        // search first chain and remove if found
        for (int i = 0; i < entries[index1].size(); i++) {
            if (entries[index1].get(i).key.equals(key)) {
                entries[index1].remove(i);
                N--;
                return;
            }
        }

        // search second chain and remove if found
        for (int i = 0; i < entries[index2].size(); i++) {
            if (entries[index2].get(i).key.equals(key)) {
                entries[index2].remove(i);
                N--;
                return;
            }
        }
    }

    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public Iterable<Key> keys() {
        Queue<Key> allKeys = new LinkedList<Key>();

        for (int i = 0; i < M; i++) {
            for (Entry<Key, Value> entry : entries[i]) {
                allKeys.add(entry.key);
            }
        }

        return allKeys;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // THESE METHODS ARE ONLY FOR GRADING AND COME FROM THE TWOPROBECHAINHT INTERFACE.

    @Override
    public int getM() {
        return M;
    }

    @Override
    public int getChainSize(int i) {
        return entries[i].size();
    }
}
