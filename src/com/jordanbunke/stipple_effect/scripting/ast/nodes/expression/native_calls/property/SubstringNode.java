package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.property;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

import java.util.Set;

public final class SubstringNode extends NativePropertyFuncNode {
    private final ExpressionNode beginning, end;

    public SubstringNode(
            final TextPosition position,
            final ExpressionNode owner,
            final ExpressionNode beginning,
            final ExpressionNode end
    ) {
        super(position, owner,
                Set.of(TypeNode.getString()));

        this.beginning = beginning;
        this.end = end;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        beginning.semanticErrorCheck(symbolTable);
        end.semanticErrorCheck(symbolTable);

        super.semanticErrorCheck(symbolTable);

        final SimpleTypeNode intType = TypeNode.getInt();

        final TypeNode
                begType = beginning.getType(symbolTable),
                endType = end.getType(symbolTable);

        if (!begType.equals(intType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    beginning.getPosition(), "Beginning index",
                    "int", begType.toString());
        if (!endType.equals(intType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    beginning.getPosition(), "End index",
                    "int", endType.toString());
    }

    @Override
    public String evaluate(final SymbolTable symbolTable) {
        final int begIndex = (int) beginning.evaluate(symbolTable),
                endIndex = (int) end.evaluate(symbolTable);

        final String s = (String) getOwner().evaluate(symbolTable);

        if (begIndex >= 0 && endIndex <= s.length() && begIndex < endIndex)
            return s.substring(begIndex, endIndex);
        else {
            if (begIndex < 0)
                ScriptErrorLog.fireError(
                        ScriptErrorLog.Message.SUB_BEG_OUT_OF_BOUNDS,
                        beginning.getPosition(), String.valueOf(begIndex));
            if (endIndex > s.length())
                ScriptErrorLog.fireError(
                        ScriptErrorLog.Message.SUB_END_OUT_OF_BOUNDS,
                        end.getPosition(), String.valueOf(s.length()),
                        String.valueOf(endIndex));
            if (begIndex >= endIndex)
                ScriptErrorLog.fireError(
                        ScriptErrorLog.Message.SUB_END_GEQ_BEG,
                        beginning.getPosition(), String.valueOf(begIndex),
                        String.valueOf(endIndex));
        }

        return null;
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getString();
    }

    @Override
    String callName() {
        return "sub()";
    }
}
