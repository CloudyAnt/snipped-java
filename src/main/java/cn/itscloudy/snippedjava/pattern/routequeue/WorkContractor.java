package cn.itscloudy.snippedjava.pattern.routequeue;

import cn.itscloudy.snippedjava.pattern.flag.Flag;
import cn.itscloudy.snippedjava.pattern.flag.FlagsHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Contractor receive work then route it to worker by id
 *
 * @param <F> The flags hold by the worker, the work content could contain adding or removing flag
 */
public class WorkContractor<F extends Flag> {

    private final List<ArrayBlockingQueue<Work>> queues;
    public static boolean acceptingWork = true;

    public WorkContractor(int workersSize, int maxWorksSizePerWorker) {
        queues = new ArrayList<>(workersSize);
        ExecutorService workerThreadsPool = new ThreadPoolExecutor(workersSize, workersSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new WorkerThreadFactory());

        for (int i = 0; i < workersSize; i++) {
            ArrayBlockingQueue<Work> queue = new ArrayBlockingQueue<>(maxWorksSizePerWorker);
            queues.add(queue);
            workerThreadsPool.submit(new Worker(queue));
        }
    }

    /**
     * Accept a work
     *
     * @param id      used for routing
     * @param content the work content
     * @param <R>     the type to return
     */
    public <R> ReturnableWork<R> accept(Object id, Supplier<R> content) {
        int index = route(id);

        ReturnableWork<R> work = new ReturnableWork<>(content);
        queues.get(index).add(work);
        return work;
    }

    /**
     * Accept a work
     *
     * @param id      used for routing
     * @param content the work content
     */
    public VoidWork accept(Object id, Runnable content) {
        int index = route(id);

        VoidWork work = new VoidWork(content);
        queues.get(index).add(work);
        return work;
    }

    private int route(Object id) {
        int hash = id == null ? 0 : id.hashCode() ^ (id.hashCode() >>> 16);
        return hash % this.queues.size();
    }

    private static class WorkerThreadFactory implements ThreadFactory {
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        private WorkerThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "worker-thread-" + POOL_NUMBER.getAndIncrement();
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}
