package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.native_calls;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.stipple_effect.scripting.util.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class DrawNode extends StatementNode {
    private final ExpressionNode canvas, superimposed, x, y;

    public DrawNode(
            final TextPosition position,
            final ExpressionNode canvas,
            final ExpressionNode superimposed,
            final ExpressionNode x, final ExpressionNode y
    ) {
        super(position);

        this.canvas = canvas;
        this.superimposed = superimposed;
        this.x = x;
        this.y = y;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        canvas.semanticErrorCheck(symbolTable);
        superimposed.semanticErrorCheck(symbolTable);
        x.semanticErrorCheck(symbolTable);
        y.semanticErrorCheck(symbolTable);

        final SimpleTypeNode
                imgType = TypeNode.getImage(),
                intType = TypeNode.getInt();

        final TypeNode
                cType = canvas.getType(symbolTable),
                sType = superimposed.getType(symbolTable),
                xType = x.getType(symbolTable),
                yType = y.getType(symbolTable);

        if (!cType.equals(imgType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    canvas.getPosition(), "Canvas",
                    "image", cType.toString());
        if (!sType.equals(imgType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    superimposed.getPosition(), "Superimposed",
                    "image", sType.toString());
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
                c = (GameImage) canvas.evaluate(symbolTable),
                s = (GameImage) superimposed.evaluate(symbolTable);

        final int xCoord = (int) x.evaluate(symbolTable),
                yCoord = (int) y.evaluate(symbolTable);

        c.draw(s, xCoord, yCoord);
        c.free();

        return FuncControlFlow.cont();
    }

    @Override
    public String toString() {
        return canvas + ".draw(" + superimposed + ", " + x + ", " + y + ");";
    }
}
