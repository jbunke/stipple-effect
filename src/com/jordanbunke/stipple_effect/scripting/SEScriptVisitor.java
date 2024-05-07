package com.jordanbunke.stipple_effect.scripting;

import com.jordanbunke.delta_time.scripting.ScriptParser;
import com.jordanbunke.delta_time.scripting.ast.nodes.ASTNode;
import com.jordanbunke.delta_time.scripting.util.ScriptVisitor;

public final class SEScriptVisitor extends ScriptVisitor {
    // TODO
    @Override
    public ASTNode visitExtensionType(
            final ScriptParser.ExtensionTypeContext ctx
    ) {
        return super.visitExtensionType(ctx);
    }

    @Override
    public ASTNode visitExtFuncCallExpression(
            final ScriptParser.ExtFuncCallExpressionContext ctx
    ) {
        return super.visitExtFuncCallExpression(ctx);
    }

    @Override
    public ASTNode visitExtFuncCallStatement(
            final ScriptParser.ExtFuncCallStatementContext ctx
    ) {
        return super.visitExtFuncCallStatement(ctx);
    }
}
