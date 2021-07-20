package cn.itscloudy.snippedjava.tool.other;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

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

        AtomicBoolean succeed = new AtomicBoolean(false);
        or.ifSuccess(s -> succeed.set(true));
        assertTrue(succeed.get());

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
        assertEquals("Other value", or.orElse("Other value"));
        assertEquals("Other value", or.orElseGet(() -> "Other value"));

        AtomicBoolean succeed = new AtomicBoolean(false);
        or.ifSuccess(s -> succeed.set(true));
        assertFalse(succeed.get());

        Optional<String> optional = or.optional();
        assertTrue(optional.isEmpty());
    }
}
