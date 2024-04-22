package com.jordanbunke.stipple_effect.scripting;

import org.antlr.v4.runtime.Token;

public record TextPosition(int line, int column) {
    public static TextPosition fromToken(final Token token) {
        return new TextPosition(token.getLine(),
                token.getCharPositionInLine());
    }
}
