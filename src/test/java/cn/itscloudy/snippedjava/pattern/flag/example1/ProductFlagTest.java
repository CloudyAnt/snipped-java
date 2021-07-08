package cn.itscloudy.snippedjava.pattern.flag.example1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductFlagTest {

    @Test
    public void shouldAddFlagsToProduct() {
        Product product = new Product();
        product.setFlags(ProductFlags.RECOMMENDED.flagValue());
        product.addFlag(ProductFlags.IMPORTED);

        int expected = ProductFlags.RECOMMENDED.flagValue() + ProductFlags.IMPORTED.flagValue();
        assertEquals(expected, product.getFlags());
        Assertions.assertTrue(product.matchFlag(ProductFlags.RECOMMENDED));
        Assertions.assertTrue(product.matchFlag(ProductFlags.IMPORTED));
    }

    @Test
    public void shouldRemoveFlagsFromProduct() {
        Product product = new Product();
        int flags = ProductFlags.RECOMMENDED.flagValue() + ProductFlags.IMPORTED.flagValue() +
                ProductFlags.SOLD_OUT.flagValue();
        product.setFlags(flags);
        product.removeFlag(ProductFlags.IMPORTED);
        product.removeFlag(ProductFlags.SOLD_OUT);

        int expected = ProductFlags.RECOMMENDED.flagValue();
        assertEquals(expected, product.getFlags());
        Assertions.assertFalse(product.matchFlag(ProductFlags.IMPORTED));
        Assertions.assertFalse(product.matchFlag(ProductFlags.SOLD_OUT));
    }
}
