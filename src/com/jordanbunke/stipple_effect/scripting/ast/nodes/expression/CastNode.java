package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.BaseTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

import java.util.Map;
import java.util.Set;

public final class CastNode extends ExpressionNode {
    private static final Map<TypeNode, Set<TypeNode>>
            CAST_MATRIX = Map.ofEntries(
                    Map.entry(TypeNode.getInt(), Set.of(TypeNode.getInt(),
                            TypeNode.getFloat(), TypeNode.getChar())),
                    Map.entry(TypeNode.getFloat(), TypeNode.numTypes()),
                    Map.entry(TypeNode.getChar(), Set.of(TypeNode.getInt())),
                    Map.entry(TypeNode.getString(), Set.of(
                            TypeNode.getChar(), TypeNode.getBool(),
                            TypeNode.getInt(), TypeNode.getFloat())));

    /*
        {
            int : { int, float, char },
            float : { int, float },
            char : { int },
            string : { char, bool, int, float }
        }
    */

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

        if (!CAST_MATRIX.containsKey(type))
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_CT,
                    type.getPosition(), "Type \"" + type + "\" is uncastable");
        else if (!CAST_MATRIX.get(type).contains(eType))
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_CT,
                    e.getPosition(), "Type \"" + eType +
                            "\" cannot be cast to \"" + type + "\"");
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final Object val = e.evaluate(symbolTable);

        return switch (((BaseTypeNode) type).getType()) {
            case INT -> {
                if (val instanceof Double d)
                    yield d.intValue();
                else if (val instanceof Character c)
                    yield (int) c;

                yield val;
            }
            case FLOAT -> {
                if (val instanceof Integer i)
                    yield i.doubleValue();

                yield val;
            }
            case CHAR -> {
                if (val instanceof Integer i)
                    yield (char) ((int) i);

                yield val;
            }
            case STRING -> String.valueOf(val);
            default -> val;
        };
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
