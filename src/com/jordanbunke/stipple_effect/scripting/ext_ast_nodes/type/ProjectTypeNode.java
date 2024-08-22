package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type;

import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.project.SEContext;

public final class ProjectTypeNode extends SEExtTypeNode {
    public static final String NAME = "project";
    private static final ProjectTypeNode INSTANCE;

    public ProjectTypeNode(final TextPosition position) {
        super(position);
    }

    private ProjectTypeNode() {
        this(TextPosition.N_A);
    }

    static {
        INSTANCE = new ProjectTypeNode();
    }

    public static ProjectTypeNode get() {
        return INSTANCE;
    }

    @Override
    public boolean complies(final Object o) {
        return o instanceof SEContext;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
