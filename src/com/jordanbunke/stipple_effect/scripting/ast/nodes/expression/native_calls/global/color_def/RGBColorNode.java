package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.global.color_def;

import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.BaseTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.awt.*;

public final class RGBColorNode extends ExpressionNode {
    private final ExpressionNode r, g, b;

    public RGBColorNode(
            final TextPosition position,
            final ExpressionNode r, final ExpressionNode g,
            final ExpressionNode b
    ) {
        super(position);

        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        r.semanticErrorCheck(symbolTable);
        g.semanticErrorCheck(symbolTable);
        b.semanticErrorCheck(symbolTable);

        final BaseTypeNode intType = TypeNode.getInt();

        final TypeNode
                rType = r.getType(symbolTable),
                gType = g.getType(symbolTable),
                bType = b.getType(symbolTable);

        if (!rType.equals(intType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.COLOR_CHANNEL_NOT_INT,
                    getPosition(), "Red", rType.toString());
        if (!gType.equals(intType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.COLOR_CHANNEL_NOT_INT,
                    getPosition(), "Green", gType.toString());
        if (!bType.equals(intType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.COLOR_CHANNEL_NOT_INT,
                    getPosition(), "Blue", bType.toString());
    }

    @Override
    public Color evaluate(final SymbolTable symbolTable) {
        final int MIN = 0, MAX = 0xff,
                rv = (int) r.evaluate(symbolTable),
                gv = (int) g.evaluate(symbolTable),
                bv = (int) b.evaluate(symbolTable);

        if (rv < MIN || rv > MAX)
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.COLOR_CHANNEL_OUT_OF_BOUNDS,
                    getPosition(), "Red", String.valueOf(rv));
        if (gv < MIN || gv > MAX)
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.COLOR_CHANNEL_OUT_OF_BOUNDS,
                    getPosition(), "Green", String.valueOf(gv));
        if (bv < MIN || bv > MAX)
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.COLOR_CHANNEL_OUT_OF_BOUNDS,
                    getPosition(), "Blue", String.valueOf(bv));

        return new Color(rv, gv, bv);
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getColor();
    }

    @Override
    public String toString() {
        return "rgb(" + r + ", " + g + ", " + b + ")";
    }
}
