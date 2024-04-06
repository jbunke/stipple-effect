package com.jordanbunke.stipple_effect.utility.setting_group;

import java.util.function.BinaryOperator;

public final class FloatToolSettingType implements ToolSettingType<Float> {
    private static final FloatToolSettingType INSTANCE = new FloatToolSettingType();

    public static FloatToolSettingType get() {
        return INSTANCE;
    }

    private FloatToolSettingType() {

    }

    @Override
    public BinaryOperator<Float> addition() {
        return Float::sum;
    }

    @Override
    public BinaryOperator<Float> subtraction() {
        return (a, b) -> a - b;
    }
}
