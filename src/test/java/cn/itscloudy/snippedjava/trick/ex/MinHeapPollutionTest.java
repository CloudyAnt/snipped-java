package cn.itscloudy.snippedjava.trick.ex;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MinHeapPollutionTest {

    @Test
    void shouldThrowClassCastException() {
        HeapPollution hp = new HeapPollution();

        assertThrows(ClassCastException.class, () -> {
            Integer[] integers = hp.polluterCaller(1);
            System.out.println(integers.length);
        });
    }

    @Test
    void shouldNotThrowException() {
        HeapPollution hp = new HeapPollution();

        assertDoesNotThrow(() -> {
            Integer i = hp.normalCaller(1);
            System.out.println(i);
        });
    }
}
