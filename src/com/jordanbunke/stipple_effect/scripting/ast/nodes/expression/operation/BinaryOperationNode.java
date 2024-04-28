package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.operation;

import com.jordanbunke.stipple_effect.scripting.ScrippleEquality;
import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.ScrippleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.util.Set;

public final class BinaryOperationNode extends ExpressionNode {
    private enum Operator {
        ADD, SUBTRACT,
        MULTIPLY, DIVIDE, MODULO,
        RAISE,
        EQUAL, NOT_EQUAL, GT, LT, GEQ, LEQ,
        OR, AND;

        private static Operator fromString(final String s) {
            return switch (s) {
                case "+" -> ADD;
                case "-" -> SUBTRACT;
                case "*" -> MULTIPLY;
                case "/" -> DIVIDE;
                case "%" -> MODULO;
                case "^" -> RAISE;
                case "==" -> EQUAL;
                case "!=" -> NOT_EQUAL;
                case ">" -> GT;
                case "<" -> LT;
                case ">=" -> GEQ;
                case "<=" -> LEQ;
                case "||" -> OR;
                default -> AND;
            };
        }

        private boolean isLogic() {
            return ordinal() >= EQUAL.ordinal();
        }
    }

    private final Operator operator;
    private final ExpressionNode o1, o2;

    public BinaryOperationNode(
            final TextPosition position,
            final String operatorString,
            final ExpressionNode o1, final ExpressionNode o2
    ) {
        super(position);

        operator = Operator.fromString(operatorString);

        this.o1 = o1;
        this.o2 = o2;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        final ScrippleTypeNode o1Type = o1.getType(symbolTable),
                o2Type = o2.getType(symbolTable);

        switch (operator) {
            case AND, OR -> {
                final SimpleTypeNode boolType =
                        new SimpleTypeNode(SimpleTypeNode.Type.BOOL);

                if (!o1Type.equals(boolType))
                    ScrippleErrorListener.fireError(
                            ScrippleErrorListener.Message.OPERAND_NOT_BOOL,
                            getPosition(), o1Type.toString());
                if (!o1Type.equals(boolType))
                    ScrippleErrorListener.fireError(
                            ScrippleErrorListener.Message.OPERAND_NOT_BOOL,
                            getPosition(), o2Type.toString());
            }
            case ADD, SUBTRACT, MULTIPLY, DIVIDE, MODULO, RAISE,
                    GT, LT, GEQ, LEQ -> {
                final Set<ScrippleTypeNode> acceptedTypes = Set.of(
                        new SimpleTypeNode(SimpleTypeNode.Type.INT),
                        new SimpleTypeNode(SimpleTypeNode.Type.FLOAT));

                if (!acceptedTypes.contains(o1Type))
                    ScrippleErrorListener.fireError(
                            ScrippleErrorListener.Message.OPERAND_NAN_SEM,
                            getPosition(), o1Type.toString());
                if (!acceptedTypes.contains(o2Type))
                    ScrippleErrorListener.fireError(
                            ScrippleErrorListener.Message.OPERAND_NAN_SEM,
                            getPosition(), o2Type.toString());
            }
        }

        o1.semanticErrorCheck(symbolTable);
        o2.semanticErrorCheck(symbolTable);
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final Object o1Value = o1.evaluate(symbolTable),
                o2Value = o2.evaluate(symbolTable);

        return switch (operator) {
            case AND, OR -> {
                final boolean b1 = (Boolean) o1Value, b2 = (Boolean) o2Value;

                yield operator == Operator.AND ? b1 && b2 : b1 || b2;
            }
            case GT, LT, GEQ, LEQ -> {
                final double n1 = (Double) o1Value, n2 = (Double) o2Value;

                yield switch (operator) {
                    case GT -> n1 > n2;
                    case LT -> n1 < n2;
                    case GEQ -> n1 >= n2;
                    default -> n1 <= n2;
                };
            }
            case EQUAL, NOT_EQUAL -> {
                final boolean equal =
                        ScrippleEquality.equal(o1Value,  o2Value);

                yield (operator == Operator.EQUAL) == equal;
            }
            case ADD, SUBTRACT, MULTIPLY, DIVIDE, MODULO, RAISE -> {
                final SimpleTypeNode
                        intType = new SimpleTypeNode(SimpleTypeNode.Type.INT);
                final boolean bothInts =
                        o1.getType(symbolTable).equals(intType) &&
                        o2.getType(symbolTable).equals(intType);
                final double n1 = (Double) o1Value, n2 = (Double) o2Value;

                final double result = switch (operator) {
                    case ADD -> n1 + n2;
                    case SUBTRACT -> n1 - n2;
                    case MULTIPLY -> n1 * n2;
                    case DIVIDE -> n1 / n2;
                    case MODULO -> n1 % n2;
                    default -> Math.pow(n1, n2);
                };

                yield bothInts ? (int) result : result;
            }
        };
    }

    @Override
    public ScrippleTypeNode getType(final SymbolTable symbolTable) {
        if (operator.isLogic())
            return new SimpleTypeNode(SimpleTypeNode.Type.BOOL);

        final ScrippleTypeNode o1Type = o1.getType(symbolTable),
                o2Type = o2.getType(symbolTable);

        final SimpleTypeNode floatType =
                new SimpleTypeNode(SimpleTypeNode.Type.FLOAT);

        if (o1Type.equals(floatType) || o2Type.equals(floatType))
            return floatType;

        return new SimpleTypeNode(SimpleTypeNode.Type.INT);
    }
}
