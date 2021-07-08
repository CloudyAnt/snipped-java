package cn.itscloudy.snippedjava.util;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

/**
 * Csv int-to-array converter <br/>
 */
public class CsvIntArrConverter implements ArgumentConverter {

    @Override
    public Object convert(Object o, ParameterContext parameterContext) throws ArgumentConversionException {
        if (o instanceof String) {
            String s = o.toString();
            String[] splits = s.split("\\|");
            int[] ints = new int[splits.length];
            for (int i = 0; i < ints.length; i++) {
                ints[i] = Integer.parseInt(splits[i]);
            }
            return ints;
        } else if (o == null) {
            return new int[0];
        }
        throw new RuntimeException("Cannot convert " + o + " to int array");
    }
}
