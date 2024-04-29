package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.sprite.TextureColorReplace;
import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class TextureColorReplaceNode extends ExpressionNode {
    private final ExpressionNode texture, lookup, replacementColors;

    public TextureColorReplaceNode(
            final TextPosition position,
            final ExpressionNode texture,
            final ExpressionNode lookup,
            final ExpressionNode replacementColors
    ) {
        super(position);

        this.texture = texture;
        this.lookup = lookup;
        this.replacementColors = replacementColors;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        texture.semanticErrorCheck(symbolTable);
        lookup.semanticErrorCheck(symbolTable);
        replacementColors.semanticErrorCheck(symbolTable);

        final SimpleTypeNode
                imageType = new SimpleTypeNode(SimpleTypeNode.Type.IMAGE);

        final TypeNode
                textureType = texture.getType(symbolTable),
                lookupType = lookup.getType(symbolTable),
                replaceType = replacementColors.getType(symbolTable);

        if (!textureType.equals(imageType))
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.TEX_COL_REPL_ARG_NOT_IMG,
                    texture.getPosition(), "Texture",
                    textureType.toString());
        if (!lookupType.equals(imageType))
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.TEX_COL_REPL_ARG_NOT_IMG,
                    lookup.getPosition(), "Lookup",
                    lookupType.toString());
        if (!replaceType.equals(imageType))
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.TEX_COL_REPL_ARG_NOT_IMG,
                    replacementColors.getPosition(), "Replacement color",
                    replaceType.toString());
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final GameImage
                tex = (GameImage) texture.evaluate(symbolTable),
                lkp = (GameImage) lookup.evaluate(symbolTable),
                rep = (GameImage) replacementColors.evaluate(symbolTable);

        return TextureColorReplace.replace(tex, lkp, rep);
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return new SimpleTypeNode(SimpleTypeNode.Type.IMAGE);
    }
}
