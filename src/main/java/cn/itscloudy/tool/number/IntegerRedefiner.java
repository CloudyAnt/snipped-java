package cn.itscloudy.tool.number;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public abstract class IntegerRedefiner {

    private final char[] chars;
    private final char negative;
    private final Map<Character, Integer> charIntMap;
    private final int radix;

    public IntegerRedefiner() {
        this.chars = chars();
        this.radix = chars.length;
        if (chars.length < Character.MIN_RADIX) {
            throw new NumberFormatException("radix " + radix +
                    " less than Character.MIN_RADIX");
        }
        this.negative = negative();

        this.charIntMap = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            Integer existentI = charIntMap.get(chars[i]);
            if (existentI != null) {
                throw new NumberFormatException("Char '" + existentI + "' duplicated!");
            }
            charIntMap.put(chars[i], i);
        }
    }

    abstract char[] chars();

    public char negative() {
        return '-';
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

    private String operate(String a, String b, BiFunction<Integer, Integer, Integer> intOperation) {
        if (empty(a) || empty(b)) {
            throw new NumberFormatException("A and B must not be empty");
        }
        int intA = stringToInt(a);
        int intB = stringToInt(b);

        Integer opResult = intOperation.apply(intA, intB);
        return intToString(opResult);
    }

    private boolean empty(String s) {
        return null == s || "".equals(s);
    }

    private int stringToInt(String s) {
        int result = 0;
        int base = 1;
        char[] chars = s.toCharArray();
        boolean negative = chars[0] == this.negative;
        int firstIndex = negative ? 1 : 0;
        for (int i = chars.length - 1; i >= firstIndex; i--) {
            char c = chars[i];
            result += base * charToInt(c);
            base *= radix;
        }
        return negative ? -result : result;
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
        return positive ? sb.toString() : sb.insert(0, '-').toString();
    }
}
