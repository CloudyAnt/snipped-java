package cn.itscloudy.snippedjava.algorithm.bst;

public class SplayTree extends AbstractBST<SplayTree.Node> {

    private Node top;

    @Override
    public Node top() {
        return top;
    }

    @Override
    public void insert(int i, String words) {
        Node node = new Node(i, words);
        if (top == null) {
            top = node;
        } else {
            AbstractBST.insert(node, top);
            search(i);
        }
    }

    @Override
    public void delete(int i) {

    }

    @Override
    public Node search(int i) {
        if (top == null || i == top.i) {
            return top;
        }
        Node node = search(i, this.top);
        if (node != null) {
            top = node;
        }
        return node;
    }

    private Node search(int i, Node p) {
        if (p.leftMightContain(i)) {
            return searchLeft(i, p);
        }
        return searchRight(i, p);
    }

    private Node searchLeft(int i, Node p) {
        if (p.left == null) {
            return null;
        }
        if (i == p.left.i) {
            return rightRotate(p);
        }

        Node node = search(i, p.left);
        if (node != null) {
            p.left = node;
            return rightRotate(p);
        }
        return null;
    }

    private Node searchRight(int i, Node p) {
        if (p.right == null) {
            return null;
        }

        if (i == p.right.i) {
            return leftRotate(p);
        }

        Node node = search(i, p.right);
        if (node != null) {
            p.right = node;
            return leftRotate(p);
        }
        return null;
    }

    protected static class Node extends AbstractBSTNode<Node> {
        public Node(int i, String words) {
            super(i, words);
        }
    }

}
