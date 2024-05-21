package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.project.SEContext;

public final class MoveLayerNode extends CondProjectOpNode {
    public static final String DOWN = "move_layer_down",
            UP = "move_layer_up";

    private final boolean down;

    private MoveLayerNode(
            final TextPosition position, final ExpressionNode[] args,
            final boolean down
    ) {
        super(position, args);

        this.down = down;
    }

    public static MoveLayerNode down(
            final TextPosition position, final ExpressionNode[] args
    ) {
        return new MoveLayerNode(position, args, true);
    }

    public static MoveLayerNode up(
            final TextPosition position, final ExpressionNode[] args
    ) {
        return new MoveLayerNode(position, args, false);
    }

    @Override
    protected boolean condition(final SEContext project) {
        return down ? project.getState().canMoveLayerDown()
                : project.getState().canMoveLayerUp();
    }

    @Override
    protected String attempt() {
        return "move the selected layer " + (down ? "down" : "up");
    }

    @Override
    protected String failReason(final SEContext project) {
        return "the selected layer is already the " +
                (down ? "lowest" : "highest") + " layer in the project";
    }

    @Override
    protected void operation(final SEContext project) {
        if (down)
            project.moveLayerDown();
        else
            project.moveLayerUp();
    }

    @Override
    protected String callName() {
        return down ? DOWN : UP;
    }
}
