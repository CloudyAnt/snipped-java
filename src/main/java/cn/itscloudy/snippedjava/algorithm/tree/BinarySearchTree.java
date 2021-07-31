package cn.itscloudy.snippedjava.algorithm.tree;

public class BinarySearchTree extends BinaryTree<BSTNode> {

    protected BSTNode top;

    public BinarySearchTree(int i, String words) {
        this.top = new BSTNode(i, words);
    }

    @Override
    public BSTNode top() {
        return top;
    }

    public void insert(int i, String words) {
        insert(new BSTNode(i, words), top);
    }

    /**
     * Insert node to parent
     */
    private static void insert(BSTNode node, BSTNode parent) {
        if (destInLeft(node, parent)) {
            BSTNode left = parent.left;
            if (left == null) {
                parent.left = node;
            } else {
                insert(node, left);
            }
        } else {
            BSTNode right = parent.right;
            if (right == null) {
                parent.right = node;
            } else {
                insert(node, right);
            }
        }
    }

    @Override
    public void delete(int i) {
    }
}
