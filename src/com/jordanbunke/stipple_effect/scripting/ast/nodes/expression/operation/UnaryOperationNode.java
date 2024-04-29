package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.operation;

import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.util.Collection;
import java.util.Set;

public final class UnaryOperationNode extends ExpressionNode {
    private enum Operator {
        NEGATE, NOT, SIZE;

        private static Operator fromString(final String s) {
            return switch (s) {
                case "-" -> NEGATE;
                case "!" -> NOT;
                default -> SIZE;
            };
        }
    }

    private final Operator operator;
    private final ExpressionNode operand;

    public UnaryOperationNode(
            final TextPosition position,
            final String operatorString,
            final ExpressionNode operand
    ) {
        super(position);

        operator = Operator.fromString(operatorString);
        this.operand = operand;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        operand.semanticErrorCheck(symbolTable);

        final TypeNode operandType = operand.getType(symbolTable);

        switch (operator) {
            case NOT -> {
                if (!operandType.equals(
                        new SimpleTypeNode(SimpleTypeNode.Type.BOOL)))
                    ScrippleErrorListener.fireError(
                            ScrippleErrorListener.Message.OPERAND_NOT_BOOL,
                            getPosition(), operandType.toString());
            }
            case NEGATE -> {
                final Set<TypeNode> acceptedTypes = Set.of(
                        new SimpleTypeNode(SimpleTypeNode.Type.INT),
                        new SimpleTypeNode(SimpleTypeNode.Type.FLOAT));

                if (!acceptedTypes.contains(operandType))
                    ScrippleErrorListener.fireError(
                            ScrippleErrorListener.Message.OPERAND_NAN_SEM,
                            getPosition(), operandType.toString());
            }
            case SIZE -> {
                if (!(operandType instanceof CollectionTypeNode))
                    ScrippleErrorListener.fireError(
                            ScrippleErrorListener.Message.OPERAND_NOT_A_COLLECTION_SEM,
                            getPosition(), operandType.toString());
            }
        }
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final Object operandValue = operand.evaluate(symbolTable);

        return switch (operator) {
            case NOT -> !((Boolean) operandValue);
            case SIZE -> {
                if (operandValue instanceof Collection<?> c)
                    yield c.size();
                else if (operandValue instanceof Object[] arr)
                    yield arr.length;

                ScrippleErrorListener.fireError(
                        ScrippleErrorListener.Message.OPERAND_NOT_A_COLLECTION_RT,
                        getPosition());
                yield null;
            }
            case NEGATE -> {
                if (operandValue instanceof Integer i)
                    yield -i;
                else if (operandValue instanceof Double f)
                    yield -f;
                else if (operandValue instanceof Float f)
                    yield -f;
                else {
                    ScrippleErrorListener.fireError(
                            ScrippleErrorListener.Message.OPERAND_NAN_RT,
                            getPosition());
                    yield null;
                }
            }
        };
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        if (operator == Operator.SIZE)
            return new SimpleTypeNode(SimpleTypeNode.Type.INT);

        return operand.getType(symbolTable);
    }
}
