package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement.global.GlobalStatementNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.ProjectTypeNode;
import com.jordanbunke.stipple_effect.utility.StatusUpdates;

public abstract class CondProjectOpNode extends GlobalStatementNode {
    public CondProjectOpNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args, ProjectTypeNode.get());
    }

    @Override
    public final FuncControlFlow execute(final SymbolTable symbolTable) {
        final SEContext project =
                (SEContext) getArgs()[0].evaluate(symbolTable);

        if (condition(project))
            operation(project);
        else
            StatusUpdates.scriptActionNotPermitted(
                    attempt(), failReason(project),
                    getArgs()[0].getPosition());

        return FuncControlFlow.cont();
    }

    protected abstract boolean condition(final SEContext project);

    protected abstract String attempt();

    protected abstract String failReason(final SEContext project);

    protected abstract void operation(final SEContext project);
}
