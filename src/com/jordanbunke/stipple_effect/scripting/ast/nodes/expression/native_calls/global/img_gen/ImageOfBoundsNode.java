package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.global.img_gen;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.BaseTypeNode;
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
        width.semanticErrorCheck(symbolTable);
        height.semanticErrorCheck(symbolTable);

        final BaseTypeNode intType = TypeNode.getInt();

        final TypeNode
                widthType = width.getType(symbolTable),
                heightType = height.getType(symbolTable);

        if (!widthType.equals(intType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    width.getPosition(), "Width",
                    "int", widthType.toString());
        if (!heightType.equals(intType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    height.getPosition(), "Height",
                    "int", heightType.toString());
    }

    @Override
    public GameImage evaluate(final SymbolTable symbolTable) {
        final int w = (int) width.evaluate(symbolTable),
                h = (int) height.evaluate(symbolTable);

        if (w > 0 && h > 0)
            return new GameImage(w, h);
        else {
            if (w <= 0)
                ScriptErrorLog.fireError(
                        ScriptErrorLog.Message.NON_POSITIVE_IMAGE_BOUND,
                        width.getPosition(), "Width", String.valueOf(w));
            if (h <= 0)
                ScriptErrorLog.fireError(
                        ScriptErrorLog.Message.NON_POSITIVE_IMAGE_BOUND,
                        height.getPosition(), "Height", String.valueOf(h));
        }

        return null;
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getImage();
    }

    @Override
    public String toString() {
        return "blank(" + width + ", " + height + ")";
    }
}
