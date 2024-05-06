package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

import java.util.Set;

public final class CastNode extends ExpressionNode {
    private final TypeNode type;
    private final ExpressionNode e;

    public CastNode(
            final TextPosition position,
            final TypeNode type, final ExpressionNode e
    ) {
        super(position);

        this.type = type;
        this.e = e;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        type.semanticErrorCheck(symbolTable);
        e.semanticErrorCheck(symbolTable);

        final TypeNode eType = e.getType(symbolTable);

        final Set<TypeNode> acceptedTypes = TypeNode.numTypes();

        if (!acceptedTypes.contains(type))
            ScriptErrorLog.fireError(ScriptErrorLog.Message.NAN,
                    type.getPosition(), "Cast type");
        if (!acceptedTypes.contains(eType))
            ScriptErrorLog.fireError(ScriptErrorLog.Message.NAN,
                    e.getPosition(), "Expression to be cast");
    }

    @Override
    public Number evaluate(final SymbolTable symbolTable) {
        final Number v = (Number) e.evaluate(symbolTable);
        final boolean castToInt = type.equals(TypeNode.getInt());

        if (v instanceof Integer i) {
            if (castToInt)
                return i;

            return i.doubleValue();
        } else if (v instanceof Double d) {
            if (castToInt)
                return d.intValue();

            return d;
        }

        return null;
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return type;
    }

    @Override
    public String toString() {
        return "(" + type + ") " + e;
    }
}
