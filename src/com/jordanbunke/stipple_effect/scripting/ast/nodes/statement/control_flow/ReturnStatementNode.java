package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.control_flow;

import com.jordanbunke.stipple_effect.scripting.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.ScriptFunctionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class ReturnStatementNode extends StatementNode {
    private final ExpressionNode expression;

    private ReturnStatementNode(
            final TextPosition position,
            final ExpressionNode expression
    ) {
        super(position);

        this.expression = expression;
    }

    public static ReturnStatementNode forVoid(
            final TextPosition position
    ) {
        return new ReturnStatementNode(position, null);
    }

    public static ReturnStatementNode forTyped(
            final TextPosition position, final ExpressionNode expression
    ) {
        return new ReturnStatementNode(position, expression);
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        final ScriptFunctionNode func = symbolTable.getFunc();

        if (func != null) {
            final TypeNode
                    returnType = func.getReturnType(),
                    exprType = expression != null
                            ? expression.getType(symbolTable) : null;
            final TextPosition pos = expression != null
                    ? expression.getPosition() : getPosition();

            final boolean
                    bothNull = exprType == null && returnType == null,
                    bothNonNull = exprType != null && returnType != null,
                    bothEqual = bothNonNull && exprType.equals(returnType);
            final boolean typeEquality = bothNull || bothEqual;

            if (!typeEquality)
                ScrippleErrorListener.fireError(
                        here, // TODO - function return type does not match expression in return statement
                        pos, String.valueOf(returnType),
                        String.valueOf(exprType));
        }
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        return expression == null
                ? FuncControlFlow.returnVoid()
                : FuncControlFlow.returnVal(expression.evaluate(symbolTable));
    }
}
