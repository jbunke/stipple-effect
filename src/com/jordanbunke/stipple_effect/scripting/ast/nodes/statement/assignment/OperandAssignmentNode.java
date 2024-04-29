package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.assignment;

import com.jordanbunke.stipple_effect.scripting.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.assignable.AssignableNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.util.Set;

public final class OperandAssignmentNode extends AssignmentNode {
    public enum Operator {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, MODULO, AND, OR;

        private boolean isDiv() {
            return this == DIVIDE || this == MODULO;
        }

        private boolean isLogic() {
            return this == AND || this == OR;
        }
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
        super.semanticErrorCheck(symbolTable);

        final TypeNode
                assignableType = getAssignable().getType(symbolTable),
                operandType = operand.getType(symbolTable);
        final SimpleTypeNode
                intType = new SimpleTypeNode(SimpleTypeNode.Type.INT),
                boolType = new SimpleTypeNode(SimpleTypeNode.Type.BOOL),
                floatType = new SimpleTypeNode(SimpleTypeNode.Type.FLOAT);

        final Set<TypeNode> numTypes = Set.of(intType, floatType);

        if (operator.isLogic()) {
            if (!assignableType.equals(boolType))
                ScrippleErrorListener.fireError(
                        here,
                        getAssignable().getPosition(),
                        assignableType.toString());
            if (!operandType.equals(boolType))
                ScrippleErrorListener.fireError(
                        here,
                        operand.getPosition(),
                        operandType.toString());
        } else {
            if (!numTypes.contains(assignableType))
                ScrippleErrorListener.fireError(
                        here,
                        getAssignable().getPosition(),
                        assignableType.toString());
            if (!numTypes.contains(operandType))
                ScrippleErrorListener.fireError(
                        here,
                        operand.getPosition(),
                        operandType.toString());
        }
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final Object
                before = getAssignable().evaluate(symbolTable),
                after = switch (operator) {
                    case OR -> (Boolean) before ||
                            (Boolean) operand.evaluate(symbolTable);
                    case AND -> (Boolean) before &&
                            (Boolean) operand.evaluate(symbolTable);
                    case ADD, SUBTRACT, MULTIPLY, DIVIDE, MODULO -> {
                        final SimpleTypeNode intType =
                                new SimpleTypeNode(SimpleTypeNode.Type.INT);
                        final boolean isInt =
                                getAssignable().getType(symbolTable)
                                        .equals(intType);

                        final double
                                bef = (double) before,
                                op = (double) operand.evaluate(symbolTable);

                        if (op == 0d && operator.isDiv())
                            ScrippleErrorListener.fireError(
                                    here,
                                    operand.getPosition());

                        final Double res = switch (operator) {
                            case ADD -> bef + op;
                            case SUBTRACT -> bef - op;
                            case MULTIPLY -> bef * op;
                            case DIVIDE -> bef / op;
                            case MODULO -> bef % op;
                            default -> bef;
                        };

                        yield isInt ? Integer.valueOf(res.intValue()) : res;
                    }
                };
        getAssignable().update(symbolTable, after);

        return FuncControlFlow.cont();
    }
}
