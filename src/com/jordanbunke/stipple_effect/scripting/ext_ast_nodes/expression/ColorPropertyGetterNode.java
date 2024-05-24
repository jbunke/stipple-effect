package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.BaseTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.utility.math.ColorMath;

import java.awt.*;

public final class ColorPropertyGetterNode extends SEExtExpressionNode {
    public static final String HUE = "hue", SAT = "sat", VAL = "value";

    private final String property;

    private ColorPropertyGetterNode(
            final TextPosition position, final ExpressionNode[] args,
            final String property
    ) {
        super(position, args, TypeNode.getColor());

        this.property = property;
    }

    public static ColorPropertyGetterNode hue(
            final TextPosition position, final ExpressionNode[] args
    ) {
        return new ColorPropertyGetterNode(position, args, HUE);
    }

    public static ColorPropertyGetterNode sat(
            final TextPosition position, final ExpressionNode[] args
    ) {
        return new ColorPropertyGetterNode(position, args, SAT);
    }

    public static ColorPropertyGetterNode val(
            final TextPosition position, final ExpressionNode[] args
    ) {
        return new ColorPropertyGetterNode(position, args, VAL);
    }

    @Override
    public Double evaluate(final SymbolTable symbolTable) {
        final Color c = (Color) getValues(symbolTable)[0];

        return switch (property) {
            case HUE -> ColorMath.rgbToHue(c);
            case SAT -> ColorMath.rgbToSat(c);
            default -> ColorMath.rgbToValue(c);
        };
    }

    @Override
    public BaseTypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getFloat();
    }

    @Override
    protected String callName() {
        return property;
    }
}
