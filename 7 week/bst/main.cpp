#include <iostream>
#include <vector>

using namespace std;

template<typename T>
class Node {
public:
    T item;
    int height;
    Node<T>* left;
    Node<T>* right;
    int index;

    Node(T value) : item(value), height(1), left(nullptr), right(nullptr), index(-1) {}

    T getItem() const {
        return item;
    }
};

template<typename T>
class AVLTree {
public:
    Node<T>* root;
    int indexCounter;

    AVLTree() : root(nullptr), indexCounter(0) {}


    int height(Node<T>* N) {
        if (N == nullptr) {
            return 0;
        }
        return N->height;
    }

    int getBalanceFactor(Node<T>* node) {
        return node ? height(node->left) - height(node->right) : 0;
    }

    std::vector<int> preOrderIndices() {
        std::vector<int> preOrderIndices;
        preOrderTraversal(root, preOrderIndices);
        return preOrderIndices;
    }

    Node<T>* rightRotation(Node<T>* y) {
        Node<T>* x = y->left;
        Node<T>* beta = x->right;

        x->right = y;
        y->left = beta;

        y->height = max(height(y->left), height(y->right)) + 1;
        x->height = max(height(x->left), height(x->right)) + 1;

        return x;
    }

    Node<T>* leftRotation(Node<T>* x) {
        Node<T>* y = x->right;
        Node<T>* beta = y->left;

        y->left = x;
        x->right = beta;

        x->height = max(height(x->left), height(x->right)) + 1;
        y->height = max(height(y->left), height(y->right)) + 1;

        return y;
    }

    Node<T>* insert(Node<T>* node, T item) {
        if (node == nullptr) {
            return new Node<T>(item);
        }
        if (item < node->item) {
            node->left = insert(node->left, item);
        }
        else if (item > node->item) {
            node->right = insert(node->right, item);
        }
        else {
            return node;
        }

        node->height = 1 + max(height(node->left), height(node->right));
        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1 && item < node->left->item)
            return rightRotation(node);

        if (balanceFactor < -1 && item > node->right->item)
            return leftRotation(node);

        if (balanceFactor > 1 && item > node->left->item) {
            node->left = leftRotation(node->left);
            return rightRotation(node);
        }

        if (balanceFactor < -1 && item < node->right->item) {
            node->right = rightRotation(node->right);
            return leftRotation(node);
        }

        return node;
    }

    void preOrder(Node<T>* node) {
        if (node != nullptr) {
            cout << node->item << " ";
            preOrder(node->left);
            preOrder(node->right);
        }
    }

    void preOrderTraversal(Node<T>* node, std::vector<int>& indices) {
        if (node) {
            node->index = indexCounter++;
            indices.push_back(node->index);
            preOrderTraversal(node->left, indices);
            preOrderTraversal(node->right, indices);
        }
    }

    Node<T>* getNodeByIndex(int index) {
        return getNodeByIndex(root, index);
    }

    Node<T>* getNodeByIndex(Node<T>* node, int index) {
        if (!node || node->index == index) {
            return node;
        }
        Node<T>* leftResult = getNodeByIndex(node->left, index);
        if (leftResult) {
            return leftResult;
        }
        return getNodeByIndex(node->right, index);
    }
};

template<typename T>
void outputResults(AVLTree<T>& tree, const std::vector<int>& indices) {
    for (int index : indices) {
        Node<T>* node = tree.getNodeByIndex(index);
        std::cout << node->item << " ";

        if (node->left) {
            std::cout << node->left->index + 1 << " ";
        } else {
            std::cout << "-1 ";
        }

        if (node->right) {
            std::cout << node->right->index + 1 << " ";
        } else {
            std::cout << "-1 ";
        }

        std::cout << std::endl;
    }
}

int main() {
    int numberNotes;
    std::cin >> numberNotes;
    AVLTree<int> tree;

    for (int i = 0; i < numberNotes; ++i) {
        int note;
        std::cin >> note;
        tree.root = tree.insert(tree.root, note);
    }

    std::cout << numberNotes << std::endl;

    std::vector<int> preOrderIndices = tree.preOrderIndices();
    outputResults(tree, preOrderIndices);

    std::cout << 1 << std::endl;

    return 0;
}
