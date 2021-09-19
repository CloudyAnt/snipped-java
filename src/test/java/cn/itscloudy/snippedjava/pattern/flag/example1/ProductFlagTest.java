package cn.itscloudy.snippedjava.pattern.flag.example1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductFlagTest {

    @Test
    void shouldAddFlagsToProduct() {
        Product product = new Product();
        product.assignFlags(ProductFlags.RECOMMENDED.flagValue());
        product.addFlag(ProductFlags.IMPORTED);

        int expected = ProductFlags.RECOMMENDED.flagValue() + ProductFlags.IMPORTED.flagValue();
        assertEquals(expected, product.currentFlags());
        assertTrue(product.hasFlag(ProductFlags.RECOMMENDED));
        assertTrue(product.hasFlag(ProductFlags.IMPORTED));
        assertFalse(product.hasFlag(ProductFlags.SOLD_OUT));
    }

    @Test
    void shouldRemoveFlagsFromProduct() {
        Product product = new Product();
        int flags = ProductFlags.RECOMMENDED.flagValue() + ProductFlags.IMPORTED.flagValue() +
                ProductFlags.SOLD_OUT.flagValue();
        product.assignFlags(flags);
        product.removeFlag(ProductFlags.IMPORTED);
        product.removeFlag(ProductFlags.SOLD_OUT);

        int expected = ProductFlags.RECOMMENDED.flagValue();
        assertEquals(expected, product.currentFlags());
        assertFalse(product.hasFlag(ProductFlags.IMPORTED));
        assertFalse(product.hasFlag(ProductFlags.SOLD_OUT));
        assertTrue(product.hasFlag(ProductFlags.RECOMMENDED));
    }
}
