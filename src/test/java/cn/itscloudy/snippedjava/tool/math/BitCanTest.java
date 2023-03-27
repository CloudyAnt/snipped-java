package cn.itscloudy.snippedjava.tool.math;

import cn.itscloudy.snippedjava.util.CsvIntArrConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BitCanTest {

    @ParameterizedTest(name = "Init by size {0}")
    @CsvSource({
            "1024",
            "1",
            "0"
    })
    void shouldInitBySize(int size) {
        BitCan bitCan = BitCan.ofSize(size);
        assertEquals(size, bitCan.size());

        for (int i = size; i > 0; i--) {
            assertFalse(bitCan.testBit(i));
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
        BitCan bitCan = BitCan.ofDecimal(decimal);
        for (int index : indices) {
            assertTrue(bitCan.testBit(index));
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
        BitCan bitCan = BitCan.ofBitString(bitString);
        for (int index : indices) {
            assertTrue(bitCan.testBit(index));
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
        BitCan bitCan = BitCan.ofHexString(hexString);
        for (int index : indices) {
            assertTrue(bitCan.testBit(index));
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
        BitCan bitCan = BitCan.ofBitString(bitString);
        List<Integer> indices = bitCan.to0BasedIndices();
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
        BitCan bitCan = BitCan.ofBitString(bitString);
        List<Integer> indices = bitCan.to1basedIndices();
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
        BitCan bitCan = BitCan.ofBitString(bitString);
        assertEquals(expectation, bitCan.toBitString());
    }

    @ParameterizedTest(name = "To reversed bit string of bit string {0}")
    @CsvSource({
            "0, 0",
            "1, 1",
            "1010, 0101",
    })
    void shouldToRecBitString(String bitString, String expectation) {
        BitCan bitCan = BitCan.ofBitString(bitString);
        assertEquals(expectation, bitCan.toRevBitString());
    }

    @ParameterizedTest(name = "To hex string of bit string {0}")
    @CsvSource({
            "0, 0",
            "1, 1",
            "1010, A",
            "11010, 1A",
    })
    void shouldToHexString(String bitString, String expectation) {
        BitCan bitCan = BitCan.ofBitString(bitString);
        assertEquals(expectation, bitCan.toHexString());
    }

    @ParameterizedTest(name = "To decimal of bit string {0}")
    @CsvSource({
            "0, 0",
            "1, 1",
            "1010, 10",
            "11010, 26",
    })
    void shouldToDecimal(String bitString, int expectation) {
        BitCan bitCan = BitCan.ofBitString(bitString);
        assertEquals(expectation, bitCan.toDecimal());
    }

    @ParameterizedTest(name = "Use {0} include {1}")
    @CsvSource({
            "0, 0, 0",
            "1, 1, 1",
            "1010, 101, 1111",
            "100, 0011, 0111",
    })
    void shouldInclude(String a, String b, String expectation) {
        BitCan bitCanA = BitCan.ofBitString(a);
        BitCan bitCanB = BitCan.ofBitString(b);
        bitCanA.include(bitCanB);
        assertEquals(expectation, bitCanA.toBitString());
    }

    @ParameterizedTest(name = "Let {0} exclude {1}")
    @CsvSource({
            "0, 0, 0",
            "11, 1, 10",
            "1111, 101, 1010",
            "1011, 0011, 1000",
    })
    void shouldExclude(String a, String b, String expectation) {
        BitCan bitCanA = BitCan.ofBitString(a);
        BitCan bitCanB = BitCan.ofBitString(b);
        bitCanA.exclude(bitCanB);
        assertEquals(expectation, bitCanA.toBitString());
    }

    @ParameterizedTest(name = "Get subtraction of {0} and {1}")
    @CsvSource({
            "0, 0, 0",
            "10, 1, 10",
            "1111, 101, 1010",
            "1011, 0011, 1000",
    })
    void shouldGetSubtract(String a, String b, String expectation) {
        BitCan bitCanA = BitCan.ofBitString(a);
        BitCan bitCanB = BitCan.ofBitString(b);
        BitCan subtraction = bitCanA.subtract(bitCanB);
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
        BitCan bitCan = BitCan.ofBitString(origin);
        bitCan.invert();
        assertEquals(complement, bitCan.toBitString());
    }

    @ParameterizedTest(name = "Get copy of {0}")
    @CsvSource({
            "0, 0",
            "1, 1",
            "1010, 1010",
            "11001011, 11001011",
    })
    void shouldGetCopy(String origin, String copy) {
        BitCan bitCan = BitCan.ofBitString(origin);
        BitCan copedBitCan = bitCan.copy();
        assertEquals(copy, copedBitCan.toBitString());
    }

    @Test
    void shouldEquals() {
        String sameBits = "10110";
        BitCan bitCan1 = BitCan.ofBitString(sameBits);
        BitCan bitCan2 = BitCan.ofBitString(sameBits);
        assertEquals(bitCan1, bitCan2);
    }
}
