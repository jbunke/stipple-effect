package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.native_calls;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.BaseTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

import java.awt.*;

public final class DotNode extends StatementNode {
    private final ExpressionNode canvas, color, x, y;

    public DotNode(
            final TextPosition position,
            final ExpressionNode canvas,
            final ExpressionNode color,
            final ExpressionNode x, final ExpressionNode y
    ) {
        super(position);

        this.canvas = canvas;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        canvas.semanticErrorCheck(symbolTable);
        color.semanticErrorCheck(symbolTable);
        x.semanticErrorCheck(symbolTable);
        y.semanticErrorCheck(symbolTable);

        final BaseTypeNode
                imgType = TypeNode.getImage(),
                colType = TypeNode.getColor(),
                intType = TypeNode.getInt();

        final TypeNode
                cType = canvas.getType(symbolTable),
                colArgType = color.getType(symbolTable),
                xType = x.getType(symbolTable),
                yType = y.getType(symbolTable);

        if (!cType.equals(imgType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    canvas.getPosition(), "Canvas",
                    "image", cType.toString());
        if (!colArgType.equals(colType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    color.getPosition(), "Color",
                    "color", colArgType.toString());
        if (!xType.equals(intType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    x.getPosition(), "X", "int", xType.toString());
        if (!yType.equals(intType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    y.getPosition(), "Y", "int", yType.toString());
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final GameImage
                c = (GameImage) canvas.evaluate(symbolTable);
        final Color col = (Color) color.evaluate(symbolTable);

        final int xCoord = (int) x.evaluate(symbolTable),
                yCoord = (int) y.evaluate(symbolTable);

        c.setRGB(xCoord, yCoord, col.getRGB());
        c.free();

        return FuncControlFlow.cont();
    }

    @Override
    public String toString() {
        return canvas + ".dot(" + color + ", " + x + ", " + y + ");";
    }
}
