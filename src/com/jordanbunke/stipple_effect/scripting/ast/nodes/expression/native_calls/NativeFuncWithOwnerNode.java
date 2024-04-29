package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls;

import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.util.Set;

public abstract sealed class NativeFuncWithOwnerNode extends ExpressionNode
        permits ImageBoundNode, ColorAtPixelNode, ColorChannelNode,
        MapKeysetNode, MapLookupNode, ContainsNode {
    private final ExpressionNode owner;
    private final Set<TypeNode> acceptedTypes;
    private final ScrippleErrorListener.Message errorMessage;

    NativeFuncWithOwnerNode(
            final TextPosition position,
            final ExpressionNode owner,
            final Set<TypeNode> acceptedTypes,
            final ScrippleErrorListener.Message errorMessage
    ) {
        super(position);

        this.owner = owner;
        this.acceptedTypes = acceptedTypes;
        this.errorMessage = errorMessage;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        owner.semanticErrorCheck(symbolTable);

        final TypeNode type = owner.getType(symbolTable);

        if (!acceptedTypes.contains(type))
            ScrippleErrorListener.fireError(
                    errorMessage, getPosition(), type.toString());
    }

    public ExpressionNode getOwner() {
        return owner;
    }
}
