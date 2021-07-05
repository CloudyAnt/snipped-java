package cn.itscloudy.tool.number.converter;

/**
 * Convert a number with radix A to the equivalent number with radix B
 */
public abstract class NumberConverter {
    private NumberConverter() {
    }

    /**
     * Using java built-in methods to do conversion. There is a limitation that radixes must between 2 and 36
     */
    public static String convert(String origin, int originRadix, int targetRadix) {
        int i = Integer.parseInt(origin, originRadix);
        return Integer.toString(i, targetRadix);
    }


}
