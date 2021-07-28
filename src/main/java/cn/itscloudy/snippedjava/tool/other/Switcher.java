package cn.itscloudy.snippedjava.tool.other;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Logic switcher
 */
public class Switcher<T> {
    private final List<Case<T>> cases;
    private final Consumer<T> def;

    /**
     * @param def equivalent to default segment
     */
    private Switcher(Consumer<T> def) {
        this.def = def;
        this.cases = new ArrayList<>();
    }

    public void accept(T t) {
        CaseResultCode code = CaseResultCode.UNMATCHED;
        for (Case<T> c : cases) {
            if (CaseResultCode.UNMATCHED.equals(code)) {
                code = c.match(t);
            } else if (CaseResultCode.GOON.equals(code)) {
                code = c.handle(t);
            }

            if (CaseResultCode.BROKEN.equals(code)) {
                return;
            }
        }
        if (def != null) {
            def.accept(t);
        }
    }

    /**
     * Get a Switch.Builder by specific type
     *
     * @return A Switch.Builder
     */
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private final List<Case<T>> cases;

        private Builder() {
            this.cases = new ArrayList<>();
        }

        /**
         * This case will definitely not break
         *
         * @param janitor The gatekeeper
         * @param handler The handler
         * @return Current Builder
         */
        public Builder<T> notBreak(Predicate<T> janitor, Consumer<T> handler) {
            cases.add(new Case<>(janitor, t -> {
                handler.accept(t);
                return false;
            }));
            return this;
        }

        /**
         * This case will do break after the handler's done
         *
         * @param janitor The gatekeeper
         * @param handler The handler
         * @return Current Builder
         */
        public Builder<T> doBreak(Predicate<T> janitor, Consumer<T> handler) {
            cases.add(new Case<>(janitor, t -> {
                handler.accept(t);
                return true;
            }));
            return this;
        }

        /**
         * This case will do break if the handler return true, not break if false
         *
         * @param janitor The gatekeeper
         * @param handler The handler
         * @return Current Builder
         */
        public Builder<T> mayBreak(Predicate<T> janitor, Predicate<T> handler) {
            cases.add(new Case<>(janitor, handler));
            return this;
        }

        /**
         * Finish building with default
         */
        public Switcher<T> end(Consumer<T> def) {
            Switcher<T> switcher = new Switcher<>(def);
            switcher.cases.addAll(this.cases);
            return switcher;
        }

        /**
         * Finish building without default
         */
        public Switcher<T> end() {
            return end(null);
        }
    }

    private static class Case<T> {
        private final Predicate<T> janitor;
        private final Predicate<T> handler;

        private Case(Predicate<T> janitor, Predicate<T> handler) {
            this.janitor = janitor;
            this.handler = handler;
        }

        /**
         * Match before handle
         */
        public CaseResultCode match(T value) {
            if (janitor.test(value)) {
                return handle(value);
            }
            return CaseResultCode.UNMATCHED;
        }

        /**
         * Handle value without match
         */
        public CaseResultCode handle(T value) {
            if (handler.test(value)) {
                return CaseResultCode.BROKEN;
            }
            return CaseResultCode.GOON;
        }
    }

    private enum CaseResultCode {
        UNMATCHED,
        GOON,
        BROKEN
    }
}
