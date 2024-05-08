package com.jordanbunke.stipple_effect.scripting;

import com.jordanbunke.delta_time.scripting.ScriptParser;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.util.ScriptVisitor;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.scripting.util.TypeCompatibility;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.SEExtExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement.SEExtStatementNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.SEExtTypeNode;
import com.jordanbunke.stipple_effect.scripting.util.SENodeDelegator;
import com.jordanbunke.stipple_effect.tools.Tool;

import java.util.Set;

public final class SEScriptVisitor extends ScriptVisitor {
    static {
        // TODO - what else?
        final Set<Class<?>> extensionTypeObjects =
                Set.of(SEContext.class, SELayer.class, Tool.class);

        extensionTypeObjects.forEach(TypeCompatibility::addBaseType);
    }

    @Override
    public SEExtTypeNode visitExtensionType(
            final ScriptParser.ExtensionTypeContext ctx
    ) {
        return SENodeDelegator.delegateType(
                TextPosition.fromToken(ctx.start), ctx.ident().getText());
    }

    @Override
    public SEExtExpressionNode visitExtFuncCallExpression(
            final ScriptParser.ExtFuncCallExpressionContext ctx
    ) {
        return SENodeDelegator.delegateExpression(
                TextPosition.fromToken(ctx.start),
                ctx.PATH().getText().substring(1),
                ctx.args().expr().stream()
                        .map(e -> (ExpressionNode) visit(e))
                        .toArray(ExpressionNode[]::new));
    }

    @Override
    public SEExtStatementNode visitExtFuncCallStatement(
            final ScriptParser.ExtFuncCallStatementContext ctx
    ) {
        return SENodeDelegator.delegateStatement(
                TextPosition.fromToken(ctx.start),
                ctx.PATH().getText().substring(1),
                ctx.args().expr().stream()
                        .map(e -> (ExpressionNode) visit(e))
                        .toArray(ExpressionNode[]::new));
    }
}
