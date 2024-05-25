package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement.layer;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.LayerTypeNode;
import com.jordanbunke.stipple_effect.scripting.util.LayerRep;
import com.jordanbunke.stipple_effect.utility.StatusUpdates;

import java.util.HashSet;
import java.util.Set;

public final class SetFrameNode extends LayerStatementNode {
    public static final String SET_NAME = "set_frame_content",
            EDIT_NAME = "edit_frame";

    private final boolean set;

    private SetFrameNode(
            final TextPosition position, final ExpressionNode scope,
            final ExpressionNode[] args, final boolean set
    ) {
        super(position, scope, args, LayerTypeNode.get(),
                TypeNode.getInt(), TypeNode.getImage());

        this.set = set;
    }

    public static SetFrameNode newSet(
            final TextPosition position,
            final ExpressionNode scope, final ExpressionNode[] args
    ) {
        return new SetFrameNode(position, scope, args, true);
    }

    public static SetFrameNode newEdit(
            final TextPosition position,
            final ExpressionNode scope, final ExpressionNode[] args
    ) {
        return new SetFrameNode(position, scope, args, false);
    }

    @Override
    protected void operation(
            final LayerRep layer, final SymbolTable symbolTable
    ) {
        final Object[] vs = arguments.getValues(symbolTable);
        final int frameIndex = (int) vs[0],
                fc = layer.project().getState().getFrameCount();
        final GameImage content = (GameImage) vs[1];
        final int aw = content.getWidth(), ah = content.getHeight(),
                ew = layer.project().getState().getImageWidth(),
                eh = layer.project().getState().getImageHeight();

        final String attempt = (set ? "set" : "edit") +
                " the content of this layer frame";

        if (frameIndex < 0)
            StatusUpdates.scriptActionNotPermitted(attempt,
                    "the frame index (" + frameIndex + ") is negative",
                    arguments.args()[0].getPosition());
        else if (frameIndex >= layer.project().getState().getFrameCount())
            StatusUpdates.scriptActionNotPermitted(attempt,
                    "the frame index (" + frameIndex + ") is " +
                            (frameIndex == fc ? "greater than " : "equal to ") +
                            "the frame count (" + fc + ")",
                    arguments.args()[0].getPosition());
        else if (aw != ew || ah != eh)
            StatusUpdates.scriptActionNotPermitted(attempt,
                    "the dimensions of the content (" + aw + "x" + ah +
                            ") do not match the project canvas bounds (" +
                            ew + "x" + eh + ")", arguments.args()[1].getPosition());
        else {
            final Set<Coord2D> pixels = new HashSet<>();
            final int w = layer.project().getState().getImageWidth(),
                    h = layer.project().getState().getImageHeight();

            for (int x = 0; x < w; x++)
                for (int y = 0; y < h; y++)
                    pixels.add(new Coord2D(x, y));

            final SELayer old = layer.project().getState().getLayers()
                    .get(layer.index()),
                    replacement = set
                            ? old.returnStamped(content, pixels, frameIndex)
                            : old.returnPaintedOver(content, frameIndex);

            layer.project().setLayerFromScript(replacement, layer.index());
        }
    }

    @Override
    protected String callName() {
        return set ? SET_NAME : EDIT_NAME;
    }
}
