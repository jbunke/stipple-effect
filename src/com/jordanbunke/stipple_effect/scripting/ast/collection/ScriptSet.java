package com.jordanbunke.stipple_effect.scripting.ast.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class ScriptSet implements ScriptCollection {
    private final List<Object> structure;

    public ScriptSet() {
        structure = new ArrayList<>();
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
        return structure.get(index);
    }

    @Override
    public void set(int index, Object element) {}

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
        final List<String> strings = structure.stream()
                .map(Object::toString).sorted().toList();

        return switch (structure.size()) {
            case 0 -> "{}";
            case 1 -> "{" + strings.get(0) + "}";
            default -> "{" + strings.stream()
                    .reduce((a, b) -> a + ", " + b).orElse("") + "}";
        };
    }
}
