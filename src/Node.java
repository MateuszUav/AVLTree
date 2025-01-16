public class Node {
    int key;       // The actual value stored
    int height;    // AVL tree height
    int size;      // Number of nodes in the subtree rooted at this node
    Node left;
    Node right;

    public Node(int key) {
        this.key = key;
        this.height = 1;
        this.size = 1;
    }

    @Override
    public String toString() {
        return "Node{" +
                "key=" + key +
                ", height=" + height +
                ", size=" + size +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
