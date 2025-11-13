package mstreplacement;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("MST Edge Replacement Demo");
        System.out.println("=========================\n");

        // Create a sample graph
        Graph graph = createSampleGraph();

        // Initialize MST Manager
        MSTManager mstManager = new MSTManager(graph);

        // Display original MST
        mstManager.displayMST();

        // Remove an edge (let's remove the first edge for demonstration)
        try {
            Graph.Edge removedEdge = mstManager.removeEdge(0);
            System.out.println();

            // Show components after removal
            List<Set<Integer>> components = mstManager.getComponentsAfterRemoval(removedEdge);
            mstManager.displayComponents(components);

            // Find and add replacement edge
            Graph.Edge replacementEdge = mstManager.findReplacementEdge(removedEdge, components);

            if (replacementEdge != null) {
                mstManager.addReplacementEdge(replacementEdge);

                // Display new MST
                System.out.println("\nNew Minimum Spanning Tree:");
                System.out.println("==========================");
                for (Graph.Edge edge : mstManager.getMST()) {
                    System.out.println(edge);
                }
                System.out.println("Total New MST Weight: " + mstManager.getMSTWeight());
            } else {
                System.out.println("No replacement edge found. The graph remains disconnected.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static Graph createSampleGraph() {
        Graph graph = new Graph(6);

        // Add edges (undirected graph)
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 2);
        graph.addEdge(2, 3, 4);
        graph.addEdge(2, 4, 5);
        graph.addEdge(3, 4, 7);
        graph.addEdge(3, 5, 6);
        graph.addEdge(4, 5, 2);

        return graph;
    }
}