package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.Constants;

public final class AddFrameNode extends CondProjectOpNode {
    public static final String ADD_NAME = "add_frame",
            DUPE_NAME = "duplicate_frame";

    private final boolean duplicate;

    private AddFrameNode(
            final TextPosition position, final ExpressionNode[] args,
            final boolean duplicate
    ) {
        super(position, args);

        this.duplicate = duplicate;
    }

    public static AddFrameNode newAdd(
            final TextPosition position, final ExpressionNode[] args
    ) {
        return new AddFrameNode(position, args, false);
    }

    public static AddFrameNode newDupe(
            final TextPosition position, final ExpressionNode[] args
    ) {
        return new AddFrameNode(position, args, true);
    }

    @Override
    protected boolean condition(final SEContext project) {
        return project.getState().getFrameCount() < Constants.MAX_NUM_FRAMES;
    }

    @Override
    protected String attempt() {
        return duplicate ?
                "duplicate the current frame" :
                "add a frame to the project";
    }

    @Override
    protected String failReason(final SEContext project) {
        return "the project already contains " +
                Constants.MAX_NUM_FRAMES + " frames";
    }

    @Override
    protected void operation(final SEContext project) {
        if (duplicate)
            project.duplicateFrame();
        else
            project.addFrame();
    }

    @Override
    protected String callName() {
        return duplicate ? DUPE_NAME : ADD_NAME;
    }
}
