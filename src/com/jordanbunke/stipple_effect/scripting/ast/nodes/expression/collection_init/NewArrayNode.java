package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.collection_init;

import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.ScrippleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class NewArrayNode extends ExpressionNode {
    private final ScrippleTypeNode type;
    private final ExpressionNode length;

    public NewArrayNode(
            final TextPosition position,
            final ScrippleTypeNode type,
            final ExpressionNode length
    ) {
        super(position);

        this.type = type;
        this.length = length;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        final ScrippleTypeNode lengthType = length.getType(symbolTable);

        if (!lengthType.equals(new SimpleTypeNode(SimpleTypeNode.Type.INT)))
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.ARR_LENGTH_NOT_INT,
                    getPosition(), lengthType.toString());

        length.semanticErrorCheck(symbolTable);
        type.semanticErrorCheck(symbolTable);
    }

    @Override
    public Object[] evaluate(final SymbolTable symbolTable) {
        final int l = (int) length.evaluate(symbolTable);

        if (l < 0)
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.ARR_LENGTH_NEGATIVE,
                    getPosition(), String.valueOf(l));

        return type.createArray(l);
    }

    @Override
    public ScrippleTypeNode getType(final SymbolTable symbolTable) {
        return new CollectionTypeNode(CollectionTypeNode.Type.ARRAY,
                new SimpleTypeNode(SimpleTypeNode.Type.RAW));
    }
}
