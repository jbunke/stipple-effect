package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement;

import com.jordanbunke.delta_time.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.util.Arguments;

public abstract class ExtStatementNode extends StatementNode {
    protected final Arguments arguments;

    public ExtStatementNode(
            final TextPosition position, final Arguments arguments
    ) {
        super(position);

        this.arguments = arguments;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        arguments.semanticErrorCheck(symbolTable, getPosition());
    }

    protected abstract String callName();
}
