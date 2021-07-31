package cn.itscloudy.snippedjava.algorithm.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AVLTest {

    @Test
    public void shouldRotateToRight() {
        AVL avl = new AVL(10, "10");
        avl.insert(9, "9");
        avl.insert(8, "8");

        assertEquals("9", avl.top.getWords());
        assertEquals("8", avl.top.left.getWords());
        assertEquals("10", avl.top.right.getWords());
    }

    @Test
    public void shouldRotateToLeft() {
        AVL avl = new AVL(10, "10");
        avl.insert(11, "11");
        avl.insert(12, "12");

        assertEquals("11", avl.top.getWords());
        assertEquals("10", avl.top.left.getWords());
        assertEquals("12", avl.top.right.getWords());
    }

    @Test
    public void shouldDelete() {
        AVL avl = new AVL(10, "10");
        avl.insert(5, "5");
        avl.insert(20, "20");
        avl.insert(4, "4");
        avl.insert(6, "6");
        avl.insert(15, "15");
        avl.insert(3, "3");

        avl.delete(10);
        assertEquals(5, avl.top.i);
        assertEquals(15, avl.top.right.i);
        assertEquals(4, avl.top.left.i);
        assertEquals(3, avl.top.left.left.i);
        assertEquals(6, avl.top.right.left.i);
        assertEquals(20, avl.top.right.right.i);

    }
}
