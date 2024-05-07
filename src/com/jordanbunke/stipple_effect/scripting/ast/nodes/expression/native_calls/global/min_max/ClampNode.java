package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.global.min_max;

import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

import java.util.Set;

public final class ClampNode extends ExpressionNode {
    private final ExpressionNode min, value, max;

    public ClampNode(
            final TextPosition position,
            final ExpressionNode min,
            final ExpressionNode value,
            final ExpressionNode max
    ) {
        super(position);

        this.min = min;
        this.value = value;
        this.max = max;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        min.semanticErrorCheck(symbolTable);
        value.semanticErrorCheck(symbolTable);
        max.semanticErrorCheck(symbolTable);

        final Set<TypeNode> acceptedTypes = TypeNode.numTypes();

        final TypeNode
                minType = min.getType(symbolTable),
                valType = value.getType(symbolTable),
                maxType = max.getType(symbolTable);

        if (!minType.equals(valType))
            ScriptErrorLog.fireError(ScriptErrorLog.Message.DIFFERENT_TYPES,
                    min.getPosition(), "Clamp arguments", "min", "value",
                    minType.toString(), valType.toString());
        if (!maxType.equals(valType))
            ScriptErrorLog.fireError(ScriptErrorLog.Message.DIFFERENT_TYPES,
                    value.getPosition(), "Clamp arguments", "max", "value",
                    maxType.toString(), valType.toString());

        if (!acceptedTypes.contains(valType))
            ScriptErrorLog.fireError(ScriptErrorLog.Message.ARG_NOT_TYPE,
                    value.getPosition(), "Clamp value",
                    "int\" or \"float", valType.toString());
    }

    @Override
    public Number evaluate(final SymbolTable symbolTable) {
        final Object minVal = min.evaluate(symbolTable),
                val = value.evaluate(symbolTable),
                maxVal = max.evaluate(symbolTable);

        if (val instanceof Double d)
            return MathPlus.bounded((double) minVal, d, (double) maxVal);

        return MathPlus.bounded((int) minVal, (int) val, (int) maxVal);
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return value.getType(symbolTable);
    }

    @Override
    public String toString() {
        return "clamp(" + min + ", " + value + ", " + max + ")";
    }
}
