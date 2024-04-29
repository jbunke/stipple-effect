package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable;

import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public sealed abstract class CollectionAssignableNode extends AssignableNode
        permits ListAssignableNode, ArrayAssignableNode {
    private final ExpressionNode index;

    public CollectionAssignableNode(
            final TextPosition position,
            final String name,
            final ExpressionNode index
    ) {
        super(position, name);

        this.index = index;
    }

    @Override
    public void update(
            final SymbolTable symbolTable,
            final Object value
    ) {
        // TODO
    }

    @Override
    public final void semanticErrorCheck(final SymbolTable symbolTable) {
        super.semanticErrorCheck(symbolTable);

        index.semanticErrorCheck(symbolTable);

        final SimpleTypeNode
                intType = new SimpleTypeNode(SimpleTypeNode.Type.INT);
        final TypeNode indexType = index.getType(symbolTable);

        if (!indexType.equals(intType))
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.INDEX_NOT_INT,
                    index.getPosition(), indexType.toString());
    }
}
