package cn.itscloudy.snippedjava.tool.other;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A container object contains the execution result of an Supplier
 *
 * @see Optional
 */
public class OptionalResult<T> {
    private Exception e;
    private T value;

    private OptionalResult() {

    }

    public static <T> OptionalResult<T> of(Supplier<? extends T> supplier) {
        try {
            T value = supplier.get();
            return ofValue(value);
        } catch (Exception e) {
            return ofException(e);
        }
    }

    private static <T> OptionalResult<T> ofException(Exception e) {
        OptionalResult<T> holder = new OptionalResult<>();
        holder.e = e;
        return holder;
    }

    private static <T> OptionalResult<T> ofValue(T value) {
        OptionalResult<T> holder = new OptionalResult<>();
        holder.value = value;
        return holder;
    }

    public <X extends Throwable> T orElseThrow(Function<Exception, X> exceptionExchanger) throws X {
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

    public void ifSuccess(Consumer<? super T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }

    public boolean isSuccess() {
        return value != null;
    }

    public T get() {
        return value;
    }

    public Exception exception() {
        return e;
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
