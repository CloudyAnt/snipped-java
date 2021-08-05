package cn.itscloudy.snippedjava.algorithm.tree;

/**
 * Prefix tree
 */
public class Trie extends AbstractTrie {
    private static final int ALPHABET_SIZE = 26;

    private final TrieNode top;

    public Trie() {
        this.top = new TrieNode();
    }

    public TrieNode top() {
        return top;
    }

    @Override
    public void insert(String word) {
        TrieNode node = top;
        int i = 0;
        for (; i < word.length() - 1; i++) {
            node = node.set(word.charAt(i));
        }
        node.end(word.charAt(i));
    }

    @Override
    public boolean check(String word) {
        TrieNode node = top;
        int i = 0;
        for (; i < word.length(); i++) {
            node = node.get(word.charAt(i));
            if (node == null) {
                return false;
            }
        }
        return node.endOfWord;
    }

    protected static class TrieNode {
        protected final TrieNode[] children;
        protected boolean endOfWord;

        private TrieNode() {
            this.children = new TrieNode[ALPHABET_SIZE];
        }

        private TrieNode set(char c) {
            assertCharValid(c);
            int index = c - 'a';
            if (children[index] == null) {
                children[index] = new TrieNode();
            }
            return children[index];
        }

        private TrieNode get(char c) {
            assertCharValid(c);
            return children[c - 'a'];
        }

        private void end(char c) {
            assertCharValid(c);
            int index = c - 'a';
            if (children[index] == null) {
                children[index] = new TrieNode();
            }
            children[index].endOfWord = true;
        }
    }
}
