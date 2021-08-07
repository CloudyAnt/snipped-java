package cn.itscloudy.snippedjava.algorithm.bst;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BTreeTest {

    @Test
    void shouldInsertToTreeWithDegree3() {
        BTree tree = new BTree(3);

        tree.insert(100, "100");
        tree.insert(50, "50");
        tree.insert(200, "200");

        assertValues(tree.top, 100, null, null);
        assertChildrenExistence(tree.top, 1, 1, 0);
        assertValues(tree.top.children[0], 50, null, null);
        assertChildrenExistence(tree.top.children[0], 0, 0, 0);
        assertValues(tree.top.children[1], 200, null, null);
        assertChildrenExistence(tree.top.children[1], 0, 0, 0);

        tree.insert(900, "900");
        tree.insert(500, "500");

        assertValues(tree.top, 100, 500, null);
        assertChildrenExistence(tree.top, 1, 1, 1);
        assertValues(tree.top.children[0], 50, null, null);
        assertChildrenExistence(tree.top.children[0], 0, 0, 0);
        assertValues(tree.top.children[1], 200, null, null);
        assertChildrenExistence(tree.top.children[1], 0, 0, 0);
        assertValues(tree.top.children[2], 900, null, null);
        assertChildrenExistence(tree.top.children[1], 0, 0, 0);
    }

    private void assertValues(BTree.BTreeNode node, Integer... values) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == null) {
                continue;
            }
            assertEquals(values[i], node.values[i].i);
        }
    }

    private void assertChildrenExistence(BTree.BTreeNode node, int... existences) {
        for (int i = 0; i < existences.length; i++) {
            if (existences[i] == 1) {
                assertNotNull(node.children[i]);
            } else {
                assertNull(node.children[i]);
            }
        }
    }
}
