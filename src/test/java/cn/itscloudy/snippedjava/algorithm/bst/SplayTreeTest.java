package cn.itscloudy.snippedjava.algorithm.bst;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SplayTreeTest {

    @Test
    void shouldRotateNodeToTopIfSearched() {
        SplayTree tree = new SplayTree();
        tree.insert(10, "10");
        tree.insert(5, "5");
        tree.insert(1, "1");

        SplayTree.STNode node = tree.search(5);
        assertEquals(5, node.i);

        SplayTree.STNode top = tree.top();
        assertEquals(5, top.i);
        assertEquals(10, top.right.i);
        assertEquals(1, top.left.i);
    }

}
