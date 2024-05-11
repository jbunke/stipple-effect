package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.utility.math.MathPlus;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.LayerTypeNode;
import com.jordanbunke.stipple_effect.scripting.util.LayerRep;
import com.jordanbunke.stipple_effect.utility.Constants;

public final class SetOpacityNode extends LayerOpNode {
    public static final String NAME = "set_opacity";

    public SetOpacityNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args, LayerTypeNode.get(), TypeNode.getFloat());
    }

    @Override
    protected void operation(
            final LayerRep layer, final SymbolTable symbolTable
    ) {
        final double opacity = (double) getArgs()[1].evaluate(symbolTable);

        layer.project().changeLayerOpacity(
                MathPlus.bounded(0d, opacity, Constants.OPAQUE),
                layer.index(), true);
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
