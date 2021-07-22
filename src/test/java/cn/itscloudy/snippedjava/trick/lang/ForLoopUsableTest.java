package cn.itscloudy.snippedjava.trick.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ForLoopUsableTest {

    @Test
    void shouldUseForLoop() {
        String[] array = {"ABC", "DEF", "GHI"};
        ForLoopUsable<String> loopUsable = new ForLoopUsable<>(array);

        int index = 0;
        for (String string : loopUsable) {
            assertEquals(array[index++], string);
        }
    }
}
