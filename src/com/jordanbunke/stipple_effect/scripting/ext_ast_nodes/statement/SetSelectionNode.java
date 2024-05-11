package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptSet;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.ProjectTypeNode;
import com.jordanbunke.stipple_effect.scripting.util.ScriptSelectionUtils;

import java.util.Set;

public final class SetSelectionNode extends SEExtStatementNode {
    public static final String NAME = "set_selection";

    public SetSelectionNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args, ProjectTypeNode.get(),
                TypeNode.setOf(TypeNode.arrayOf(TypeNode.getInt())));
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final Object[] vs = getValues(symbolTable);
        final SEContext project = (SEContext) vs[0];
        final int w = project.getState().getImageWidth(),
                h = project.getState().getImageHeight();
        final Set<Coord2D> pixels =
                ScriptSelectionUtils.convertSelection((ScriptSet) vs[1], w, h);

        project.deselect(false);
        project.editSelection(pixels, true);

        return FuncControlFlow.cont();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
