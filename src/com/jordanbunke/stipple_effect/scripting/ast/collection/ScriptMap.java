package com.jordanbunke.stipple_effect.scripting.ast.collection;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public final class ScriptMap extends HashMap<Object, Object> {
    @Override
    public String toString() {
        final List<Object> keys = keySet().stream()
                .sorted(Comparator.comparing(Object::toString)).toList();

        return switch (keys.size()) {
            case 0 -> "{:}";
            case 1 -> "{ " + keys.get(0) + ":" + get(keys.get(0)) + " }";
            default -> "{" + keys.stream()
                    .map(k -> k + ":" + get(k))
                    .reduce((a, b) -> a + ", " + b).orElse("") + "}";
        };
    }
}
