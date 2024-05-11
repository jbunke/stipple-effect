package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.tools.ToolThatSearches;

import java.util.HashSet;
import java.util.Set;

public abstract class SearchNode extends SEExtExpressionNode {
    protected SearchNode(
            final TextPosition position, final ExpressionNode[] args,
            final TypeNode... expectedTypes
    ) {
        super(position, args, expectedTypes);
    }

    protected final Set<Coord2D> search(
            final GameImage image, final int x, final int y,
            final double tol, final boolean global, final boolean diag
    ) {
        if (x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight())
            return new HashSet<>();

        final double tolBef = ToolThatSearches.getTolerance();
        final boolean globalBef = ToolThatSearches.isGlobal(),
                diagBef = ToolThatSearches.isSearchDiag();

        ToolThatSearches.setGlobal(global);
        ToolThatSearches.setSearchDiag(diag);
        ToolThatSearches.setTolerance(tol);

        final Set<Coord2D> pixels =
                ToolThatSearches.search(image, new Coord2D(x, y));

        ToolThatSearches.setGlobal(globalBef);
        ToolThatSearches.setSearchDiag(diagBef);
        ToolThatSearches.setTolerance(tolBef);

        return pixels;
    }
}
