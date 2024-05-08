package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type;

import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.tools.Tool;

public final class ToolTypeNode extends SEExtTypeNode {
    public static final String NAME = "tool";
    private static final ToolTypeNode INSTANCE;

    public ToolTypeNode(TextPosition position) {
        super(position);
    }

    public ToolTypeNode() {
        this(TextPosition.N_A);
    }

    static {
        INSTANCE = new ToolTypeNode();
    }

    public static ToolTypeNode get() {
        return INSTANCE;
    }

    @Override
    public boolean complies(final Object o) {
        return o instanceof Tool;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
