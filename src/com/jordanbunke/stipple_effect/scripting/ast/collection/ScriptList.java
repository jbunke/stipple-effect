package com.jordanbunke.stipple_effect.scripting.ast.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class ScriptList implements ScriptCollection {
    private final List<Object> structure;

    public ScriptList() {
        structure = new ArrayList<>();
    }

    public ScriptList(final Stream<Object> elements) {
        this();
        elements.forEach(structure::add);
    }

    @Override
    public void add(final int index, final Object element) {
        structure.add(index, element);
    }

    @Override
    public void add(final Object element) {
        structure.add(element);
    }

    @Override
    public void remove(final int index) {
        structure.remove(index);
    }

    @Override
    public boolean contains(final Object element) {
        return structure.contains(element);
    }

    @Override
    public Object get(final int index) {
        return structure.get(index);
    }

    @Override
    public void set(final int index, final Object element) {
        structure.set(index, element);
    }

    @Override
    public int size() {
        return structure.size();
    }

    @Override
    public Stream<Object> stream() {
        return structure.stream();
    }

    @Override
    public String toString() {
        return switch (structure.size()) {
            case 0 -> "<>";
            case 1 -> "<" + structure.get(0) + ">";
            default -> "<" + structure.stream()
                    .map(Object::toString)
                    .reduce((a, b) -> a + ", " + b).orElse("") + ">";
        };
    }
}
