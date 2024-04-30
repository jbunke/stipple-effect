package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.native_calls;

import com.jordanbunke.stipple_effect.scripting.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptMap;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.MapTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class MapDefineNode extends StatementNode {
    private final ExpressionNode map, key, value;

    public MapDefineNode(
            final TextPosition position,
            final ExpressionNode map,
            final ExpressionNode key,
            final ExpressionNode value
    ) {
        super(position);

        this.map = map;
        this.key = key;
        this.value = value;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        map.semanticErrorCheck(symbolTable);
        key.semanticErrorCheck(symbolTable);
        value.semanticErrorCheck(symbolTable);

        final TypeNode
                mapType = map.getType(symbolTable),
                keyType = key.getType(symbolTable),
                valueType = value.getType(symbolTable);

        if (!(mapType instanceof MapTypeNode m))
            ScrippleErrorListener.fireError(
                    here, // TODO - expecting map but got
                    map.getPosition(), mapType.toString());
        else if (!keyType.equals(m.getKeyType()))
            ScrippleErrorListener.fireError(
                    here, // TODO - key type mismatch
                    key.getPosition(), m.getKeyType().toString(),
                    keyType.toString());
        else if (!valueType.equals(m.getValueType()))
            ScrippleErrorListener.fireError(
                    here, // TODO - value type mismatch
                    value.getPosition(), m.getValueType().toString(),
                    valueType.toString());
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final ScriptMap m = (ScriptMap) map.evaluate(symbolTable);
        final Object
                k = key.evaluate(symbolTable),
                v = value.evaluate(symbolTable);

        m.put(k, v);

        return FuncControlFlow.cont();
    }
}
