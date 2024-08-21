package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.layer;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.utility.Constants;

public final class OpacityGetterNode extends LayerExpressionNode {
    public  static final String OPACITY = "get_opacity",
            OPAQUE = "is_opaque";

    private final boolean get;

    private OpacityGetterNode(
            final TextPosition position, final ExpressionNode scope,
            final ExpressionNode[] args, final boolean get
    ) {
        super(position, scope, args);

        this.get = get;
    }

    public static OpacityGetterNode opacity(
            final TextPosition position,
            final ExpressionNode scope, final ExpressionNode[] args
    ) {
        return new OpacityGetterNode(position, scope, args, true);
    }

    public static OpacityGetterNode opaque(
            final TextPosition position,
            final ExpressionNode scope, final ExpressionNode[] args
    ) {
        return new OpacityGetterNode(position, scope, args, false);
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final double opacity = layerRep(symbolTable).get().getOpacity();

        return get ? opacity : opacity == Constants.OPAQUE;
    }

    @Override
    public TypeNode getType(SymbolTable symbolTable) {
        return get ? TypeNode.getFloat() : TypeNode.getBool();
    }

    @Override
    protected String callName() {
        return get ? OPACITY : OPAQUE;
    }
}
