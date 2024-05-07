package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.function;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.function.HelperFuncNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.FuncTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public final class HOFuncNode extends FuncRetrievableNode {
    public HOFuncNode(
            final TextPosition position, final String name
    ) {
        super(position, name);
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        // return value is not needed, but call is needed to
        // trigger an error if func association is not found
        getFunc(symbolTable);
    }

    @Override
    public HelperFuncNode evaluate(final SymbolTable symbolTable) {
        return getFunc(symbolTable);
    }

    @Override
    public FuncTypeNode getType(final SymbolTable symbolTable) {
        final HelperFuncNode func = getFunc(symbolTable);

        return func != null ? func.getType() : null;
    }

    @Override
    public String toString() {
        return "::" + name;
    }
}
