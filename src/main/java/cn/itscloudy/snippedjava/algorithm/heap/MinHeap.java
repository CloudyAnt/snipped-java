package cn.itscloudy.snippedjava.algorithm.heap;

import java.util.ArrayList;
import java.util.Collection;

public class MinHeap<E extends Comparable<E>> extends ArrayList<E> {

    @Override
    public boolean add(E e) {
        boolean added = super.add(e);
        if (added) {
            int index = size() - 1;
            while (index > 0) {
                int parentIndex = (index - 1) / 2;
                E parent = get(parentIndex);
                if (parent.compareTo(e) > 0) {
                    set(parentIndex, e);
                    set(index, parent);
                }
                index = parentIndex;
            }
        }
        return added;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        c.forEach(this::add);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        c.forEach(this::add);
        return true;
    }

    @Override
    public void add(int index, E e) {
        add(e);
    }
}
