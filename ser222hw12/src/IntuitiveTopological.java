package edu.ser222.m04_02;

import java.util.LinkedList;

/**
 * This class does a topological sort without DFS.
 * It keeps taking a vertex with indegree 0, adding it to the answer,
 * and removing it from a copy of the graph.
 */
public class IntuitiveTopological implements TopologicalSort {

    private LinkedList<Integer> order;
    private boolean dag;

    
     //builds the topological ordering for the graph.
    public IntuitiveTopological(EditableDiGraph graph) {
        order = new LinkedList<Integer>();
        dag = true;

        // copy the graph because this algorithm removes vertices
        // dont destroy the original graph from CompletedMain
        BetterDiGraph copy = copyGraph(graph);
        while (!copy.isEmpty()) {
            Integer zeroVertex = findZeroIndegreeVertex(copy);

            //if there is no indegree 0 vertex the graph has a cycle
            if (zeroVertex == null) {
                dag = false;
                order = null;
                return;
            }
            order.add(zeroVertex);
            copy.removeVertex(zeroVertex);
        }
    }

    
     //makes a full copy of the graph using the interface methods.
    private BetterDiGraph copyGraph(EditableDiGraph graph) {
        BetterDiGraph copy = new BetterDiGraph();
        //add all vertices first, including vertices with no edges.
        for (Integer v : graph.vertices()) {
            copy.addVertex(v);
        }
        //then add all edges.
        for (Integer v : graph.vertices()) {
            for (Integer w : graph.getAdj(v)) {
                copy.addEdge(v, w);
            }
        }
        return copy;
    }

    
     //finds one vertex that currently has no incoming edges.
    private Integer findZeroIndegreeVertex(EditableDiGraph graph) {
        for (Integer v : graph.vertices()) {
            if (graph.getIndegree(v) == 0) {
                return v;
            }
        }
        return null;
    }
    
     //returns the topological order, or null if there was a cycle.
    public Iterable<Integer> order() {
        return order;
    }
    
     //returns true when the graph is a DAG.
    public boolean isDAG() {
        return dag;
    }
}
