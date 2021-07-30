package cn.itscloudy.snippedjava.algorithm.tree;

class Node {
    private final int i;
    private Node left;
    private Node right;

    public Node(int i) {
        this.i = i;
    }

    public int addChild(int i) {
        if (this.i > i) {
            return setLeft(i) + 1;
        }
        return setRight(i) + 1;
    }

    private int setLeft(int i) {
        if (left == null) {
            this.left = new Node(i);
            return 0;
        }
        return left.addChild(i);
    }

    private int setRight(int i) {
        if (right == null) {
            this.right = new Node(i);
            return 0;
        }
        return right.addChild(i);
    }
}
