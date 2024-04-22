package com.jordanbunke.stipple_effect.scripting.ast.nodes.types;

import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.ScrippleASTNode;

public abstract class ScrippleTypeNode extends ScrippleASTNode {
    public ScrippleTypeNode(final TextPosition position) {
        super(position);
    }
}
