package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.collection.ScriptSet;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.SEExtExpressionNode;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class FillSelectionNode extends SEExtExpressionNode {
    public static final String NAME = "fill_selection";

    private final boolean systemSelection;

    private FillSelectionNode(
            final TextPosition position, final ExpressionNode[] args,
            final boolean systemSelection, final TypeNode... expectedTypes
    ) {
        super(position, args, expectedTypes);

        this.systemSelection = systemSelection;
    }

    public static FillSelectionNode system(
            final TextPosition position, final ExpressionNode[] args
    ) {
        return new FillSelectionNode(position, args, true,
                TypeNode.getImage(), TypeNode.getColor());
    }

    public static FillSelectionNode custom(
            final TextPosition position, final ExpressionNode[] args
    ) {
        return new FillSelectionNode(position, args, false,
                TypeNode.getImage(), TypeNode.getColor(),
                TypeNode.setOf(TypeNode.arrayOf(TypeNode.getInt())));
    }

    @Override
    public GameImage evaluate(final SymbolTable symbolTable) {
        final Object[] vs = getValues(symbolTable);
        final GameImage img = (GameImage) vs[0],
                res = new GameImage(img.getWidth(), img.getHeight());
        final Color c = (Color) vs[1];
        final Set<Coord2D> pixels = systemSelection
                ? StippleEffect.get().getContext().getState().getSelection()
                : convertSelection((ScriptSet) vs[2]);

        for (Coord2D pixel : pixels)
            res.setRGB(pixel.x, pixel.y, c.getRGB());

        return res;
    }

    private Set<Coord2D> convertSelection(final ScriptSet input) {
        final Set<Coord2D> pixels = new HashSet<>();

        input.stream().map(px -> (ScriptArray) px).forEach(a -> {
            final int x = (int) a.get(0), y = (int) a.get(1);
            pixels.add(new Coord2D(x, y));
        });

        return pixels;
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
