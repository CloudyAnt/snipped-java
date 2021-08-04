package cn.itscloudy.snippedjava.algorithm.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrieTest {

    @Test
    void shouldInsertWords() {
        Trie trie = new Trie();

        trie.insert("hey");
        trie.insert("hi");

        int hi = 'h' - 'a';
        int ii = 'i' - 'a';
        int ei = 'e' - 'a';
        int yi = 'y' - 'a';
        int zi = 'z' - 'a';

        Trie.TrieNode top = trie.top();

        // check not-nulls and the field endOfWord
        assertNotNull(top.children[hi].children[ii]);
        assertTrue(top.children[hi].children[ii].endOfWord);
        assertNotNull(top.children[hi].children[ei].children[yi]);
        assertTrue(top.children[hi].children[ei].children[yi].endOfWord);
        assertFalse(top.children[hi].children[ei].endOfWord);


        // check nulls
        int i = 0;
        for (; i < hi; i++) {
            assertNull(top.children[i]);
        }
        for (i++; i <= zi; i++) {
            assertNull(top.children[i]);
        }
        for (i = 0; i < ei; i++) {
            assertNull(top.children[hi].children[i]);
        }
        for (i++; i < ii; i++) {
            assertNull(top.children[hi].children[i]);
        }
        for (i++; i <= zi; i++) {
            assertNull(top.children[hi].children[i]);
        }
        for (i = 0; i < yi; i++) {
            assertNull(top.children[hi].children[ei].children[i]);
        }
        for (i++; i <= zi; i++) {
            assertNull(top.children[hi].children[ei].children[i]);
        }
    }

    @Test
    void shouldSearch() {
        Trie trie = new Trie();

        trie.insert("hello");
        trie.insert("world");
        trie.insert("jack");
        trie.insert("rose");

        assertTrue(trie.check("hello"));
        assertTrue(trie.check("world"));
        assertTrue(trie.check("jack"));
        assertTrue(trie.check("rose"));
        assertFalse(trie.check("alpha"));
        assertFalse(trie.check("beta"));
        assertFalse(trie.check("gamma"));
        assertFalse(trie.check("delta"));
    }

    @Test
    void shouldThrow() {
        Trie trie = new Trie();

        assertThrows(RuntimeException.class, () -> trie.insert("!@#"));
        assertThrows(RuntimeException.class, () -> trie.check("!@#"));
    }
}
