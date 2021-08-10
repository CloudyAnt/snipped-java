package cn.itscloudy.snippedjava.algorithm.bst;

import lombok.AllArgsConstructor;

/**
 * B+ Tree
 */
public class BPlusTree {
    private final int indexArraySize;
    private final int valueArraySize;
    private final int nextLeafIndex;

    private Node top;

    public BPlusTree(int maxDegree) {
        indexArraySize = maxDegree;
        valueArraySize = maxDegree + 1;
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
    }

    private class IndexNode extends Node {
        protected final Node[] children;
        protected final int[] is;

        public IndexNode() {
            this.children = new Node[indexArraySize];
            this.is = new int[indexArraySize + 1];
        }

        @Override
        protected void insert(Value value) {

        }

        @Override
        protected Value search(int i) {
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
    }

    @AllArgsConstructor
    protected static class Value {
        protected final int i;
        protected final String words;
    }

}
