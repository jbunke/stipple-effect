package com.jordanbunke.stipple_effect.scripting.ast.collection;

public interface ScriptCollection {
    void add(final int index, final Object element);
    void add(final Object element);
    void remove(final int index);
    boolean contains(final Object element);
    Object get(final int index);
    void set(final int index, final Object element);
    int size();

    String collectionName();
}
