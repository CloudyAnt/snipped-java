package cn.itscloudy.snippedjava.tool.math;

import cn.itscloudy.snippedjava.util.CsvIntArrConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BitsTest {

    @ParameterizedTest(name = "Init by size {0}")
    @CsvSource({
            "1024",
            "1",
            "0"
    })
    void shouldInitBySize(int size) {
        Bits bits = Bits.ofSize(size);
        assertEquals(size, bits.size());

        for (int i = size; i > 0; i--) {
            assertFalse(bits.testBit(i));
        }
    }

    @ParameterizedTest(name = "Init by decimal {0}")
    @CsvSource({
            "0,",
            "1, 0",
            "1024, 10",
            "1025, 10|0",
    })
    void shouldInitByDecimal(long decimal, @ConvertWith(CsvIntArrConverter.class) int[] indices) {
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
    void shouldInitByBitString(String bitString, @ConvertWith(CsvIntArrConverter.class) int[] indices) {
        Bits bits = Bits.ofBitString(bitString);
        for (int index : indices) {
            assertTrue(bits.testBit(index));
        }
    }

    @ParameterizedTest(name = "Init by hex string {0}")
    @CsvSource({
            "0,",
            "1, 0",
            "A, 1|3",
            "10, 4",
            "1F, 0|1|2|3|4"
    })
    void shouldInitByHexString(String hexString, @ConvertWith(CsvIntArrConverter.class) int[] indices) {
        Bits bits = Bits.ofHexString(hexString);
        for (int index : indices) {
            assertTrue(bits.testBit(index));
        }
    }

    @ParameterizedTest(name = "To 0 based indices of {0}")
    @CsvSource({
            "0,",
            "1, 0",
            "1010, 1|3",
            "11010, 1|3|4",
    })
    void shouldTo0BasedIndices(String bitString, @ConvertWith(CsvIntArrConverter.class) int[] expectations) {
        Bits bits = Bits.ofBitString(bitString);
        List<Integer> indices = bits.to0BasedIndices();
        assertEquals(expectations.length, indices.size());
        for (int i = 0; i < expectations.length; i++) {
            assertEquals(expectations[i], indices.get(i));
        }
    }


    @ParameterizedTest(name = "To 1 based indices of {0}")
    @CsvSource({
            "0,",
            "1, 1",
            "1010, 2|4",
            "11010, 2|4|5",
    })
    void shouldTo1BasedIndices(String bitString, @ConvertWith(CsvIntArrConverter.class) int[] expectations) {
        Bits bits = Bits.ofBitString(bitString);
        List<Integer> indices = bits.to1basedIndices();
        assertEquals(expectations.length, indices.size());
        for (int i = 0; i < expectations.length; i++) {
            assertEquals(expectations[i], indices.get(i));
        }
    }

    @ParameterizedTest(name = "To bit string of bit string {0}")
    @CsvSource({
            "0, 0",
            "1, 1",
            "1010, 1010",
    })
    void shouldToBitString(String bitString, String expectation) {
        Bits bits = Bits.ofBitString(bitString);
        assertEquals(expectation, bits.toBitString());
    }

    @ParameterizedTest(name = "To reversed bit string of bit string {0}")
    @CsvSource({
            "0, 0",
            "1, 1",
            "1010, 0101",
    })
    void shouldToRecBitString(String bitString, String expectation) {
        Bits bits = Bits.ofBitString(bitString);
        assertEquals(expectation, bits.toRevBitString());
    }

    @ParameterizedTest(name = "To hex string of bit string {0}")
    @CsvSource({
            "0, 0",
            "1, 1",
            "1010, A",
            "11010, 1A",
    })
    void shouldToHexString(String bitString, String expectation) {
        Bits bits = Bits.ofBitString(bitString);
        assertEquals(expectation, bits.toHexString());
    }

    @ParameterizedTest(name = "To decimal of bit string {0}")
    @CsvSource({
            "0, 0",
            "1, 1",
            "1010, 10",
            "11010, 26",
    })
    void shouldToDecimal(String bitString, int expectation) {
        Bits bits = Bits.ofBitString(bitString);
        assertEquals(expectation, bits.toDecimal());
    }

    @ParameterizedTest(name = "Use {0} include {1}")
    @CsvSource({
            "0, 0, 0",
            "1, 1, 1",
            "1010, 101, 1111",
            "100, 0011, 0111",
    })
    void shouldInclude(String a, String b, String expectation) {
        Bits bitsA = Bits.ofBitString(a);
        Bits bitsB = Bits.ofBitString(b);
        bitsA.include(bitsB);
        assertEquals(expectation, bitsA.toBitString());
    }

    @ParameterizedTest(name = "Let {0} exclude {1}")
    @CsvSource({
            "0, 0, 0",
            "11, 1, 10",
            "1111, 101, 1010",
            "1011, 0011, 1000",
    })
    void shouldExclude(String a, String b, String expectation) {
        Bits bitsA = Bits.ofBitString(a);
        Bits bitsB = Bits.ofBitString(b);
        bitsA.exclude(bitsB);
        assertEquals(expectation, bitsA.toBitString());
    }

    @ParameterizedTest(name = "Get subtraction of {0} and {1}")
    @CsvSource({
            "0, 0, 0",
            "10, 1, 10",
            "1111, 101, 1010",
            "1011, 0011, 1000",
    })
    void shouldGetSubtract(String a, String b, String expectation) {
        Bits bitsA = Bits.ofBitString(a);
        Bits bitsB = Bits.ofBitString(b);
        Bits subtraction = bitsA.subtract(bitsB);
        assertEquals(expectation, subtraction.toBitString());
    }

    @ParameterizedTest(name = "To complement of {0}")
    @CsvSource({
            "0, 1",
            "1, 0",
            "1010, 0101",
            "11001011, 00110100",
    })
    void shouldInvert(String origin, String complement) {
        Bits bits = Bits.ofBitString(origin);
        bits.invert();
        assertEquals(complement, bits.toBitString());
    }

    @ParameterizedTest(name = "Get copy of {0}")
    @CsvSource({
            "0, 0",
            "1, 1",
            "1010, 1010",
            "11001011, 11001011",
    })
    void shouldGetCopy(String origin, String copy) {
        Bits bits = Bits.ofBitString(origin);
        Bits copedBits = bits.copy();
        assertEquals(copy, copedBits.toBitString());
    }

    @Test
    void shouldEquals() {
        String sameBits = "10110";
        Bits bits1 = Bits.ofBitString(sameBits);
        Bits bits2 = Bits.ofBitString(sameBits);
        assertEquals(bits1, bits2);
    }
}
