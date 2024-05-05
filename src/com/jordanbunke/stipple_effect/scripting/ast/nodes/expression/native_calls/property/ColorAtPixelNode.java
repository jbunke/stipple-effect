package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.property;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.awt.*;
import java.util.Set;

public final class ColorAtPixelNode extends NativePropertyFuncNode {
    private final ExpressionNode x, y;

    public ColorAtPixelNode(
            final TextPosition position,
            final ExpressionNode owner,
            final ExpressionNode x,
            final ExpressionNode y
    ) {
        super(position, owner,
                Set.of(TypeNode.getImage()));

        this.x = x;
        this.y = y;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        x.semanticErrorCheck(symbolTable);
        y.semanticErrorCheck(symbolTable);

        super.semanticErrorCheck(symbolTable);

        final SimpleTypeNode intType = TypeNode.getInt();

        final TypeNode
                xType = x.getType(symbolTable),
                yType = y.getType(symbolTable);

        if (!xType.equals(intType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    getPosition(), "X", "int", xType.toString());
        if (!yType.equals(intType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    getPosition(), "Y", "int", yType.toString());
    }

    @Override
    public Color evaluate(final SymbolTable symbolTable) {
        final int pixelX = (int) x.evaluate(symbolTable),
                pixelY = (int) y.evaluate(symbolTable);
        final GameImage img = ((GameImage) getOwner().evaluate(symbolTable));

        if (pixelX < 0 || pixelX >= img.getWidth())
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.PIX_ARG_OUT_OF_BOUNDS,
                    getPosition(), "X", String.valueOf(pixelX),
                    "width -- " + img.getWidth());
        if (pixelY < 0 || pixelY >= img.getHeight())
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.PIX_ARG_OUT_OF_BOUNDS,
                    getPosition(), "Y", String.valueOf(pixelY),
                    "height -- " + img.getHeight());

        return img.getColorAt(pixelX, pixelY);
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getColor();
    }

    @Override
    String callName() {
        return "pixel";
    }
}