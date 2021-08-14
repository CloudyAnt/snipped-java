package cn.itscloudy.snippedjava.algorithm.bst;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BPlusTreeTest {

    @Test
    void shouldInsertTo3DegreeTree() {
        BPlusTree tree = new BPlusTree(3);

        tree.insert(100, "100");
        tree.insert(50, "50");
        assert3DegreeLeafNode(tree.top, 50, 100);

        tree.insert(200, "200");
        tree.insert(200, "200");
        assert3DegreeIndexNode(tree.top, 100, null, 0);
        BPlusTree.IndexNode top = (BPlusTree.IndexNode) tree.top;
        assert3DegreeLeafNode(top.children[0], 50, null);
        assert3DegreeLeafNode(top.children[1], 100, 200);

        tree.insert(150, "150");
        tree.insert(130, "130");
        assert3DegreeIndexNode(tree.top, 100, 150, 1);
        top = (BPlusTree.IndexNode) tree.top;
        assert3DegreeLeafNode(top.children[0], 50, null);
        assert3DegreeLeafNode(top.children[1], 100, 130);
        assert3DegreeLeafNode(top.children[2], 150, 200);

        tree.insert(125, "125");
        assert3DegreeIndexNode(tree.top, 125, null, 0);
        top = (BPlusTree.IndexNode) tree.top;
        assert3DegreeIndexNode(top.children[0], 100, null, 0);
        assert3DegreeIndexNode(top.children[1], 125, 150, 0);
        BPlusTree.IndexNode child0 = (BPlusTree.IndexNode) top.children[0];
        assert3DegreeLeafNode(child0.children[0], 50, null);
        assert3DegreeLeafNode(child0.children[1], 100, null);
        BPlusTree.IndexNode child1 = (BPlusTree.IndexNode) top.children[1];
        assert3DegreeLeafNode(child1.children[0], 125, 130);
        assert3DegreeLeafNode(child1.children[1], 150, 200);
    }

    @Test
    void shouldIterate3DegreeTree() {
        BPlusTree tree = new BPlusTree(3);

        tree.insert(100, "100");
        tree.insert(50, "50");
        tree.insert(200, "200");
        tree.insert(150, "150");
        tree.insert(130, "130");
        tree.insert(125, "125");

        int i = 0;
        int[] is1 = new int[]{50, 100, 125, 130, 150, 200};
        for (BPlusTree.Value value : tree) {
            assertEquals(is1[i], value.i);
            i++;
        }
    }

    private void assert3DegreeLeafNode(BPlusTree.Node testNode, Integer vk1, Integer vk2) {
        assertTrue(testNode instanceof BPlusTree.LeafNode);
        BPlusTree.LeafNode node = (BPlusTree.LeafNode) testNode;

        assertEquals(vk1, node.values[0].i);

        if (vk2 == null) {
            assertNull(node.values[1]);
        } else {
            assertEquals(vk2, node.values[1].i);
        }
    }

    private void assert3DegreeIndexNode(BPlusTree.Node testNode, Integer key1, Integer key2, Integer child3) {
        assertTrue(testNode instanceof BPlusTree.IndexNode);
        BPlusTree.IndexNode node = (BPlusTree.IndexNode) testNode;

        assertEquals(key1, node.keys[0]);
        assertEquals(key2, node.keys[1]);
        assertNotNull(node.children[0]);
        assertNotNull(node.children[1]);
        assertEquals(child3 == 0, node.children[2] == null);
    }
}
