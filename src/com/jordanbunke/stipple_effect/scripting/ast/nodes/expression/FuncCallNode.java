package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.function.HelperFuncNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.Variable;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

import java.util.Arrays;

public final class FuncCallNode extends ExpressionNode {
    private final String name;
    private final ExpressionNode[] args;

    public FuncCallNode(
            final TextPosition position,
            final String name,
            final ExpressionNode[] args
    ) {
        super(position);

        this.name = name;
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

        final SymbolTable funcTable = symbolTable.getRoot().getChild(func);
        final Object[] argVals = Arrays.stream(args)
                .map(a -> a.evaluate(symbolTable)).toArray(Object[]::new);

        return func.execute(funcTable, argVals);
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        final HelperFuncNode func = getFunc(symbolTable);

        return func == null ? null : func.getReturnType();
    }

    private HelperFuncNode getFunc(final SymbolTable symbolTable) {
        final Variable var = symbolTable.get(SymbolTable.funcWithName(name));

        if (var == null) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.UNDEFINED_FUNC,
                    getPosition(), name);

            return null;
        }

        return (HelperFuncNode) var.get();
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
