import java.util.*;

public class DijkstraAlgorithm {
    // Function to find the shortest path using Dijkstra's algorithm
    public static int[] dijkstra(int[][] graph, int source) {
        int n = graph.length;
        int[] dist = new int[n]; // Distance from source to each node
        boolean[] visited = new boolean[n]; // Track visited nodes
        Arrays.fill(dist, Integer.MAX_VALUE); // Initialize distances to infinity

        // Distance from source to source is 0
        dist[source] = 0;

        // Dijkstra's algorithm
        for (int i = 0; i < n - 1; i++) {
            // Find the vertex with the minimum distance
            int minVertex = -1;
            for (int v = 0; v < n; v++) {
                if (!visited[v] && (minVertex == -1 || dist[v] < dist[minVertex])) {
                    minVertex = v;
                }
            }

            // Mark the selected vertex as visited
            visited[minVertex] = true;

            // Update the distance to neighboring vertices
            for (int v = 0; v < n; v++) {
                if (graph[minVertex][v] != 0 && !visited[v] && dist[minVertex] != Integer.MAX_VALUE &&
                        dist[minVertex] + graph[minVertex][v] < dist[v]) {
                    dist[v] = dist[minVertex] + graph[minVertex][v];
                }
            }
        }

        return dist;
    }

    // Test the algorithm
    public static void main(String[] args) {
        // Example adjacency matrix
        int[][] graph = {
                { 0,1,4,6,8 },
                { 1,0,2,3,1 },
                {4,2,0,4,0},
                { 6,3,4,0,4},
                { 8,1,0,4,0 }
        };

        // Source node
        int source = 0;

        // Find shortest path from source
        int[] shortestPath = dijkstra(graph, source);

        // Print shortest path distances from source to all nodes
        System.out.println("Shortest path distances from node " + source + ":");
        for (int i = 0; i < shortestPath.length; i++) {
            System.out.println("Node " + i + ": Distance " + shortestPath[i]);
        }
    }
}

// This code is contributed by Aakash Hasija
/*

{ 0,1,4,6,8 },
        { 1,0,2,3,1 },
        {4,2,0,4,0},
        { 6,3,4,0,4},
        { 8,1,0,4,0 }*/
