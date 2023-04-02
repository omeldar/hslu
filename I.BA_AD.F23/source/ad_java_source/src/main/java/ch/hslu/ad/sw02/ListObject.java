package ch.hslu.ad.sw02;

import org.jetbrains.annotations.NotNull;

public class ListObject<E> extends Object {
    private Object value;
    private ListObject<E> next = null;
    private int index;

    private ListObject(){    }

    private ListObject(int index, Object value){
        this.index = index;
        this.value = value;
    }

    @NotNull
    public static ListObject makeObject(int index, Object value){
        return new ListObject(index, value);
    }

    public Object getValue(){
        return value;
    }

    public ListObject<E> getNext(){
        return next;
    }

    public void bind(ListObject<E> next){
        this.next = next;
    }

    @Override
    public String toString(){
        return value.toString();
    }
}
