package com.jordanbunke.stipple_effect.scripting;

import org.antlr.v4.runtime.Token;

public record TextPosition(int line, int column) {
    public static final TextPosition N_A = new TextPosition(-1, -1);

    public static TextPosition fromToken(final Token token) {
        return new TextPosition(token.getLine(),
                token.getCharPositionInLine());
    }
}
