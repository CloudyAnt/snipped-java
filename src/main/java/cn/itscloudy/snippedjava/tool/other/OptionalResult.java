package cn.itscloudy.snippedjava.tool.other;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A container object contains the execution result of a Supplier
 *
 * @see Optional
 */
public class OptionalResult<T> {
    private Throwable e;
    private T value;
    private long elapsedTime;

    private OptionalResult() {

    }

    public static <T> OptionalResult<T> of(Callable<? extends T> callable, boolean recordEt) {
        if (!recordEt) {
            return of(callable);
        }

        long startTime = System.currentTimeMillis();
        OptionalResult<T> result = of(callable);
        result.elapsedTime = System.currentTimeMillis() - startTime;
        return result;
    }

    public static <T> OptionalResult<T> of(Callable<? extends T> callable) {
        try {
            return ofValue(callable.call());
        } catch (Exception e) {
            return ofThrowable(e);
        }
    }

    private static <T> OptionalResult<T> ofThrowable(Throwable e) {
        OptionalResult<T> holder = new OptionalResult<>();
        holder.e = e;
        return holder;
    }

    private static <T> OptionalResult<T> ofValue(T value) {
        OptionalResult<T> holder = new OptionalResult<>();
        holder.value = value;
        return holder;
    }

    public <X extends Throwable> T orElseThrow(Function<Throwable, X> exceptionExchanger) throws X {
        if (e != null) {
            throw exceptionExchanger.apply(e);
        }
        return value;
    }

    public T orElse(T other) {
        if (e != null) {
            return other;
        }
        return value;
    }

    public T orElseGet(Supplier<? extends T> other) {
        if (e != null) {
            return other.get();
        }
        return value;
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }

    public boolean isPresent() {
        return value != null;
    }

    public T get() {
        return value;
    }

    public Throwable getE() {
        return e;
    }

    public long elapsedTime() {
        return elapsedTime;
    }

    public Optional<T> optional() {
        return Optional.ofNullable(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof OptionalResult)) {
            return false;
        }

        OptionalResult<?> other = (OptionalResult<?>) obj;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value != null
                ? String.format("OptionalResult[%s]", value)
                : String.format("OptionalResult(%s)", e.getClass().getSimpleName());
    }
}
