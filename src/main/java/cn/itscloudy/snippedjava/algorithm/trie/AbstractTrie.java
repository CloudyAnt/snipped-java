package cn.itscloudy.snippedjava.algorithm.trie;

/**
 * A tree data structure used for locating specific keys from within a set
 */
public abstract class AbstractTrie {

    public abstract void insert(String word);

    public abstract boolean check(String word);

    protected static void assertCharValid(char c) {
        if (c < 'a' || c > 'z') {
            throw new RuntimeException("Invalid char: " + c);
        }
    }
}
