package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.graphics;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.delta_time.utility.math.Pair;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public final class LightMaskNode extends GraphicsExpressionNode {
    public static final String NAME = "light_mask";

    public LightMaskNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args, TypeNode.getImage(),
                TypeNode.getFloat(), TypeNode.getFloat());
    }

    @Override
    public GameImage evaluate(final SymbolTable symbolTable) {
        final Object[] args = arguments.getValues(symbolTable);

        final GameImage source = (GameImage) args[0];
        final double luminosity = (double) args[1], radius = (double) args[2];

        return generate(source, luminosity, radius);
    }

    private GameImage generate(
            final GameImage source,
            final double luminosity, final double radius
    ) {
        final Set<Pair<Color, Coord2D>> lightSources = new HashSet<>();
        final int w = source.getWidth(), h = source.getHeight();
        final GameImage lightMask = new GameImage(w, h);

        if (radius <= 0d || luminosity <= 0d)
            return lightMask.submit();

        // 1: identify all non-transparent pixels as light sources

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                final Color c = source.getColorAt(x, y);

                if (c.getAlpha() > 0)
                    lightSources.add(new Pair<>(c, new Coord2D(x, y)));
            }
        }

        // 2: apply lighting

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                for (Pair<Color, Coord2D> lightSource : lightSources) {
                    final double distance = Coord2D.unitDistanceBetween(
                            new Coord2D(x, y), lightSource.b());

                    if (distance >= radius)
                        continue;

                    final double intensity = luminosity * (1d - (distance / radius));
                    final int r = lightSource.a().getRed(),
                            g = lightSource.a().getGreen(),
                            b = lightSource.a().getBlue(),
                            a = lightSource.a().getAlpha();
                    final Color delta = new Color(r, g, b,
                            Math.min(Constants.RGBA_SCALE, (int)(intensity * a)));

                    lightMask.dot(delta, x, y);
                }
            }
        }

        return lightMask.submit();
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
