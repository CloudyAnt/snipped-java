package cn.itscloudy.snippedjava.trick.ex;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HeapPollutionTest {

    @Test
    void shouldThrowClassCastException() {
        HeapPollution hp = new HeapPollution();

        assertThrows(ClassCastException.class, () -> hp.polluterCaller(1));
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
