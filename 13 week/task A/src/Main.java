// Ulyana Chaikouskaya
// rely on geeckfogeecks web-site

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int numberVertices = scan.nextInt();
        Graph <Integer, Integer> graph = new Graph<>();

        for(int i = 0; i < numberVertices; i++){
            graph.addVertex(i);
        }

        for (int i = 0; i < numberVertices; i++) {
            for (int j = 0; j < numberVertices; j++) {
                int currentEdgeWeight = scan.nextInt();
                if (currentEdgeWeight != 100000) {
                    graph.addEdge(graph.getVertices().get(i),
                            graph.getVertices().get(j), currentEdgeWeight);

                }
            }
        }
        graph.UlyanaChaikouskaya_sp();
    }
}


class Graph<V, E> {
    class Vertex {
        V value;
        boolean visited;
        List<Edge> adjacent;

        public Vertex(V value) {
            this.value = value;
            this.visited = false;
            this.adjacent = new ArrayList<Edge>();
        }
    }
    class Edge {
        Vertex from;
        Vertex to;

        E weight;

        public Edge(Vertex from, Vertex to, E weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

    }
    List<Vertex> vertices;
    List<Edge> edges;

    public Graph() {
        this.vertices = new ArrayList<Vertex>();
        this.edges = new ArrayList<Edge>();
    }

    void addVertex(V value) {
        Vertex vertex = new Vertex(value);
        this.vertices.add(vertex);
    }

    void addEdge(Vertex from, Vertex to, E weight) {
        Edge edge = new Edge(from, to, weight);
        this.edges.add(edge);
        from.adjacent.add(edge);
        to.adjacent.add(edge);
    }

    boolean adjacent(Vertex v, Vertex u) {
        for (Edge edge : u.adjacent) {
            if (edge.from.equals(v) || edge.to.equals(v))
                return true;
        }

        return false;
    }
    public List<Vertex> getVertices() {
        return vertices;
    }

    void dfs(Vertex currentVertex) {
        currentVertex.visited = true;
        for (Edge edge : currentVertex.adjacent) {
            Vertex neighbor = edge.from.equals(currentVertex) ? edge.to : edge.from;
            if (!neighbor.visited && adjacent(currentVertex, neighbor)) {
                dfs(neighbor);
            }
        }
    }

    boolean isConnected() {
        // Perform DFS from the first vertex
        dfs(vertices.getFirst());
        // Check if all vertices are visited
        for (Vertex vertex : vertices) {
            if (!vertex.visited) {
                return false;
            }
        }
        return true;
    }

    // BellmanFord algorithm
    public void UlyanaChaikouskaya_sp (){
        int numberVertices = vertices.size();
        int numberEdges = edges.size();
        int[] distance = new int[numberVertices];
        // create array with predecessors (include case that re whole graph is cycle)
        int[] numberPreviousValue = new int[numberVertices];
        // first added vertex --> src


        for (int i = 0; i < numberVertices; i++) {
            distance[i] = 100000;
            numberPreviousValue[i] = -1;
        }
        distance[0] = 0;
        int lastUpdatedVertices = -1;


        for (int i = 0; i < numberVertices; i++) {
            lastUpdatedVertices = -1;
            for (int j = 0; j < numberEdges; j++) {
                int u = (int) edges.get(j).from.value;
                int v = (int) edges.get(j).to.value;
                int weight = (int) edges.get(j).weight;

                if (distance[u] + weight < distance[v]) {
                    distance[v] = distance[u] + weight;
                    numberPreviousValue[v] = u;
                    lastUpdatedVertices = v;
                }
            }
            if (lastUpdatedVertices == -1) {
                break;
            }
        }

        // go backward if negative cycle was founded
        if (lastUpdatedVertices != -1) {
            System.out.println("YES");
            List<Integer> negativeCycle = new ArrayList<>();
            for (int i = 0; i < numberVertices; i++) {
                lastUpdatedVertices = numberPreviousValue[lastUpdatedVertices];
            }
            int currentVertex = lastUpdatedVertices;
            do {
                negativeCycle.add(currentVertex);
                currentVertex = numberPreviousValue[currentVertex];
            } while (currentVertex != lastUpdatedVertices);

            System.out.println(negativeCycle.size());
            for (int i = negativeCycle.size() - 1; i >= 0; i--) {
                System.out.print((negativeCycle.get(i) + 1) + " ");
            }

        } else {
            System.out.println("NO");
        }



    }


}
