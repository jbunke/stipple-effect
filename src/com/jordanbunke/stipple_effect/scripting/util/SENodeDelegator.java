package com.jordanbunke.stipple_effect.scripting.util;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.IllegalExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.statement.IllegalStatementNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.*;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.global.GlobalExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.layer.LayerIndexPropertyNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.layer.LayerProjectPropertyNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.palette.PaletteMutableNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement.*;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement.global.GlobalStatementNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.*;

public final class SENodeDelegator {
    public static GlobalStatementNode globalFunctionStatement(
            final TextPosition position, final String fID,
            final ExpressionNode[] args
    ) {
        final GlobalStatementNode s = switch (fID) {
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
            case AddFrameNode.ADD_NAME -> AddFrameNode.newAdd(position, args);
            case AddFrameNode.DUPE_NAME -> AddFrameNode.newDupe(position, args);
            case AddLayerNode.ADD_NAME -> AddLayerNode.newAdd(position, args);
            case AddLayerNode.DUPE_NAME -> AddLayerNode.newDupe(position, args);
            case RemoveFrameNode.NAME -> new RemoveFrameNode(position, args);
            case RemoveLayerNode.NAME -> new RemoveLayerNode(position, args);
            case SetIndexNode.FRAME_NAME ->
                    SetIndexNode.newFrame(position, args);
            case SetIndexNode.LAYER_NAME ->
                    SetIndexNode.newLayer(position, args);
            case SetFrameContentNode.SET_NAME ->
                    SetFrameContentNode.newSet(position, args);
            case SetFrameContentNode.EDIT_NAME ->
                    SetFrameContentNode.newEdit(position, args);
            case WipeFrameNode.NAME -> new WipeFrameNode(position, args);
            case AssignColorNode.PRIM_NAME ->
                    AssignColorNode.primary(position, args);
            case AssignColorNode.SEC_NAME ->
                    AssignColorNode.secondary(position, args);
            case NewPaletteNode.NAME -> new NewPaletteNode(position, args);
            case MoveFrameNode.BACK -> MoveFrameNode.back(position, args);
            case MoveFrameNode.FORWARD -> MoveFrameNode.forward(position, args);
            case MoveLayerNode.DOWN -> MoveLayerNode.down(position, args);
            case MoveLayerNode.UP -> MoveLayerNode.up(position, args);
            case MergeLayersNode.NAME -> new MergeLayersNode(position, args);
            case FlattenNode.NAME -> new FlattenNode(position, args);
            case SetSelectionNode.NAME -> new SetSelectionNode(position, args);
            case SelectionOpNode.INV -> new SelectionOpNode(
                    position, args, SelectionOpNode.Op.INVERT_SELECTION);
            case SelectionOpNode.ALL -> new SelectionOpNode(
                    position, args, SelectionOpNode.Op.SELECT_ALL);
            case SelectionOpNode.DESELECT -> new SelectionOpNode(
                    position, args, SelectionOpNode.Op.DESELECT);
            case SetOpacityNode.NAME -> new SetOpacityNode(position, args);
            case SetSideMaskNode.NAME -> new SetSideMaskNode(position, args);
            // extend here
            default -> null;
        };

        if (s == null)
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_CT,
                    position, "Undefined function \"$" + fID + "\"");

        return s;
    }

    public static GlobalExpressionNode globalFunctionExpression(
            final TextPosition position, final String fID,
            final ExpressionNode[] args
    ) {
        final GlobalExpressionNode e = switch (fID) {
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
            case GetFrameContentNode.NAME ->
                    new GetFrameContentNode(position, args);
            case IsEnabledNode.NAME -> new IsEnabledNode(position, args);
            case IsLinkedNode.NAME -> new IsLinkedNode(position, args);
            case GetColorNode.PRIM_NAME ->
                    GetColorNode.primary(position, args);
            case GetColorNode.SEC_NAME ->
                    GetColorNode.secondary(position, args);
            case PaletteGetterNode.GET ->
                    PaletteGetterNode.newGet(position, args);
            case PaletteGetterNode.HAS ->
                    PaletteGetterNode.newHas(position, args);
            case SelectionGetter.GET -> SelectionGetter.newGet(position, args);
            case SelectionGetter.HAS -> SelectionGetter.newHas(position, args);
            case IsSelectedNode.NAME -> new IsSelectedNode(position, args);
            case FillSelectionNode.NAME -> args.length == 2
                    ? FillSelectionNode.system(position, args)
                    : FillSelectionNode.custom(position, args);
            case OpacityGetterNode.OPACITY ->
                    OpacityGetterNode.opacity(position, args);
            case OpacityGetterNode.OPAQUE ->
                    OpacityGetterNode.opaque(position, args);
            case FillNode.NAME -> new FillNode(position, args);
            case WandNode.NAME -> new WandNode(position, args);
            case HSVNode.NAME -> args.length == 4
                    ? HSVNode.withAlpha(position, args)
                    : HSVNode.newHSV(position, args);
            case OutlineNode.NAME -> new OutlineNode(position, args);
            case PresetOutlineNode.SINGLE -> PresetOutlineNode.sng(position, args);
            case PresetOutlineNode.DOUBLE -> PresetOutlineNode.dbl(position, args);
            case GetSideMaskNode.NAME -> new GetSideMaskNode(position, args);
            // extend here
            default -> null;
        };

        if (e == null)
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_CT,
                    position, "Undefined function \"$" + fID + "\"");

        return e;
    }

    public static ExpressionNode property(
            final TextPosition position, final ExpressionNode scope,
            final String propertyID
    ) {
        return switch (propertyID) {
            case ColorPropertyGetterNode.HUE ->
                    ColorPropertyGetterNode.hue(position, scope);
            case ColorPropertyGetterNode.SAT ->
                    ColorPropertyGetterNode.sat(position, scope);
            case ColorPropertyGetterNode.VAL ->
                    ColorPropertyGetterNode.val(position, scope);
            case PaletteMutableNode.NAME ->
                    new PaletteMutableNode(position, scope);
            case LayerIndexPropertyNode.NAME ->
                    new LayerIndexPropertyNode(position, scope);
            case LayerProjectPropertyNode.NAME ->
                    new LayerProjectPropertyNode(position, scope);
            // extend here
            default -> new IllegalExpressionNode(position,
                    "No property \"" + propertyID + "\" exists");
        };
    }

    public static ExpressionNode scopedFunctionExpression(
            final TextPosition position, final ExpressionNode scope,
            final String fID, final ExpressionNode... args
    ) {
        return switch (fID) {
            // TODO
            default -> new IllegalExpressionNode(position,
                    "No scoped function \"" + fID + "\" with " +
                            args.length + " arguments exists");
        };
    }

    public static StatementNode scopedFunctionStatement(
            final TextPosition position, final ExpressionNode scope,
            final String fID, final ExpressionNode... args
    ) {
        return switch (fID) {
            // TODO
            default -> new IllegalStatementNode(position,
                    "No scoped function \"" + fID + "\" with " +
                            args.length + " arguments exists");
        };
    }

    public static SEExtTypeNode type(
            final TextPosition position, final String typeID
    ) {
        final SEExtTypeNode t = switch (typeID) {
            case LayerTypeNode.NAME -> new LayerTypeNode(position);
            case ProjectTypeNode.NAME -> new ProjectTypeNode(position);
            case PaletteTypeNode.NAME -> new PaletteTypeNode(position);
            default -> null;
        };

        if (t == null)
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_CT,
                    position, "Undefined type \"" + typeID + "\"");

        return t;
    }
}
