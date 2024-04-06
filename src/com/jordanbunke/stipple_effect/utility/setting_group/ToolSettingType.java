package com.jordanbunke.stipple_effect.utility.setting_group;

import java.util.function.BinaryOperator;

public sealed interface ToolSettingType<R extends Number> permits
        DoubleToolSettingType, FloatToolSettingType, IntToolSettingType {
    BinaryOperator<R> addition();
    BinaryOperator<R> subtraction();
}
