package cn.itscloudy.tool.math;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberConverterTest {

    @ParameterizedTest(name = "convert {0} in radix {1} to radix {2}")
    @CsvSource({
            "210, 3, 16, 15",
            "100, 10, 8, 144",
            "100, 10, 16, 64"
    })
    public void should_convert(String origin, int originRadix, int targetRadix, String target) {
        String conversion = NumberConverter.convert(origin, originRadix, targetRadix);
        assertEquals(target, conversion);
    }
}
