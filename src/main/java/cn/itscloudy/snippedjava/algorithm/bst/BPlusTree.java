package cn.itscloudy.snippedjava.algorithm.bst;

import lombok.AllArgsConstructor;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * B+ Tree
 */
public class BPlusTree implements Iterable<BPlusTree.Value> {
    private final int maxValueIndex;
    private final int maxKeyIndex;
    private final int maxChildIndex;
    private final int overflowIndex;

    protected Node top;

    public BPlusTree(int maxDegree) {
        if (maxDegree < 3) {
            throw new RuntimeException("MaxDegree can't be less than 3");
        }

        maxValueIndex = maxDegree - 2;
        maxKeyIndex = maxDegree - 2;
        maxChildIndex = maxDegree - 1;
        overflowIndex = (maxValueIndex + 1) / 2;
    }

    public Node top() {
        return top;
    }

    public void insert(int i, String words) {
        Value value = new Value(i, words);
        if (top == null) {
            LeafNode newTop = new LeafNode();
            newTop.values[0] = value;
            top = newTop;
            return;
        }
        Overflow overflow = null;
        try {
            overflow = top.insert(value);
        } catch (DuplicateKeyException ignore) {
            // ignore duplicate key
        }

        if (overflow != null) {
            IndexNode newTop = new IndexNode();
            newTop.keys[0] = overflow.key;
            newTop.children[0] = top;
            newTop.children[1] = overflow.newNode;
            this.top = newTop;
        }
    }

    public Value search(int i) {
        return top == null ? null : top.search(i);
    }

    @Override
    public Iterator<Value> iterator() {
        Node node = top;
        while (!(node instanceof LeafNode)) {
            node = ((IndexNode) node).children[0];
        }
        return new Itr((LeafNode) node);
    }

    private class Itr implements Iterator<Value> {
        private LeafNode n;
        private int i;

        public Itr(LeafNode n) {
            this.n = n;
            i = 0;
        }

        @Override
        public boolean hasNext() {
            return i <= maxValueIndex || n.next != null;
        }

        @Override
        public Value next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (i <= maxValueIndex && n.values[i] != null) {
                return n.values[i++];
            }
            if (n.next != null) {
                n = n.next;
                i = 0;
            }
            return n.values[i++];
        }
    }

    protected abstract static class Node {
        protected abstract Overflow insert(Value value);

        protected abstract Value search(int key);
    }

    protected class IndexNode extends Node {
        protected final Node[] children = new Node[maxChildIndex + 1];
        protected final Integer[] keys = new Integer[maxKeyIndex + 1];

        @Override
        protected Overflow insert(Value v) {
            // get child branch
            int i = 0;
            Node child = children[0];
            for (; i <= maxChildIndex; i++) {
                if (keys[i] == null || v.i < keys[i]) {
                    child = children[i];
                    break;
                }
                if (v.i == keys[i]) {
                    // skip same value
                    throw new DuplicateKeyException();
                }
            }
            // insert into child
            Overflow overflow = child.insert(v);
            if (overflow == null) {
                return null;
            }
            Node extrudedNode = insertToAscArr(children, overflow.newNode, i + 1);
            Integer extrudedKey = insertToAscArr(keys, overflow.key, i);
            if (extrudedKey == null) {
                return null;
            }
            Overflow newOV = new Overflow();
            newOV.key = keys[overflowIndex];
            newOV.newNode = split(extrudedKey, extrudedNode);
            return newOV;
        }

        private Node split(Integer extrudedKey, Node extrudedNode) {
            IndexNode right = new IndexNode();
            int ri = 0;
            for (int i = overflowIndex; i <= maxKeyIndex; i++, ri++) {
                right.keys[ri] = keys[i];
                keys[i] = null;
            }
            right.keys[ri] = extrudedKey;

            ri = 0;
            for (int i = overflowIndex + 1; i <= maxChildIndex; i++, ri++) {
                right.children[ri] = children[i];
                children[i] = null;
            }
            right.children[ri] = extrudedNode;
            return right;
        }

        @Override
        protected Value search(int key) {
            int i = 0;
            for (; i < keys.length; i++) {
                if (keys[i] >= key) {
                    break;
                }
            }
            return children[i].search(key);
        }
    }

    protected class LeafNode extends Node {
        protected final Value[] values = new Value[maxValueIndex + 1];
        private LeafNode next;

        @Override
        protected Overflow insert(Value value) {
            int i = locate(value);
            Value extrudedValue = insertToAscArr(values, value, maxValueIndex, i);
            if (extrudedValue == null) {
                return null;
            }
            Overflow of = new Overflow();
            of.key = values[overflowIndex].i;
            of.newNode = split(extrudedValue);
            return of;
        }

        private int locate(Value newItem) {
            int i = 0;
            for (; i <= maxValueIndex; i++) {
                if (values[i] == null || values[i].i > newItem.i) {
                    break;
                }
                if (values[i].i == newItem.i) {
                    throw new DuplicateKeyException();
                }
            }
            return i;
        }

        private LeafNode split(Value extrudedValue) {
            LeafNode right = new LeafNode();
            int ri = 0;
            for (int i = overflowIndex; i <= maxValueIndex; i++, ri++) {
                right.values[ri] = values[i];
                values[i] = null;
            }
            right.values[ri] = extrudedValue;
            right.next = next;
            next = right;
            return right;
        }

        @Override
        protected Value search(int key) {
            for (Value value : values) {
                if (value.i == key) {
                    return value;
                }
            }
            return null;
        }
    }

    private static <T> T insertToAscArr(T[] ascArr, T newItem, int index) {
        return insertToAscArr(ascArr, newItem, ascArr.length - 1, index);
    }

    /**
     * @return the extrusion
     */
    private static <T> T insertToAscArr(T[] ascArr, T newItem, int endIndex, int index) {
        T tmp = newItem;
        for (int i = index; i <= endIndex; i++) {
            T tmp1 = ascArr[i];
            ascArr[i] = tmp;
            tmp = tmp1;
        }
        return tmp;
    }

    @AllArgsConstructor
    protected static class Value {
        protected final int i;
        protected final String words;
    }

    private static class Overflow {
        private int key;
        private Node newNode;
    }

    private static class DuplicateKeyException extends RuntimeException {

    }
}
