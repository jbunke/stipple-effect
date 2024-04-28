package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.assignment;

import com.jordanbunke.stipple_effect.scripting.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable.AssignableNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class OperandAssignmentNode extends AssignmentNode {
    public enum Operator {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, MODULO, AND, OR
    }

    private final Operator operator;
    private final ExpressionNode operand;

    public OperandAssignmentNode(
            final TextPosition position,
            final AssignableNode assignable,
            final Operator operator,
            final ExpressionNode operand
    ) {
        super(position, assignable);

        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        // TODO
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        // TODO

        return FuncControlFlow.cont();
    }
}
