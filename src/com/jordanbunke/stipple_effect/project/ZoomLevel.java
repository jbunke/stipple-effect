package com.jordanbunke.stipple_effect.project;

public enum ZoomLevel {
    MIN(0.0625f),
    MINUS_5(0.125f), MINUS_4(0.25f),
    MINUS_3(0.375f), MINUS_2(0.5f), MINUS_1(0.75f),
    NONE(1f),
    PLUS_1(1.25f), PLUS_2(1.5f), PLUS_3(1.75f),
    PLUS_4(2f), PLUS_5(3f), PLUS_6(4f),
    PLUS_7(5f), PLUS_8(6f), PLUS_9(8f),
    PLUS_10(10f), PLUS_11(12f), PLUS_12(16f),
    PLUS_13(24f), PLUS_14(32f), PLUS_15(48f),
    MAX(64f);

    public final float z;

    ZoomLevel(final float z) {
        this.z = z;
    }

    public static ZoomLevel fromZ(final float z) {
        for (final ZoomLevel zl : ZoomLevel.values())
            if (zl.z == z)
                return zl;

        return NONE;
    }

    public ZoomLevel out() {
        return switch (this) {
            case MAX -> PLUS_15;
            case PLUS_15 -> PLUS_14;
            case PLUS_14 -> PLUS_13;
            case PLUS_13 -> PLUS_12;
            case PLUS_12 -> PLUS_11;
            case PLUS_11 -> PLUS_10;
            case PLUS_10 -> PLUS_9;
            case PLUS_9 -> PLUS_8;
            case PLUS_8 -> PLUS_7;
            case PLUS_7 -> PLUS_6;
            case PLUS_6 -> PLUS_5;
            case PLUS_5 -> PLUS_4;
            case PLUS_4 -> PLUS_3;
            case PLUS_3 -> PLUS_2;
            case PLUS_2 -> PLUS_1;
            case PLUS_1 -> NONE;
            case NONE -> MINUS_1;
            case MINUS_1 -> MINUS_2;
            case MINUS_2 -> MINUS_3;
            case MINUS_3 -> MINUS_4;
            case MINUS_4 -> MINUS_5;
            default -> MIN;
        };
    }

    public ZoomLevel in() {
        return switch (this) {
            case PLUS_14 -> PLUS_15;
            case PLUS_13 -> PLUS_14;
            case PLUS_12 -> PLUS_13;
            case PLUS_11 -> PLUS_12;
            case PLUS_10 -> PLUS_11;
            case PLUS_9 -> PLUS_10;
            case PLUS_8 -> PLUS_9;
            case PLUS_7 -> PLUS_8;
            case PLUS_6 -> PLUS_7;
            case PLUS_5 -> PLUS_6;
            case PLUS_4 -> PLUS_5;
            case PLUS_3 -> PLUS_4;
            case PLUS_2 -> PLUS_3;
            case PLUS_1 -> PLUS_2;
            case NONE -> PLUS_1;
            case MINUS_1 -> NONE;
            case MINUS_2 -> MINUS_1;
            case MINUS_3 -> MINUS_2;
            case MINUS_4 -> MINUS_3;
            case MINUS_5 -> MINUS_4;
            case MIN -> MINUS_5;
            default -> MAX;
        };
    }

    @Override
    public String toString() {
        return z >= MINUS_2.z
                ? (int)(z * 100) + "%"
                : (z * 100) + "%";
    }
}
