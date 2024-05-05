package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.property;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

import java.awt.*;
import java.util.Set;

public final class ColorChannelNode extends NativePropertyFuncNode {
    private enum ColorChannel {
        RED, GREEN, BLUE, ALPHA;

        private static ColorChannel fromString(final String s) {
            final char channel = s.charAt(0);

            return switch (channel) {
                case 'r' -> RED;
                case 'g' -> GREEN;
                case 'b' -> BLUE;
                default -> ALPHA;
            };
        }

        private int evaluate(final Color c) {
            return switch (this) {
                case RED -> c.getRed();
                case GREEN -> c.getGreen();
                case BLUE -> c.getBlue();
                case ALPHA -> c.getAlpha();
            };
        }
    }

    private final ColorChannel channel;

    public ColorChannelNode(
            final TextPosition position,
            final ExpressionNode owner,
            final String channelString
    ) {
        super(position, owner, Set.of(TypeNode.getColor()));

        channel = ColorChannel.fromString(channelString);
    }

    @Override
    public Integer evaluate(final SymbolTable symbolTable) {
        return channel.evaluate(((Color) getOwner().evaluate(symbolTable)));
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getInt();
    }

    @Override
    String callName() {
        return channel.name().toLowerCase();
    }
}
