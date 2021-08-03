package cn.itscloudy.snippedjava.algorithm.tree;

/**
 * Self-balancing binary search tree(named after inventors Adelson-Velsky and Landis)
 */
public class AVL extends BST<AVL.AVLNode> {

    protected AVLNode top;

    @Override
    public AVLNode top() {
        return top;
    }

    @Override
    public void insert(int i, String words) {
        AVLNode node = new AVLNode(i, words);
        if (this.top == null) {
            this.top = node;
        } else {
            this.top = insert(node, this.top);
        }
    }

    private AVLNode insert(AVLNode node, AVLNode parent) {
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

    protected void resetHeightOf(AVLNode parent) {
        int leftHeight = parent.left == null ? 0 : parent.left.height;
        int rightHeight = parent.right == null ? 0 : parent.right.height;
        parent.height = Math.max(leftHeight, rightHeight) + 1;
    }

    private AVLNode balance(AVLNode parent) {
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
    protected AVLNode rightRotate(AVLNode parent) {
        AVLNode newParent = super.rightRotate(parent);
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
    protected AVLNode leftRotate(AVLNode parent) {
        AVLNode newParent = super.leftRotate(parent);
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
        AVLNode orphan = deleteAndGetOrphan(i, top);
        if (orphan != null) {
            insert(orphan, top);
        }
    }

    /**
     * Delete and find the orphan
     *
     * @return Orphan
     */
    private AVLNode deleteAndGetOrphan(int i, AVLNode parent) {
        if (parent.leftMightContain(i)) {
            AVLNode left = parent.left;
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
            AVLNode right = parent.right;
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
    static class AVLNode extends BSTNode<AVLNode> {
        protected int height;

        public AVLNode(int i, String words) {
            super(i, words);
            this.height = 1;
        }

    }
}
