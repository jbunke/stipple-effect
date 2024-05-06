package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.literal;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.stip.ParserSerializer;

import java.awt.*;

public final class ColorHexCodeLiteralNode extends LiteralNode {
    private final Color value;

    public ColorHexCodeLiteralNode(
            final TextPosition position,
            final String code
    ) {
        super(position);

        // trim prepended #
        final String contents = code.substring(1);

        this.value = ParserSerializer.deserializeColor(
                contents + (contents.length() == 6 ? "ff" : ""));
    }

    @Override
    public Color evaluate(final SymbolTable symbolTable) {
        return value;
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getColor();
    }

    @Override
    public String toString() {
        return "#" + ParserSerializer.serializeColor(value, true);
    }
}
