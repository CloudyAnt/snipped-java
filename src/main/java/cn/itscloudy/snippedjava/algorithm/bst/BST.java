package cn.itscloudy.snippedjava.algorithm.bst;

/**
 * Basic Binary Search Tree
 */
public class BST extends AbstractBST<BST.Node> {
    protected Node top;

    @Override
    public Node top() {
        return top;
    }

    @Override
    public void insert(int i, String words) {
        Node node = new Node(i, words);
        if (top == null) {
            top = node;
        } else {
            AbstractBST.insert(node, top);
        }
    }

    @Override
    public void delete(int i) {

    }

    static class Node extends AbstractBSTNode<Node> {
        public Node(int i, String words) {
            super(i, words);
        }
    }
}
