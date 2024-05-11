package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.project.SEContext;

public final class SelectionOpNode extends CondProjectOpNode {
    public static final String
            INV = "invert_selection",
            ALL = "select_all",
            DESELECT = "deselect";

    public enum Op {
        DESELECT, SELECT_ALL, INVERT_SELECTION;

        public final String NAME = name().toLowerCase();
    }

    private final Op operation;

    public SelectionOpNode(
            final TextPosition position, final ExpressionNode[] args,
            final Op operation
    ) {
        super(position, args);

        this.operation = operation;
    }

    @Override
    protected boolean condition(final SEContext project) {
        return true;
    }

    @Override
    protected String attempt() {
        return switch (operation) {
            case DESELECT -> "void the project's selection";
            case SELECT_ALL -> "select all pixels in the project canvas";
            case INVERT_SELECTION -> "invert the project selection";
        };
    }

    @Override
    protected String failReason(final SEContext project) {
        return ""; // never reached because condition() returns true
    }

    @Override
    protected void operation(final SEContext project) {
        switch (operation) {
            case SELECT_ALL -> project.selectAll();
            case DESELECT -> project.deselect(true);
            case INVERT_SELECTION -> project.invertSelection();
        }
    }

    @Override
    protected String callName() {
        return operation.NAME;
    }
}
