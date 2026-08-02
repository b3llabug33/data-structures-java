package edu.ser222.m03_04;

/**
 * A symbol table implemented using a hashtable with linear probing.
 * 
 * @author Bella Sheridan, Sedgewick and Wayne, Acuna
 */
import java.util.LinkedList;
import java.util.Queue;

public class CompletedLinearProbingHT<Key, Value> implements ProbingHT<Key, Value> {

    protected static final int DEFAULT_M = 997; // default table size 
    protected int M; // size of array
    protected int N; // number of value pairs stored

    // single object to hold key and value
    protected static class Entry<Key, Value> {
        Key key;
        Value value;

        Entry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }
    
    protected Entry<Key, Value>[] entries; //hashtable array


    public CompletedLinearProbingHT() {
        this.M = DEFAULT_M;
        this.N = 0;

        //create an entry array and cast it
        this.entries = (Entry<Key, Value>[]) new Entry[M];
    }

    @Override
    public int hash(Key key, int i) {
        // ((key.hashCode() & 0x7fffffff) + i) % Mc 
        return ((key.hashCode() & 0x7fffffff) + i) % M;
    }

    @Override
    public void put(Key key, Value val) {
        // go through the probe sequence until we find either
        // the same key -> update value
        //  an empty spot -> insert new entry
        for (int i = 0; i < M; i++) {
            int index = hash(key, i);

            if (entries[index] == null) {
                entries[index] = new Entry<Key, Value>(key, val);
                N++;
                return;
            }

            if (entries[index].key.equals(key)) {
                entries[index].value = val;
                return;
            }
        }

        // the table is full
    }

    @Override
    public Value get(Key key) {
        // same probe sequence used in put()
        for (int i = 0; i < M; i++) {
            int index = hash(key, i);

            // if null the key isnt in the table 
            if (entries[index] == null) {
                return null;
            }

            if (entries[index].key.equals(key)) {
                return entries[index].value;
            }
        }

        return null;
    }

    @Override
    public void delete(Key key) {
        // find where the key actually is
        int foundIndex = -1;

        for (int i = 0; i < M; i++) {
            int index = hash(key, i);

            if (entries[index] == null) {
                return; // key not found
            }

            if (entries[index].key.equals(key)) {
                foundIndex = index;
                break;
            }
        }

        if (foundIndex == -1) {
            return;
        }

        // remove the key
        entries[foundIndex] = null;
        N--;

        // extra credit
        // reinsert the rest of the cluster after the deleted spot so the probe sequence still works correctly
        int next = (foundIndex + 1) % M;
        while (entries[next] != null) {
            Entry<Key, Value> entryToRedo = entries[next];

            // remove it temp
            entries[next] = null;
            N--;

            // reinsert it in proper place
            put(entryToRedo.key, entryToRedo.value);
            next = (next + 1) % M;
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
            if (entries[i] != null) {
                allKeys.add(entries[i].key);
            }
        }

        return allKeys;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // THESE METHODS ARE ONLY FOR GRADING AND COME FROM THE PROBINGHT INTERFACE.

    @Override
    public int getM() {
        return M;
    }

    @Override
    public Object getTableEntry(int i) {
        return entries[i];
    }
}
