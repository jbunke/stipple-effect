package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable;

import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptCollection;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.Variable;

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
    public final void update(
            final SymbolTable symbolTable, final Object value
    ) {
        final ScriptCollection c = evaluate(symbolTable);
        c.set((int) index.evaluate(symbolTable), value);
    }

    @Override
    public abstract ScriptCollection evaluate(final SymbolTable symbolTable);

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

    @Override
    public final TypeNode getType(SymbolTable symbolTable) {
        final Variable var = symbolTable.get(getName());

        if (var == null) {
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.UNDEFINED_VAR,
                    getPosition(), getName());
            return null;
        }

        final CollectionTypeNode colType = (CollectionTypeNode) var.getType();
        return colType.getElementType();
    }
}
