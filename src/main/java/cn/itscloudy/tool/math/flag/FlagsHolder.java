package cn.itscloudy.tool.math.flag;

public abstract class FlagsHolder<F extends Flag> {

    abstract protected int getFlags();

    abstract protected void setFlags(int newFlags);

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
