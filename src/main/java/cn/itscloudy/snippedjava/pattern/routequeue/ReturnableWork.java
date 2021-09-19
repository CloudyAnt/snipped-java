package cn.itscloudy.snippedjava.pattern.routequeue;

import java.util.function.Supplier;

public class ReturnableWork<R> extends Work {
    
    private final Supplier<R> content;

    private R result;
    private volatile boolean done;

    public ReturnableWork(Supplier<R> content) {
        this.content = content;
    }

    @Override
    protected void accept() {
        this.result = content.get();
        this.done = true;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    public R get(int waitMillis, Supplier<RuntimeException> onTimeOut) {
        long endMills = System.currentTimeMillis() + waitMillis;
        while (!done) {
            if (System.currentTimeMillis() > endMills) {
                throw onTimeOut.get();
            }
        }
        return result;
    }

    public R getOr(int waitMillis, Supplier<R> bottomUpPlan) {
        long waitingEndAt = System.currentTimeMillis() + waitMillis;
        while (!done) {
            if (System.currentTimeMillis() > waitingEndAt) {
                return bottomUpPlan.get();
            }
        }
        return result;
    }
}
