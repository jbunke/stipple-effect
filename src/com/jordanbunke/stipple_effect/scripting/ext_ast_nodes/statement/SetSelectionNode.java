package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.collection.ScriptSet;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.ProjectTypeNode;

import java.util.HashSet;
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
                convertSelection((ScriptSet) vs[1], w, h);

        project.deselect(false);
        project.editSelection(pixels, true);

        return FuncControlFlow.cont();
    }

    private Set<Coord2D> convertSelection(
            final ScriptSet input, final int w, final int h
    ) {
        final Set<Coord2D> pixels = new HashSet<>();

        input.stream().map(px -> (ScriptArray) px).forEach(a -> {
            final int x = (int) a.get(0), y = (int) a.get(1);

            if (x >= 0 && x < w && y >= 0 && y < h)
                pixels.add(new Coord2D(x, y));
        });

        return pixels;
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
