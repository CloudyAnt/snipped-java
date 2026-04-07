package cn.itscloudy.snippedjava.algorithm.bst;

/**
 * This tree was invented in 1972 by Rudolf Bayer
 */
public class RedBlackTree extends AbstractBST<RedBlackTree.Node> {
    private Node top;

    @Override
    public Node top() {
        return top;
    }

    @Override
    public void insert(int i, String words) {
        top = insert(top, new Node(i, words));
        top.setBlack();
    }

    private Node insert(Node root, Node x) {
        if (root == null) {
            return x;
        }
        if (root.leftMightContain(x)) {
            root.left = insert(root.left, x);
        } else {
            root.right = insert(root.right, x);
        }
        return balance(root);
    }

    private Node balance(Node root) {
        if (isRed(root.right) && !isRed(root.left)) {
            root = leftRotateAndRecolor(root);
        }
        if (isRed(root.left) && isRed(root.left.left)) {
            root = rightRotateAndRecolor(root);
        }
        if (isRed(root.left) && isRed(root.right)) {
            flipColors(root);
        }
        return root;
    }

    private Node leftRotateAndRecolor(Node root) {
        Node newRoot = leftRotate(root);
        newRoot.red = root.red;
        newRoot.left.setRed();
        return newRoot;
    }

    private Node rightRotateAndRecolor(Node root) {
        Node newRoot = rightRotate(root);
        newRoot.red = root.red;
        newRoot.right.setRed();
        return newRoot;
    }

    private void flipColors(Node root) {
        root.setRed();
        root.left.setBlack();
        root.right.setBlack();
    }

    private boolean isRed(Node root) {
        return root != null && root.red;
    }

    @Override
    public void delete(int i) {
        // complete this later
    }

    protected static class Node extends AbstractBSTNode<Node> {
        boolean red;

        public Node(int i, String words) {
            super(i, words);
            this.red = true;
        }

        private void setBlack() {
            this.red = false;
        }

        private void setRed() {
            this.red = true;
        }
    }

}
