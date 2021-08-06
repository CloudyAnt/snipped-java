package cn.itscloudy.snippedjava.algorithm.trie;

import java.util.Arrays;

/**
 * A space-optimized trie
 */
public class RadixTree extends AbstractTrie {
    private static final int ALPHABET_SIZE = 26;

    private final RadixTreeNode top;

    public RadixTree() {
        this.top = new RadixTreeNode();
    }

    protected RadixTreeNode top() {
        return top;
    }

    @Override
    public void insert(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }
        top.setChild(word, 0);
    }

    @Override
    public boolean check(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }
        return top.checkChild(word, 0);
    }

    protected static class RadixTreeNode {
        protected RadixTreeNode[] children;
        protected boolean endOfWord;
        protected char[] extraChars;

        private RadixTreeNode() {
            resetChildren();
            extraChars = new char[]{};
        }

        private void setRestAsExtraChars(String word, int from) {
            if (from < word.length()) {
                extraChars = new char[word.length() - from];
                for (int i = 0; i < extraChars.length; i++) {
                    extraChars[i] = word.charAt(from++);
                }
            }
        }

        private void resetChildren() {
            this.children = new RadixTreeNode[ALPHABET_SIZE];
        }

        private void setChild(String s, int from) {
            char c = s.charAt(from);
            int ci = c - 'a';
            if (children[ci] == null) {
                children[ci] = new RadixTreeNode();
                children[ci].setRestAsExtraChars(s, from + 1);
                children[ci].endOfWord = true;
            } else {
                children[ci].set(s, from + 1);
            }
        }

        private void set(String s, int from) {
            for (int i = 0; i < extraChars.length; i++, from++) {
                if ((from == s.length()) || (s.charAt(from) != extraChars[i])) {
                    cut(i);
                    break;
                }
            }
            if (from == s.length()) {
                endOfWord = true;
            } else {
                setChild(s, from);
            }
        }

        private void cut(int i) {
            if (extraChars.length == 0) {
                return;
            }
            RadixTreeNode newNode = new RadixTreeNode();
            newNode.children = children;
            newNode.extraChars = Arrays.copyOfRange(extraChars, i + 1, extraChars.length);
            newNode.endOfWord = endOfWord;

            children = new RadixTreeNode[ALPHABET_SIZE];
            children[extraChars[i] - 'a'] = newNode;
            extraChars = Arrays.copyOfRange(extraChars, 0, i);
            endOfWord = false;
        }

        public boolean checkChild(String s, int from) {
            char c = s.charAt(from);
            int ci = c - 'a';
            if (children[ci] == null) {
                return false;
            }
            return children[ci].check(s, from + 1);
        }

        public boolean check(String s, int from) {
            int i = 0;
            for (; i < extraChars.length && from < s.length(); i++, from++) {
                if (s.charAt(from) != extraChars[i]) {
                    return false;
                }
            }
            if (from == s.length()) {
                return i == extraChars.length && endOfWord;
            } else {
                return checkChild(s, from);
            }
        }
    }
}
