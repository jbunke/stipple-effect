package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.function;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.function.HelperFuncNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.FuncHelper;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

import java.util.Arrays;

public final class FuncCallNode extends FuncRetrievableNode {
    private final ExpressionNode[] args;

    public FuncCallNode(
            final TextPosition position,
            final String name,
            final ExpressionNode[] args
    ) {
        super(position, name);

        this.args = args;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        for (ExpressionNode arg : args)
            arg.semanticErrorCheck(symbolTable);

        final HelperFuncNode func = getFunc(symbolTable);

        if (func == null)
            return;

        final TypeNode[] argTypes = Arrays.stream(args)
                .map(a -> a.getType(symbolTable))
                .toArray(TypeNode[]::new);

        if (!func.paramsMatch(argTypes))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARGS_SIGNATURE_MISMATCH,
                    getPosition(), name);
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final HelperFuncNode func = getFunc(symbolTable);
        assert func != null;

        return FuncHelper.evaluate(func, args, symbolTable);
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        final HelperFuncNode func = getFunc(symbolTable);

        return func == null ? null : func.getReturnType();
    }

    @Override
    public String toString() {
        final String contents = args.length == 1
                ? args[0].toString()
                : Arrays.stream(args)
                .map(ExpressionNode::toString)
                .reduce((a, b) -> a + ", " + b).orElse("");

        return name + "(" + contents + ")";
    }
}
