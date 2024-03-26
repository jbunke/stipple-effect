package com.jordanbunke.stipple_effect.utility.setting_group;

import java.util.function.BinaryOperator;

public final class DoubleToolSettingType implements ToolSettingType<Double> {
    private static final DoubleToolSettingType INSTANCE = new DoubleToolSettingType();

    public static DoubleToolSettingType get() {
        return INSTANCE;
    }

    private DoubleToolSettingType() {

    }

    @Override
    public BinaryOperator<Double> addition() {
        return Double::sum;
    }

    @Override
    public BinaryOperator<Double> subtraction() {
        return (a, b) -> a - b;
    }
}
