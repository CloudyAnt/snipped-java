package cn.itscloudy.snippedjava.tool.math;

import java.util.*;

/**
 * A BitCan contains a boolean array which referring to a bit array. With the bit array you can operate binary easily
 */
public class BitCan {

    private boolean[] values;

    private BitCan() {
    }

    public BitCan(boolean[] values) {
        this.values = values;
    }

    public static BitCan ofDecimal(long decimal) {
        int i = 0;

        ArrayList<Integer> indices = new ArrayList<>();
        while (i < decimal) {
            int b = (int) Math.pow(2, i);

            if ((decimal & b) == b) {
                indices.add(i);
            }
            i++;
        }
        return ofIndices(indices, true);
    }

    public static BitCan ofSize(int size) {
        return new BitCan(new boolean[size]);
    }

    public static BitCan ofIndices(Collection<Integer> indices, boolean basedOn0) {
        Integer size = 0;
        for (Integer index : indices) {
            if (index > size) {
                size = index;
            }
        }
        boolean[] bits = new boolean[basedOn0 ? size + 1 : size];
        int offset = basedOn0 ? 0 : -1;
        for (Integer index : indices) {
            bits[index + offset] = true;
        }
        return new BitCan(bits);
    }

    private static final int[] bitValues = new int[]{1, 2, 4, 8};

    public static BitCan ofHexString(String hexStr) {
        int len = hexStr.length();
        boolean[] bits = new boolean[len * 4];

        int index = 0;
        for (int i = len - 1; i >= 0; i--) {
            char c = hexStr.charAt(i);
            int decimal;
            if (c >= '0' && c <= '9') {
                decimal = c - '0';
            } else if (c >= 'A' && c <= 'F') {
                decimal = c - 'A' + 10;
            } else {
                throw new NumberFormatException(hexStr + " -×-> bits, invalid char: " + c);
            }
            for (int bi = 0; bi < 4; bi++) {
                bits[index++] = (decimal & bitValues[bi]) == bitValues[bi];
            }
        }
        return new BitCan(bits);
    }

    public static BitCan ofBitString(String bitString) {
        int len = bitString.length();
        boolean[] bits = new boolean[len];

        int maxI = len - 1;
        for (int i = maxI; i >= 0; i--) {
            char c = bitString.charAt(i);
            if ('1' == c) {
                bits[maxI - i] = true;
            } else if ('0' == c) {
                bits[maxI - i] = false;
            } else {
                throw new NumberFormatException(bitString + " -×-> bits, invalid char: " + c);
            }
        }
        return new BitCan(bits);
    }

    public void setBit(int index) {
        if (index >= values.length) {
            values = Arrays.copyOf(values, index + 1);
        }
        values[index] = true;
    }

    public void clearBit(int index) {
        if (index >= values.length) {
            return;
        }
        values[index] = false;
    }

    public boolean testBit(int index) {
        return index < values.length && values[index];
    }

    public int size() {
        return values.length;
    }

    /**
     * Equivalent to (a | b)
     */
    public void include(BitCan bBitCan) {
        if (bBitCan.size() > values.length) {
            values = Arrays.copyOf(values, bBitCan.size());
        }

        for (int i = 0; i < values.length; i++) {
            if (bBitCan.testBit(i)) {
                setBit(i);
            }
        }
    }

    /**
     * Equivalent to (a & ~b)
     */
    public void exclude(BitCan bBitCan) {
        for (int i = 0; i < values.length; i++) {
            if (testBit(i) && bBitCan.testBit(i)) {
                clearBit(i);
            }
        }
    }

    /**
     * Equivalent to (~a)
     */
    public void invert() {
        for (int i = 0; i < values.length; i++) {
            values[i] = !values[i];
        }
    }

    /**
     * Equivalent to (c = a & ~b)
     */
    public BitCan subtract(BitCan bBitCan) {
        BitCan newBitCan = copy();
        newBitCan.exclude(bBitCan);
        return newBitCan;
    }

    /**
     * Works as clone()
     */
    public BitCan copy() {
        BitCan newBitCan = new BitCan();
        newBitCan.values = new boolean[values.length];
        for (int i = 0; i < values.length; i++) {
            if (values[i]) {
                newBitCan.setBit(i);
            }
        }
        return newBitCan;
    }

    public String toBitString() {
        StringBuilder sb = new StringBuilder();
        for (int i = values.length - 1; i >= 0; i--) {
            sb.append(values[i] ? 1 : 0);
        }
        return sb.toString();
    }

    public String toRevBitString() {
        StringBuilder sb = new StringBuilder();
        for (boolean bit : values) {
            sb.append(bit ? 1 : 0);
        }
        return sb.toString();
    }

    public List<Integer> to0BasedIndices() {
        List<Integer> indices = new ArrayList<>(values.length);
        for (int i = 0; i < values.length; i++) {
            if (values[i]) {
                indices.add(i);
            }
        }
        return indices;
    }

    public List<Integer> to1basedIndices() {
        List<Integer> indices = new ArrayList<>(values.length);
        for (int i = 0; i < values.length; i++) {
            if (values[i]) {
                indices.add(i + 1);
            }
        }
        return indices;
    }

    public long toDecimal() {
        long decimal = 0;
        long base = 1;
        for (boolean bit : values) {
            if (bit) {
                decimal += base;
            }
            base *= 2;
        }
        return decimal;
    }

    public String toHexString() {
        StringBuilder sb = new StringBuilder();
        int bi = 0;
        int decimal = 0;
        for (boolean bit : values) {
            if (bi == 4) {
                sb.insert(0, hexCharOf(decimal));
                decimal = 0;
                bi = 0;
            }
            if (bit) {
                decimal += bitValues[bi];
            }
            bi++;
        }
        sb.insert(0, hexCharOf(decimal));
        return sb.toString();
    }

    private char hexCharOf(int decimal) {
        if (decimal < 10) {
            return (char) ('0' + decimal);
        } else {
            return (char) ('A' + decimal - 10);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(toBitString());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof BitCan)) {
            return false;
        }

        BitCan other = (BitCan) obj;
        int maxSize = Math.max(size(), other.size());
        for (int i = 0; i < maxSize; i++) {
            if (testBit(i) != other.testBit(i)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Bits{bits=" + toBitString() + "}";
    }
}
