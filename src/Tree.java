public class Tree {

    private Node root;

    public int size() {
        return getSize(root);
    }

    /**
     * Returns the value at position 'index' (0-based)
     * in the in-order traversal of this AVL tree.
     */
    public int get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        return getValueByIndex(root, index);
    }

    /**
     * Inserts 'key' at the in-order position 'index' in the AVL tree.
     * 0-based indexing is assumed.
     */
    public void insertAt(int index, int key) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        root = insertAt(root, index, key);
    }

    /**
     * Removes and returns the value at the in-order position 'index'.
     */
    public int removeAt(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        // First, we get the value to return, then we do the removal.
        int value = get(index);
        root = removeAt(root, index);
        return value;
    }

    /* ---------------------------------------------------------------
       Private helpers for the above public operations
       --------------------------------------------------------------- */

    // Get value by index (0-based) in an Order Statistic Tree
    private int getValueByIndex(Node node, int index) {
        if (node == null) {
            throw new IllegalArgumentException("Invalid state: node is null");
        }
        int leftSize = getSize(node.left);
        if (index < leftSize) {
            return getValueByIndex(node.left, index);
        } else if (index == leftSize) {
            return node.key;
        } else {
            return getValueByIndex(node.right, index - leftSize - 1);
        }
    }

    // Insert key at in-order position index
    private Node insertAt(Node node, int index, int key) {
        if (node == null) {
            return new Node(key);
        }

        int leftSize = getSize(node.left);

        if (index <= leftSize) {
            node.left = insertAt(node.left, index, key);
        } else {
            node.right = insertAt(node.right, index - leftSize - 1, key);
        }

        update(node);
        return balance(node);
    }

    // Remove node at in-order position index
    private Node removeAt(Node node, int index) {
        if (node == null) {
            return null;
        }

        int leftSize = getSize(node.left);

        if (index < leftSize) {
            node.left = removeAt(node.left, index);
        } else if (index > leftSize) {
            node.right = removeAt(node.right, index - leftSize - 1);
        } else {
            // node is the element to remove
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                // Replace with successor
                Node successor = getMin(node.right);
                node.key = successor.key;
                node.right = removeMin(node.right);
            }
        }

        if (node != null) {
            update(node);
            node = balance(node);
        }
        return node;
    }

    // Get minimum node (used for removing successor)
    private Node getMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // Remove the minimum node in a subtree
    private Node removeMin(Node node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = removeMin(node.left);
        update(node);
        return balance(node);
    }

    // Updates height and size of a node
    private void update(Node node) {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        node.size = 1 + getSize(node.left) + getSize(node.right);
    }

    // Balances an AVL node
    private Node balance(Node node) {
        int balanceFactor = getHeight(node.left) - getHeight(node.right);
        if (balanceFactor > 1) {
            // Left heavy
            if (getHeight(node.left.left) < getHeight(node.left.right)) {
                node.left = rotateLeft(node.left);
            }
            node = rotateRight(node);
        } else if (balanceFactor < -1) {
            // Right heavy
            if (getHeight(node.right.right) < getHeight(node.right.left)) {
                node.right = rotateRight(node.right);
            }
            node = rotateLeft(node);
        }
        return node;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node z = x.right;
        x.right = y;
        y.left = z;

        update(y);
        update(x);
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node z = y.left;
        y.left = x;
        x.right = z;

        update(x);
        update(y);
        return y;
    }

    private int getHeight(Node node) {
        return (node == null) ? 0 : node.height;
    }

    private int getSize(Node node) {
        return (node == null) ? 0 : node.size;
    }

    public String toCyclicString() {
        if (size() == 0) return "[]";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            sb.append(get(i)).append(" ");
        }
        return sb.toString().trim();
    }


    @Override
    public String toString() {
        return "Tree{" +
                "root=" + root +
                '}';
    }
}
