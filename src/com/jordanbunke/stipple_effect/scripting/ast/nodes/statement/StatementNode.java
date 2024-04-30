package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement;

import com.jordanbunke.stipple_effect.scripting.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.ASTNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public abstract class StatementNode extends ASTNode {
    public StatementNode(final TextPosition position) {
        super(position);
    }

    public abstract FuncControlFlow execute(final SymbolTable symbolTable);
}