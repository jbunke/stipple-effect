package com.jordanbunke.stipple_effect.utility.settings.types;

import java.util.Optional;

public final class BooleanSettingType implements SettingType<Boolean> {
    private static final BooleanSettingType INSTANCE;

    private BooleanSettingType() {}

    static {
        INSTANCE = new BooleanSettingType();
    }

    public static BooleanSettingType get() {
        return INSTANCE;
    }

    @Override
    public Optional<Boolean> parseValueFromRead(
            final String value
    ) {
        return Optional.of(Boolean.parseBoolean(value));
    }

    @Override
    public Class<Boolean> getValueClass() {
        return Boolean.class;
    }
}
