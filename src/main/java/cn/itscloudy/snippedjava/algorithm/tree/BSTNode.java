package cn.itscloudy.snippedjava.algorithm.tree;

/**
 * Binary Search Tree Node
 */
public abstract class BSTNode<N extends BSTNode<N>> {
    protected final int i;
    private final String words;
    protected N left;
    protected N right;

    public BSTNode(int i, String words) {
        this.i = i;
        this.words = words;
    }

    public String getWords() {
        return words;
    }

    public boolean leftMightContain(int i) {
        return i < this.i;
    }

    public boolean leftMightContain(N n) {
        return n.i < this.i;
    }
}
