package com.jordanbunke.stipple_effect.utility.settings.types;

import java.util.Optional;

public final class StringSettingType implements SettingType<String> {
    private static final StringSettingType INSTANCE;

    private StringSettingType() {}

    static {
        INSTANCE = new StringSettingType();
    }

    public static StringSettingType get() {
        return INSTANCE;
    }

    @Override
    public Optional<String> parseValueFromRead(
            final String value
    ) {
        return Optional.of(value);
    }

    @Override
    public Class<String> getValueClass() {
        return String.class;
    }
}
