package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.function;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.function.HelperFuncNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.Variable;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public abstract class FuncRetrievableNode extends ExpressionNode {
    final String name;

    public FuncRetrievableNode(
            final TextPosition position, final String name
    ) {
        super(position);
        this.name = name;
    }

    HelperFuncNode getFunc(final SymbolTable symbolTable) {
        final Variable var = symbolTable.get(SymbolTable.funcWithName(name));

        if (var == null) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.UNDEFINED_FUNC,
                    getPosition(), name);

            return null;
        }

        return (HelperFuncNode) var.get();
    }
}
