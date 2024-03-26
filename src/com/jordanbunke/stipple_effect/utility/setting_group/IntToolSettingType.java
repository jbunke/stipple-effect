package com.jordanbunke.stipple_effect.utility.setting_group;

import java.util.function.BinaryOperator;

public final class IntToolSettingType implements ToolSettingType<Integer> {
    private static final IntToolSettingType INSTANCE = new IntToolSettingType();

    public static IntToolSettingType get() {
        return INSTANCE;
    }

    private IntToolSettingType() {

    }

    @Override
    public BinaryOperator<Integer> addition() {
        return Integer::sum;
    }

    @Override
    public BinaryOperator<Integer> subtraction() {
        return (a, b) -> a - b;
    }
}
