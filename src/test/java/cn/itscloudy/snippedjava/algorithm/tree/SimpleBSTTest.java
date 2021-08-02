package cn.itscloudy.snippedjava.algorithm.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleBSTTest {

    @Test
    public void shouldInsert() {
        SimpleBST tree = new SimpleBST();
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
    public void shouldSearch() {
        SimpleBST tree = new SimpleBST();
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
}
