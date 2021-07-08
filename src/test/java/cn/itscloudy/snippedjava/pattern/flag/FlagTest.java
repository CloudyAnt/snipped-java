package cn.itscloudy.snippedjava.pattern.flag;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FlagTest {

    @Test
    public void shouldCombineFlags() {
        int flags = Flag.collect(Feature.A, Feature.B);

        int expected = Feature.A.flagValue() + Feature.B.flagValue();
        assertEquals(expected, flags);
        assertTrue(Feature.A.match(flags));
        assertTrue(Feature.B.match(flags));
        assertFalse(Feature.C.match(flags));
    }

    @Test
    public void shouldAddFlag() {
        int flags = Flag.operate(1)
                .add(Feature.B)
                .toFlags();

        int expected = 1 + Feature.B.flagValue();
        assertEquals(expected, flags);
        assertTrue(Feature.A.match(flags));
        assertTrue(Feature.B.match(flags));
        assertFalse(Feature.C.match(flags));
    }

    @Test
    public void shouldRemoveFlag() {
        int flags = Flag.operate(7)
                .remove(Feature.A)
                .toFlags();

        int expected = 7 - Feature.A.flagValue();
        assertEquals(expected, flags);
        assertFalse(Feature.A.match(flags));
        assertTrue(Feature.B.match(flags));
        assertTrue(Feature.C.match(flags));
    }

    private enum Feature implements Flag {
        A,
        B,
        C
    }
}
