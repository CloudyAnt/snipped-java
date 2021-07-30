package cn.itscloudy.snippedjava.algorithm.tree;

/**
 * Balanced binary search tree
 */
public class AVL {
    private Node top;
    private int leftHeight = 0;
    private int rightHeight = 0;

    public AVL(int i) {
        this.top = new Node(i);
    }

    public void insert(int i) {

        int height = top.addChild(i);
        // judge and set left height or right height

        int diff = leftHeight + rightHeight;
        if (diff > 1) {
            // rotate to left
        } else if (diff < -1) {
            // rotate to right
        }
    }

}
