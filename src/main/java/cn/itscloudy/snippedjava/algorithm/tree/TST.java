package cn.itscloudy.snippedjava.algorithm.tree;

/**
 * Ternary Search Tree
 */
public class TST {

    private TSTNode top;
    private static final char ENDING = '$';

    public TSTNode top() {
        return top;
    }

    public void insert(String word) {
        if (word == null || word.length() == 0) {
            return;
        }
        if (top == null) {
            top = new TSTNode(word.charAt(0));
        }

        TSTNode node = top;
        char c = charAt(word, 0);
        for (int i = 0; ; ) {
            int diff = Character.compare(c, node.c);
            if (diff == 0) {
                if ((c = charAt(word, ++i)) == ENDING) {
                    break;
                } else {
                    if (node.equal == null) {
                        node.equal = new TSTNode(c);
                    }
                    node = node.equal;
                }
            } else if (diff < 0) {
                if (node.left == null) {
                    node.left = new TSTNode(c);
                }
                node = node.left;
            } else {
                if (node.right == null) {
                    node.right = new TSTNode(c);
                }
                node = node.right;
            }
        }
        node.endOfWord = true;
    }

    public boolean check(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }

        TSTNode node = top;
        char c = charAt(word, 0);
        for (int i = 0; ; ) {
            if (node == null) {
                return false;
            }
            int diff = Character.compare(c, node.c);
            if (diff == 0) {
                if ((c = charAt(word, ++i)) == ENDING) {
                    break;
                } else {
                    node = node.equal;
                }
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

    private static int compare(char a, char b) {
        return Integer.compare(Character.compare(a, b), 0);
    }

    private static void assertCharValid(char c) {
        if (c < 'a' || c > 'z') {
            throw new RuntimeException("Invalid char: " + c);
        }
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
    }
}
