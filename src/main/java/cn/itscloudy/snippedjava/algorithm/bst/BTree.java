package cn.itscloudy.snippedjava.algorithm.bst;

import lombok.AllArgsConstructor;

public class BTree {
    private final int maxValueIndex;
    private final int maxChildIndex;
    private final int overflowIndex;

    public Node top;

    public BTree(int maxDegree) {
        if (maxDegree < 3) {
            throw new RuntimeException("MaxDegree can't be less than 3");
        }

        maxValueIndex = maxDegree - 2;
        maxChildIndex = maxDegree - 1;
        overflowIndex = (maxValueIndex + 1) / 2;
    }

    public Node top() {
        return top;
    }

    public void insert(int i, String words) {
        if (top == null) {
            top = new Node();
            top.values[0] = new Value(i, words);
        } else {
            Overflow overflow = null;
            try {
                overflow = top.insert(new Value(i, words));
            } catch (DuplicateKeyException ignore) {
                // ignore duplicate key
            }

            if (overflow != null) {
                Node newTop = new Node();
                newTop.values[0] = overflow.value;
                newTop.children[0] = top;
                newTop.children[1] = overflow.newNode;
                this.top = newTop;
            }
        }
    }

    public Value search(int i) {
        return top == null ? null : top.search(i);
    }

    protected class Node {
        protected final Value[] values = new Value[maxValueIndex + 1];
        protected Node[] children = new Node[maxChildIndex + 1];

        private Overflow insert(Value v) {
            if (children[0] == null) {
                // insert to current node
                return insert(v, locate(v), null);
            }

            // get child branch
            int i = 0;
            Node child = children[0];
            for (; i <= maxChildIndex; i++) {
                if (values[i] == null || v.i < values[i].i) {
                    child = children[i];
                    break;
                }
                if (v.i == values[i].i) {
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
            return insert(overflow.value, i, extrudedNode);
        }

        private Overflow insert(Value v, int i, Node extrudedNode) {
            Value extrudedValue = insertToAscArr(values, v, i);
            if (extrudedValue == null) {
                return null;
            }
            Overflow of = new Overflow();
            of.value = values[overflowIndex];
            values[overflowIndex] = null;
            of.newNode = split(extrudedValue, extrudedNode);
            return of;
        }

        /**
         * Split current node and return the new node (right of parent)
         *
         * @return new node
         */
        private Node split(Value extrudedValue, Node extrudedNode) {
            Node right = new Node();
            int ri = 0;
            for (int i = overflowIndex + 1; i <= maxValueIndex; i++, ri++) { // values[overflowIndex] was overflowed
                right.values[ri] = values[i];
                values[i] = null;
            }
            right.values[ri] = extrudedValue;

            ri = 0;
            for (int i = overflowIndex + 1; i <= maxChildIndex; i++, ri++) { // children[overflowIndex] keeps in left
                right.children[ri] = children[i];
                children[i] = null;
            }
            right.children[ri] = extrudedNode;
            return right;
        }

        public Value search(int i) {
            int index = 0;
            for (; index < values.length; index++) {
                if (values[index] == null || values[index].i > i) {
                    break;
                }
                if (values[index].i == i) {
                    return values[index];
                }
            }
            if (children[index] == null) {
                return null;
            }
            return children[index].search(i);
        }

        private int locate(Value newItem) {
            int i = 0;
            for (; i <= maxValueIndex; i++) {
                if (values[i] == null) {
                    break;
                }
                if (values[i].i == newItem.i) {
                    throw new DuplicateKeyException();
                }
                if (values[i].i > newItem.i) {
                    break;
                }
            }
            return i;
        }
    }

    /**
     * @return the extrusion
     */
    private static <T> T insertToAscArr(T[] ascArr, T newItem, int index) {
        T tmp = newItem;
        for (int i = index; i < ascArr.length; i++) {
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
        private Value value;
        private Node newNode;
    }

    private static class DuplicateKeyException extends RuntimeException {

    }
}
