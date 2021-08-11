package cn.itscloudy.snippedjava.algorithm.bst;

import lombok.AllArgsConstructor;

/**
 * B+ Tree
 */
public class BPlusTree {
    private final int childrenArraySize;
    private final int maxChildIndex;
    private final int valueArraySize;
    private final int maxValueIndex;
    private final int nextLeafIndex;

    private Node top;

    public BPlusTree(int maxDegree) {
        childrenArraySize = maxDegree;
        maxChildIndex = childrenArraySize - 1;
        valueArraySize = maxDegree + 1;
        maxValueIndex = valueArraySize - 2;
        nextLeafIndex = valueArraySize - 1;
    }

    public void insert(int i, String words) {
        Value value = new Value(i, words);
        if (top == null) {
            top = new LeafNode(value);
        } else {
            top.insert(value);
        }
    }

    public Value search(int i) {
        if (top == null) {
            return null;
        }
        return top.search(i);
    }

    private abstract static class Node {
        protected abstract void insert(Value value);

        protected abstract Value search(int i);

        protected abstract boolean needSplit();

        protected abstract Node split();

        protected abstract Value pourOverflow();
    }

    private class IndexNode extends Node {
        protected final Node[] children;
        protected final Integer[] keys;

        public IndexNode() {
            this.children = new Node[childrenArraySize];
            this.keys = new Integer[childrenArraySize + 1];
        }

        @Override
        protected void insert(Value v) {
            // get child branch
            int i = 0;
            Node child = children[0];
            for (; i < maxChildIndex; i++) {
                if (keys[i] == null || v.i < keys[i]) {
                    child = children[i];
                    break;
                }
            }

            // insert into child
            child.insert(v);
            if (child.needSplit()) {
                Node split = child.split();
            }
        }

        @Override
        protected Value search(int i) {
            return null;
        }

        @Override
        protected boolean needSplit() {
            return false;
        }

        @Override
        protected Node split() {
            return null;
        }

        @Override
        protected Value pourOverflow() {
            return null;
        }
    }

    private class LeafNode extends Node {
        protected final Value[] values;

        public LeafNode(Value value) {
            this.values = new Value[valueArraySize];
            this.values[0] = value;
        }

        @Override
        protected void insert(Value value) {

        }

        @Override
        protected Value search(int i) {
            return null;
        }

        @Override
        protected boolean needSplit() {
            return false;
        }

        @Override
        protected Node split() {
            return null;
        }

        @Override
        protected Value pourOverflow() {
            return null;
        }
    }

    @AllArgsConstructor
    protected static class Value {
        protected final int i;
        protected final String words;
    }

}
