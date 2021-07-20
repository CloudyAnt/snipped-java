package cn.itscloudy.snippedjava.tool.other;

import org.junit.jupiter.api.Test;

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
    }

    @Test
    public void shouldContainException() {
        OptionalResult<String> or = OptionalResult.of(() -> {
            if (System.currentTimeMillis() > 0) {
                throw new RuntimeException("Runtime Exception");
            }
            return "Unexpected Value";
        });

        assertFalse(or.isSuccess());
        assertNull(or.get());
        assertNotNull(or.exception());
        assertThrows(RuntimeException.class, () -> or.orElseThrow(e -> new RuntimeException("E")));
    }
}
