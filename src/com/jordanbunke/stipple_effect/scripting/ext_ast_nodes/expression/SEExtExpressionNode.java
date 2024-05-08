package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

import java.util.Arrays;

public abstract class SEExtExpressionNode extends ExpressionNode {
    private final ExpressionNode[] args;
    private final TypeNode[] expectedTypes;

    public SEExtExpressionNode(
            final TextPosition position,
            final ExpressionNode[] args,
            final TypeNode... expectedTypes
    ) {
        super(position);

        this.args = args;
        this.expectedTypes = expectedTypes;
    }

    protected ExpressionNode[] getArgs() {
        return args;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        final TypeNode[] argTypes = Arrays.stream(args)
                .map(a -> a.getType(symbolTable))
                .toArray(TypeNode[]::new);

        if (argTypes.length != expectedTypes.length) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_CT,
                    args.length > 0 ? args[0].getPosition() : getPosition(),
                    "Passing " + args.length +
                            " arguments into a function expecting " +
                            expectedTypes.length + " arguments");
            return;
        }

        for (int i = 0; i < expectedTypes.length; i++) {
            final TypeNode expected = expectedTypes[i], argType = argTypes[i];

            if (!argType.equals(expected))
                ScriptErrorLog.fireError(ScriptErrorLog.Message.ARG_NOT_TYPE,
                        args[i].getPosition(), "function",
                        expected.toString(), argType.toString());
        }
    }

    private String argsToString() {
        return switch (args.length) {
            case 0 -> "";
            case 1 -> args[0].toString();
            default -> Arrays.stream(args).map(ExpressionNode::toString)
                    .reduce((a, b) -> a + ", " + b).orElse("");
        };
    }

    protected abstract String callName();

    @Override
    public String toString() {
        return "$" + callName() + "(" + argsToString() + ");";
    }
}
