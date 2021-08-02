package cn.itscloudy.snippedjava.algorithm.tree;

public class RedBlackTree implements BST<RedBlackTree.RBNode> {
    private RBNode top;

    @Override
    public RBNode top() {
        return top;
    }

    @Override
    public void insert(int i, String words) {
        RBNode node = new RBNode(i, words);
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
    private RBNode insert(RBNode x, RBNode p, RBNode gp) {
        boolean pLeft = p.leftMightContain(x);

        // Left/Right if not null, insert to Left/Right
        if (pLeft && p.left != null) {
            p = insert(x, p.left, p);
            blackGP(p, gp);
            return gp;
        }
        if (!pLeft && p.right != null) {
            p = insert(x, p.right, p);
            blackGP(p, gp);
            return p;
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
        boolean gpLeft = gp.leftMightContain(p);
        if ((gpLeft && gp.rightRed()) || (!gpLeft && gp.leftRed())) {
            x.setBlack();
            return gp;
        }

        // Else, rotate and recolor
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

    private void blackPAndRedGP(RBNode p, RBNode gp) {
        gp.setRed();
        p.setBlack();
    }

    private void blackGP(RBNode p, RBNode gp) {
        if (p.red && gp.red) {
            gp.setBlack();
        }
    }

    private RBNode rightRotate(RBNode node) {
        RBNode left = node.left;
        RBNode leftRight = left.right;
        left.right = node;
        node.left = leftRight;
        return left;
    }

    private RBNode leftRotate(RBNode node) {
        RBNode right = node.right;
        RBNode rightLeft = right.left;
        right.left = node;
        node.left = rightLeft;
        return right;
    }

    @Override
    public void delete(int i) {

    }

    protected static class RBNode extends BSTNode<RBNode> {
        boolean red;

        public RBNode(int i, String words) {
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
