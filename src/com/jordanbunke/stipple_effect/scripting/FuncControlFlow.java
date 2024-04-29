package com.jordanbunke.stipple_effect.scripting;

public final class FuncControlFlow {
    public final boolean cont;
    public final Object value;

    private FuncControlFlow(
            final boolean cont, final Object value
    ) {
        this.cont = cont;
        this.value = value;
    }

    public static FuncControlFlow cont() {
        return new FuncControlFlow(true, null);
    }

    public static FuncControlFlow breakCF() {
        return new FuncControlFlow(false, null);
    }

    public static FuncControlFlow returnVoid() {
        return new FuncControlFlow(false, null);
    }

    public static FuncControlFlow returnVal(final Object value) {
        return new FuncControlFlow(false, value);
    }
}
