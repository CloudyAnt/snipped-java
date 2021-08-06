package cn.itscloudy.snippedjava.algorithm.bst;

import lombok.AllArgsConstructor;

public class BTree {
    private final int maxDegree;

    public BTreeNode top;

    public BTree(int maxDegree) {
        this.maxDegree = maxDegree;
    }

    public BTreeNode top() {
        return top;
    }

    public void insert(int i, String words) {
        if (top == null) {
            top = new BTreeNode(i, words);
        }
        top.insert(new Value(i, words));
    }

    public void delete(int i) {

    }

    protected class BTreeNode {
        private final int maxValueIndex = maxDegree - 2;
        private final int tmpValueIndex = maxDegree - 1;
        private final int splitMiddle = maxDegree / 2;
        private final int overflowIndex = (maxDegree - 1) / 2;
        private Value[] values = new Value[maxDegree];
        private BTreeNode[] children = new BTreeNode[maxDegree];

        private BTreeNode(int i, String words) {
            values[0] = new Value(i, words);
        }

        private BTreeNode(Value value) {
            values[0] = value;
        }

        private boolean insert(Value nv) {
            if (children[0] == null) {
                // insert into leaf
                return insertToThis(nv);
            } else {
                // get child branch
                int i = 0;
                BTreeNode child = children[0];
                for (; i < maxDegree; i++) {
                    if (values[i] == null || nv.i < values[i].i) {
                        child = children[i];
                    }
                }
                // insert into child branch
                boolean childNeedSplit = child.insert(nv);
                if (childNeedSplit) {
                    // pour overflow to parent
                    Value overflow = child.pourOverflow();
                    BTreeNode rightChild = child.split();
                    // ???
                    return insertToThis(overflow);
                }
                return false;
            }
        }

        /**
         * Insert into current node
         *
         * @param nv New value
         * @return Whether this node need to be split
         */
        private boolean insertToThis(Value nv) {
            if (values[maxValueIndex] != null) {
                // overflow
                this.values[tmpValueIndex] = nv;
                sortValues();
                return true;
            }

            Value temp = null;
            for (int i = 0; i < maxValueIndex; i++) {
                if (temp == null) {
                    // finding the right index of nv
                    if (values[i] == null) {
                        // insert to the end
                        values[i] = nv;
                        return false;
                    }
                    if (values[i].i == nv.i) {
                        // skip same value
                        return false;
                    }
                    if (values[i].i > nv.i) {
                        // mark to start swap
                        temp = values[i];
                        values[i] = nv;
                    }
                } else {
                    Value temp1 = values[i];
                    values[i] = temp;
                    temp = temp1;
                    if (temp == null) {
                        break;
                    }
                }
            }
            return false;
        }

        /**
         * Split current node and return the new node (right of parent)
         *
         * @return new node
         */
        private BTreeNode split() {
            BTreeNode right = new BTreeNode(values[splitMiddle]);
            values[splitMiddle] = null;
            for (int vi = maxValueIndex, rvi = 1; vi > splitMiddle; vi--, rvi++) {
                right.values[rvi] = values[vi];
                right.children[rvi] = children[vi];
                values[vi] = null;
                children[vi] = null;
            }
            return right;
        }

        private Value pourOverflow() {
            Value overflow = values[overflowIndex];
            values[overflowIndex] = null;
            return overflow;
        }

        // ???
        private void sortValues() {
            for (int i = 1; i < values.length; i++) {
                for (int j = 0; j < values.length - i; j++) {
                    if (values[j].i > values[j + 1].i) {
                        Value temp = values[j];
                        values[j] = values[j + 1];
                        values[j + 1] = temp;
                    }
                }
            }
        }
    }

    @AllArgsConstructor
    private static class Value {
        private final int i;
        private final String words;
    }

}
