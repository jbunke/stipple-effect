package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.scoped;

import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.util.Set;

public abstract class ScopedNativeCallNode extends ExpressionNode {
    private final ExpressionNode scope;
    private final Set<TypeNode> acceptedTypes;

    ScopedNativeCallNode(
            final TextPosition position,
            final ExpressionNode scope,
            final Set<TypeNode> acceptedTypes
    ) {
        super(position);

        this.scope = scope;
        this.acceptedTypes = acceptedTypes;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        scope.semanticErrorCheck(symbolTable);

        final TypeNode type = scope.getType(symbolTable);

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

    public ExpressionNode getScope() {
        return scope;
    }

    abstract String callName();

    @Override
    public String toString() {
        return scope + "." + callName();
    }
}
