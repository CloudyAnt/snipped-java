package cn.itscloudy.snippedjava.pattern.flag;

/**
 * A FlagHolder must implement the {@link #getFlags()} and {@link #setFlags(int)} methods
 */
public abstract class FlagsHolder<F extends Flag> {

    protected abstract int getFlags();

    protected abstract void setFlags(int newFlags);

    public FlagsHolder<F> addFlag(F f) {
        int newFlags = f.addTo(getFlags());
        setFlags(newFlags);
        return this;
    }

    public FlagsHolder<F> removeFlag(F f) {
        int newFlags = f.removeFrom(getFlags());
        setFlags(newFlags);
        return this;
    }

    public boolean matchFlag(F f) {
        return f.match(getFlags());
    }
}
