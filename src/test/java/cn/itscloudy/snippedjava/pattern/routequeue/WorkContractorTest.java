package cn.itscloudy.snippedjava.pattern.routequeue;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class WorkContractorTest {

    @Test
    void shouldReturnNormally() {
        WorkContractor<WorkFlags> contractor = new WorkContractor<>(2, 3);

        String result = contractor.accept(1, () -> {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "FINISHED";
        }).getOr(10, () -> "TIME OUT");
        assertEquals("FINISHED", result);
    }

    @Test
    void shouldUseBottomUpPlanIfTimeout() {
        WorkContractor<WorkFlags> contractor = new WorkContractor<>(2, 3);

        String result = contractor.accept(1, () -> {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "FINISHED";
        }).getOr(10, () -> "TIME OUT");
        assertEquals("TIME OUT", result);
    }

    @Test
    void shouldFinishNormally() {
        WorkContractor<WorkFlags> contractor = new WorkContractor<>(2, 3);

        assertDoesNotThrow(() -> contractor.accept(1, () -> {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).timeout(10, () -> new RuntimeException("TIME OUT")));
    }

    @Test
    void shouldThrowIfTimeout() {
        WorkContractor<WorkFlags> contractor = new WorkContractor<>(2, 3);

        assertThrows(RuntimeException.class, () -> contractor.accept(1, () -> {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).timeout(10, () -> new RuntimeException("TIME OUT")));
    }

    @Test
    void shouldRunBottomUpPlanIfTimeout() {
        WorkContractor<WorkFlags> contractor = new WorkContractor<>(2, 3);

        AtomicBoolean bottomUpPlanCalled = new AtomicBoolean(false);
        contractor.accept(1, () -> {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).timeout(10, () -> bottomUpPlanCalled.set(true));

        assertTrue(bottomUpPlanCalled.get());
    }
}
