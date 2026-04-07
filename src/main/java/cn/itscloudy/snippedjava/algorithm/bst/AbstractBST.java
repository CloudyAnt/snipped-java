package cn.itscloudy.snippedjava.algorithm.bst;

import java.util.ArrayList;
import java.util.List;

/**
 * Binary Search Tree
 */
public abstract class AbstractBST<N extends AbstractBSTNode<N>> {

    public abstract N top();

    public abstract void insert(int i, String words);

    protected static <N extends AbstractBSTNode<N>> void insert(N node, N parent) {
        N current = parent;
        while (true) {
            if (current.leftMightContain(node)) {
                if (current.left == null) {
                    current.left = node;
                    break;
                } else {
                    current = current.left;
                }
            } else {
                if (current.right == null) {
                    current.right = node;
                    break;
                } else {
                    current = current.right;
                }
            }
        }
    }

    public abstract void delete(int i);

    public N search(int i) {
        return search(i, top());
    }

    private N search(int i, N parent) {
        N current = parent;
        while (current != null) {
            if (i == current.i) {
                return current;
            }
            if (current.leftMightContain(i)) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    /**
     * Right rotate and get the new parent
     *
     * @param parent The node of rotation based on
     * @return The new parent
     */
    protected N rightRotate(N parent) {
        N l = parent.left;
        N lr = l.right;
        l.right = parent;
        parent.left = lr;
        return l;
    }

    /**
     * Left rotate and get the new parent
     *
     * @param parent The node of rotation based on
     * @return The new parent
     */
    protected N leftRotate(N parent) {
        N r = parent.right;
        N rl = r.left;
        r.left = parent;
        parent.right = rl;
        return r;
    }

    public void print() {
        new Printer<>(this).print();
    }

    private static class Printer<N extends AbstractBSTNode<N>> {

        private final ArrayList<String> cellStrings;
        private final N top;
        private final int cellWidth;
        private final int width;
        private final int height;

        public Printer(AbstractBST<N> bst) {
            this.top = bst.top();
            cellWidth = cellWidthOf(top);
            height = heightOf(top);
            width = height == 0 ? 0 : cellWidth * (1 << (height - 1));
            int capacity = height == 0 ? 0 : (1 << height) - 1;
            this.cellStrings = new ArrayList<>(capacity);
        }

        private void print() {
            collectStrings(top, 0);
            for (int i = 0; i < cellStrings.size(); i++) {
                if (cellStrings.get(i) == null) {
                    cellStrings.set(i, cellString(""));
                }
            }

            int level = 0;
            while (level < height) {
                int levelStart = (1 << level) - 1;
                int levelEnd = (1 << (level + 1)) - 1;

                print(cellStrings.subList(levelStart, levelEnd), width);
                System.out.println();
                level++;
            }
        }

        private void collectStrings(N node, int index) {
            if (node == null) {
                setCellString(index, null);
                return;
            }

            String cellString = cellString(node.getWords());
            setCellString(index, cellString);

            collectStrings(node.left, index * 2 + 1);
            collectStrings(node.right, index * 2 + 2);
        }

        private void setCellString(int index, String string) {
            if (index >= cellStrings.size()) {
                cellStrings.ensureCapacity(index + 1);
                for (int i = cellStrings.size(); i <= index; i++) {
                    cellStrings.add(null);
                }
            }
            cellStrings.set(index, string);
        }

        private String cellString(String words) {
            int startIndex = (cellWidth - words.length()) / 2;
            int leftSpaces = startIndex;
            int rightSpaces = cellWidth - (startIndex + words.length());
            return " ".repeat(leftSpaces) + words + " ".repeat(rightSpaces);
        }

        private void print(List<String> words, int len) {
            int wordsLen = words.size() * cellWidth;
            int gapSize = (len - wordsLen) / (words.size() + 1);
            String gap = " ".repeat(gapSize);
            StringBuilder sb = new StringBuilder(len);
            for (String word : words) {
                sb.append(gap);
                sb.append(word);
            }
            System.out.print(sb);
        }

        private int heightOf(N n) {
            if (n == null) {
                return 0;
            }
            return Math.max(heightOf(n.left) + 1, heightOf(n.right) + 1);
        }

        private int cellWidthOf(N n) {
            if (n == null) {
                return 0;
            }
            int max = n.getWords().length() + 2;
            max = Math.max(max, cellWidthOf(n.left));
            max = Math.max(max, cellWidthOf(n.right));
            return max;
        }
    }
}
