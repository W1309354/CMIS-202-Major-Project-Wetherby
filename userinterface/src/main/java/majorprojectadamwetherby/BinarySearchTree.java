package majorprojectadamwetherby;

import java.util.LinkedList;
import java.util.Queue;

// Node for the binary tree
class Node {
    // Value for the node
    private String value;
    // Left node of the current node
    private Node left;
    // Right node of the current node
    private Node right;

    Node(String value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    // Setter methods
    public void setValue(String value) {
        this.value = value;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    // Getter methods
    public String getValue() {
        return this.value;
    }

    public Node getLeft() {
        return this.left;
    }

    public Node getRight() {
        return this.right;
    }
}

// Binary tree implementation
public class BinarySearchTree {
    // Root node
    private Node root;

    // Constructor
    BinarySearchTree() {
        root = null;
    }

    // Get the root node
    public Node getRoot() {
        return this.root;
    }

    // Insert a new node into the bst
    public Node insert(Node node, String value) {
        // If the tree is empty return a new node
        if (node == null) {
            node = new Node(value);
            return node;
        }

        // Recur down the tree
        if (value.compareTo(node.getValue()) < 0) {
            node.setLeft(this.insert(node.getLeft(), value));
        } else if (value.compareTo(node.getValue()) > 0) {
            node.setRight(this.insert(node.getRight(), value));
        }

        // Return the unchanged node pointer
        return node;
    }

    // Search for a value in the bst
    public Node search(Node root, String value) {
        // Root doesn't exist or the key is at the root
        if (root == null || root.getValue().compareTo(value) == 0) {
            return root;
        }

        // Key is greater than the root's key
        if (root.getValue().compareTo(value) > 0) {
            return this.search(root.getRight(), value);
        }

        // Key is smaller than the root's key
        return this.search(root.getLeft(), value);
    }
}
