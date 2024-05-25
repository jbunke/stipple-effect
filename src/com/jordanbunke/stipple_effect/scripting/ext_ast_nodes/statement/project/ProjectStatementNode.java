package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement.project;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement.ScopedStatementNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.ProjectTypeNode;

public abstract class ProjectStatementNode extends ScopedStatementNode {
    ProjectStatementNode(
            final TextPosition position, final ExpressionNode scope,
            final ExpressionNode[] args, final TypeNode... expectedArgTypes
    ) {
        super(position, scope, ProjectTypeNode.get(), args, expectedArgTypes);
    }

    protected final SEContext getProject(final SymbolTable symbolTable) {
        return (SEContext) scope.evaluate(symbolTable);
    }
}
