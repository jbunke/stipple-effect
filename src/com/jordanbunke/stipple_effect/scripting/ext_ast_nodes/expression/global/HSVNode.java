package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.global;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.BaseTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.math.ColorMath;

import java.awt.*;

public final class HSVNode extends GlobalExpressionNode {
    public static final String NAME = "hsv";

    private HSVNode(
            final TextPosition position, final ExpressionNode[] args,
            final TypeNode... expectedTypes
    ) {
        super(position, args, expectedTypes);
    }

    public static HSVNode newHSV(
            final TextPosition position, final ExpressionNode[] args
    ) {
        return new HSVNode(position, args, TypeNode.getFloat(),
                TypeNode.getFloat(), TypeNode.getFloat());
    }

    public static HSVNode withAlpha(
            final TextPosition position, final ExpressionNode[] args
    ) {
        return new HSVNode(position, args, TypeNode.getFloat(),
                TypeNode.getFloat(), TypeNode.getFloat(), TypeNode.getInt());
    }

    @Override
    public Color evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.getValues(symbolTable);
        final double h = (double) vs[0],
                s = (double) vs[1], v = (double) vs[2];
        final int a = vs.length == 4 ? (int) vs[3] : Constants.RGBA_SCALE;

        return ColorMath.fromHSV(h, s, v, a);
    }

    @Override
    public BaseTypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getColor();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
