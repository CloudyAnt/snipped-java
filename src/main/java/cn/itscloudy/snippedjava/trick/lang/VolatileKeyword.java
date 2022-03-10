package cn.itscloudy.snippedjava.trick.lang;

/**
 * Illustrate the usage of the 'volatile' keyword
 */
public class VolatileKeyword {
    private VolatileKeyword() {
    }

    static class VariableWithoutVolatile extends Thread {

        int result = 0;
        boolean keepRunning = true;

        @Override
        public void run() {
            while (keepRunning) {
            }
            result = 1;
        }
    }

    static class VariableWithVolatile extends Thread {

        int result = 0;
        volatile boolean keepRunning = true;

        @Override
        public void run() {
            while (keepRunning) {
//                Thread.onSpinWait(); // This would hide the volatile effects
            }
            result = 1;
        }

    }
}
