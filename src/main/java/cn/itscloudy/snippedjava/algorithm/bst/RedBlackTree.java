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
        Node node = new Node(i, words);
        if (top == null) {
            this.top = node;
            top.red = false;
        } else {
            if (top.leftMightContain(node)) {
                if (top.left == null) {
                    top.left = node;
                } else {
                    top = insert(node, top.left, top);
                }
            } else {
                if (top.right == null) {
                    top.right = node;
                } else {
                    top = insert(node, top.right, top);
                }
            }
        }
    }

    /**
     * Insert new node
     *
     * @param x  The node to insert
     * @param p  The parent node
     * @param gp The grandparent node
     * @return The grandparent node
     */
    private Node insert(Node x, Node p, Node gp) {
        boolean pLeft = p.leftMightContain(x);
        boolean gpLeft = gp.leftMightContain(p);

        // Left/Right if not null, insert to Left/Right
        if (pLeft && p.left != null) {
            return insertToChild(x, p.left, p, gp, gpLeft);
        }
        if (!pLeft && p.right != null) {
            return insertToChild(x, p.right,  p, gp, gpLeft);
        }

        if (pLeft) {
            p.left = x;
        } else {
            p.right = x;
        }

        // If x's parent is black, just insert
        if (!p.red) {
            return gp;
        }

        // If x's uncle is also red, invert color of gp and gp's children
        if ((gpLeft && gp.rightRed()) || (!gpLeft && gp.leftRed())) {
            x.setBlack();
            return gp;
        }

        return rotateAndRecolor(x, p, gp, pLeft, gpLeft);
    }

    private Node rotateAndRecolor(Node x, Node p, Node gp, boolean pLeft, boolean gpLeft) {
        if (pLeft) {
            if (gpLeft) { // LL
                blackPAndRedGP(p, gp);
                return rightRotate(gp);
            } else { // RL
                gp.right = rightRotate(p);
                blackPAndRedGP(x, gp);
                return leftRotate(gp);
            }
        } else {
            if (gpLeft) { // LR
                gp.left = leftRotate(p);
                blackPAndRedGP(x, gp);
                return rightRotate(gp);
            } else { // RR
                blackPAndRedGP(p, gp);
                return leftRotate(gp);
            }
        }
    }

    private Node insertToChild(Node x, Node c, Node p, Node gp, boolean gpLeft) {
        p = insert(x, c, p);
        if (gpLeft) {
            gp.left = p;
        } else {
            gp.right = p;
        }
        return gp;
    }

    private void blackPAndRedGP(Node p, Node gp) {
        gp.setRed();
        p.setBlack();
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

        private boolean leftRed() {
            return left != null && left.red;
        }

        private boolean rightRed() {
            return right != null && right.red;
        }

        private void setBlack() {
            this.red = false;
        }

        private void setRed() {
            this.red = true;
        }
    }

}
