package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
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
                Set.of(new SimpleTypeNode(SimpleTypeNode.Type.IMAGE)));

        this.width = width;
    }

    @Override
    public Integer evaluate(final SymbolTable symbolTable) {
        final GameImage img = ((GameImage) getOwner().evaluate(symbolTable));

        return width ? img.getWidth() : img.getHeight();
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return new SimpleTypeNode(SimpleTypeNode.Type.INT);
    }

    @Override
    String callName() {
        return width ? "width" : "height";
    }
}
