import java.util.List;

// data structure that represents a node in the tree
// class RedBlackTree implements the operations in Red Black Tree
public class RedBlackTree {
    private RedBlackNode root;
    public RedBlackNode TNULL;


    public RedBlackNode getRoot() {
        return root;
    }

    public RedBlackNode getTNULL() {
        return TNULL;

    }

    // insert the key to the tree using binary search tree in its appropriate position and fix the tree using fixInsert function
    public void insert(RedBlackNode node) {
        RedBlackNode y = TNULL;
        RedBlackNode x = this.root;

        while (x != TNULL) {
            y = x;
            if (node.data.getBuildingNumber() < x.data.getBuildingNumber()) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        // y is parent of x
        node.parent = y;
        if (y == TNULL) {
            root = node;
        } else if (node.data.getBuildingNumber() < y.data.getBuildingNumber()) {
            y.left = node;
        } else if (node.data.getBuildingNumber() > y.data.getBuildingNumber()) {
            y.right = node;
        } else if (node.data.getBuildingNumber() == y.data.getBuildingNumber()) {
            throw new IllegalArgumentException("Exception:You cannot add duplicate node. Building with buildingNumber :" + node.data.getBuildingNumber()
                    + "already exist");
        }
        node.left = TNULL;
        node.right = TNULL;
        node.color = 1;

        fixInsert(node);
    }

    // fix the red-black tree if there are two consecutive red nodes
    private void fixInsert(RedBlackNode insertedNode) {
        RedBlackNode uncleNode;
        while (insertedNode.parent.color == 1) {                            //if the inserted Node parent color is red
            if (insertedNode.parent == insertedNode.parent.parent.right) {  //if parent is a right child
                uncleNode = insertedNode.parent.parent.left;               // uncle would be the right child
                if (uncleNode.color == 1) {                                 //if parent and uncle both are red , we will go for color flip
                    uncleNode.color = 0;
                    insertedNode.parent.color = 0;
                    insertedNode.parent.parent.color = 1;
                    insertedNode = insertedNode.parent.parent;
                } else {                                                    //if the uncle node is black
                    if (insertedNode == insertedNode.parent.left) {         // node is inserted as  left child  so RL rotation should be done
                        insertedNode = insertedNode.parent;
                        rightRotate(insertedNode);
                    }
                    insertedNode.parent.color = 0;
                    insertedNode.parent.parent.color = 1;
                    leftRotate(insertedNode.parent.parent);
                }
            } else {
                uncleNode = insertedNode.parent.parent.right;       // if the parent is left child and uncle is right child

                if (uncleNode.color == 1) {                         //same as above , flip the color if the uncle is red
                    uncleNode.color = 0;
                    insertedNode.parent.color = 0;
                    insertedNode.parent.parent.color = 1;
                    insertedNode = insertedNode.parent.parent;
                } else {                                                    //if the uncle is black
                    if (insertedNode == insertedNode.parent.right) {        //if node is inserted as right child follor LR rotation
                        insertedNode = insertedNode.parent;
                        leftRotate(insertedNode);
                    }
                    insertedNode.parent.color = 0;
                    insertedNode.parent.parent.color = 1;
                    rightRotate(insertedNode.parent.parent);
                }
            }
        }
        root.color = 0;
    }

    public RedBlackTree() {
        TNULL = new RedBlackNode();
        TNULL.color = 0;
        TNULL.left = null;
        TNULL.right = null;
        TNULL.data = null;
        root = TNULL;
    }


    // find the node with the minimum key
    public RedBlackNode minimum(RedBlackNode node) {
        while (node.left != TNULL) {
            node = node.left;
        }
        return node;
    }

    // rotate left at node x
    public void leftRotate(RedBlackNode x) {
        RedBlackNode y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == TNULL) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    // rotate right at node x
    public void rightRotate(RedBlackNode x) {
        RedBlackNode y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == TNULL) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    // search the tree for the key k
    // and return the corresponding node
    public RedBlackNode searchTree(int k) {
        return searchTreeHelper(this.root, k);
    }

    //Function to search a particular building number in a tree using binary search tree property
    private RedBlackNode searchTreeHelper(RedBlackNode node, int key) {
        if (node == TNULL || key == node.data.getBuildingNumber()) {
            return node;
        }
        if (key < node.data.getBuildingNumber()) {
            return searchTreeHelper(node.left, key);
        }
        return searchTreeHelper(node.right, key);
    }



    // delete the node from the tree
    public void deleteNode(RedBlackNode deleteNode) {
        RedBlackNode x, y;
        y = deleteNode;
        int yOriginalColor = y.color;
        if (deleteNode.left == TNULL) {
            x = deleteNode.right;
            rbTransplant(deleteNode, deleteNode.right);
        } else if (deleteNode.right == TNULL) {
            x = deleteNode.left;
            rbTransplant(deleteNode, deleteNode.left);
        } else {
            y = minimum(deleteNode.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == deleteNode) {
                x.parent = y;
            } else {
                rbTransplant(y, y.right);
                y.right = deleteNode.right;
                y.right.parent = y;
            }

            rbTransplant(deleteNode, y);
            y.left = deleteNode.left;
            y.left.parent = y;
            y.color = deleteNode.color;
        }
        if (yOriginalColor == 0) {
            fixDelete(x);
        }
    }

    // fix the rb tree modified by the delete operation if a black node is deleted
    private void fixDelete(RedBlackNode x) {
        RedBlackNode s;
        while (x != root && x.color == 0) {
            if (x == x.parent.left) {
                s = x.parent.right;
                if (s.color == 1) {
                    s.color = 0;
                    x.parent.color = 1;
                    leftRotate(x.parent);
                    s = x.parent.right;
                }

                if (s.left.color == 0 && s.right.color == 0) {
                    s.color = 1;
                    x = x.parent;
                } else {
                    if (s.right.color == 0) {
                        s.left.color = 0;
                        s.color = 1;
                        rightRotate(s);
                        s = x.parent.right;
                    }
                    s.color = x.parent.color;
                    x.parent.color = 0;
                    s.right.color = 0;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                s = x.parent.left;
                if (s.color == 1) {
                    s.color = 0;
                    x.parent.color = 1;
                    rightRotate(x.parent);
                    s = x.parent.left;
                }

                if (s.right.color == 0 && s.left.color == 0) {
                    s.color = 1;
                    x = x.parent;
                } else {
                    if (s.left.color == 0) {
                        s.right.color = 0;
                        s.color = 1;
                        leftRotate(s);
                        s = x.parent.left;
                    }
                    s.color = x.parent.color;
                    x.parent.color = 0;
                    s.left.color = 0;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = 0;
    }

    private void rbTransplant(RedBlackNode u, RedBlackNode v) {
        if (u.parent == TNULL) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }



//    function to search a range of building numbers in a tree using bst property
    public List<Building> searchTree(RedBlackNode node, long buildingNumber1, long buildingNumber2, List<Building> outputList) {
        if (node == TNULL) {
            return outputList;
        }
        if (buildingNumber1 < node.getData().getBuildingNumber()) {
            searchTree(node.getLeft(), buildingNumber1, buildingNumber2, outputList);
        }
        if (buildingNumber1 <= node.data.getBuildingNumber() && buildingNumber2 >= node.data.getBuildingNumber()) {
            outputList.add(node.data);
        }
        if (buildingNumber2 > node.getData().getBuildingNumber()) {
            searchTree(node.getRight(), buildingNumber1, buildingNumber2, outputList);
        }
        return outputList;
    }

}