package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.global.tex_lookup;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.sprite.TextureColorReplace;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.BaseTypeNode;
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

        final BaseTypeNode
                imageType = TypeNode.getImage();

        final TypeNode
                textureType = texture.getType(symbolTable),
                lookupType = lookup.getType(symbolTable),
                replaceType = replacementColors.getType(symbolTable);

        if (!textureType.equals(imageType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    texture.getPosition(), "Texture",
                    imageType.toString(), textureType.toString());
        if (!lookupType.equals(imageType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    lookup.getPosition(), "Lookup",
                    imageType.toString(), lookupType.toString());
        if (!replaceType.equals(imageType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    replacementColors.getPosition(), "Replacement color",
                    imageType.toString(), replaceType.toString());
    }

    @Override
    public GameImage evaluate(final SymbolTable symbolTable) {
        final GameImage
                tex = (GameImage) texture.evaluate(symbolTable),
                lkp = (GameImage) lookup.evaluate(symbolTable),
                rep = (GameImage) replacementColors.evaluate(symbolTable);

        return TextureColorReplace.replace(tex, lkp, rep);
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getImage();
    }

    @Override
    public String toString() {
        return "tex_col_repl(" + texture + ", " + lookup + ", " +
                replacementColors + ")";
    }
}
