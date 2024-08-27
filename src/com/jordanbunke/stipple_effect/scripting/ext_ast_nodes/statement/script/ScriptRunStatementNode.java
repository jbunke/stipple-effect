package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement.script;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.HeadFuncNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.SEInterpreter;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement.ScopedStatementNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.ScriptTypeNode;
import com.jordanbunke.stipple_effect.scripting.util.ScriptUtils;

public final class ScriptRunStatementNode extends ScopedStatementNode {
    public static final String NAME = "run";

    public ScriptRunStatementNode(
            final TextPosition position, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(position, scope, ScriptTypeNode.get(),
                args, ScriptUtils.wildcards(args));
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final HeadFuncNode script = (HeadFuncNode) scope.evaluate(symbolTable);
        final Object[] args = arguments.getValues(symbolTable);

        // SEInterpreter.get().run(script, args);
        script.execute(symbolTable, args);

        return FuncControlFlow.cont();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
