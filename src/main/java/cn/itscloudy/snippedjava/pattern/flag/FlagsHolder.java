package cn.itscloudy.snippedjava.pattern.flag;

/**
 * A FlagHolder must implement the {@link #currentFlags()} and {@link #assignFlags(int)} methods
 */
public interface FlagsHolder<F extends Flag> {

    int currentFlags();

    void assignFlags(int newFlags);

    default FlagsHolder<F> addFlag(F f) {
        int newFlags = f.addTo(currentFlags());
        assignFlags(newFlags);
        return this;
    }

    default FlagsHolder<F> removeFlag(F f) {
        int newFlags = f.removeFrom(currentFlags());
        assignFlags(newFlags);
        return this;
    }

    default boolean hasFlag(F f) {
        return f.match(currentFlags());
    }
}
