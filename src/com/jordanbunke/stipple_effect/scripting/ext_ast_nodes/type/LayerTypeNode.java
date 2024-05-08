package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type;

import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.util.LayerRep;

public final class LayerTypeNode extends SEExtTypeNode {
    public static final String NAME = "layer";
    private static final LayerTypeNode INSTANCE;

    public LayerTypeNode(final TextPosition position) {
        super(position);
    }

    public LayerTypeNode() {
        this(TextPosition.N_A);
    }

    static {
        INSTANCE = new LayerTypeNode();
    }

    public static LayerTypeNode get() {
        return INSTANCE;
    }

    @Override
    public boolean complies(final Object o) {
        return o instanceof LayerRep;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
