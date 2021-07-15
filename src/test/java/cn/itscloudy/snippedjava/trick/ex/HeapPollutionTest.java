package cn.itscloudy.snippedjava.trick.ex;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HeapPollutionTest {

    @Test
    public void shouldThrowClassCastException() {
        HeapPollution hp = new HeapPollution();

        assertThrows(ClassCastException.class, () -> {
            Integer[] caller = hp.polluterCaller(1);
            System.out.println(Arrays.toString(caller));
        });
    }

    @Test
    public void shouldNotThrowException() {
        HeapPollution hp = new HeapPollution();

        assertDoesNotThrow(() -> {
            Integer i = hp.normalCaller(1);
            System.out.println(i);
        });
    }
}
