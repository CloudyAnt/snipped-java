package cn.itscloudy.tool.math.flag;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlagTest {

    @Test
    public void shouldCombineFlags() {
        int flags = Flag.combine(ProductFlags.RECOMMENDED, ProductFlags.IMPORTED);

        int expected = ProductFlags.RECOMMENDED.flagValue() + ProductFlags.IMPORTED.flagValue();
        assertEquals(expected, flags);
        assertFalse(ProductFlags.SOLD_OUT.match(flags));
        assertTrue(ProductFlags.RECOMMENDED.match(flags));
        assertTrue(ProductFlags.IMPORTED.match(flags));
    }

    @Test
    public void shouldAddFlag() {
        int flags = Flag.of(1)
                .add(ProductFlags.IMPORTED)
                .toFlags();

        int expected = 1 + ProductFlags.IMPORTED.flagValue();
        assertEquals(expected, flags);
        assertFalse(ProductFlags.SOLD_OUT.match(flags));
        assertTrue(ProductFlags.RECOMMENDED.match(flags));
        assertTrue(ProductFlags.IMPORTED.match(flags));
    }

    @Test
    public void shouldRemoveFlag() {
        int flags = Flag.of(7)
                .remove(ProductFlags.IMPORTED)
                .toFlags();

        int expected = 7 - ProductFlags.IMPORTED.flagValue();
        assertEquals(expected, flags);
        assertTrue(ProductFlags.SOLD_OUT.match(flags));
        assertFalse(ProductFlags.RECOMMENDED.match(flags));
        assertTrue(ProductFlags.IMPORTED.match(flags));
    }

}
