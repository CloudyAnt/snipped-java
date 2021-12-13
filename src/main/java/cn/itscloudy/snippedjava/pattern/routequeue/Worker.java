package cn.itscloudy.snippedjava.pattern.routequeue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

public class Worker implements Callable<Boolean> {

    private final ArrayBlockingQueue<Work> queue;

    private final int maxWorksSize;

    public Worker(int maxWorksSize) {
        this.queue = new ArrayBlockingQueue<>(maxWorksSize);
        this.maxWorksSize = maxWorksSize;
    }

    public void add(Work work) {
        queue.add(work);
    }

    @Override
    public Boolean call() {
        while (WorkContractor.acceptingWork) {
            try {
                /*
                 * According to https://stackoverflow.com/a/23379710/9275156
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
