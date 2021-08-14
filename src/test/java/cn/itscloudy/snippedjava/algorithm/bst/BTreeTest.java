package cn.itscloudy.snippedjava.algorithm.bst;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BTreeTest {

    @Test
    void shouldInsertTo3DegreeTree() {
        BTree tree = new BTree(3);

        tree.insert(100, "100");
        tree.insert(50, "50");
        assertValues(tree.top, 50, 100);
        assertChildrenExistence(tree.top, 0, 0, 0);

        tree.insert(200, "200");
        tree.insert(200, "200");

        assertValues(tree.top, 100, null);
        assertChildrenExistence(tree.top, 1, 1, 0);
        assertValues(tree.top.children[0], 50, null);
        assertChildrenExistence(tree.top.children[0], 0, 0, 0);
        assertValues(tree.top.children[1], 200, null);
        assertChildrenExistence(tree.top.children[1], 0, 0, 0);

        tree.insert(900, "900");
        tree.insert(500, "500");
        tree.insert(500, "500");

        assertValues(tree.top, 100, 500);
        assertChildrenExistence(tree.top, 1, 1, 1);
        assertValues(tree.top.children[0], 50, null);
        assertChildrenExistence(tree.top.children[0], 0, 0, 0);
        assertValues(tree.top.children[1], 200, null);
        assertChildrenExistence(tree.top.children[1], 0, 0, 0);
        assertValues(tree.top.children[2], 900, null);
        assertChildrenExistence(tree.top.children[1], 0, 0, 0);

        tree.insert(400, "400");
        tree.insert(300, "300");
        tree.insert(300, "300");

        assertValues(tree.top, 300, null);
        assertChildrenExistence(tree.top, 1, 1, 0);
        assertValues(tree.top.children[0], 100, null);
        assertChildrenExistence(tree.top.children[0], 1, 1, 0);
        assertValues(tree.top.children[1], 500, null);
        assertChildrenExistence(tree.top.children[1], 1, 1, 0);
        assertValues(tree.top.children[0].children[0], 50, null);
        assertChildrenExistence(tree.top.children[0].children[0], 0, 0, 0);
        assertValues(tree.top.children[0].children[1], 200, null);
        assertChildrenExistence(tree.top.children[0].children[1], 0, 0, 0);
        assertValues(tree.top.children[1].children[0], 400, null);
        assertChildrenExistence(tree.top.children[1].children[0], 0, 0, 0);
        assertValues(tree.top.children[1].children[1], 900, null);
        assertChildrenExistence(tree.top.children[1].children[1], 0, 0, 0);
    }

    private void assertValues(BTree.Node node, Integer... values) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == null) {
                assertNull(node.values[i]);
            } else {
                assertEquals(values[i], node.values[i].i);
            }
        }
    }

    private void assertChildrenExistence(BTree.Node node, int... childExistence) {
        for (int i = 0; i < childExistence.length; i++) {
            if (childExistence[i] == 1) {
                assertNotNull(node.children[i]);
            } else {
                assertNull(node.children[i]);
            }
        }
    }

    @Test
    void shouldSearch3DegreeTree() {
        BTree tree = new BTree(3);

        tree.insert(100, "100");
        tree.insert(50, "50");
        tree.insert(200, "200");
        tree.insert(900, "900");
        tree.insert(500, "500");

        assertEquals("100", tree.search(100).words);
        assertEquals("50", tree.search(50).words);
        assertEquals("200", tree.search(200).words);
        assertEquals("900", tree.search(900).words);
        assertEquals("500", tree.search(500).words);
    }

    @Test
    void shouldInsertTo4DegreeTree() {
        BTree tree = new BTree(4);

        tree.insert(100, "100");
        tree.insert(50, "50");
        tree.insert(200, "200");

        assertValues(tree.top, 50, 100, 200);
        assertChildrenExistence(tree.top, 0, 0, 0);

        tree.insert(300, "300");
        assertValues(tree.top, 100, null, null);
        assertChildrenExistence(tree.top, 1, 1, 0);
        assertValues(tree.top.children[0], 50, null, null);
        assertChildrenExistence(tree.top.children[0], 0, 0, 0);
        assertValues(tree.top.children[1], 200, 300, null);
        assertChildrenExistence(tree.top.children[1], 0, 0, 0);
    }

}
