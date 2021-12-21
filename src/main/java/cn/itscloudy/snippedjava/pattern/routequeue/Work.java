package cn.itscloudy.snippedjava.pattern.routequeue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class Work {

    private final CountDownLatch latch;

    protected Work() {
        this.latch = new CountDownLatch(1);
    }

    protected void countDownLatch() {
        latch.countDown();
    }

    protected abstract void accept();

    protected boolean awaitLatch(long waitMills) {
        try {
            return latch.await(waitMills, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException("Worker thread interrupted", e);
        }
    }
}
