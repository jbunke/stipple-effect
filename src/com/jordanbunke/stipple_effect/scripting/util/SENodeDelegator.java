package com.jordanbunke.stipple_effect.scripting.util;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.*;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement.*;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.*;

public final class SENodeDelegator {
    public static SEExtStatementNode delegateStatement(
            final TextPosition position,
            final String fID,
            final ExpressionNode[] args
    ) {
        final SEExtStatementNode s = switch (fID) {
            case NewProjectStatementNode.NAME ->
                    new NewProjectStatementNode(position, args);
            case ResizeNode.NAME -> new ResizeNode(position, args);
            // TODO - extend
            default -> null;
        };

        if (s == null)
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_CT,
                    position, "Undefined function \"$" + fID + "\"");

        return s;
    }

    public static SEExtExpressionNode delegateExpression(
            final TextPosition position,
            final String fID,
            final ExpressionNode[] args
    ) {
        final SEExtExpressionNode e = switch (fID) {
            case GetProjectNoArgsNode.NAME ->
                    new GetProjectNoArgsNode(position, args);
            case GetProjectsNode.NAME -> new GetProjectsNode(position, args);
            // TODO - extend
            default -> null;
        };

        if (e == null)
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_CT,
                    position, "Undefined function \"$" + fID + "\"");

        return e;
    }

    public static SEExtTypeNode delegateType(
            final TextPosition position,
            final String typeID
    ) {
        final SEExtTypeNode t = switch (typeID) {
            case LayerTypeNode.NAME -> new LayerTypeNode(position);
            case ProjectTypeNode.NAME -> new ProjectTypeNode(position);
            // TODO - extend
            default -> null;
        };

        if (t == null)
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_CT,
                    position, "Undefined type \"" + typeID + "\"");

        return t;
    }
}
