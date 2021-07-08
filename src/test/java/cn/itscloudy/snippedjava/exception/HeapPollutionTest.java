package cn.itscloudy.snippedjava.exception;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class HeapPollutionTest {

    @Test
    public void shouldThrowClassCastException() {
        HeapPollution hp = new HeapPollution();

        assertThrows(ClassCastException.class, () -> {
            Integer[] caller = hp.caller(1);
            System.out.println(Arrays.toString(caller));
        });
    }
}
