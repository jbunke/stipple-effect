package com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.native_calls;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.GameImageIO;
import com.jordanbunke.stipple_effect.scripting.util.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.nio.file.Path;

public final class ImageFromPathNode extends ExpressionNode {
    private final ExpressionNode path;

    public ImageFromPathNode(
            final TextPosition position,
            final ExpressionNode path
    ) {
        super(position);

        this.path = path;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        path.semanticErrorCheck(symbolTable);

        final TypeNode pathType = path.getType(symbolTable);

        if (!pathType.equals(new SimpleTypeNode(SimpleTypeNode.Type.STRING)))
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.PATH_NOT_STRING,
                    getPosition(), pathType.toString());
    }

    @Override
    public GameImage evaluate(final SymbolTable symbolTable) {
        final String fp = (String) path.evaluate(symbolTable);
        final GameImage image = GameImageIO.readImage(Path.of(fp));

        if (image == null)
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.PATH_DOES_NOT_CONTAIN_IMAGE,
                    getPosition(), fp);

        return image;
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return new SimpleTypeNode(SimpleTypeNode.Type.IMAGE);
    }

    @Override
    public String toString() {
        return "from(" + path + ")";
    }
}
