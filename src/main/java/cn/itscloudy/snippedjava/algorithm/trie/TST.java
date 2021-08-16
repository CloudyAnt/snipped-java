package cn.itscloudy.snippedjava.algorithm.trie;

/**
 * Ternary Search Tree
 */
public class TST extends AbstractTrie {

    private TSTNode top;
    private static final char ENDING = '$';

    public TSTNode top() {
        return top;
    }

    @Override
    public void insert(String word) {
        if (word == null || word.length() == 0) {
            return;
        }
        if (top == null) {
            top = new TSTNode(word.charAt(0));
        }
        TSTNode node = top;
        char c = charAt(word, 0);
        int i = 0;
        while (true) {
            int diff = Character.compare(c, node.c);
            if (diff == 0) {
                if ((c = charAt(word, ++i)) == ENDING) {
                    break;
                }
                node = node.getEqual(c);
            } else if (diff < 0) {
                node = node.getLeft(c);
            } else {
                node = node.getRight(c);
            }
        }
        node.endOfWord = true;
    }

    @Override
    public boolean check(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }

        TSTNode node = top;
        int i = 0;
        char c = charAt(word, 0);
        while (true) {
            if (node == null) {
                return false;
            }
            int diff = Character.compare(c, node.c);
            if (diff == 0) {
                if ((c = charAt(word, ++i)) == ENDING) {
                    break;
                }
                node = node.equal;
            } else if (diff < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node.endOfWord;
    }

    private char charAt(String word, int i) {
        if (i >= word.length()) {
            return ENDING;
        }
        char c = word.charAt(i);
        assertCharValid(c);
        return c;
    }

    protected static class TSTNode {
        protected final char c;
        protected TSTNode left;
        protected TSTNode right;
        protected TSTNode equal;
        protected boolean endOfWord;

        private TSTNode(char c) {
            assertCharValid(c);
            this.c = c;
        }

        public TSTNode getEqual(char c) {
            if (equal == null) {
                equal = new TSTNode(c);
            }
            return equal;
        }

        public TSTNode getLeft(char c) {
            if (left == null) {
                left = new TSTNode(c);
            }
            return left;
        }

        public TSTNode getRight(char c) {
            if (right == null) {
                right = new TSTNode(c);
            }
            return right;
        }
    }
}
