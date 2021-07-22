package cn.itscloudy.snippedjava.trick.lang;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Be able to be looped by "for" segment
 */
public class ForLoopUsable<T> implements Iterable<T> {

    private final T[] ts;

    @SafeVarargs
    public ForLoopUsable(T... ts) {
        this.ts = ts;
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {
        int cursor = 0;
        @Override
        public boolean hasNext() {
            return cursor < ts.length;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return ts[cursor++];
        }
    }

}
