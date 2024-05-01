package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.assignment;

import com.jordanbunke.stipple_effect.scripting.util.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.util.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
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

        public String toString() {
            return " " + switch (this) {
                case MODULO -> "%";
                case DIVIDE -> "/";
                case MULTIPLY -> "*";
                case SUBTRACT -> "-";
                case ADD -> "+";
                case AND -> "&";
                case OR -> "|";
            } + "= ";
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
                        ScrippleErrorListener.Message.VAR_NOT_BOOL,
                        getAssignable().getPosition(),
                        assignableType.toString());
            if (!operandType.equals(boolType))
                ScrippleErrorListener.fireError(
                        ScrippleErrorListener.Message.VAR_NOT_BOOL,
                        operand.getPosition(),
                        operandType.toString());
        } else {
            if (!numTypes.contains(assignableType))
                ScrippleErrorListener.fireError(
                        ScrippleErrorListener.Message.VAR_NOT_NUM,
                        getAssignable().getPosition(),
                        assignableType.toString());
            if (!numTypes.contains(operandType))
                ScrippleErrorListener.fireError(
                        ScrippleErrorListener.Message.VAR_NOT_NUM,
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
                                bef = ((Number) before).doubleValue(),
                                op = ((Number) operand
                                        .evaluate(symbolTable)).doubleValue();

                        if (op == 0d && operator.isDiv())
                            ScrippleErrorListener.fireError(
                                    ScrippleErrorListener.Message.DIV_BY_ZERO,
                                    operand.getPosition());

                        final Double res = switch (operator) {
                            case ADD -> bef + op;
                            case SUBTRACT -> bef - op;
                            case MULTIPLY -> bef * op;
                            case DIVIDE -> bef / op;
                            case MODULO -> bef % op;
                            default -> bef;
                        };

                        if (isInt)
                            yield res.intValue();

                        yield res;
                    }
                };
        getAssignable().update(symbolTable, after);

        return FuncControlFlow.cont();
    }

    @Override
    public String toString() {
        return getAssignable() + operator.toString() + operand + ";";
    }
}
