package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.global;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.collection.ScriptSet;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.util.ScriptSelectionUtils;
import com.jordanbunke.stipple_effect.selection.Outliner;
import com.jordanbunke.stipple_effect.selection.Selection;

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
        final Object[] vs = arguments.getValues(symbolTable);
        final Selection selection =
                ScriptSelectionUtils.convertSelection((ScriptSet) vs[0]);
        final int[] sideMask = ((ScriptArray) vs[1]).stream()
                .mapToInt(s -> (int) s).toArray();

        if (ScriptSelectionUtils.invalidSideMask(
                sideMask, arguments.args()[1]))
            return null;

        return new ScriptSet(Outliner.outline(selection, sideMask).getPixels()
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
