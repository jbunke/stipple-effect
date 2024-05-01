package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.operation;

import com.jordanbunke.stipple_effect.scripting.util.ScrippleEquality;
import com.jordanbunke.stipple_effect.scripting.util.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
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

        private boolean isDiv() {
            return this == DIVIDE || this == MODULO;
        }

        @Override
        public String toString() {
            return switch (this) {
                case ADD -> "+";
                case SUBTRACT -> "-";
                case MULTIPLY -> "*";
                case DIVIDE -> "/";
                case MODULO -> "%";
                case RAISE -> "^";
                case EQUAL -> "==";
                case NOT_EQUAL -> "!=";
                case GT -> ">";
                case LT -> "<";
                case GEQ -> ">=";
                case LEQ -> "<=";
                case OR -> "||";
                case AND -> "&&";
            };
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
        o1.semanticErrorCheck(symbolTable);
        o2.semanticErrorCheck(symbolTable);

        final TypeNode o1Type = o1.getType(symbolTable),
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
                final Set<TypeNode> acceptedTypes = Set.of(
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
                final double
                        n1 = ((Number) o1Value).doubleValue(),
                        n2 = ((Number) o2Value).doubleValue();

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
                final double
                        n1 = ((Number) o1Value).doubleValue(),
                        n2 = ((Number) o2Value).doubleValue();

                if (n2 == 0d && operator.isDiv())
                    ScrippleErrorListener.fireError(
                            ScrippleErrorListener.Message.DIV_BY_ZERO,
                            o2.getPosition());

                final Double result = switch (operator) {
                    case ADD -> n1 + n2;
                    case SUBTRACT -> n1 - n2;
                    case MULTIPLY -> n1 * n2;
                    case DIVIDE -> n1 / n2;
                    case MODULO -> n1 % n2;
                    default -> Math.pow(n1, n2);
                };

                if (bothInts)
                    yield result.intValue();

                yield result;
            }
        };
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        if (operator.isLogic())
            return new SimpleTypeNode(SimpleTypeNode.Type.BOOL);

        final TypeNode o1Type = o1.getType(symbolTable),
                o2Type = o2.getType(symbolTable);

        final SimpleTypeNode floatType =
                new SimpleTypeNode(SimpleTypeNode.Type.FLOAT);

        if (o1Type.equals(floatType) || o2Type.equals(floatType))
            return floatType;

        return new SimpleTypeNode(SimpleTypeNode.Type.INT);
    }

    @Override
    public String toString() {
        return o1 + " " + operator.toString() + " " + o2;
    }
}
