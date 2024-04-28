package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.operation;

import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.ScrippleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class TernaryOperationNode extends ExpressionNode {
    private final ExpressionNode condition, a, b;

    public TernaryOperationNode(
            final TextPosition position,
            final ExpressionNode condition,
            final ExpressionNode a,
            final ExpressionNode b
    ) {
        super(position);

        this.condition = condition;
        this.a = a;
        this.b = b;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        final ScrippleTypeNode
                cType = condition.getType(symbolTable),
                aType = a.getType(symbolTable),
                bType = b.getType(symbolTable);

        if (!cType.equals(new SimpleTypeNode(SimpleTypeNode.Type.BOOL)))
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.TERNARY_CONDITION_NOT_BOOL,
                    getPosition(), cType.toString());

        if (!aType.equals(bType))
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.TERNARY_BRANCHES_OF_DIFFERENT_TYPES,
                    getPosition(), aType.toString(), bType.toString());

        condition.semanticErrorCheck(symbolTable);
        a.semanticErrorCheck(symbolTable);
        b.semanticErrorCheck(symbolTable);
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        return (Boolean) condition.evaluate(symbolTable)
                ? a.evaluate(symbolTable)
                : b.evaluate(symbolTable);
    }

    @Override
    public ScrippleTypeNode getType(final SymbolTable symbolTable) {
        return a.getType(symbolTable);
    }
}
