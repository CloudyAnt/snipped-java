package cn.itscloudy.tool.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntegerRedefinerTest extends IntegerRedefiner {

    public IntegerRedefinerTest() {
        super('@', '#', '$');
    }

    @ParameterizedTest(name = "{0} add {1}")
    @CsvSource({
            ", #, EX",
            "@@, @@@@, @",
            "#, #, $",
            "##, #, #$"
    })
    public void should_add(String a, String b, String expectation) {
        assertEquals(a, b, expectation, this::add);
    }

    @ParameterizedTest(name = "{0} minus {1}")
    @CsvSource({
            ", #, EX",
            "$, $, @",
            "$$, $, $@",
            "$, ##, -$"
    })
    public void should_minus(String a, String b, String expectation) {
        assertEquals(a, b, expectation, this::minus);
    }


    @ParameterizedTest(name = "{0} multiply {1}")
    @CsvSource({
            ", #, EX",
            "@@, @@, @",
            "#, @, @",
            "@, #, @",
            "#, #, #",
            "##, $$, #@#$",
            "##, -$$, -#@#$",
    })
    public void should_multiply(String a, String b, String expectation) {
        assertEquals(a, b, expectation, this::multiply);
    }


    @ParameterizedTest(name = "{0} divide {1}")
    @CsvSource({
            ", #, EX",
            "@, @, EX",
            "#, #, #",
            "#, $, @",
            "#@#$, $$, ##",
            "-#@#$, $$, -##"
    })
    public void should_divide(String a, String b, String expectation) {
        assertEquals(a, b, expectation, this::divide);
    }

    @ParameterizedTest(name = "{0} divide {1}")
    @CsvSource({
            ", #, EX",
            "#@, $, #",
            "##, $, @",
            "$$, -#@, $"
    })
    public void should_mod(String a, String b, String expectation) {
        assertEquals(a, b, expectation, this::mod);
    }


    public void assertEquals(String a, String b, String expectation,
                             BiFunction<String, String, String> fun) {
        if ("EX".equals(expectation)) {
            assertThrows(Exception.class, () -> fun.apply(a, b));
            return;
        }
        Assertions.assertEquals(expectation, fun.apply(a, b));
    }

}
