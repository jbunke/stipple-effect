package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls.global.tex_lookup;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.sprite.TextureColorReplace;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.BaseTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;

public final class GenLookupNode extends ExpressionNode {
    private final ExpressionNode source, vert;

    public GenLookupNode(
            final TextPosition position,
            final ExpressionNode source,
            final ExpressionNode vert
    ) {
        super(position);

        this.source = source;
        this.vert = vert;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        source.semanticErrorCheck(symbolTable);
        vert.semanticErrorCheck(symbolTable);

        final BaseTypeNode
                imageType = TypeNode.getImage(),
                boolType = TypeNode.getBool();

        final TypeNode
                sourceType = source.getType(symbolTable),
                vertType = vert.getType(symbolTable);

        if (!sourceType.equals(imageType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    source.getPosition(), "Source image",
                    imageType.toString(), sourceType.toString());
        if (!vertType.equals(boolType))
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARG_NOT_TYPE,
                    vert.getPosition(), "Vertical striping flag",
                    boolType.toString(), vertType.toString());
    }

    @Override
    public GameImage evaluate(final SymbolTable symbolTable) {
        final GameImage src = (GameImage) source.evaluate(symbolTable);
        final boolean isVert = (boolean) vert.evaluate(symbolTable);

        return TextureColorReplace.generateNaiveLookup(src, isVert);
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getImage();
    }

    @Override
    public String toString() {
        return "gen_lookup(" + source + ", " + vert + ")";
    }
}
