package com.jordanbunke.stipple_effect.utility.settings.types;

import java.util.Optional;

public final class IntSettingType implements SettingType<Integer> {
    private static final IntSettingType INSTANCE;

    private IntSettingType() {}

    static {
        INSTANCE = new IntSettingType();
    }

    public static IntSettingType get() {
        return INSTANCE;
    }

    @Override
    public Optional<Integer> parseValueFromRead(
            final String value
    ) {
        try {
            return Optional.of(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Override
    public Class<Integer> getValueClass() {
        return Integer.class;
    }
}
