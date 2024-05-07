package com.jordanbunke.stipple_effect.scripting.ast.nodes;

import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public final class IllegalLanguageFeatureNode extends ASTNode {
    private final String description;

    public IllegalLanguageFeatureNode(
            final TextPosition position,
            final String description
    ) {
        super(position);
        this.description = description;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_CT,
                getPosition(), toString());
    }

    @Override
    public String toString() {
        return "Illegal language feature: " + description;
    }
}
