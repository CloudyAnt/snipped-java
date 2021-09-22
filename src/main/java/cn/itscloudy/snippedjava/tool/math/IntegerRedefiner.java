package cn.itscloudy.snippedjava.tool.math;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntBinaryOperator;

/**
 * Redefine the symbol of some numbers. <p>
 * If an IntegerRedefiner object redefine '1' as 0, then 1 + 1 = 1 is correct in this object
 */
public abstract class IntegerRedefiner {

    private final char[] chars;
    private final char negative;
    private final Map<Character, Integer> charIntMap;
    private final int radix;

    /**
     * @param chars redefined symbols. chars.length equals to the radix
     */
    protected IntegerRedefiner(char... chars) {
        this(chars, '-');
    }

    /**
     * @param chars redefined symbols. chars.length equals to the radix
     * @param negative negative symbol
     */
    protected IntegerRedefiner(char[] chars, char negative) {
        this.charIntMap = new HashMap<>();
        if (chars.length < Character.MIN_RADIX) {
            throw new NumberFormatException("radix " + chars.length +
                    " less than Character.MIN_RADIX");
        }

        for (int i = 0; i < chars.length; i++) {
            Integer existentI = charIntMap.get(chars[i]);
            if (existentI != null) {
                throw new NumberFormatException("Char '" + existentI + "' is duplicated!");
            }
            charIntMap.put(chars[i], i);
        }
        if (charIntMap.get(negative) != null) {
            throw new NumberFormatException("Negative char '" + negative + "' is duplicated with chars!");
        }

        this.chars = chars;
        this.radix = chars.length;
        this.negative = negative;
    }

    public char[] chars() {
        return this.chars;
    }

    public char negative() {
        return this.negative;
    }

    public int radix() {
        return this.radix;
    }

    public String add(String a, String b) {
        return operate(a, b, Integer::sum);
    }

    public String minus(String a, String b) {
        return operate(a, b, (iA, iB) -> iA - iB);
    }

    public String multiply(String a, String b) {
        return operate(a, b, (iA, iB) -> iA * iB);
    }

    public String divide(String a, String b) {
        return operate(a, b, (iA, iB) -> iA / iB);
    }

    public String mod(String a, String b) {
        return operate(a, b, (iA, iB) -> iA % iB);
    }

    private String operate(String a, String b, IntBinaryOperator intOperation) {
        if (empty(a) || empty(b)) {
            throw new NumberFormatException("A and B must not be empty");
        }
        int intA = stringToInt(a);
        int intB = stringToInt(b);

        int opResult = intOperation.applyAsInt(intA, intB);
        return intToString(opResult);
    }

    private boolean empty(String s) {
        return null == s || "".equals(s);
    }

    private int stringToInt(String s) {
        int result = 0;
        int base = 1;
        char[] sChars = s.toCharArray();
        boolean sNegative = sChars[0] == this.negative;
        int firstIndex = sNegative ? 1 : 0;
        for (int i = sChars.length - 1; i >= firstIndex; i--) {
            char c = sChars[i];
            result += base * charToInt(c);
            base *= radix;
        }
        return sNegative ? -result : result;
    }

    private Integer charToInt(char c) {
        Integer i = charIntMap.get(c);
        if (i == null) {
            throw new NumberFormatException("Unknown character: " + c);
        }
        return i;
    }

    private String intToString(int i) {
        if (i == 0) {
            return chars[0] + "";
        }
        boolean positive = i > 0;
        i = Math.abs(i);

        StringBuilder sb = new StringBuilder();
        while (i > 0) {
            int mod = i % radix;
            char s = chars[mod];
            sb.insert(0, s);
            i = i / radix;
        }
        return positive ? sb.toString() : sb.insert(0, negative).toString();
    }
}
