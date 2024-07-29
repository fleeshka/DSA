// Ulyana Chaikouskaya
// ideas taken from the site geeksforgeeks

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int numberCities = scan.nextInt();
        Graph <Integer, Integer> graph = new Graph<>();

        for(int i = 0; i < numberCities; i++){
            graph.addVertex(i);
        }

        for (int i = 0; i < numberCities; i++) {
            for (int j = 0; j < numberCities; j++) {
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
        List<Edge> adjacent;

        public Vertex(V value) {
            this.value = value;
            this.adjacent = new ArrayList<>();
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
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
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

    public List<Vertex> getVertices() {
        return vertices;
    }

    // BellmanFord algorithm
    public void UlyanaChaikouskaya_sp (){
        int numberVertices = vertices.size();
        int numberEdges = edges.size();
        int[] distance = new int[numberVertices];
        int[] arrayPreviousVisitedV = new int[numberVertices];
        for (int i = 0; i < numberVertices; i++) {
            distance[i] = 100000;
            arrayPreviousVisitedV[i] = -1;
        }
        distance[0] = 0;
        int lastUpdatedVertex = -1;
        for (int i = 0; i < numberVertices; i++) {
            lastUpdatedVertex = -1;
            for (int j = 0; j < numberEdges; j++) {
                int u = (int) edges.get(j).from.value;
                int v = (int) edges.get(j).to.value;
                int weight = (int) edges.get(j).weight;

                if (distance[u] + weight < distance[v]) {
                    distance[v] = distance[u] + weight;
                    arrayPreviousVisitedV[v] = u;
                    lastUpdatedVertex = v;
                }
            }
            if (lastUpdatedVertex == -1) {
                break;
            }
        }
        if (lastUpdatedVertex != -1) {
            System.out.println("YES");
            List<Integer> negativeCycle = new ArrayList<>();
            for (int i = 0; i < numberVertices; i++) {
                lastUpdatedVertex = arrayPreviousVisitedV[lastUpdatedVertex];
            }
            int currentVertex = lastUpdatedVertex;
            do {
                negativeCycle.add(currentVertex);
                currentVertex = arrayPreviousVisitedV[currentVertex];
            } while (currentVertex != lastUpdatedVertex);
            System.out.println(negativeCycle.size());
            for (int i = negativeCycle.size() - 1; i >= 0; i--) {
                System.out.print((negativeCycle.get(i) + 1) + " ");
            }
        } else {
            System.out.println("NO");
        }
    }
}
