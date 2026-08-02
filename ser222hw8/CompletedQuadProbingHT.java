package edu.ser222.m03_04;

/**
 * A symbol table implemented using a hashtable with quadratic probing.
 * 
 * @author Bella Sheridan, Acuna
 */
public class CompletedQuadProbingHT<Key, Value> extends CompletedLinearProbingHT<Key, Value> {

    //constructor
    public CompletedQuadProbingHT() {
        super();
    }

    @Override
    public int hash(Key key, int i) {
        // quadratic probing hash 
        // ((key.hashCode() & 0x7fffffff) + i*i) % M
        return ((key.hashCode() & 0x7fffffff) + (i * i)) % M;
    }
}
