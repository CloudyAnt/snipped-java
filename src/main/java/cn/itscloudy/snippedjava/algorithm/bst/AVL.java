package cn.itscloudy.snippedjava.algorithm.bst;

/**
 * Self-balancing binary search tree(named after inventors Adelson-Velsky and Landis)
 */
public class AVL extends AbstractBST<AVL.Node> {

    protected Node top;

    @Override
    public Node top() {
        return top;
    }

    @Override
    public void insert(int i, String words) {
        Node node = new Node(i, words);
        if (this.top == null) {
            this.top = node;
        } else {
            this.top = insert(node, this.top);
        }
    }

    private Node insert(Node node, Node parent) {
        if (parent.leftMightContain(node)) {
            if (parent.left == null) {
                parent.left = node;
            } else {
                parent.left = insert(node, parent.left);
            }
        } else {
            if (parent.right == null) {
                parent.right = node;
            } else {
                parent.right = insert(node, parent.right);
            }
        }

        resetHeightOf(parent);
        return balance(parent);
    }

    protected void resetHeightOf(Node parent) {
        int leftHeight = parent.left == null ? 0 : parent.left.height;
        int rightHeight = parent.right == null ? 0 : parent.right.height;
        parent.height = Math.max(leftHeight, rightHeight) + 1;
    }

    private Node balance(Node parent) {
        int leftHeight = parent.left == null ? 0 : parent.left.height;
        int rightHeight = parent.right == null ? 0 : parent.right.height;

        int diff = leftHeight - rightHeight;
        if (diff > 1) {
            return rightRotate(parent);
        } else if (diff < -1) {
            return leftRotate(parent);
        }
        return parent;
    }

    /**
     * Right rotate and get the new parent
     *
     * @param parent The node of rotation based on
     * @return The new parent
     */
    @Override
    protected Node rightRotate(Node parent) {
        Node newParent = super.rightRotate(parent);
        resetHeightOf(parent);
        resetHeightOf(newParent);
        return newParent;
    }

    /**
     * Left rotate and get the new parent
     *
     * @param parent The node of rotation based on
     * @return The new parent
     */
    @Override
    protected Node leftRotate(Node parent) {
        Node newParent = super.leftRotate(parent);
        resetHeightOf(parent);
        resetHeightOf(newParent);
        return newParent;
    }

    @Override
    public void delete(int i) {
        if (i == top.i) {
            if (top.left.height > top.right.height) {
                top = rightRotate(top);
            } else {
                top = leftRotate(top);
            }
        }
        Node orphan = deleteAndGetOrphan(i, top);
        if (orphan != null) {
            insert(orphan, top);
        }
    }

    /**
     * Delete and find the orphan
     *
     * @return Orphan
     */
    private Node deleteAndGetOrphan(int i, Node parent) {
        if (parent.leftMightContain(i)) {
            Node left = parent.left;
            if (left == null) {
                return null;
            }
            if (i == left.i) {
                parent.left = left.left;
                resetHeightOf(parent);
                return left.right;
            } else {
                return deleteAndGetOrphan(i, parent.left);
            }
        } else {
            Node right = parent.right;
            if (right == null) {
                return null;
            }
            if (i == right.i) {
                parent.right = right.right;
                resetHeightOf(parent);
                return right.left;
            } else {
                return deleteAndGetOrphan(i, parent.right);
            }
        }
    }

    /**
     * Avl tree node
     */
    static class Node extends AbstractBSTNode<Node> {
        protected int height;

        public Node(int i, String words) {
            super(i, words);
            this.height = 1;
        }

    }
}
