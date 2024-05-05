package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.property;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

import java.util.Set;

public final class CharAtNode extends NativePropertyFuncNode {
    private final ExpressionNode index;

    public CharAtNode(
            final TextPosition position,
            final ExpressionNode owner,
            final ExpressionNode index
    ) {
        super(position, owner,
                Set.of(TypeNode.getString()));

        this.index = index;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        index.semanticErrorCheck(symbolTable);

        super.semanticErrorCheck(symbolTable);

        final SimpleTypeNode intType = TypeNode.getInt();

        final TypeNode
                indexType = index.getType(symbolTable);

        if (!indexType.equals(intType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    index.getPosition(), "Index",
                    "int", indexType.toString());
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final int i = (int) index.evaluate(symbolTable);
        final String s = (String) getOwner().evaluate(symbolTable);

        if (i >= 0 && i < s.length())
            return s.charAt(i);
        else
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.INDEX_OUT_OF_BOUNDS,
                    index.getPosition(), String.valueOf(i),
                    String.valueOf(s.length()), String.valueOf(false));

        return null;
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getChar();
    }

    @Override
    String callName() {
        return "at()";
    }
}
