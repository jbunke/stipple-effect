package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.palette;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.BaseTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.palette.Palette;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.PropertyNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.PaletteTypeNode;

public final class PaletteMutableNode extends PropertyNode {
    public static final String NAME = "mutable";

    public PaletteMutableNode(
            final TextPosition position, final ExpressionNode scope
    ) {
        super(position, scope, PaletteTypeNode.get());
    }

    @Override
    public Boolean evaluate(final SymbolTable symbolTable) {
        final Palette p = (Palette) scope.evaluate(symbolTable);

        return p.isMutable();
    }

    @Override
    public BaseTypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getBool();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
