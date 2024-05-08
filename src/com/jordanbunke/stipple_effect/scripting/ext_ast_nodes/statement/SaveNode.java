package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.ProjectTypeNode;
import com.jordanbunke.stipple_effect.utility.StatusUpdates;

public final class SaveNode extends SEExtStatementNode {
    public static final String NAME = "save";

    public SaveNode(
            final TextPosition position,
            final ExpressionNode[] args
    ) {
        super(position, args, ProjectTypeNode.get());
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final SEContext project =
                (SEContext) getArgs()[0].evaluate(symbolTable);

        if (project.projectInfo.hasSaveAssociation())
            project.projectInfo.save();
        else
            StatusUpdates.scriptActionNotPermitted("save the project",
                    "the project does not have a valid save association");

        return FuncControlFlow.cont();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
