package edu.ser222.m04_02;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;


 //used a HashMap so each vertex can point to its adjacency list
 //also kept a vertex list so the graph remembers the order vertices were added

public class BetterDiGraph implements EditableDiGraph {
    private HashMap<Integer, LinkedList<Integer>> adj;
    private LinkedList<Integer> vertexList;
    private int edgeCount;
    
    //makes an empty graph
    public BetterDiGraph() {
        adj = new HashMap<Integer, LinkedList<Integer>>();
        vertexList = new LinkedList<Integer>();
        edgeCount = 0;
    }
    
      //adds an edge v -> w if either vertex is missing add it first
    public void addEdge(int v, int w) {
        addVertex(v);
        addVertex(w);

        LinkedList<Integer> list = adj.get(v);

        // do not add the same edge twice
        if (!list.contains(w)) {
            list.add(w);
            edgeCount++;
        }
    }

     //adds one vertex if it is not already in the graph.
    public void addVertex(int v) {
        if (!adj.containsKey(v)) {
            adj.put(v, new LinkedList<Integer>());
            vertexList.add(v);
        }
    }
   
     //returns the vertices that v points to.
    public Iterable<Integer> getAdj(int v) {
        if (!adj.containsKey(v)) {
            return new LinkedList<Integer>();
        }

        //return a copy so outside code does not mess up my real graph
        LinkedList<Integer> copy = new LinkedList<Integer>();
        for (Integer next : adj.get(v)) {
            copy.add(next);
        }
        return copy;
    }
    
//returns how many edges are in the graph.
    public int getEdgeCount() {
        return edgeCount;
    }

    //counts how many edges point into v.
    public int getIndegree(int v) throws NoSuchElementException {
        if (!adj.containsKey(v)) {
            throw new NoSuchElementException("Vertex does not exist.");
        }

        int count = 0;

        // check every adjacency list and count how many contain v.
        for (Integer current : vertexList) {
            if (adj.get(current).contains(v)) {
                count++;
            }
        }

        return count;
    }  
     //returns how many vertices are in the graph.  
    public int getVertexCount() {
        return vertexList.size();
    }
     //removes the edge v -> w if it exists.
    public void removeEdge(int v, int w) {
        if (!adj.containsKey(v) || !adj.containsKey(w)) {
            return;
        }
        if (adj.get(v).remove((Integer) w)) {
            edgeCount--;
        }
    }
     //removes a vertex and all edges connected to it
    public void removeVertex(int v) {
        if (!adj.containsKey(v)) {
            return;
        }

        // first remove all edges going out from v
        edgeCount = edgeCount - adj.get(v).size();
        adj.remove(v);
        vertexList.remove((Integer) v);

        // then remove all edges going into v from other vertices
        for (Integer current : vertexList) {
            if (adj.get(current).remove((Integer) v)) {
                edgeCount--;
            }
        }
    }   
    //returns all vertices in the graph
    public Iterable<Integer> vertices() {
        LinkedList<Integer> copy = new LinkedList<Integer>();
        for (Integer v : vertexList) {
            copy.add(v);
        }
        return copy;
    }
     //returns true if there are no vertices
    public boolean isEmpty() {
        return vertexList.isEmpty();
    }
     //returns true if the graph has this vertex
    public boolean containsVertex(int v) {
        return adj.containsKey(v);
    }
}
