package cn.itscloudy.snippedjava.algorithm.trie;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RadixTreeTest {

    @Test
    void shouldInsert() {
        RadixTree tree = new RadixTree();

        tree.insert("hey");
        RadixTree.RadixTreeNode top = tree.top();
        assertNode(top, false, null);
        assertNode(top.children[7], true, new char[]{'e', 'y'});

        tree.insert("hi");
        assertNode(top, false, null);
        assertNode(top.children[7], false, null);
        assertNode(top.children[7].children[4], true, new char[]{'y'});
        assertNode(top.children[7].children[8], true, null);

        tree.insert("he");
        assertNode(top, false, null);
        assertNode(top.children[7], false, null);
        assertNode(top.children[7].children[4], true, null);
        assertNode(top.children[7].children[4].children[24], true, null);
        assertNode(top.children[7].children[8], true, null);
    }

    private void assertNode(RadixTree.RadixTreeNode node, boolean endOfWord, char[] extraChars,
                            char... existentChildren) {
        assertNotNull(node);
        assertEquals(endOfWord, node.endOfWord);

        if (extraChars == null) {
            assertEquals(0, node.extraChars.length);
        } else {
            assertArrayEquals(node.extraChars, extraChars);
        }
        if (existentChildren.length > 0) {
            RadixTree.RadixTreeNode[] children = node.children;
            assertNotNull(children);

            ArrayList<Integer> cis = new ArrayList<>();
            for (char c : existentChildren) {
                cis.add(c - 'a');
            }
            for (int i = 0; i < children.length; i++) {
                if (cis.contains(i)) {
                    assertNull(children[i]);
                } else {
                    assertNotNull(children[i]);
                }
            }
        }
    }

    @Test
    void shouldCheck() {
        RadixTree tree = new RadixTree();

        tree.insert("hey");
        assertTrue(tree.check("hey"));
        assertFalse(tree.check("hi"));
        assertFalse(tree.check("he"));
        assertFalse(tree.check("hello"));

        tree.insert("hi");
        assertTrue(tree.check("hey"));
        assertTrue(tree.check("hi"));
        assertFalse(tree.check("he"));
        assertFalse(tree.check("hello"));

        tree.insert("he");
        assertTrue(tree.check("hey"));
        assertTrue(tree.check("hi"));
        assertTrue(tree.check("he"));
        assertFalse(tree.check("hello"));
    }
}
