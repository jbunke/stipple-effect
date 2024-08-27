package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type;

import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.project.SaveConfig;

public final class SaveConfigTypeNode extends SEExtTypeNode {
    public static final String NAME = "save_config";

    private static final SaveConfigTypeNode INSTANCE;

    public SaveConfigTypeNode(final TextPosition position) {
        super(position);
    }

    private SaveConfigTypeNode() {
        this(TextPosition.N_A);
    }

    static {
        INSTANCE = new SaveConfigTypeNode();
    }

    public static SaveConfigTypeNode get() {
        return INSTANCE;
    }

    @Override
    public boolean complies(final Object o) {
        return o instanceof SaveConfig;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
