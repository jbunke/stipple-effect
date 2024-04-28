package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls;

import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.ScrippleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.awt.*;

public final class RGBAColorNode extends ExpressionNode {
    private final ExpressionNode r, g, b, a;

    public RGBAColorNode(
            final TextPosition position,
            final ExpressionNode r, final ExpressionNode g,
            final ExpressionNode b, final ExpressionNode a
    ) {
        super(position);

        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        final SimpleTypeNode intType =
                new SimpleTypeNode(SimpleTypeNode.Type.INT);

        final ScrippleTypeNode
                rType = r.getType(symbolTable),
                gType = g.getType(symbolTable),
                bType = b.getType(symbolTable),
                aType = a.getType(symbolTable);

        if (!rType.equals(intType))
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.COLOR_CHANNEL_NOT_INT,
                    getPosition(), "Red", rType.toString());
        if (!gType.equals(intType))
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.COLOR_CHANNEL_NOT_INT,
                    getPosition(), "Green", gType.toString());
        if (!bType.equals(intType))
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.COLOR_CHANNEL_NOT_INT,
                    getPosition(), "Blue", bType.toString());
        if (!aType.equals(intType))
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.COLOR_CHANNEL_NOT_INT,
                    getPosition(), "Alpha", aType.toString());

        r.semanticErrorCheck(symbolTable);
        g.semanticErrorCheck(symbolTable);
        b.semanticErrorCheck(symbolTable);
        a.semanticErrorCheck(symbolTable);
    }

    @Override
    public Color evaluate(final SymbolTable symbolTable) {
        final int MIN = 0, MAX = 0xff,
                rv = (int) r.evaluate(symbolTable),
                gv = (int) g.evaluate(symbolTable),
                bv = (int) b.evaluate(symbolTable),
                av = (int) a.evaluate(symbolTable);

        if (rv < MIN || rv > MAX)
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.COLOR_CHANNEL_OUT_OF_BOUNDS,
                    getPosition(), "Red", String.valueOf(rv));
        if (gv < MIN || gv > MAX)
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.COLOR_CHANNEL_OUT_OF_BOUNDS,
                    getPosition(), "Green", String.valueOf(gv));
        if (bv < MIN || bv > MAX)
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.COLOR_CHANNEL_OUT_OF_BOUNDS,
                    getPosition(), "Blue", String.valueOf(bv));
        if (av < MIN || av > MAX)
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.COLOR_CHANNEL_OUT_OF_BOUNDS,
                    getPosition(), "Alpha", String.valueOf(av));

        return new Color(rv, gv, bv, av);
    }

    @Override
    public ScrippleTypeNode getType(final SymbolTable symbolTable) {
        return new SimpleTypeNode(SimpleTypeNode.Type.COLOR);
    }
}
