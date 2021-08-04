package cn.itscloudy.snippedjava.algorithm.tree;

/**
 * Ternary Search Tree
 */
public class TST {

    private TSTNode top;

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
        int status = -2;
        char c = word.charAt(0);
        assertCharValid(c);
        for (int i = 0; ; ) {
            switch (status) {
                case 1:
                    if (node.right == null) {
                        node.right = new TSTNode(c);
                    }
                    node = node.right;
                    break;
                case -1:
                    if (node.left == null) {
                        node.left = new TSTNode(c);
                    }
                    node = node.left;
                    break;
                case 0:
                    if (node.equal == null) {
                        node.equal = new TSTNode(c);
                    }
                    node = node.equal;
                    break;
            }
            status = compare(c, node.c);
            if (status == 0) {
                i++;
                if (i < word.length()) {
                    c = charAt(word, i);
                } else {
                    break;
                }
            }
        }
        node.endOfWord = true;
    }

    public boolean check(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }

        TSTNode node = top;
        int status = -2;
        char c = word.charAt(0);
        assertCharValid(c);
        for (int i = 0; ; ) {
            switch (status) {
                case 1:
                    node = node.right;
                    break;
                case -1:
                    node = node.left;
                    break;
                case 0:
                    node = node.equal;
                    break;
            }
            if (node == null) {
                return false;
            }
            status = compare(c, node.c);
            if (status == 0) {
                i++;
                if (i < word.length()) {
                    c = charAt(word, i);
                } else {
                    break;
                }
            }
        }
        return node.endOfWord;
    }

    private char charAt(String word, int i) {
        char c;
        c = word.charAt(i);
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
