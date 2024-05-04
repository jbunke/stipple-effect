package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.native_calls;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

import java.awt.*;

public final class DrawLineNode extends StatementNode {
    private final ExpressionNode canvas, color, breadth, x1, y1, x2, y2;

    public DrawLineNode(
            final TextPosition position,
            final ExpressionNode canvas,
            final ExpressionNode color,
            final ExpressionNode breadth,
            final ExpressionNode x1, final ExpressionNode y1,
            final ExpressionNode x2, final ExpressionNode y2
    ) {
        super(position);

        this.canvas = canvas;
        this.color = color;
        this.breadth = breadth;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        canvas.semanticErrorCheck(symbolTable);
        color.semanticErrorCheck(symbolTable);
        breadth.semanticErrorCheck(symbolTable);
        x1.semanticErrorCheck(symbolTable);
        y1.semanticErrorCheck(symbolTable);
        x2.semanticErrorCheck(symbolTable);
        y2.semanticErrorCheck(symbolTable);

        final SimpleTypeNode
                imgType = new SimpleTypeNode(SimpleTypeNode.Type.IMAGE),
                colType = new SimpleTypeNode(SimpleTypeNode.Type.COLOR),
                floatType = new SimpleTypeNode(SimpleTypeNode.Type.FLOAT),
                intType = new SimpleTypeNode(SimpleTypeNode.Type.INT);

        final TypeNode
                cType = canvas.getType(symbolTable),
                colArgType = color.getType(symbolTable),
                breadthType = breadth.getType(symbolTable),
                x1Type = x1.getType(symbolTable),
                y1Type = y1.getType(symbolTable),
                x2Type = x2.getType(symbolTable),
                y2Type = y2.getType(symbolTable);

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
        if (!breadthType.equals(floatType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    color.getPosition(), "Stroke breadth",
                    "float", breadthType.toString());
        if (!x1Type.equals(intType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    x1.getPosition(), "X1", "int", x1Type.toString());
        if (!y1Type.equals(intType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    y1.getPosition(), "Y1", "int", y1Type.toString());
        if (!x2Type.equals(intType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    x2.getPosition(), "X2", "int", x2Type.toString());
        if (!y2Type.equals(intType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    y2.getPosition(), "Y2", "int", y2Type.toString());
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final GameImage
                c = (GameImage) canvas.evaluate(symbolTable);
        final Color col = (Color) color.evaluate(symbolTable);

        final float b = ((Double) breadth.evaluate(symbolTable)).floatValue();

        if (b < 0f)
            return FuncControlFlow.cont();

        final int x1Coord = (int) x1.evaluate(symbolTable),
                y1Coord = (int) y1.evaluate(symbolTable),
                x2Coord = (int) x2.evaluate(symbolTable),
                y2Coord = (int) y2.evaluate(symbolTable);

        c.drawLine(col, b, x1Coord, y1Coord, x2Coord, y2Coord);
        c.free();

        return FuncControlFlow.cont();
    }

    @Override
    public String toString() {
        return canvas + ".line(" + color + ", " + x1 + ", " +
                y1 + ", " + x2 + ", " + y2 + ");";
    }
}
