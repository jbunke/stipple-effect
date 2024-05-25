package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptSet;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.palette.Palette;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.global.GlobalExpressionNode;

import java.util.Arrays;

public final class PaletteGetterNode extends GlobalExpressionNode {
    public static final String GET = "get_pal", HAS = "has_pal";

    private final boolean get;

    private PaletteGetterNode(
            final TextPosition position, final ExpressionNode[] args,
            final boolean get
    ) {
        super(position, args);

        this.get = get;
    }

    public static PaletteGetterNode newGet(
            final TextPosition position, final ExpressionNode[] args
    ) {
        return new PaletteGetterNode(position, args, true);
    }

    public static PaletteGetterNode newHas(
            final TextPosition position, final ExpressionNode[] args
    ) {
        return new PaletteGetterNode(position, args, false);
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final boolean hasPalette = StippleEffect.get().hasPaletteContents();

        if (!get)
            return hasPalette;

        if (hasPalette) {
            final Palette p = StippleEffect.get().getSelectedPalette();
            return new ScriptSet(Arrays.stream(p.getColors()));
        } else {
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    getPosition(), "There is no palette to get");

            return null;
        }
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return get ? TypeNode.setOf(TypeNode.getColor()) : TypeNode.getBool();
    }

    @Override
    protected String callName() {
        return get ? GET : HAS;
    }
}
