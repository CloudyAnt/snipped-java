package cn.itscloudy.snippedjava.algorithm.tree;

/**
 * Basic Binary Search Tree
 */
public class SimpleBST extends BST<SimpleBST.SimpleBSTNode> {
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
            BST.insert(node, top);
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
