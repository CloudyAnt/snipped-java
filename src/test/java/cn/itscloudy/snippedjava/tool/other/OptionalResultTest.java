package cn.itscloudy.snippedjava.tool.other;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class OptionalResultTest {

    @Test
    public void shouldContainValue() {
        String result = "MONKEY";
        OptionalResult<String> or = OptionalResult.of(() -> result);

        assertTrue(or.isSuccess());
        assertEquals(result, or.get());
        assertNull(or.exception());
        assertEquals(result, or.orElseThrow(e -> new RuntimeException("E")));

        Optional<String> optional = or.optional();
        assertTrue(optional.isPresent());
        assertEquals(result, optional.get());
    }

    @Test
    public void shouldContainException() {
        RuntimeException ex = new RuntimeException("Runtime Exception");
        OptionalResult<String> or = OptionalResult.of(() -> {
            if (System.currentTimeMillis() > 0) {
                throw ex;
            }
            return "Unexpected Value";
        });

        assertFalse(or.isSuccess());
        assertNull(or.get());
        assertEquals(ex, or.exception());
        assertThrows(RuntimeException.class, () -> or.orElseThrow(e -> new RuntimeException("E")));

        Optional<String> optional = or.optional();
        assertTrue(optional.isEmpty());
    }
}
