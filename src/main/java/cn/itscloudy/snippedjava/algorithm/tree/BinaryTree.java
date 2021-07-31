package cn.itscloudy.snippedjava.algorithm.tree;

public abstract class BinaryTree<N extends BinaryNode<N>> {

    public abstract N top();

    public abstract void insert(int i, String words);

    public N search(int i) {
        return search(i, top());
    }

    protected static <N extends BinaryNode<N>> boolean destInLeft(int i, N parent) {
        return i < parent.i;
    }

    protected static <N extends BinaryNode<N>> boolean destInLeft(N node, N parent) {
        return destInLeft(node.i, parent);
    }

    protected static <N extends BinaryNode<N>> N search(int i, N node) {
        if (node == null) {
            return null;
        }
        if (i == node.i) {
            return node;
        }
        if (destInLeft(i, node)) {
            return search(i, node.left);
        }
        return search(i, node.right);
    }

    public abstract void delete(int i);
}
