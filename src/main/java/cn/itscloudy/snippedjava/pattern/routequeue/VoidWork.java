package cn.itscloudy.snippedjava.pattern.routequeue;


import java.util.function.Supplier;

public class VoidWork extends Work {

    private final Runnable content;

    protected volatile boolean done;

    public VoidWork(Runnable content) {
        this.content = content;
    }

    @Override
    protected void accept() {
        content.run();
        done = true;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    public void timeout(int waitMillis, Supplier<RuntimeException> onTimeOut){
        long endMills = System.currentTimeMillis() + waitMillis;

        while (!done) {
            if (System.currentTimeMillis() > endMills) {
                throw onTimeOut.get();
            }
        }
    }

    public void timeout(int waitMillis, Runnable bottomUpPlan){
        long endMills = System.currentTimeMillis() + waitMillis;

        while (!done) {
            if (System.currentTimeMillis() > endMills) {
                bottomUpPlan.run();
            }
        }
    }
}
