package cn.itscloudy.snippedjava.algorithm.trie;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TSTTest {

    @Test
    void shouldInsert() {
        TST tst = new TST();
        tst.insert("hey");
        tst.insert("hi");

        TST.TSTNode top = tst.top();
        assertEquals('h', top.c);
        assertFalse(top.endOfWord);
        assertNull(top.left);
        assertNull(top.right);

        assertEquals('e', top.equal.c);
        assertFalse(top.equal.endOfWord);
        assertNull(top.equal.left);

        assertEquals('y', top.equal.equal.c);
        assertTrue(top.equal.equal.endOfWord);
        assertNull(top.equal.equal.left);
        assertNull(top.equal.equal.right);

        assertEquals('i', top.equal.right.c);
        assertTrue(top.equal.right.endOfWord);
        assertNull(top.equal.right.left);
        assertNull(top.equal.right.right);
    }

    @Test
    void shouldSearch() {
        TST tst = new TST();

        tst.insert("hello");
        tst.insert("world");
        tst.insert("jack");
        tst.insert("rose");

        assertTrue(tst.check("hello"));
        assertTrue(tst.check("world"));
        assertTrue(tst.check("jack"));
        assertTrue(tst.check("rose"));
        assertFalse(tst.check("alpha"));
        assertFalse(tst.check("beta"));
        assertFalse(tst.check("gamma"));
        assertFalse(tst.check("delta"));
    }

    @Test
    void shouldThrow() {
        TST tst = new TST();
        tst.insert("");

        assertThrows(RuntimeException.class, () -> tst.insert("!@#"));
        assertThrows(RuntimeException.class, () -> tst.check("!@#"));
    }
}
