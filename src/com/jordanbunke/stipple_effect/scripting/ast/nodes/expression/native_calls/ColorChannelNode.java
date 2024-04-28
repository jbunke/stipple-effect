package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls;

import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.ScrippleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.awt.*;
import java.util.Set;

public final class ColorChannelNode extends NativeFuncWithOwnerNode {
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
        super(position, owner,
                Set.of(new SimpleTypeNode(SimpleTypeNode.Type.COLOR)),
                ScrippleErrorListener.Message.EXPECTED_COLOR_FOR_CALL);

        channel = ColorChannel.fromString(channelString);
    }

    @Override
    public Integer evaluate(final SymbolTable symbolTable) {
        return channel.evaluate(((Color) getOwner().evaluate(symbolTable)));
    }

    @Override
    public ScrippleTypeNode getType(final SymbolTable symbolTable) {
        return new SimpleTypeNode(SimpleTypeNode.Type.INT);
    }
}
