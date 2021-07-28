package cn.itscloudy.snippedjava.tool.other;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SwitcherTest {

    Integer quadrant = 0;
    Switcher<Integer> quadrantTeller = Switcher
            .<Integer>builder()
            .doBreak(v -> v > 0 && v < 90, v -> quadrant = 1)
            .doBreak(v -> v > 90 && v < 180, v -> quadrant = 2)
            .doBreak(v -> v > 180 && v < 270, v -> quadrant = 3)
            .doBreak(v -> v > 270 && v < 360, v -> quadrant = 4)
            .end(v -> quadrant = -1);

    @ParameterizedTest(name = "Tell the quadrant of angle {0}")
    @CsvSource({
            "0, -1",
            "1, 1",
            "89, 1",
            "91, 2",
            "179, 2",
            "181, 3",
            "269, 3",
            "271, 4",
            "359, 4",
    })
    void shouldSwitchToTargetPart(int value, int expectedPart) {
        quadrantTeller.accept(value);
        assertEquals(expectedPart, quadrant);
    }

    String color = "KNOWN";
    Switcher<String> colorTeller = Switcher
            .<String>builder()
            .notBreak(v -> v.equals("#FF5555"), v -> {})
            .doBreak(v -> v.equals("#FF0000"), v -> color = "RED")
            .notBreak(v -> v.equals("#55FF55"), v -> {})
            .doBreak(v -> v.equals("#00FF00"), v -> color = "GREEN")
            .notBreak(v -> v.equals("#5555FF"), v -> {})
            .doBreak(v -> v.equals("#0000FF"), v -> color = "BLUE")
            .end();

    @ParameterizedTest(name = "Tell the color of ${0}")
    @CsvSource({
            "#FF5555, RED",
            "#FF0000, RED",
            "#55FF55, GREEN",
            "#00FF00, GREEN",
            "#5555FF, BLUE",
            "#0000FF, BLUE",
            "#ABCDEF, KNOWN"
    })
    void shouldTellTheColor(String expression, String expectedColor) {
        colorTeller.accept(expression);
        assertEquals(expectedColor, color);
    }

    Boolean restricted = false;
    Switcher<String> restrictionTeller = Switcher
            .<String>builder()
            .mayBreak(v -> v.startsWith("MOVIE"), v -> {
                if (v.contains("Violation")) {
                    restricted = true;
                    return true;
                }
                return false;
            })
            .mayBreak(v -> v.startsWith("GAME"), v -> {
                if (v.contains("Infringement")) {
                    restricted = true;
                }
                return false;
            })
            .end();
    @ParameterizedTest(name = "Tell the restriction of  {0}")
    @CsvSource({
            "MOVIE:Avatar[Alien, Tec], true",
            "MOVIE:Avengers4[Heroes, Violation], false",
            "GAME:RedAlert3-Copy[War, Infringement], true",
            "GAME:ForTheKing[Roguelike, MiddleAges], false",
            "BOOK:TheOldManAndTheSea, false"
    })
    void shouldTellTheRestriction(String id, boolean expectation) {
        restrictionTeller.accept(id);
        assertEquals(expectation, restricted);
    }
}
