package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type;

import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.palette.Palette;

public final class PaletteTypeNode extends SEExtTypeNode {
    public static final String NAME = "palette";
    private static final PaletteTypeNode INSTANCE;

    public PaletteTypeNode(final TextPosition position) {
        super(position);
    }

    private PaletteTypeNode() {
        this(TextPosition.N_A);
    }

    static {
        INSTANCE = new PaletteTypeNode();
    }

    public static PaletteTypeNode get() {
        return INSTANCE;
    }

    @Override
    public boolean complies(final Object o) {
        return o instanceof Palette;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
