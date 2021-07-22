package cn.itscloudy.snippedjava.tool.other;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class OptionalResultTest {

    @Test
    void shouldContainValue() {
        String result = "MONKEY";
        OptionalResult<String> or = OptionalResult.of(() -> result);

        assertTrue(or.isSuccess());
        assertEquals(result, or.get());
        assertNull(or.exception());
        assertEquals(result, or.orElseThrow(e -> new RuntimeException("E")));

        AtomicBoolean succeed = new AtomicBoolean(false);
        or.ifSuccess(s -> succeed.set(true));
        assertTrue(succeed.get());

        Optional<String> optional = or.optional();
        assertTrue(optional.isPresent());
        assertEquals(result, optional.get());
    }

    @Test
    void shouldContainException() {
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
        assertEquals("Other value", or.orElse("Other value"));
        assertEquals("Other value", or.orElseGet(() -> "Other value"));

        AtomicBoolean succeed = new AtomicBoolean(false);
        or.ifSuccess(s -> succeed.set(true));
        assertFalse(succeed.get());

        Optional<String> optional = or.optional();
        assertTrue(optional.isEmpty());
    }

    @Test
    void shouldEquals() {
        String sameResult = "Greetings from the Moon";
        OptionalResult<String> or1 = OptionalResult.of(() -> sameResult);
        OptionalResult<String> or2 = OptionalResult.of(() -> sameResult);
        assertEquals(or2, or1);
    }
}
