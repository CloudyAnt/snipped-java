package cn.itscloudy.snippedjava.algorithm.heap;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinHeapTest {

    @Test
    void shouldHeapify() {
        MinHeap<Value> heap = new MinHeap<>();
        heap.add(new Value(5, "S"));
        heap.add(new Value(2, "C"));
        heap.add(new Value(1, "J"));
        heap.add(new Value(6, "K"));
        heap.add(new Value(4, "A"));
        heap.add(new Value(7, "O"));
        heap.add(new Value(3, "N"));

        String s = "JACKSON";
        for (int i = 0; i < s.length(); i++) {
            assertEquals(s.charAt(i) + "", heap.get(i).words);
        }
    }

    @AllArgsConstructor
    private static class Value implements Comparable<Value> {
        private final int i;
        private final String words;

        @Override
        public int compareTo(Value o) {
            return Integer.compare(i, o.i);
        }
    }

}
