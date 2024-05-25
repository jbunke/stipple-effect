package com.jordanbunke.stipple_effect.scripting.util;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.IllegalExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.statement.IllegalStatementNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.*;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.global.*;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.layer.*;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.palette.PaletteColorSetGetterNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.palette.PaletteMutableNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.project.*;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement.global.*;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement.layer.*;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement.palette.MutablePaletteColorOpNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement.project.*;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.*;

public final class SENodeDelegator {
    public static StatementNode globalFunctionStatement(
            final TextPosition position, final String fID,
            final ExpressionNode[] args
    ) {
        return switch (fID) {
            case NewProjectStatementNode.NAME ->
                    new NewProjectStatementNode(position, args);
            case AssignColorNode.PRIM_NAME ->
                    AssignColorNode.primary(position, args);
            case AssignColorNode.SEC_NAME ->
                    AssignColorNode.secondary(position, args);
            case NewPaletteNode.NAME -> new NewPaletteNode(position, args);
            case SetSideMaskNode.NAME -> new SetSideMaskNode(position, args);
            // extend here
            default -> new IllegalStatementNode(position,
                    "Undefined function \"$" + fID + "\"");
        };
    }

    public static ExpressionNode globalFunctionExpression(
            final TextPosition position, final String fID,
            final ExpressionNode[] args
    ) {
        return switch (fID) {
            case GetProjectNoArgsNode.NAME ->
                    new GetProjectNoArgsNode(position, args);
            case GetProjectsNode.NAME -> new GetProjectsNode(position, args);
            case NewProjectExpressionNode.NAME ->
                    new NewProjectExpressionNode(position, args);
            case GetColorNode.PRIM_NAME ->
                    GetColorNode.primary(position, args);
            case GetColorNode.SEC_NAME ->
                    GetColorNode.secondary(position, args);
            case PaletteGetterNode.GET ->
                    PaletteGetterNode.newGet(position, args);
            case PaletteGetterNode.HAS ->
                    PaletteGetterNode.newHas(position, args);
            case FillSelectionNode.NAME -> args.length == 2
                    ? FillSelectionNode.system(position, args)
                    : FillSelectionNode.custom(position, args);
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
            default -> new IllegalExpressionNode(position,
                    "Undefined function \"$" + fID + "\"");
        };
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
            case OpacityGetterNode.OPACITY ->
                    OpacityGetterNode.opacity(position, scope, args);
            case OpacityGetterNode.OPAQUE ->
                    OpacityGetterNode.opaque(position, scope, args);
            case GetFrameNode.NAME ->
                    new GetFrameNode(position, scope, args);
            case IsEnabledNode.NAME ->
                    new IsEnabledNode(position, scope, args);
            case IsLinkedNode.NAME ->
                    new IsLinkedNode(position, scope, args);
            case IsSelectedNode.NAME ->
                    new IsSelectedNode(position, scope, args);
            case GetLayersNode.NAME ->
                    new GetLayersNode(position, scope, args);
            case IsAnimNode.NAME ->
                    new IsAnimNode(position, scope, args);
            case GetFrameCountNode.NAME ->
                    new GetFrameCountNode(position, scope, args);
            case GetFrameIndexNode.NAME ->
                    new GetFrameIndexNode(position, scope, args);
            case GetLayerIndexNode.NAME ->
                    new GetLayerIndexNode(position, scope, args);
            case GetLayerNode.NAME -> switch (args.length) {
                case 0 -> new GetEditingLayerNode(position, scope, args);
                case 1 -> new GetIndexLayerNode(position, scope, args);
                default -> new IllegalExpressionNode(position,
                        "Scoped function \"" + fID +
                                "\" takes 0 or 1 argument(s)");
            };
            case SelectionGetter.GET ->
                    SelectionGetter.newGet(position, scope, args);
            case SelectionGetter.HAS ->
                    SelectionGetter.newHas(position, scope, args);
            case PaletteColorSetGetterNode.COLORS ->
                    PaletteColorSetGetterNode.colors(position, scope, args);
            case PaletteColorSetGetterNode.INCLUDED ->
                    PaletteColorSetGetterNode.included(position, scope, args);
            // extend here
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
            case SetFrameNode.SET_NAME ->
                    SetFrameNode.newSet(position, scope, args);
            case SetFrameNode.EDIT_NAME ->
                    SetFrameNode.newEdit(position, scope, args);
            case SetOpacityNode.NAME ->
                    new SetOpacityNode(position, scope, args);
            case WipeFrameNode.NAME ->
                    new WipeFrameNode(position, scope, args);
            case UnlinkFramesNode.NAME ->
                    new UnlinkFramesNode(position, scope, args);
            case LinkFramesNode.NAME ->
                    new LinkFramesNode(position, scope, args);
            case DisableLayerNode.NAME ->
                    new DisableLayerNode(position, scope, args);
            case EnableLayerNode.NAME ->
                    new EnableLayerNode(position, scope, args);
            case ResizeNode.NAME ->
                    new ResizeNode(position, scope, args);
            case SaveNode.NAME ->
                    new SaveNode(position, scope, args);
            case PadNode.NAME ->
                    new PadNode(position, scope, args);
            case StitchNode.NAME ->
                    new StitchNode(position, scope, args);
            case SplitByPixelsNode.NAME ->
                    new SplitByPixelsNode(position, scope, args);
            case SplitByDimsNode.NAME ->
                    new SplitByDimsNode(position, scope, args);
            case AddFrameNode.ADD_NAME ->
                    AddFrameNode.newAdd(position, scope, args);
            case AddFrameNode.DUPE_NAME ->
                    AddFrameNode.newDupe(position, scope, args);
            case AddLayerNode.ADD_NAME ->
                    AddLayerNode.newAdd(position, scope, args);
            case AddLayerNode.DUPE_NAME ->
                    AddLayerNode.newDupe(position, scope, args);
            case RemoveFrameNode.NAME ->
                    new RemoveFrameNode(position, scope, args);
            case RemoveLayerNode.NAME ->
                    new RemoveLayerNode(position, scope, args);
            case SetIndexNode.FRAME_NAME ->
                    SetIndexNode.newFrame(position, scope, args);
            case SetIndexNode.LAYER_NAME ->
                    SetIndexNode.newLayer(position, scope, args);
            case MoveFrameNode.BACK ->
                    MoveFrameNode.back(position, scope, args);
            case MoveFrameNode.FORWARD ->
                    MoveFrameNode.forward(position, scope, args);
            case MoveLayerNode.DOWN ->
                    MoveLayerNode.down(position, scope, args);
            case MoveLayerNode.UP ->
                    MoveLayerNode.up(position, scope, args);
            case MergeLayersNode.NAME ->
                    new MergeLayersNode(position, scope, args);
            case FlattenNode.NAME ->
                    new FlattenNode(position, scope, args);
            case SetSelectionNode.NAME ->
                    new SetSelectionNode(position, scope, args);
            case SelectionOpNode.INV ->
                    new SelectionOpNode(position, scope,
                            args, SelectionOpNode.Op.INVERT_SELECTION);
            case SelectionOpNode.ALL ->
                    new SelectionOpNode(position, scope,
                            args, SelectionOpNode.Op.SELECT_ALL);
            case SelectionOpNode.DESELECT ->
                    new SelectionOpNode(position, scope,
                            args, SelectionOpNode.Op.DESELECT);
            case PaletteActionNode.PALETTIZE ->
                    PaletteActionNode.palettize(position, scope, args);
            case PaletteActionNode.EXTRACT ->
                    PaletteActionNode.extract(position, scope, args);
            case MutablePaletteColorOpNode.ADD ->
                    MutablePaletteColorOpNode.add(position, scope, args);
            case MutablePaletteColorOpNode.REMOVE ->
                    MutablePaletteColorOpNode.remove(position, scope, args);
            case MutablePaletteColorOpNode.MOVE_LEFT ->
                    MutablePaletteColorOpNode.moveLeft(position, scope, args);
            case MutablePaletteColorOpNode.MOVE_RIGHT ->
                    MutablePaletteColorOpNode.moveRight(position, scope, args);
            // extend here
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
