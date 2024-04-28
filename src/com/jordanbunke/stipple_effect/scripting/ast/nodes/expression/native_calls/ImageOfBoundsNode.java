package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.ScrippleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class ImageOfBoundsNode extends ExpressionNode {
    private final ExpressionNode width, height;

    public ImageOfBoundsNode(
            final TextPosition position,
            final ExpressionNode width,
            final ExpressionNode height
    ) {
        super(position);

        this.width = width;
        this.height = height;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        final SimpleTypeNode intType =
                new SimpleTypeNode(SimpleTypeNode.Type.INT);

        final ScrippleTypeNode
                widthType = width.getType(symbolTable),
                heightType = height.getType(symbolTable);

        if (!widthType.equals(intType))
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.IMG_ARG_NOT_INT,
                    width.getPosition(), "Width", widthType.toString());
        if (!heightType.equals(intType))
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.IMG_ARG_NOT_INT,
                    height.getPosition(), "Height", heightType.toString());

        width.semanticErrorCheck(symbolTable);
        height.semanticErrorCheck(symbolTable);
    }

    @Override
    public GameImage evaluate(final SymbolTable symbolTable) {
        final int w = (int) width.evaluate(symbolTable),
                h = (int) height.evaluate(symbolTable);

        if (w > 0 && h > 0)
            return new GameImage(w, h);
        else if (w <= 0)
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.NON_POSITIVE_IMAGE_BOUND,
                    width.getPosition(), "Width", String.valueOf(w));
        else
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.NON_POSITIVE_IMAGE_BOUND,
                    height.getPosition(), "Height", String.valueOf(h));

        return null;
    }

    @Override
    public ScrippleTypeNode getType(final SymbolTable symbolTable) {
        return new SimpleTypeNode(SimpleTypeNode.Type.IMAGE);
    }
}
