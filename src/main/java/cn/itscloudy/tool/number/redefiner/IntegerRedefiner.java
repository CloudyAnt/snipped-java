package cn.itscloudy.tool.number.redefiner;

import java.util.HashMap;
import java.util.Map;

public abstract class IntegerRedefiner {

    private final char[] chars;
    private final Map<Character, Integer> charIntMap;
    private final int radix;

    public IntegerRedefiner() {
        this.chars = chars();
        this.radix = chars.length;
        if (chars.length < Character.MIN_RADIX) {
            throw new NumberFormatException("radix " + radix +
                    " less than Character.MIN_RADIX");
        }

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

    public String add(String a, String b) {
        int intA = stringToInt(a);
        int intB = stringToInt(b);
        return intToString(intA + intB);
    }

    public String minus(String a, String b) {
        int intA = stringToInt(a);
        int intB = stringToInt(b);
        return intToString(intA - intB);
    }

    public String multiply(String a, String b) {
        int intA = stringToInt(a);
        int intB = stringToInt(b);
        return intToString(intA * intB);
    }

    public String divide(String a, String b) {
        int intA = stringToInt(a);
        int intB = stringToInt(b);
        return intToString(intA / intB);
    }

    public String mod(String a, String b) {
        int intA = stringToInt(a);
        int intB = stringToInt(b);
        return intToString(intA % intB);
    }

    private int stringToInt(String s) {
        int result = 0;
        int base = 1;
        char[] chars = s.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            char c = chars[i];
            result += base * charToInt(c);
            base *= radix;
        }
        return result;
    }

    private Integer charToInt(char c) {
        Integer i = charIntMap.get(c);
        if (i == null) {
            throw new NumberFormatException("Unknown character: " + c);
        }
        return i;
    }

    private String intToString(int i) {
        StringBuilder sb = new StringBuilder();
        while (i > 0) {
            int mod = i % radix;
            char s = chars[mod];
            sb.insert(0, s);
            i = i / radix;
        }
        return sb.toString();
    }
}
