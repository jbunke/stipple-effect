package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.collection.ScriptSet;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.global.GlobalExpressionNode;
import com.jordanbunke.stipple_effect.scripting.util.ScriptSelectionUtils;
import com.jordanbunke.stipple_effect.selection.Outliner;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.util.Set;

public final class PresetOutlineNode extends GlobalExpressionNode {
    public static final String SINGLE = "single_outline",
            DOUBLE = "double_outline";

    private final boolean single;

    private PresetOutlineNode(
            final TextPosition position, final ExpressionNode[] args,
            final boolean single
    ) {
        super(position, args,
                TypeNode.setOf(TypeNode.arrayOf(TypeNode.getInt())),
                TypeNode.getInt());

        this.single = single;
    }

    public static PresetOutlineNode sng(
            final TextPosition position, final ExpressionNode[] args
    ) {
        return new PresetOutlineNode(position, args, true);
    }

    public static PresetOutlineNode dbl(
            final TextPosition position, final ExpressionNode[] args
    ) {
        return new PresetOutlineNode(position, args, false);
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final Object[] vs = getValues(symbolTable);
        final Set<Coord2D> selection =
                ScriptSelectionUtils.convertSelection((ScriptSet) vs[0]);

        final int side = (int) vs[1], MAX = Constants.MAX_OUTLINE_PX;

        if (side < -MAX || side > MAX) {
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    getArgs()[1].getPosition(),
                    "Side mask value " + side + " is out of bounds (" +
                            (-MAX) + " <= px <= " + MAX + ")");
            return null;
        }

        final int[] sideMask = new int[]
                { side, side, side, side, side, side, side, side };

        return new ScriptSet(Outliner.outline(selection, sideMask)
                .stream().map(c -> ScriptArray.of(c.x, c.y)));
    }

    @Override
    public CollectionTypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.setOf(TypeNode.arrayOf(TypeNode.getInt()));
    }

    @Override
    protected String callName() {
        return single ? SINGLE : DOUBLE;
    }
}
