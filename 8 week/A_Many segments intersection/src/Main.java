import java.util.*;


public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int x1 = scan.nextInt();
            int y1 = scan.nextInt();
            int x2 = scan.nextInt();
            int y2 = scan.nextInt();
            Point p = new Point(Math.min(x1, x2), Math.min(y1, y2), true, null);
            Point q = new Point(Math.max(x1, x2), Math.max(y1, y2), false, null);
            points.add(p);
            points.add(q);
        }

        // Sort the points
        Collections.sort(points);

        // Create the AVL tree
        AVLTree tree = new AVLTree();

        // Process the points to detect intersections
        for (Point point : points) {
            if (point.isLeft) {
                Segment segment = new Segment(point, point.segment.q);
                Segment intersection = tree.searchIntersection(segment);
                if (intersection != null) {
                    System.out.println("INTERSECTION");
                    System.out.println(segment);
                    System.out.println(intersection);
                    return;
                }
                tree.insert(segment);
            } else {
                tree.delete(point.segment);
            }
        }

        System.out.println("NO INTERSECTIONS");
    }
}


class Point implements Comparable<Point> {
    int x, y;
    boolean isLeft;
    Segment segment;

    public Point(int x, int y, boolean isLeft, Segment segment) {
        this.x = x;
        this.y = y;
        this.isLeft = isLeft;
        this.segment = segment;
    }

    @Override
    public int compareTo(Point other) {
        if (this.x != other.x) {
            return Integer.compare(this.x, other.x);
        } else {
            return Boolean.compare(this.isLeft, other.isLeft);
        }
    }
}

class Segment {
    Point p, q;

    public Segment(Point p, Point q) {
        this.p = p;
        this.q = q;
    }

    public boolean intersects(Segment other) {
        return doIntersect(this.p, this.q, other.p, other.q);
    }

    private boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
        // Implement the orientation and intersection check here
        // This is a simplified version and may not work for all cases
        return !(q2.y < p1.y || q1.y < p2.y || q1.x < p2.x || q2.x < p1.x);
    }

    @Override
    public String toString() {
        return p.x + " " + p.y + " " + q.x + " " + q.y;
    }
}

class AVLTree {
    // Implement the AVL tree here
    // This is a simplified version and may not work for all cases
    private Node root;

    public void insert(Segment segment) {
        // Insert the segment into the tree
    }

    public void delete(Segment segment) {
        // Delete the segment from the tree
    }

    public Segment searchIntersection(Segment segment) {
        // Search the tree for an intersection with the given segment
        return null; // Replace with actual search logic
    }

    // Inner Node class for the AVL tree
    private class Node {
        // Implement the node structure here
    }
}

