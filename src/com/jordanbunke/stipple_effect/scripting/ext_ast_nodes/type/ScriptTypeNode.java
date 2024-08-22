package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type;

import com.jordanbunke.delta_time.scripting.ast.nodes.function.HeadFuncNode;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public class ScriptTypeNode extends SEExtTypeNode {
    public static final String NAME = "script";
    private static final ScriptTypeNode INSTANCE;

    public ScriptTypeNode(final TextPosition position) {
        super(position);
    }

    private ScriptTypeNode() {
        this(TextPosition.N_A);
    }

    static {
        INSTANCE = new ScriptTypeNode();
    }

    public static ScriptTypeNode get() {
        return INSTANCE;
    }

    @Override
    public boolean complies(final Object o) {
        return o instanceof HeadFuncNode;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
