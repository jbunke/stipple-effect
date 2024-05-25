package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.global;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.BaseTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.utility.math.Coord2D;

import java.awt.*;
import java.util.Set;

public final class FillNode extends SearchNode {
    public static final String NAME = "fill";

    public FillNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args, TypeNode.getImage(), TypeNode.getColor(),
                TypeNode.getInt(), TypeNode.getInt(), TypeNode.getFloat(),
                TypeNode.getBool(), TypeNode.getBool());
    }

    @Override
    public GameImage evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.getValues(symbolTable);
        final GameImage img = (GameImage) vs[0];
        final Color color = (Color) vs[1];
        final int x = (int) vs[2], y = (int) vs[3];
        final double tol = (double) vs[4];
        final boolean global = (boolean) vs[5], diag = (boolean) vs[6];

        final Set<Coord2D> pixels = search(img, x, y, tol, global, diag);
        final GameImage filled = new GameImage(img);

        for (Coord2D pixel : pixels)
            filled.setRGB(pixel.x, pixel.y, color.getRGB());

        return filled;
    }

    @Override
    public BaseTypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getImage();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
