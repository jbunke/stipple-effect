package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls;

import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.util.Set;

public abstract sealed class NativeFuncWithOwnerNode extends ExpressionNode
        permits ImageBoundNode, ColorAtPixelNode, ColorChannelNode,
        MapKeysetNode, MapLookupNode, ContainsNode, ImageSectionNode,
        SubstringNode, CharAtNode {
    private final ExpressionNode owner;
    private final Set<TypeNode> acceptedTypes;

    NativeFuncWithOwnerNode(
            final TextPosition position,
            final ExpressionNode owner,
            final Set<TypeNode> acceptedTypes
    ) {
        super(position);

        this.owner = owner;
        this.acceptedTypes = acceptedTypes;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        owner.semanticErrorCheck(symbolTable);

        final TypeNode type = owner.getType(symbolTable);

        if (!acceptedTypes.contains(type)) {
            final String validTypes = acceptedTypes.size() == 1
                    ? acceptedTypes.stream().toList().get(0).toString()
                    : acceptedTypes.stream().map(TypeNode::toString)
                    .reduce((a, b) -> a + "\", \"" + b).orElse("");

            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.EXPECTED_FOR_CALL,
                    getPosition(), callName(), validTypes, type.toString());
        }
    }

    public ExpressionNode getOwner() {
        return owner;
    }

    abstract String callName();

    @Override
    public String toString() {
        return owner + "." + callName();
    }
}
