package cn.itscloudy.snippedjava.algorithm.bst;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RedBlackTreeTest {

    @Test
    void shouldKeepPropertiesAfterDescendingInsert() {
        RedBlackTree tree = new RedBlackTree();
        tree.insert(10, "10");
        tree.insert(9, "9");
        tree.insert(8, "8");
        tree.insert(7, "7");
        tree.insert(6, "6");

        assertRedBlackTree(tree.top());
    }

    @Test
    void shouldKeepPropertiesAfterMixedInsert() {
        RedBlackTree tree = new RedBlackTree();
        int[] values = {41, 38, 31, 12, 19, 8, 25, 32, 47, 3, 9};
        for (int value : values) {
            tree.insert(value, String.valueOf(value));
        }

        assertRedBlackTree(tree.top());
    }

    private void assertRedBlackTree(RedBlackTree.Node root) {
        assertFalse(root.red, "Root must be black");
        assertNoConsecutiveRed(root);
        assertSameBlackHeight(root);
        assertInOrder(root);
    }

    private void assertNoConsecutiveRed(RedBlackTree.Node node) {
        if (node == null) {
            return;
        }
        if (node.red) {
            assertFalse(isRed(node.left), "Red node cannot have red left child");
            assertFalse(isRed(node.right), "Red node cannot have red right child");
        }
        assertNoConsecutiveRed(node.left);
        assertNoConsecutiveRed(node.right);
    }

    private int assertSameBlackHeight(RedBlackTree.Node node) {
        if (node == null) {
            return 1;
        }
        int leftBlackHeight = assertSameBlackHeight(node.left);
        int rightBlackHeight = assertSameBlackHeight(node.right);
        assertTrue(leftBlackHeight == rightBlackHeight, "Black height must be the same on both sides");
        return leftBlackHeight + (node.red ? 0 : 1);
    }

    private void assertInOrder(RedBlackTree.Node root) {
        List<Integer> values = new ArrayList<>();
        collectInOrder(root, values);
        for (int i = 1; i < values.size(); i++) {
            assertTrue(values.get(i - 1) <= values.get(i), "Tree must preserve in-order ordering");
        }
    }

    private void collectInOrder(RedBlackTree.Node node, List<Integer> values) {
        if (node == null) {
            return;
        }
        collectInOrder(node.left, values);
        values.add(node.i);
        collectInOrder(node.right, values);
    }

    private boolean isRed(RedBlackTree.Node node) {
        return node != null && node.red;
    }
}
