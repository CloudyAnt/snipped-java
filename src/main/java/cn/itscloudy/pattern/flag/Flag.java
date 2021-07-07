package cn.itscloudy.pattern.flag;

/**
 * Each Flag object referred to a bit in the bitmap(using int)
 */
public interface Flag {

    /**
     * The referring bit ordinal.
     * The {@link Enum} contains this method.
     * You can also use id of an entity as ordinal.
     */
    int ordinal();

    default int flagValue() {
        return (int) Math.pow(2, ordinal());
    }

    default int addTo(int flags) {
        return flags | flagValue();
    }

    default int removeFrom(int flags) {
        return flags & ~flagValue();
    }

    default boolean match(int flags) {
        return (flagValue() & flags) == flagValue();
    }

    default boolean antiMatch(int flags) {
        return (flagValue() & flags) == 0;
    }

    static int collect(Flag... flags) {
        int flagsI = 0;
        for (Flag flag : flags) {
            flagsI = flag.addTo(flagsI);
        }
        return flagsI;
    }

    static Operator operate(int flags) {
        return new Operator(flags);
    }

    class Operator {

        public int flags;

        private Operator(int flags) {
            this.flags = flags;
        }

        public Operator add(Flag another) {
            flags |= another.flagValue();
            return this;
        }

        public Operator remove(Flag another) {
            flags = flags & ~another.flagValue();
            return this;
        }

        public int toFlags() {
            return flags;
        }
    }
}
