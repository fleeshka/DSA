import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberVertices = scanner.nextInt();
        int numberEdges = scanner.nextInt();

        Graph<Integer, Integer> graph = new Graph<>();

        for (int i = 0; i < numberVertices; ++i) {
            graph.addVertex(i-1);
        }

        // to make 0 base for vertices
        for (int i = 0; i < numberEdges; ++i) {
            graph.addEdgeUsingIndex(scanner.nextInt() - 1, scanner.nextInt() - 1, scanner.nextInt());
        }

        graph.minimumSpanningForest();

    }
}

class Graph<V, E> {

    LinkedList<Vertex> vertices;
    LinkedList<Edge> edges;

    public Graph() {
        vertices = new LinkedList<>();
        edges = new LinkedList<>();
    }

    class Vertex {
        V value;
        boolean visited;
        List<Vertex> adjacent;
        int ID;
        int inputEdges = 0;
        int outputEdges = 0;


        public Vertex(V value, int id) {
            this.value = value;
            this.visited = false;
            this.adjacent = new LinkedList<>();
            this.ID = id;
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

        Vertex addVertex(V value) {
            Vertex vertex = new Vertex(value, vertices.size());
            this.vertices.add(vertex);
            return vertex;
        }

         Edge addEdge(Vertex from, Vertex to, E weight) {
            Edge edge = new Edge(from, to, weight);
            this.edges.add(edge);
            from.adjacent.add(to);
            to.adjacent.add(from);
            from.outputEdges++;
            to.inputEdges++;
            return edge;
        }

        void addEdgeUsingIndex (int fromIndex, int toIndex, E weight) {
            if ((fromIndex < vertices.size()) && (toIndex < vertices.size())) {
                addEdge(vertices.get(fromIndex), vertices.get(toIndex), weight);
            }
        }

        static class DisjointSetUnion {
            int[] parent;
            int[] size;

            DisjointSetUnion(int n) {
                int[] parent = new int[n];
                int[] size = new int[n];
                Arrays.fill(parent, -1);
                Arrays.fill(size, 1);
            }

            public int find(int i) {
                if (parent[i] < 0) return i;
                return parent[i] = find(parent[i]);
            }

            boolean union(int vertex1, int vertex2) {
                int parentVertex1 = find(vertex1);
                int parentVertex2 = find(vertex2);
                if (parentVertex1 != parentVertex2) {
                    if (size[parentVertex1] < size[parentVertex2]) {
                        int temp = parentVertex1;
                        parentVertex1 = parentVertex2;
                        parentVertex1 = temp;
                    }
                    parent[parentVertex2] = parentVertex1;
                    size[parentVertex1] += size[parentVertex2];
                    return true;
                }
                return false;
            }
        }

    void countingSort(Edge[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        // Find the maximum value in the array
        int max = Integer.MIN_VALUE;
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }

        // Create an array to store counts of each element
        int[] count = new int[max + 1];

        // Count occurrences of each element
        for (int num : arr) {
            count[num]++;
        }

        // Modify count array such that each element at each index stores
        // the sum of previous counts
        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }

        // Create a temporary array to store sorted elements
        int[] sortedArr = new int[arr.length];

        // Build the sorted array
        for (int i = arr.length - 1; i >= 0; i--) {
            sortedArr[count[arr[i]] - 1] = arr[i];
            count[arr[i]]--;
        }

        // Copy sorted elements back to the original array
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sortedArr[i];
        }
    }

        void minimumSpanningForest() {
            countingSort(edges);

            List<Edge> selectedMSTEdges = new ArrayList<>();
            DisjointSetUnion disjointSetUnion = new DisjointSetUnion(vertices.size());

            for (Edge currentEdge : edges) {
                int rootOfSource = disjointSetUnion.find(currentEdge.from.ID);
                int rootOfDestination = disjointSetUnion.find(currentEdge.to.ID);
                if (rootOfSource != rootOfDestination) {
                    disjointSetUnion.union(rootOfSource, rootOfDestination);
                    selectedMSTEdges.add(currentEdge);
                }
            }

            List<List<Edge>> componentEdges = new ArrayList<>(vertices.size());
            List<Integer> componentSizes = new ArrayList<>(vertices.size());
            List<Integer> componentRoots = new ArrayList<>(vertices.size());
            for (int i = 0; i < vertices.size(); i++) {
                componentEdges.add(new ArrayList<>());
                componentSizes.add(0);
                componentRoots.add(-1);
            }

            for (Edge edge : selectedMSTEdges) {
                int rootSource = disjointSetUnion.find(edge.from.ID);
                int rootDestination = disjointSetUnion.find(edge.to.ID);
                if (rootSource == rootDestination) {
                    componentEdges.get(rootSource).add(edge);
                    componentSizes.set(rootSource, componentSizes.get(rootSource) + 1);
                    if (componentRoots.get(rootSource) == -1) {
                        componentRoots.set(rootSource, edge.from.ID);
                    }
                }
            }

            int treeCount = 0;
            for (Integer size : componentSizes) {
                if (size > 0) {
                    treeCount++;
                }
            }

            for (Vertex vertex : vertices) {
                if (vertex.inputEdges == 0 && vertex.outputEdges == 0) {
                    treeCount++;
                }
            }
            System.out.println(treeCount);

            for (int i = 0; i < componentEdges.size(); ++i) {
                if (!componentEdges.get(i).isEmpty()) {
                    System.out.println(componentSizes.get(i) + 1 + " " + (componentRoots.get(i) + 1));
                    for (Edge edge : componentEdges.get(i)) {
                        System.out.println(edge.from.value + " " + edge.to.value + " " + edge.weight);
                    }
                }
            }
            for (Vertex vertex : vertices) {
                if (vertex.inputEdges == 0 && vertex.outputEdges == 0) {
                    System.out.println("1 " + vertex.value);
                }
            }
        }
    }


