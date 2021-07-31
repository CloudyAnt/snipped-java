package cn.itscloudy.snippedjava.algorithm.tree;

/**
 * Self-balancing binary search tree(named after inventors Adelson-Velsky and Landis)
 */
public class AVL extends BinaryTree<AVLNode> {
    protected AVLNode top;

    public AVL(int i, String words) {
        this.top = new AVLNode(i, words);
    }

    @Override
    public AVLNode top() {
        return top;
    }

    @Override
    public void insert(int i, String words) {
        top = insert(new AVLNode(i, words), top);
    }

    private void resetHeightOf(AVLNode parent) {
        int leftHeight = parent.left == null ? 0 : parent.left.height;
        int rightHeight = parent.right == null ? 0 : parent.right.height;
        parent.height = Math.max(leftHeight, rightHeight) + 1;
    }

    private AVLNode insert(AVLNode node, AVLNode parent) {
        if (destInLeft(node, parent)) {
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

    private AVLNode rightRotate(AVLNode node) {
        AVLNode left = node.left;
        AVLNode leftRight = left.right;
        left.right = node;
        node.left = leftRight;

        resetHeightOf(node);
        resetHeightOf(left);
        return left;
    }

    private AVLNode leftRotate(AVLNode node) {
        AVLNode right = node.right;
        AVLNode rightLeft = right.left;
        right.left = node;
        node.right = rightLeft;

        resetHeightOf(node);
        resetHeightOf(right);
        return right;
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
     * @return orphan
     */
    private AVLNode deleteAndGetOrphan(int i, AVLNode parent) {
        if (destInLeft(i, parent)) {
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
}
