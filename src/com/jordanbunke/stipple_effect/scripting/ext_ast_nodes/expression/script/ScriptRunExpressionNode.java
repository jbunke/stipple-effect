package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.script;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.HeadFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.SEInterpreter;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.ScopedExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.ScriptTypeNode;
import com.jordanbunke.stipple_effect.scripting.util.ScriptUtils;

public final class ScriptRunExpressionNode extends ScopedExpressionNode {
    public static final String NAME = "run";

    public ScriptRunExpressionNode(
            final TextPosition position, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(position, scope, ScriptTypeNode.get(),
                args, ScriptUtils.wildcards(args));
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final HeadFuncNode script = (HeadFuncNode) scope.evaluate(symbolTable);
        final Object[] args = arguments.getValues(symbolTable);

        // return SEInterpreter.get().run(script, args);
        return script.execute(symbolTable, args);
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        final HeadFuncNode script = (HeadFuncNode) scope.evaluate(symbolTable);

        return script.getReturnType();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
