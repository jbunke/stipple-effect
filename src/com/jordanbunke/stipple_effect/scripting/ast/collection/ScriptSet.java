package com.jordanbunke.stipple_effect.scripting.ast.collection;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public final class ScriptSet implements ScriptCollection {
    private final Set<Object> structure;

    public ScriptSet() {
        structure = new HashSet<>();
    }

    public ScriptSet(final Stream<Object> elements) {
        this();
        elements.forEach(structure::add);
    }

    @Override
    public void add(final int index, final Object element) {}

    @Override
    public void add(final Object element) {
        structure.add(element);
    }

    @Override
    public void remove(final int index) {}

    @Override
    public boolean contains(final Object element) {
        return structure.contains(element);
    }

    @Override
    public Object get(int index) {
        return null;
    }

    @Override
    public void set(int index, Object element) {}

    @Override
    public int size() {
        return structure.size();
    }

    @Override
    public String collectionName() {
        return "set - {}";
    }
}
