// just problem set solution


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

class Graph {
    private int V; // No. of vertices
    private LinkedList<Integer> adj[]; // Adjacency List
    private Map<Character, Integer> charToIndex; // Map characters to indices

    // Constructor
    Graph(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
        charToIndex = new HashMap<>();
    }

    // Function to add an edge into the graph
    void addEdge(char v, char w) {
        if (!charToIndex.containsKey(v)) {
            charToIndex.put(v, charToIndex.size());
        }
        if (!charToIndex.containsKey(w)) {
            charToIndex.put(w, charToIndex.size());
        }
        int vIndex = charToIndex.get(v);
        int wIndex = charToIndex.get(w);
        adj[vIndex].add(wIndex);
    }

    // A recursive function used by allTopologicalSortUtil
    void allTopologicalSortUtil(boolean[] visited, int[] indegree, ArrayList<Character> res) {
        // To indicate whether all topological orders are found or not
        boolean flag = false;
        for (int i = 0; i < V; i++) {
            // If a vertex is not visited yet and has zero in-degree, add it to the result
            if (!visited[i] && indegree[i] == 0) {
                // Reduce in-degree of adjacent vertices
                for (int adjacent : adj[i]) {
                    indegree[adjacent]--;
                }
                res.add((char) ('A' + i)); // Convert index back to character
                visited[i] = true;
                allTopologicalSortUtil(visited, indegree, res);

                // Reset visited, res and in-degree for backtracking
                visited[i] = false;
                res.remove(res.size() - 1);
                for (int adjacent : adj[i]) {
                    indegree[adjacent]++;
                }

                flag = true;
            }
        }

        // If all vertices are visited and included in the result, print the result
        if (!flag) {
            for (char c : res) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    // Prints all topological sorts of the complete graph
    void allTopologicalSort() {
        boolean[] visited = new boolean[V];
        int[] indegree = new int[V];

        // Compute in-degree of each vertex
        for (int i = 0; i < V; i++) {
            for (int adjacent : adj[i]) {
                indegree[adjacent]++;
            }
        }

        ArrayList<Character> res = new ArrayList<>();
        allTopologicalSortUtil(visited, indegree, res);
    }

    public static void main(String[] args) {
        Graph g = new Graph(8);
        g.addEdge('B', 'A');
        g.addEdge('B', 'C');
        g.addEdge('B', 'E');
        g.addEdge('B', 'D');
        g.addEdge('C', 'D');
        g.addEdge('F', 'D');
        g.addEdge('F', 'E');
        g.addEdge('D', 'H');
        g.addEdge('G', 'E');
        g.addEdge('G', 'F');
        g.addEdge('G', 'H');

        System.out.println("All Topological sorts of the graph: ");
        g.allTopologicalSort();
    }
}