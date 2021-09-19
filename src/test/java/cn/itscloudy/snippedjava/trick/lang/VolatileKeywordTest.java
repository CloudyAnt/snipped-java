package cn.itscloudy.snippedjava.trick.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VolatileKeywordTest {

    @Test
    void shouldNotReadChangeIfNotVolatile(){
        VolatileKeyword.VariableWithoutVolatile withoutVolatile = new VolatileKeyword.VariableWithoutVolatile();
        withoutVolatile.start();
        await(1);
        withoutVolatile.keepRunning = false;

        await(100);
        assertEquals(0, withoutVolatile.result);
    }

    @Test
    void shouldReadChangeIfVolatile(){
        VolatileKeyword.VariableWithVolatile withVolatile = new VolatileKeyword.VariableWithVolatile();
        withVolatile.start();
        await(1);
        withVolatile.keepRunning = false;

        await(100);
        assertEquals(1, withVolatile.result);
    }


    private void await(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
