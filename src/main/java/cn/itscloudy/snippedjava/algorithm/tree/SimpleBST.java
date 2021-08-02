package cn.itscloudy.snippedjava.algorithm.tree;

/**
 * Basic Binary Search Tree
 */
public class SimpleBST implements BST<SimpleBST.SimpleBSTNode> {
    protected SimpleBSTNode top;

    @Override
    public SimpleBSTNode top() {
        return top;
    }

    @Override
    public void insert(int i, String words) {
        SimpleBSTNode node = new SimpleBSTNode(i, words);
        if (top == null) {
            top = node;
        } else {
            insert(node, top);
        }
    }

    private <N extends BSTNode<N>> void insert(N node, N parent) {
        if (parent.leftMightContain(node)) {
            N left = parent.left;
            if (left == null) {
                parent.left = node;
            } else {
                insert(node, left);
            }
        } else {
            N right = parent.right;
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

    static class SimpleBSTNode extends BSTNode<SimpleBSTNode> {
        public SimpleBSTNode(int i, String words) {
            super(i, words);
        }
    }
}
