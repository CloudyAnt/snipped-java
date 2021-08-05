package cn.itscloudy.snippedjava.algorithm.tree;

/**
 * Basic Binary Search Tree
 */
public class BST extends AbstractBST<BST.SimpleBSTNode> {
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
            AbstractBST.insert(node, top);
        }
    }

    @Override
    public void delete(int i) {

    }

    static class SimpleBSTNode extends AbstractBSTNode<SimpleBSTNode> {
        public SimpleBSTNode(int i, String words) {
            super(i, words);
        }
    }
}
