package cn.itscloudy.snippedjava.algorithm.bst;

import lombok.AllArgsConstructor;

public class BTree {
    private final int maxDegree;
    private final int maxValueIndex;
    private final int tmpValueIndex;
    private final int maxChildIndex;
    private final int tmpChildIndex;
    private final int overflowIndex;
    private final int splitMiddle;

    public BTreeNode top;

    public BTree(int maxDegree) {
        this.maxDegree = maxDegree;
        maxValueIndex = maxDegree - 2;
        tmpValueIndex = maxDegree - 1;
        maxChildIndex = maxDegree - 1;
        tmpChildIndex = maxDegree;
        overflowIndex = (maxDegree - 1) / 2;
        splitMiddle = overflowIndex + 1;
    }

    public BTreeNode top() {
        return top;
    }

    public void insert(int i, String words) {
        if (top == null) {
            top = new BTreeNode(i, words);
        } else {
            top.insert(new Value(i, words));
            if (top.needSplit()) {
                Value newTopValue = top.pourOverflow();
                BTreeNode newNode = top.split();

                BTreeNode newTop = new BTreeNode(newTopValue);
                newTop.children[0] = top;
                newTop.children[1] = newNode;
                top = newTop;
            }
        }
    }

    protected class BTreeNode {
        protected final Value[] values = new Value[maxDegree];
        protected final BTreeNode[] children = new BTreeNode[maxDegree + 1];

        private BTreeNode(int i, String words) {
            values[0] = new Value(i, words);
        }

        private BTreeNode(Value value) {
            values[0] = value;
        }

        private void insert(Value v) {
            if (children[0] == null) {
                insertToLeaf(v);
                return;
            }

            // get child branch
            int i = 0;
            BTreeNode child = children[0];
            for (; i < maxChildIndex; i++) {
                if (values[i] == null || v.i < values[i].i) {
                    child = children[i];
                    break;
                }
            }
            // insert into child branch
            child.insert(v);
            if (child.needSplit()) {
                // pour overflow
                v = child.pourOverflow();
                BTreeNode newChild = child.split();

                int j = i;
                Value tmpValue = v;
                while (j < tmpValueIndex) {
                    Value tmp1 = values[j];
                    values[j] = tmpValue;
                    tmpValue = tmp1;
                    j++;
                }

                j = i + 1;
                BTreeNode tmpNode = newChild;
                while (j < tmpChildIndex) {
                    BTreeNode tmp1 = children[j];
                    children[j] = tmpNode;
                    tmpNode = tmp1;
                    j++;
                }
            }
        }

        /**
         * Insert into current leaf
         *
         * @param nv New value
         */
        private void insertToLeaf(Value nv) {
            if (values[maxValueIndex] != null) {
                // overflow
                this.values[tmpValueIndex] = nv;
                sortLeafValues();
                return;
            }

            Value temp = null;
            for (int i = 0; i <= tmpValueIndex; i++) {
                if (temp == null) {
                    // finding the right index of nv
                    if (values[i] == null) {
                        // insert to the end
                        values[i] = nv;
                        return;
                    }
                    if (values[i].i == nv.i) {
                        // skip same value
                        return;
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

        private void sortLeafValues() {
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

        private boolean needSplit() {
            return values[tmpValueIndex] != null;
        }
    }

    @AllArgsConstructor
    protected static class Value {
        protected final int i;
        protected final String words;
    }

}
