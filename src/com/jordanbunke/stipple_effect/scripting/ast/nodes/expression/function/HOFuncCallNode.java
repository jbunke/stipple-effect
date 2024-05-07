package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.function;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.function.HelperFuncNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.FuncTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.FuncHelper;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

import java.util.Arrays;

public final class HOFuncCallNode extends ExpressionNode {
    private final ExpressionNode f;
    private final ExpressionNode[] args;

    public HOFuncCallNode(
            final TextPosition position,
            final ExpressionNode f, final ExpressionNode[] args
    ) {
        super(position);

        this.f = f;
        this.args = args;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        for (ExpressionNode arg : args)
            arg.semanticErrorCheck(symbolTable);

        final TypeNode fType = f.getType(symbolTable);

        if (!(fType instanceof FuncTypeNode funcType)) {
            ScriptErrorLog.fireError(ScriptErrorLog.Message.NOT_HOF,
                    f.getPosition(), f.toString(), fType.toString());
            return;
        }

        final TypeNode[] argTypes = Arrays.stream(args)
                .map(a -> a.getType(symbolTable))
                .toArray(TypeNode[]::new);

        final TypeNode[] paramTypes = funcType.getParamTypes();

        if (paramTypes.length != argTypes.length) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARGS_SIGNATURE_MISMATCH,
                    getPosition(), "higher-order function " + f.toString());
            return;
        }

        for (int i = 0; i < paramTypes.length; i++)
            if (!argTypes[i].equals(paramTypes[i]))
                ScriptErrorLog.fireError(
                        ScriptErrorLog.Message.ARG_NOT_TYPE,
                        args[i].getPosition(), "higher-order function",
                        paramTypes[i].toString(), argTypes[i].toString());
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final HelperFuncNode func = (HelperFuncNode) f.evaluate(symbolTable);
        assert func != null;

        return FuncHelper.evaluate(func, args, symbolTable);
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return ((FuncTypeNode) f.getType(symbolTable)).getReturnType();
    }

    @Override
    public String toString() {
        final String contents = args.length == 1
                ? args[0].toString()
                : Arrays.stream(args)
                .map(ExpressionNode::toString)
                .reduce((a, b) -> a + ", " + b).orElse("");

        return f + ".call(" + contents + ")";
    }
}
