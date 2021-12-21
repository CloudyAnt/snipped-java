package cn.itscloudy.snippedjava.pattern.routequeue;


import java.util.function.Supplier;

public class VoidWork extends Work {

    private final Runnable content;

    public VoidWork(Runnable content) {
        this.content = content;
    }

    @Override
    protected void accept() {
        content.run();
        countDownLatch();
    }

    public void timeout(int waitMillis, Supplier<RuntimeException> onTimeOut) {
        if (!awaitLatch(waitMillis)) {
            throw onTimeOut.get();
        }
    }

    public void timeout(int waitMillis, Runnable bottomUpPlan) {
        if (!awaitLatch(waitMillis)) {
            bottomUpPlan.run();
        }
    }
}
