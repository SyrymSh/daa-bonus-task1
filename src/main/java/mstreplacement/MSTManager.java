package mstreplacement;

import java.util.*;

public class MSTManager {
    private Graph graph;
    private List<Graph.Edge> mst;

    public MSTManager(Graph graph) {
        this.graph = graph;
        buildMST();
    }

    private void buildMST() {
        this.mst = graph.kruskalMST();
    }

    public void displayMST() {
        System.out.println("Minimum Spanning Tree Edges:");
        System.out.println("============================");
        int totalWeight = 0;
        for (Graph.Edge edge : mst) {
            System.out.println(edge);
            totalWeight += edge.weight;
        }
        System.out.println("Total MST Weight: " + totalWeight);
        System.out.println();
    }

    public Graph.Edge removeEdge(int edgeIndex) {
        if (edgeIndex < 0 || edgeIndex >= mst.size()) {
            throw new IllegalArgumentException("Invalid edge index");
        }

        Graph.Edge removedEdge = mst.remove(edgeIndex);
        System.out.println("Removed edge: " + removedEdge);
        return removedEdge;
    }

    public List<Set<Integer>> getComponentsAfterRemoval(Graph.Edge removedEdge) {
        return graph.findComponents(new ArrayList<>(mst), removedEdge);
    }

    public void displayComponents(List<Set<Integer>> components) {
        System.out.println("Components after edge removal:");
        System.out.println("==============================");
        for (int i = 0; i < components.size(); i++) {
            System.out.println("Component " + (i + 1) + ": " + components.get(i));
        }
        System.out.println();
    }

    public Graph.Edge findReplacementEdge(Graph.Edge removedEdge, List<Set<Integer>> components) {
        return graph.findReplacementEdge(new ArrayList<>(mst), removedEdge, components);
    }

    public void addReplacementEdge(Graph.Edge replacementEdge) {
        if (replacementEdge != null) {
            mst.add(replacementEdge);
            System.out.println("Added replacement edge: " + replacementEdge);
        }
    }

    public List<Graph.Edge> getMST() {
        return new ArrayList<>(mst);
    }

    public int getMSTWeight() {
        return mst.stream().mapToInt(edge -> edge.weight).sum();
    }
}