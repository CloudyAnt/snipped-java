package cn.itscloudy.snippedjava.algorithm.tree;

/**
 * Binary Search Tree
 */
public interface BST<N extends BSTNode<N>> {

    N top();

    void insert(int i, String words);

    void delete(int i);

    default N search(int i) {
        return search(i, top());
    }

    private N search(int i, N parent) {
        if (parent == null) {
            return null;
        }
        if (i == parent.i) {
            return parent;
        }
        if (parent.leftMightContain(i)) {
            return search(i, parent.left);
        }
        return search(i, parent.right);
    }
}
