package cn.itscloudy.snippedjava.algorithm.bst;

/**
 * Binary Search Tree Node
 */
public abstract class AbstractBSTNode<N extends AbstractBSTNode<N>> {
    protected final int i;
    private final String words;
    protected N left;
    protected N right;

    protected AbstractBSTNode(int i, String words) {
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
