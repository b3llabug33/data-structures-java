package edu.ser222.m04_02;

/**
 * This program loads kanji data into a directed graph and then prints
 * a topological order so simpler kanji/components come before harder ones.
 *
 * Completion time: about 4 hours
 *
 * @author Bella Sheridan, Acuna, Buckner
 * @version 4/29
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;

public class CompletedMain implements KanjiMain {

    //Do not add any member variables to this class.

     //loads the kanji file.
     //each normal line has an integer ID and a kanji character.  
    public HashMap<Integer, String> loadKanji(String filename, EditableDiGraph graph) {
        HashMap<Integer, String> kanjiMap = new HashMap<Integer, String>();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(filename), "UTF-8"));

            String line = reader.readLine();

            while (line != null) {
                line = line.replace("\uFEFF", "").trim();
                // Skip blank lines and comments.
                if (line.length() > 0 && !line.startsWith("#")) {
                    String[] parts = line.split("\\s+");

                    int id = Integer.parseInt(parts[0]);
                    String kanji = parts[1];

                    kanjiMap.put(id, kanji);
                    graph.addVertex(id);
                }

                line = reader.readLine();
            }
            reader.close();  }
        catch (IOException e) {
            throw new RuntimeException("Could not read kanji file.");
        }
        return kanjiMap;
    }

    
     //loads the component file
     //each normal line has src dst, meaning src is a component of dst
    public void loadDataComponents(String filename, EditableDiGraph graph) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(filename), "UTF-8"));
            String line = reader.readLine();
            while (line != null) {
                line = line.replace("\uFEFF", "").trim();
                // skip blank lines and comments
                if (line.length() > 0 && !line.startsWith("#")) {
                    String[] parts = line.split("\\s+");
                    int src = Integer.parseInt(parts[0]);
                    int dst = Integer.parseInt(parts[1]);
                    graph.addEdge(src, dst);
                }
                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException e) {
            throw new RuntimeException("Could not read component file.");
        }
    }
      //builds the exact output string
     //original is the graph in the order vertices were added
      //sorted is the topological order.
     
    public String buildOrderString(EditableDiGraph graph, TopologicalSort topSort,
                                   HashMap<Integer, String> kanjiMap) {
        String original = "";
        String sorted = "";
        for (Integer id : graph.vertices()) {
            original = original + kanjiMap.get(id);
        }
        if (topSort.isDAG()) {
            for (Integer id : topSort.order()) {
                sorted = sorted + kanjiMap.get(id);
            }
        }
        else {
            sorted = "Graph has a cycle.";
        }
        return "Original:\n" + original + "\nSorted:\n" + sorted;
    }

    public static void main(String[] args) {
        /***************************************************************************
         * START - CORE DRIVER LOGIC, DO NOT MODIFY                                *
         **************************************************************************/
        String FILENAME_KANJI = "data-kanji.txt";
        String FILENAME_COMPONENTS = "data-components.txt";

        KanjiMain driver = new CompletedMain();
        EditableDiGraph graph = new BetterDiGraph();

        HashMap<Integer, String> kanjiMap = driver.loadKanji(FILENAME_KANJI, graph);
        driver.loadDataComponents(FILENAME_COMPONENTS, graph);

        TopologicalSort intuitive = new IntuitiveTopological(graph);

        System.out.println(driver.buildOrderString(graph, intuitive, kanjiMap));

        /***************************************************************************
         * END - CORE DRIVER LOGIC, DO NOT MODIFY                                  *
         **************************************************************************/
    }
}
