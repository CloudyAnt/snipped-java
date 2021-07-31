package cn.itscloudy.snippedjava.algorithm.tree;

public class BinaryNode<N extends BinaryNode<N>> {

    protected final int i;
    private final String words;
    protected N left;
    protected N right;

    public BinaryNode(int i, String words) {
        this.i = i;
        this.words = words;
    }

    public String getWords() {
        return words;
    }

}
