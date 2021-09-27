package cn.itscloudy.snippedjava.pattern.routequeue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

public class Worker implements Callable<Boolean> {

    private final ArrayBlockingQueue<Work> queue;

    public Worker(ArrayBlockingQueue<Work> queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() {
        while (WorkContractor.acceptingWork) {
            try {
                /*
                 * As to https://stackoverflow.com/a/23379710/9275156
                 * poll() will cause performance problems, but take() won't
                 */
                Work work = this.queue.take();
                work.accept();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
