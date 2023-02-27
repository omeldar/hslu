package ch.hslu.sw02;

import jdk.jshell.spi.ExecutionControl;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class CustomList<E> implements List<E> {
    private int size = 0;
    private ListObject<E> first;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        ListObject<E> it = first;
        while (true){
            try{
                if (it.getValue().equals(o)){
                    return true;
                }
                it = it.getNext();
            } catch(NullPointerException nex){
                return false;
            }
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int currentIndex = 0;
            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public E next() {
                return (E) get(currentIndex);
            }
        };
    }

    @NotNull
    @Override
    public Object[] toArray() {
        ListObject<E> it = first;
        Object[] val = new Object[size];
        int last = 0;
        while(true){
            try{
                val[last] = it.getValue();
                it = it.getNext();
                last++;
            } catch (NullPointerException nex){
                return val;
            }
        }
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        if (a.length < size)
            return (T[]) Arrays.copyOf(this.toArray(), size, a.getClass()); // for sure not correct with this.toArray(), not sure how to do it correctly
        System.arraycopy(this.toArray(), 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    @Override
    public boolean add(E e) {
        first = ListObject.makeObject(size, e);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListObject<E> it = first;
        ListObject<E> before = it, after = it.getNext();
        while (true) {
            if (after == null) {
                if (after.equals(o)) {
                    first = null;
                    return true;
                }
                return false;
            }
            try {
                if (it.getValue().equals(o)) {
                    before.bind(after);
                    size--;
                }
                before = it;
                after = it.getNext();
                it = it.getNext();
            } catch (NullPointerException nex) {
                return true;
            }
        }
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        first = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }
}
