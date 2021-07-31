package cn.itscloudy.snippedjava.algorithm.tree;

/**
 * Avl tree node
 */
public class AVLNode extends BinaryNode<AVLNode> {
    protected int height;

    public AVLNode(int i, String words) {
        super(i, words);
        this.height = 1;
    }

}
