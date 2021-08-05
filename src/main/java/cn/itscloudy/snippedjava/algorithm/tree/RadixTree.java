package cn.itscloudy.snippedjava.algorithm.tree;

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
        top.set(word, 0);
    }

    @Override
    public boolean check(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }
        return top.check(word, 0);
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

        private void set(String s, int from) {
            char c = s.charAt(from);
            int ci = c - 'a';
            if (children[ci] == null) {
                children[ci] = new RadixTreeNode();
                children[ci].setRestAsExtraChars(s, from + 1);
                children[ci].endOfWord = true;
            } else {
                children[ci].setByExtraChars(s, from + 1);
            }
        }

        private void setByExtraChars(String s, int from) {
            if (from == s.length()) {
                if (extraChars.length != 0) {
                    cut(1, new char[]{});
                }
                endOfWord = true;
            } else if (extraChars.length == 0) {
                set(s, from);
            } else {
                for (int i = 0; i < extraChars.length; ) {
                    char c = s.charAt(from);
                    char ec = extraChars[i];

                    if (c != ec) {
                        cut(i + 1, Arrays.copyOfRange(extraChars, 0, i));
                        break;
                    }

                    i++;
                    if (++from == s.length()) {
                        if (i != extraChars.length) {
                            cut(i + 1, Arrays.copyOfRange(extraChars, 0, i));
                        }
                        endOfWord = true;
                        break;
                    }
                }
                if (from != s.length()) {
                    set(s, from);
                }
            }
        }

        private void cut(int i, char[] newExtraChars) {
            RadixTreeNode newNode = new RadixTreeNode();
            newNode.children = children;
            newNode.extraChars = Arrays.copyOfRange(extraChars, i, extraChars.length);
            newNode.endOfWord = endOfWord;

            children = new RadixTreeNode[ALPHABET_SIZE];
            children[extraChars[0] - 'a'] = newNode;
            extraChars = newExtraChars;
            endOfWord = false;
        }

        public boolean check(String s, int from) {
            char c = s.charAt(from);
            int ci = c - 'a';
            if (children[ci] == null) {
                return false;
            }
            return children[ci].checkByExtraChars(s, from + 1);
        }

        public boolean checkByExtraChars(String s, int from) {
            if (from == s.length()) {
                if (extraChars.length == 0) {
                    return endOfWord;
                }
                return false;
            }
            for (int i = 0; i < extraChars.length; ) {
                char ec = extraChars[i];
                char c = s.charAt(from);
                if (c != ec) {
                    return check(s, from);
                }
                i++;
                if (++from == s.length()) {
                    if (i == extraChars.length) {
                        return endOfWord;
                    } else {
                        return false;
                    }
                }
            }
            return check(s, from);
        }
    }
}
