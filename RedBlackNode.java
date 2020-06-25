// java implementation of RedBlackTree Node and its attributes

public class RedBlackNode {
    Building data; // holds the key
    RedBlackNode parent; // pointer to the parent
    RedBlackNode left; // pointer to left child
    RedBlackNode right; // pointer to right child
    int color; // 1 . Red, 0 . Black

    public RedBlackNode() {
    }

    public RedBlackNode(Building data, RedBlackNode parent, RedBlackNode left, RedBlackNode right, int color) {
        this.data = data;
        this.parent = parent;
        this.left = left;
        this.right = right;
        this.color = color;
    }

    public Building getData() {
        return data;
    }

    public RedBlackNode getLeft() {
        return left;
    }

    public RedBlackNode getRight() {
        return right;
    }

}
