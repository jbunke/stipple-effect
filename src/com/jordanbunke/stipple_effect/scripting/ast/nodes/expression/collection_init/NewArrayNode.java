package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.collection_init;

import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptArray;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public final class NewArrayNode extends ExpressionNode {
    private final TypeNode elementType;
    private final ExpressionNode length;

    public NewArrayNode(
            final TextPosition position,
            final TypeNode elementType,
            final ExpressionNode length
    ) {
        super(position);

        this.elementType = elementType;
        this.length = length;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        length.semanticErrorCheck(symbolTable);
        elementType.semanticErrorCheck(symbolTable);

        final TypeNode lengthType = length.getType(symbolTable);

        if (!lengthType.equals(TypeNode.getInt()))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARR_LENGTH_NOT_INT,
                    getPosition(), lengthType.toString());
    }

    @Override
    public ScriptArray evaluate(final SymbolTable symbolTable) {
        final int l = (int) length.evaluate(symbolTable);

        if (l < 0)
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARR_LENGTH_NEGATIVE,
                    getPosition(), String.valueOf(l));

        return new ScriptArray(l);
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return new CollectionTypeNode(
                CollectionTypeNode.Type.ARRAY, elementType);
    }

    @Override
    public String toString() {
        return "new " + elementType + "[" + length + "]";
    }
}
