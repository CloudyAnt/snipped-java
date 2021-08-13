package cn.itscloudy.snippedjava.algorithm.bst;

import lombok.AllArgsConstructor;

import java.util.Comparator;

public class BTree1 {
    private final int maxValueIndex;
    private final int maxChildIndex;
    private final int overflowIndex;

    public Node top;

    public BTree1(int maxDegree) {
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
            top = new Node(i, words);
        } else {
            Overflow overflow = null;
            try {
                overflow = top.insert(new Value(i, words));
            } catch (DuplicateKeyException ignore) {
                // ignore duplicate key
            }

            if (overflow != null) {
                Node newTop = new Node(overflow.value);
                newTop.children[0] = top;
                newTop.children[1] = overflow.newNode;
                this.top = newTop;
            }
        }
    }

    public Value search(int i) {
        if (top == null) {
            return null;
        }
        return top.search(i);
    }

    protected class Node {
        protected final Value[] values = new Value[maxValueIndex + 1];
        protected Node[] children = new Node[maxChildIndex + 1];

        private Node(int i, String words) {
            values[0] = new Value(i, words);
        }

        private Node(Value value) {
            values[0] = value;
        }

        private Overflow insert(Value v) {
            if (children[0] == null) {
                if (full()) {
                    Value newV = getOverflowValue(values, v, Comparator.comparingInt(a -> a.i));
                    return new Overflow(newV, split());
                }
                insertToAscArr(values, v,
                        Comparator.comparingInt(a -> a.i));
                return null;
            }

            // get child branch
            int i = 0;
            Node child = children[0];
            for (; i <= maxChildIndex; i++) {
                if (values[i] == null || v.i < values[i].i) {
                    child = children[i];
                    break;
                }
            }
            // insert into child branch
            Overflow overflow = child.insert(v);
            return accept(overflow, i);
        }

        private Overflow accept(Overflow overflow, int childIndex) {
            if (overflow == null) {
                return null;
            }
            if (!full()) {
                insertToAscArr(values, overflow.value, Comparator.comparingInt(a -> a.i));
                insertToAscArr(children, overflow.newNode, childIndex + 1);
                return null;
            }
            Overflow newOV = new Overflow();
            if (childIndex == overflowIndex) {
                newOV.value = overflow.value;
            } else {
                newOV.value = getOverflowValue(values, overflow.value, Comparator.comparingInt(a -> a.i));
            }
            newOV.newNode = split(overflow.newNode, childIndex + 1);
            return newOV;
        }

        private Node split(Node newChild, int newChildIndex) {
            Node extrusion = insertToAscArr(children, newChild, newChildIndex);
            int rightStart = overflowIndex;
            Node right = new Node(values[rightStart]);
            values[rightStart] = null;

            int ri = 1;
            for (int i = rightStart; i <= maxValueIndex; i++, ri++) {
                right.values[ri] = values[i];
                values[i] = null;
            }

            ri = 0;
            for (int i = rightStart + 1; i <= maxChildIndex; i++, ri++) {
                right.children[ri] = children[i];
                children[i] = null;
            }
            right.children[ri] = extrusion;
            return right;

        }

        /**
         * Split current node and return the new node (right of parent)
         *
         * @return new node
         */
        private Node split() {
            int rightStart = overflowIndex;
            Node right = new Node(values[rightStart]);
            for (int vi = rightStart + 1, rvi = 1; vi <= maxValueIndex; vi++, rvi++) {
                right.values[rvi] = values[vi];
                right.children[rvi] = children[vi];
                values[vi] = null;
                children[vi] = null;
            }
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

        private boolean full() {
            return values[maxValueIndex] != null;
        }
    }

    @AllArgsConstructor
    protected static class Value {
        protected final int i;
        protected final String words;
    }

    @AllArgsConstructor
    private static class Overflow {
        private Value value;
        private Node newNode;

        private Overflow() {
        }
    }

    private <T> T getOverflowValue(T[] ascArr, T newItem, Comparator<T> comparator) {
        int i = locate(ascArr, newItem, comparator);
        int replaceIndex = overflowIndex;
        if (i == replaceIndex) {
            return newItem;
        }
        T result = ascArr[replaceIndex];
        if (i < replaceIndex) {
            while (replaceIndex > i) {
                ascArr[replaceIndex] = ascArr[replaceIndex - 1];
                replaceIndex--;
            }
        } else {
            while (replaceIndex < i && replaceIndex + 1 < ascArr.length) {
                ascArr[replaceIndex] = ascArr[replaceIndex + 1];
                replaceIndex++;
            }
        }
        ascArr[replaceIndex] = newItem;
        return result;
    }

    private static <T> void insertToAscArr(T[] ascArr, T newItem, Comparator<T> comparator) {
        int i = locate(ascArr, newItem, comparator);
        insertToAscArr(ascArr, newItem, i);
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

    private static <T> int locate(T[] ascArr, T newItem, Comparator<T> comparator) {
        int i = 0;
        for (; i < ascArr.length; i++) {
            if (ascArr[i] == null) {
                break;
            }
            int compare = comparator.compare(ascArr[i], newItem);
            if (compare == 0) {
                throw new DuplicateKeyException();
            }
            if (compare > 0) {
                break;
            }
        }
        return i;
    }

    private static class DuplicateKeyException extends RuntimeException {

    }
}
