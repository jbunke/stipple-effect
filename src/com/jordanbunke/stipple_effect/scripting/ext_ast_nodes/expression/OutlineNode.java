package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.collection.ScriptSet;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.global.GlobalExpressionNode;
import com.jordanbunke.stipple_effect.scripting.util.ScriptSelectionUtils;
import com.jordanbunke.stipple_effect.selection.Outliner;

import java.util.Set;

public final class OutlineNode extends GlobalExpressionNode {
    public static final String NAME = "outline";

    public OutlineNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args,
                TypeNode.setOf(TypeNode.arrayOf(TypeNode.getInt())),
                TypeNode.arrayOf(TypeNode.getInt()));
    }

    @Override
    public ScriptSet evaluate(final SymbolTable symbolTable) {
        final Object[] vs = getValues(symbolTable);
        final Set<Coord2D> selection =
                ScriptSelectionUtils.convertSelection((ScriptSet) vs[0]);
        final int[] sideMask = ((ScriptArray) vs[1]).stream()
                .mapToInt(s -> (int) s).toArray();

        if (ScriptSelectionUtils.invalidSideMask(sideMask, getArgs()[1]))
            return null;

        return new ScriptSet(Outliner.outline(selection, sideMask)
                .stream().map(c -> ScriptArray.of(c.x, c.y)));
    }

    @Override
    public CollectionTypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.setOf(TypeNode.arrayOf(TypeNode.getInt()));
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
