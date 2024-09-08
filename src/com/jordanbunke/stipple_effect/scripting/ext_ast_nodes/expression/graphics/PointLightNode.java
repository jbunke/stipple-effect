package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.graphics;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.utility.math.Coord2D;

import java.awt.*;

import static com.jordanbunke.stipple_effect.scripting.util.LightingUtils.*;

public final class PointLightNode extends GraphicsExpressionNode {
    public static final String NAME = "point_light";

    public PointLightNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args, TypeNode.getImage(), TypeNode.getImage(),
                TypeNode.getColor(), TypeNode.getInt(), TypeNode.getInt(),
                TypeNode.getFloat(), TypeNode.getFloat(), TypeNode.getFloat());
    }

    @Override
    public GameImage evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.getValues(symbolTable);

        final GameImage texture = (GameImage) vs[0],
                normalMap = (GameImage) vs[1];
        final Color lightC = (Color) vs[2];

        final int x = (int) vs[3], y = (int) vs[4];
        final double z = (double) vs[5], lumAtSource = (double) vs[6],
                radius = (double) vs[7];

        return pointLight(texture, normalMap, lightC,
                new Coord2D(x, y), z, lumAtSource, radius);
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getImage();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
