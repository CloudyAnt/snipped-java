package cn.itscloudy.snippedjava.feature.j21;

import java.util.ArrayList;
import java.util.List;

/**
 * A virtual thread is a light-weight thread that is managed by the virtual thread scheduler.
 * Multiple virtual threads can be run on the same carrier thread.
 */
public class VirtualThreadFeat {

    List<String> runAndGetCarrierThreadNames() throws InterruptedException {
        List<String> carrierThreadNames = new ArrayList<>();
        Thread t1 = Thread.ofVirtual().name("vt-1").start(() -> {
            carrierThreadNames.add(Thread.currentThread().getName());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = Thread.ofVirtual().name("vt-2").start(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            carrierThreadNames.add(Thread.currentThread().getName());
        });

        t1.join();
        t2.join();
        return carrierThreadNames; // Carrier thread names. They are supposed to be the same.
    }
}
