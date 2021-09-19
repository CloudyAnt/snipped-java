package cn.itscloudy.snippedjava.pattern.routequeue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Worker implements Callable<Boolean> {

    private final ArrayBlockingQueue<Work> queue;

    public Worker(ArrayBlockingQueue<Work> queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() {
        while (WorkContractor.acceptingWork) {
            try {
                Work work = this.queue.poll(1, TimeUnit.NANOSECONDS);
                if (work != null) {
                    work.accept();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
