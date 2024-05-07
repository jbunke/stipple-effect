package com.jordanbunke.stipple_effect.scripting.ast.nodes.function;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.Variable;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public final class HelperFuncNode extends FuncNode {
    private final String name;

    public HelperFuncNode(
            final TextPosition position,
            final FuncSignatureNode signature,
            final StatementNode body, final String name
    ) {
        super(position, signature, body);

        this.name = name;
    }

    public void link(final SymbolTable symbolTable) {
        symbolTable.put(SymbolTable.funcWithName(name),
                new Variable(false, getReturnType(), this));
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        final SymbolTable helperTable = new SymbolTable(this, symbolTable);
        super.semanticErrorCheck(helperTable);
    }

    @Override
    public String toString() {
        return "$" + name + super.toString();
    }
}
