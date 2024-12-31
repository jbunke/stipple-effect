package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type;

import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.util.Light;

public final class LightTypeNode extends SEExtTypeNode {
    public static final String NAME = "light";

    private static final LightTypeNode INSTANCE;

    public LightTypeNode(final TextPosition position) {
        super(position);
    }

    private LightTypeNode() {
        this(TextPosition.N_A);
    }

    static {
        INSTANCE = new LightTypeNode();
    }

    public static LightTypeNode get() {
        return INSTANCE;
    }

    @Override
    public boolean complies(final Object o) {
        return o instanceof Light;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
