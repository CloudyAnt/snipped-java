package cn.itscloudy.snippedjava.algorithm.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BSTTest {

    @Test
    void shouldInsert() {
        BST tree = new BST();
        tree.insert(10, "10");
        tree.insert(5, "5");
        tree.insert(15, "15");
        tree.insert(5, "another 5");
        tree.insert(3, "3");

        assertEquals("5", tree.top.left.getWords());
        assertEquals("another 5", tree.top.left.right.getWords());
        assertEquals("3", tree.top.left.left.getWords());
        assertEquals("15", tree.top.right.getWords());
        assertNull(tree.top.right.left);
        assertNull(tree.top.right.right);
        assertNull(tree.top.left.left.left);
        assertNull(tree.top.left.left.right);
        assertNull(tree.top.left.right.left);
        assertNull(tree.top.left.right.right);
    }

    @Test
    void shouldSearch() {
        BST tree = new BST();
        tree.insert(10, "10");
        tree.insert(5, "5");
        tree.insert(15, "15");
        tree.insert(6, "6");
        tree.insert(7, "7");

        assertEquals("10", tree.search(10).getWords());
        assertEquals("5", tree.search(5).getWords());
        assertEquals("15", tree.search(15).getWords());
        assertEquals("6", tree.search(6).getWords());
        assertEquals("7", tree.search(7).getWords());
        assertNull(tree.search(1));
        assertNull(tree.search(8));
        assertNull(tree.search(20));
    }

    @Test
    void shouldPrint() {
        BST bst = new BST();
        bst.insert(10, "TEN");
        bst.insert(5, "FIVE");
        bst.insert(15, "FIFTEEN");
        bst.insert(4, "FOUR");
        bst.insert(5, "SIX");
        bst.insert(14, "FOURTEEN");
        bst.insert(16, "SIXTEEN");
        bst.print();
    }
}
