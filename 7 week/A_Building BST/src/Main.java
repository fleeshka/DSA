import java.util.*;


public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int numberNotes = scan.nextInt();
        List<Integer> items = new ArrayList<>();
        AVLTree<Integer> tree = new AVLTree<>();

        for (int i = 0; i < numberNotes; i++) {
            items.add(scan.nextInt());
        }
        tree.outputTree(items);
    }

}

class Node<T extends Comparable<T>>{
    T item;
    int height;
    Node<T> left, right;

    Node (T value) {
        this.item = value;
    }

}

class AVLTree <T extends Comparable<T>>{
    Node<T> root;

    int height(Node<T> node){
        if (node == null){
            return 0;
        }
        return node.height;
    }

    Node<T> newNode(T key) {
        Node<T> node = new Node<>(key);
        node.left = null;
        node.right = null;
        node.height = 1;
        return node;
    }

    //insert method
    Node<T> insert (Node<T> node, T item){

        if (node == null){
            return (new Node<>(item));
        }
        if (item.compareTo(node.item) < 0){
            node.left = insert(node.left, item);
        } else if (item.compareTo(node.item) > 0){
            node.right = insert(node.right, item);
        } else {
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1 && item.compareTo(node.left.item) < 0)
            return rightRotation(node);

        if (balanceFactor < -1 && item.compareTo(node.right.item) > 0)
            return leftRotation(node);


        if (balanceFactor > 1 && item.compareTo(node.left.item) > 0) {
            node.left = leftRotation(node.left);
            return rightRotation(node);
        }

        //RL_rotation
        if (balanceFactor < -1 && item.compareTo(node.right.item) < 0) {
            node.right = rightRotation(node.right);
            return leftRotation(node);
        }

        return node;

    }

    private Node<T> rightRotation(Node<T> y) {
        Node<T> x = y.left;
        Node<T> betta = x.right;

        x.right = y;
        y.left = betta;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Node<T> leftRotation(Node<T> x) {
        Node<T> y = x.right;
        Node<T> betta = y.left;

        y.left = x;
        x.right = betta;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    private int getBalanceFactor(Node<T> node) {
        if (node == null){
            return 0;
        }
        return height(node.left) - height(node.right);
    }


    private void inOrderTraversal(Node<T> node, List<Node<T>> list) {
        if (node != null) {
            inOrderTraversal(node.left, list);
            list.add(node);
            inOrderTraversal(node.right, list);
        }
    }


    public void outputTree(List<T> inputItemsArray) {
        Node<T> currentNode = null;

        for (T key : inputItemsArray) {
            currentNode = insert(currentNode, key);
        }


        List<Node<T>> arrayNodesInOrder = new ArrayList<>();
        inOrderTraversal(currentNode, arrayNodesInOrder);

        System.out.println(arrayNodesInOrder.size());

        for (Node<T> node : arrayNodesInOrder) {
            T leftKey = (node.left != null) ? node.left.item : (T) "-1";
            T rightKey = (node.right != null) ? node.right.item : (T) "-1";

            System.out.println(node.item + " " + leftKey + " " + rightKey);
        }

        System.out.println(currentNode.item);
    }

}