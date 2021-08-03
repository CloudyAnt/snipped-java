package cn.itscloudy.snippedjava.algorithm.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedBlackTreeTest {

    @Test
    void shouldInsert() {
        BST<?> tree = new RedBlackTree();
        tree.insert(10, "10");
        tree.insert(9, "9");
        tree.insert(8, "8");
        tree.insert(7, "7");
        tree.insert(6, "6");

        RedBlackTree.RBNode top = (RedBlackTree.RBNode) tree.top();
        assertNode(top, 9, false);
        assertNode(top.left, 8, true);
        assertNode(top.right, 10, true);
        assertNode(top.left.left, 7, false);
        assertNode(top.left.left.left, 6, true);
    }

    private void assertNode(RedBlackTree.RBNode node, int i, boolean red) {
        assertEquals(i, node.i);
        assertEquals(red, node.red);
    }
}
