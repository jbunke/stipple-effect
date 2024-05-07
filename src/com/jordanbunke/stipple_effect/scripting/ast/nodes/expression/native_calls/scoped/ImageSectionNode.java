package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.scoped;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.BaseTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

import java.util.Set;

public final class ImageSectionNode extends ScopedNativeCallNode {
    final ExpressionNode x, y, width, height;

    public ImageSectionNode(
            final TextPosition position,
            final ExpressionNode owner,
            final ExpressionNode x,
            final ExpressionNode y,
            final ExpressionNode width,
            final ExpressionNode height
    ) {
        super(position, owner,
                Set.of(TypeNode.getImage()));

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        x.semanticErrorCheck(symbolTable);
        y.semanticErrorCheck(symbolTable);
        width.semanticErrorCheck(symbolTable);
        height.semanticErrorCheck(symbolTable);

        super.semanticErrorCheck(symbolTable);

        final BaseTypeNode intType = TypeNode.getInt();

        final TypeNode
                xType = x.getType(symbolTable),
                yType = y.getType(symbolTable),
                wType = width.getType(symbolTable),
                hType = height.getType(symbolTable);

        if (!xType.equals(intType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    x.getPosition(), "X", "int", xType.toString());
        if (!yType.equals(intType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    y.getPosition(), "Y", "int", yType.toString());
        if (!wType.equals(intType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    width.getPosition(), "Width", "int", wType.toString());
        if (!hType.equals(intType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    height.getPosition(), "Height", "int", hType.toString());
    }

    @Override
    public GameImage evaluate(final SymbolTable symbolTable) {
        final int
                xVal = (int) x.evaluate(symbolTable),
                yVal = (int) y.evaluate(symbolTable),
                w = (int) width.evaluate(symbolTable),
                h = (int) height.evaluate(symbolTable);
        final GameImage source = (GameImage) getScope().evaluate(symbolTable);

        if (w > 0 && h > 0) {
            final GameImage section = new GameImage(w, h);
            section.draw(source, -xVal, -yVal);

            return section.submit();
        } else {
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
    String callName() {
        return "section()";
    }
}
