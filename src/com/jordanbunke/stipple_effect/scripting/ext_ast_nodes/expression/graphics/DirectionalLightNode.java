package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.graphics;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;

import java.awt.*;

import static com.jordanbunke.stipple_effect.scripting.util.LightingUtils.*;

public final class DirectionalLightNode extends GraphicsExpressionNode {
    public static final String NAME = "directional_light";

    public DirectionalLightNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args, TypeNode.getImage(), TypeNode.getImage(),
                TypeNode.getColor(), TypeNode.getFloat(),
                TypeNode.arrayOf(TypeNode.getFloat()));
    }

    @Override
    public GameImage evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.getValues(symbolTable);

        final GameImage texture = (GameImage) vs[0],
                normalMap = (GameImage) vs[1];
        final Color lightC = (Color) vs[2];
        final double luminosity = (double) vs[3];

        final ScriptArray scriptLightDir = (ScriptArray) vs[4];
        final double[] lightDir = scriptLightDir.stream()
                .mapToDouble(dim -> (double) dim).toArray();

        return directionalLight(texture, normalMap, lightC,
                luminosity, lightDir);
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
