package cn.itscloudy.tool.number;

import cn.itscloudy.util.CsvIntArrConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class BitsTest {

    @ParameterizedTest(name = "Init by size {0}")
    @CsvSource({
            "1024",
            "1",
            "0"
    })
    public void shouldInitBySize(int size) {
        Bits bits = Bits.ofSize(size);
        assertEquals(size, bits.length());

        for (int i = size; i > 0; i--) {
            assertFalse(bits.testBit(i));
        }
    }

    @ParameterizedTest(name = "Init by decimal {0}")
    @CsvSource({
            "0,",
            "1, 0",
            "1024, 10",
            "1025, 10, 0",
    })
    public void shouldInitByDecimal(long decimal, @ConvertWith(CsvIntArrConverter.class) int[] indices) {
        Bits bits = Bits.ofDecimal(decimal);
        for (int index : indices) {
            assertTrue(bits.testBit(index));
        }
    }

    @ParameterizedTest(name = "Init by bit string {0}")
    @CsvSource({
            "0,",
            "1, 0",
            "10110, 1|2|4",
            "111, 0|1|2"
    })
    public void shouldInitByBitString(String s, @ConvertWith(CsvIntArrConverter.class) int[] indices) {
        Bits bits = Bits.ofBitString(s);
        for (int index : indices) {
            assertTrue(bits.testBit(index));
        }
    }

    @ParameterizedTest(name = "Init by hex string {0}")
    @CsvSource({
            "0,",
            "1, 0",
            "A, 1, 3",
            "10, 4",
            "1F, 0, 1, 2, 3, 4"
    })
    public void shouldInitByHexString(String s, @ConvertWith(CsvIntArrConverter.class) int[] indices) {
        Bits bits = Bits.ofHexString(s);
        for (int index : indices) {
            assertTrue(bits.testBit(index));
        }
    }

    @ParameterizedTest(name = "To bit string of bit string {0}")
    @CsvSource({
            "0, 0",
            "1, 1",
            "1010, 1010",
    })
    public void shouldToBitString(String bitString, String expectation) {
        Bits bits = Bits.ofBitString(bitString);
        assertEquals(expectation, bits.toString());
    }

    @ParameterizedTest(name = "To reversed bit string of bit string {0}")
    @CsvSource({
            "0, 0",
            "1, 1",
            "1010, 0101",
    })
    public void shouldToRecBitString(String bitString, String expectation) {
        Bits bits = Bits.ofBitString(bitString);
        assertEquals(expectation, bits.toRevString());
    }

    @ParameterizedTest(name = "To hex string of bit string {0}")
    @CsvSource({
            "0, 0",
            "1, 1",
            "1010, A",
            "11010, 1A",
    })
    public void shouldToHexString(String bitString, String expectation) {
        Bits bits = Bits.ofBitString(bitString);
        assertEquals(expectation, bits.toHexString());
    }

    @ParameterizedTest(name = "Use {0} include {1}")
    @CsvSource({
            "0, 0, 0",
            "1, 1, 1",
            "1010, 101, 1111",
            "100, 0011, 0111",
    })
    public void shouldInclude(String a, String b, String expectation) {
        Bits bitsA = Bits.ofBitString(a);
        Bits bitsB = Bits.ofBitString(b);
        bitsA.include(bitsB);
        assertEquals(expectation, bitsA.toString());
    }

    @ParameterizedTest(name = "Let {0} exclude {1}")
    @CsvSource({
            "0, 0, 0",
            "11, 1, 10",
            "1111, 101, 1010",
            "1011, 0011, 1000",
    })
    public void shouldExclude(String a, String b, String expectation) {
        Bits bitsA = Bits.ofBitString(a);
        Bits bitsB = Bits.ofBitString(b);
        bitsA.exclude(bitsB);
        assertEquals(expectation, bitsA.toString());
    }

    @ParameterizedTest(name = "Get subtraction of {0} and {1}")
    @CsvSource({
            "0, 0, 0",
            "10, 1, 10",
            "1111, 101, 1010",
            "1011, 0011, 1000",
    })
    public void shouldGetSubtract(String a, String b, String expectation) {
        Bits bitsA = Bits.ofBitString(a);
        Bits bitsB = Bits.ofBitString(b);
        Bits subtraction = bitsA.subtract(bitsB);
        assertEquals(expectation, subtraction.toString());
    }

    @ParameterizedTest(name = "To complement of {0}")
    @CsvSource({
            "0, 1",
            "1, 0",
            "1010, 0101",
            "11001011, 00110100",
    })
    public void shouldInvert(String origin, String complement) {
        Bits bits = Bits.ofBitString(origin);
        bits.invert();
        assertEquals(complement, bits.toString());
    }

    @ParameterizedTest(name = "Get copy of {0}")
    @CsvSource({
            "0, 0",
            "1, 1",
            "1010, 1010",
            "11001011, 11001011",
    })
    public void shouldGetCopy(String origin, String copy) {
        Bits bits = Bits.ofBitString(origin);
        Bits copedBits = bits.copy();
        assertEquals(copy, copedBits.toString());
    }
}
