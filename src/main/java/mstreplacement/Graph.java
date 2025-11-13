package mstreplacement;

import java.util.*;

public class Graph {
    private int vertices;
    private List<Edge> edges;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
    }

    public void addEdge(int src, int dest, int weight) {
        edges.add(new Edge(src, dest, weight));
    }

    public List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }

    public int getVertices() {
        return vertices;
    }

    // Kruskal's algorithm for MST
    public List<Edge> kruskalMST() {
        List<Edge> mst = new ArrayList<>();
        List<Edge> allEdges = new ArrayList<>(edges);

        // Sort edges by weight
        Collections.sort(allEdges, Comparator.comparingInt(e -> e.weight));

        UnionFind uf = new UnionFind(vertices);

        for (Edge edge : allEdges) {
            if (uf.find(edge.src) != uf.find(edge.dest)) {
                mst.add(edge);
                uf.union(edge.src, edge.dest);
            }
        }

        return mst;
    }

    // Find connected components after removing an edge from MST
    public List<Set<Integer>> findComponents(List<Edge> mst, Edge removedEdge) {
        List<Edge> remainingEdges = new ArrayList<>(mst);
        remainingEdges.remove(removedEdge);

        UnionFind uf = new UnionFind(vertices);
        for (Edge edge : remainingEdges) {
            uf.union(edge.src, edge.dest);
        }

        Map<Integer, Set<Integer>> components = new HashMap<>();
        for (int i = 0; i < vertices; i++) {
            int root = uf.find(i);
            components.computeIfAbsent(root, k -> new HashSet<>()).add(i);
        }

        return new ArrayList<>(components.values());
    }

    // Find replacement edge to reconnect components
    public Edge findReplacementEdge(List<Edge> mst, Edge removedEdge, List<Set<Integer>> components) {
        if (components.size() <= 1) {
            return null; // No replacement needed
        }

        Set<Integer> component1 = components.get(0);
        Set<Integer> component2 = components.get(1);

        Edge bestReplacement = null;

        for (Edge edge : edges) {
            // Skip the removed edge and edges already in MST
            if (edge.equals(removedEdge) || mst.contains(edge)) {
                continue;
            }

            boolean connectsComponent1 = component1.contains(edge.src) || component1.contains(edge.dest);
            boolean connectsComponent2 = component2.contains(edge.src) || component2.contains(edge.dest);

            // Check if this edge connects the two components
            if (connectsComponent1 && connectsComponent2) {
                if (bestReplacement == null || edge.weight < bestReplacement.weight) {
                    bestReplacement = edge;
                }
            }
        }

        return bestReplacement;
    }

    public static class Edge {
        public int src, dest, weight;

        public Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Edge edge = (Edge) obj;
            return src == edge.src && dest == edge.dest && weight == edge.weight;
        }

        @Override
        public int hashCode() {
            return Objects.hash(src, dest, weight);
        }

        @Override
        public String toString() {
            return src + " - " + dest + " (weight: " + weight + ")";
        }
    }

    // Union-Find (Disjoint Set Union) data structure
    private static class UnionFind {
        private int[] parent;
        private int[] rank;

        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX != rootY) {
                if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }
    }
}