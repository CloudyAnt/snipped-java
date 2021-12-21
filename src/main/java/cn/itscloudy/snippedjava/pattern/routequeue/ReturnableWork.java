package cn.itscloudy.snippedjava.pattern.routequeue;

import java.util.function.Supplier;

public class ReturnableWork<R> extends Work {
    
    private final Supplier<R> content;

    private R result;

    public ReturnableWork(Supplier<R> content) {
        this.content = content;
    }

    @Override
    protected void accept() {
        this.result = content.get();
        countDownLatch();
    }

    public R get(int waitMillis, Supplier<RuntimeException> onTimeOut) {
        if (awaitLatch(waitMillis)) {
            return result;
        } else {
            throw onTimeOut.get();
        }
    }

    public R getOr(int waitMillis, Supplier<R> bottomUpPlan) {
        if (awaitLatch(waitMillis)) {
            return result;
        } else {
            return bottomUpPlan.get();
        }
    }
}
