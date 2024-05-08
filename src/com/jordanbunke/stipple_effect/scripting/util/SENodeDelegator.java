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
            case SaveNode.NAME -> new SaveNode(position, args);
            case UnlinkFramesNode.NAME -> new UnlinkFramesNode(position, args);
            case LinkFramesNode.NAME -> new LinkFramesNode(position, args);
            case PadNode.NAME -> new PadNode(position, args);
            case StitchNode.NAME -> new StitchNode(position, args);
            case SplitByPixelsNode.NAME ->
                    new SplitByPixelsNode(position, args);
            case SplitByDimsNode.NAME -> new SplitByDimsNode(position, args);
            case DisableLayerNode.NAME -> new DisableLayerNode(position, args);
            case EnableLayerNode.NAME -> new EnableLayerNode(position, args);
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
            case NewProjectExpressionNode.NAME ->
                    new NewProjectExpressionNode(position, args);
            case GetLayerNode.NAME -> switch (args.length) {
                case 0 -> new GetLayerNoArgsNode(position, args);
                case 1 -> new GetLayerOneArgNode(position, args);
                default -> new GetLayerTwoArgsNode(position, args);
            };
            case GetLayersNode.NAME -> new GetLayersNode(position, args);
            case IsAnimNode.NAME -> new IsAnimNode(position, args);
            case GetFrameCountNode.NAME ->
                    new GetFrameCountNode(position, args);
            case GetFrameIndexNode.NAME ->
                    new GetFrameIndexNode(position, args);
            case GetLayerIndexNode.NAME ->
                    new GetLayerIndexNode(position, args);
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
