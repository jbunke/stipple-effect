package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.ScrippleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.util.Set;

public final class ImageBoundNode extends NativeFuncWithOwnerNode {
    private final boolean width;

    public ImageBoundNode(
            final TextPosition position,
            final ExpressionNode owner,
            final boolean width
    ) {
        super(position, owner,
                Set.of(new SimpleTypeNode(SimpleTypeNode.Type.IMAGE)),
                ScrippleErrorListener.Message.EXPECTED_IMAGE_FOR_CALL);

        this.width = width;
    }

    @Override
    public Integer evaluate(final SymbolTable symbolTable) {
        final GameImage img = ((GameImage) getOwner().evaluate(symbolTable));

        return width ? img.getWidth() : img.getHeight();
    }

    @Override
    public ScrippleTypeNode getType(final SymbolTable symbolTable) {
        return new SimpleTypeNode(SimpleTypeNode.Type.INT);
    }
}
