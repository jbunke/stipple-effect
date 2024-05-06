package com.jordanbunke.stipple_effect.scripting.util;

import org.antlr.v4.runtime.Token;

public record TextPosition(int line, int column) {
    public static final TextPosition N_A = new TextPosition(-1, -1);

    public static TextPosition fromToken(final Token token) {
        return new TextPosition(token.getLine(),
                token.getCharPositionInLine() + 1);
    }

    @Override
    public String toString() {
        if (isNA())
            return "N/A";

        return line + ":" + column;
    }

    private boolean isNA() {
        return line == N_A.line && column == N_A.column;
    }
}
