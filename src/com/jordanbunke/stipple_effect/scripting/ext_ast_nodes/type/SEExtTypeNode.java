package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type;

import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

public abstract class SEExtTypeNode extends TypeNode {
    public SEExtTypeNode(
            final TextPosition position
    ) {
        super(position);
    }

    @Override
    public final boolean hasSize() {
        return false;
    }

    @Override
    public final boolean equals(final Object o) {
        return getClass().equals(o.getClass());
    }

    @Override
    public final int hashCode() {
        return 0;
    }
}
