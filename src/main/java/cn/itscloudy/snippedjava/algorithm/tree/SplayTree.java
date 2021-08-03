package cn.itscloudy.snippedjava.algorithm.tree;

public class SplayTree extends BST<SplayTree.STNode> {

    private STNode top;

    @Override
    public STNode top() {
        return top;
    }

    @Override
    public void insert(int i, String words) {
        STNode node = new STNode(i, words);
        if (top == null) {
            top = node;
        } else {
            BST.insert(node, top);
        }
    }

    @Override
    public void delete(int i) {

    }

    @Override
    public STNode search(int i) {
        if (top == null || i == top.i) {
            return top;
        }
        STNode node = search(i, this.top);
        if (node != null) {
            top = node;
        }
        return node;
    }

    private STNode search(int i, STNode p) {
        if (p.leftMightContain(i)) {
            return searchLeft(i, p);
        }
        return searchRight(i, p);
    }

    private STNode searchLeft(int i, STNode p) {
        if (p.left == null) {
            return null;
        }
        if (i == p.left.i) {
            return rightRotate(p);
        }

        STNode node = search(i, p.left);
        if (node != null) {
            p.left = node;
            return rightRotate(p);
        }
        return null;
    }

    private STNode searchRight(int i, STNode p) {
        if (p.right == null) {
            return null;
        }

        if (i == p.right.i) {
            return leftRotate(p);
        }

        STNode node = search(i, p.right);
        if (node != null) {
            p.right = node;
            return leftRotate(p);
        }
        return null;
    }

    protected static class STNode extends BSTNode<STNode> {
        public STNode(int i, String words) {
            super(i, words);
        }
    }

}
