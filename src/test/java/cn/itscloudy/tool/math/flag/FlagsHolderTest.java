package cn.itscloudy.tool.math.flag;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FlagsHolderTest {

    @Test
    public void shouldAddFlagsToHolder() {
        Product product = new Product();
        product.addFlag(ProductFlags.RECOMMENDED).addFlag(ProductFlags.IMPORTED);

        int flags = ProductFlags.RECOMMENDED.flagValue() + ProductFlags.IMPORTED.flagValue();
        assertEquals(flags, product.getFlags());
        assertTrue(product.matchFlag(ProductFlags.RECOMMENDED));
        assertTrue(product.matchFlag(ProductFlags.IMPORTED));
    }

    @Test
    public void shouldRemoveFlagsFromHolder() {
        Product product = new Product();
        product.setFlags(7);

        product.removeFlag(ProductFlags.RECOMMENDED).removeFlag(ProductFlags.IMPORTED);

        int flags = ProductFlags.SOLD_OUT.flagValue();
        assertEquals(flags, product.getFlags());
        assertTrue(product.matchFlag(ProductFlags.SOLD_OUT));
    }
}
