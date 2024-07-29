import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class trySolve {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int numberNotes = scan.nextInt();
        AVLTree<Integer> tree = new AVLTree<>();

        List<Integer> keys = new ArrayList<>();
        for (int i = 0; i < numberNotes; ++i) {
            keys.add(scan.nextInt());
        }

        tree.printAVLTree(keys);
    }
}

class Node<T extends Comparable<T>> {
    T key;
    Node<T> left, right;
    int height;

    Node(T key) {
        this.key = key;
    }
}

class AVLTree<T extends Comparable<T>> {
    Node<T> root;

    Node<T> newNode(T key) {
        Node<T> node = new Node<>(key);
        node.left = null;
        node.right = null;
        node.height = 1;
        return node;
    }

    int getHeight(Node<T> node) {
        return (node == null) ? 0 : node.height;
    }

    int getBalance(Node<T> node) {
        return (node == null) ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    Node<T> rightRotate(Node<T> y) {
        Node<T> x = y.left;
        Node<T> T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));

        return x;
    }

    Node<T> leftRotate(Node<T> y) {
        Node<T> x = y.right;
        Node<T> T2 = x.left;

        x.left = y;
        y.right = T2;

        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));

        return x;
    }

    Node<T> insert(Node<T> root, T key) {
        if (root == null)
            return newNode(key);

        if (key.compareTo(root.key) < 0)
            root.left = insert(root.left, key);
        else if (key.compareTo(root.key) > 0)
            root.right = insert(root.right, key);

        root.height = 1 + Math.max(getHeight(root.left), getHeight(root.right));

        int balance = getBalance(root);

        if (balance > 1 && key.compareTo(root.left.key) < 0)
            return rightRotate(root);

        if (balance < -1 && key.compareTo(root.right.key) > 0)
            return leftRotate(root);

        if (balance > 1 && key.compareTo(root.left.key) > 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        if (balance < -1 && key.compareTo(root.right.key) < 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    void inOrderTraversal(Node<T> root, List<Node<T>> nodes) {
        if (root != null) {
            inOrderTraversal(root.left, nodes);
            nodes.add(root);
            inOrderTraversal(root.right, nodes);
        }
    }

    void printAVLTree(List<T> keys) {
        Node<T> root = null;

        for (T key : keys) {
            root = insert(root, key);
        }

        List<Node<T>> nodes = new ArrayList<>();
        inOrderTraversal(root, nodes);

        System.out.println(nodes.size());

        for (Node<T> node : nodes) {
            T leftKey = (node.left != null) ? node.left.key : (T) "-1";
            T rightKey = (node.right != null) ? node.right.key : (T) "-1";

            System.out.println(node.key + " " + leftKey + " " + rightKey);
        }

        System.out.println(root.key);
    }
}


