package com.jordanbunke.stipple_effect.scripting.util;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

import java.util.Arrays;

public record Arguments(ExpressionNode[] args, TypeNode... expectedArgTypes) {

    public Object[] getValues(final SymbolTable symbolTable) {
        return Arrays.stream(args)
                .map(a -> a.evaluate(symbolTable))
                .toArray(Object[]::new);
    }

    public void semanticErrorCheck(
            final SymbolTable symbolTable, final TextPosition position
    ) {
        final TypeNode[] argTypes = Arrays.stream(args)
                .map(a -> a.getType(symbolTable))
                .toArray(TypeNode[]::new);

        if (argTypes.length != expectedArgTypes.length) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_CT,
                    args.length > 0 ? args[0].getPosition() : position,
                    "Passing " + args.length +
                            " arguments into a function expecting " +
                            expectedArgTypes.length + " arguments");
            return;
        }

        for (int i = 0; i < expectedArgTypes.length; i++) {
            final TypeNode expected = expectedArgTypes[i], argType = argTypes[i];

            if (!argType.equals(expected))
                ScriptErrorLog.fireError(ScriptErrorLog.Message.ARG_NOT_TYPE,
                        args[i].getPosition(), "function",
                        expected.toString(), argType.toString());
        }
    }

    @Override
    public String toString() {
        return "(" + switch (args.length) {
            case 0 -> "";
            case 1 -> args[0].toString();
            default -> Arrays.stream(args).map(ExpressionNode::toString)
                    .reduce((a, b) -> a + ", " + b).orElse("");
        } + ")";
    }
}
