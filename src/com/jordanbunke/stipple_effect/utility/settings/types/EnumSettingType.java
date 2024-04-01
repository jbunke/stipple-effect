package com.jordanbunke.stipple_effect.utility.settings.types;

import java.util.IllegalFormatException;
import java.util.Optional;

public final class EnumSettingType<T extends Enum<T>> implements SettingType<T> {
    private final Class<T> enumClass;

    public EnumSettingType(final Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public Optional<T> parseValueFromRead(final String value) {
        try {
            return Optional.of(Enum.valueOf(enumClass, value));
        } catch (IllegalFormatException e) {
            return Optional.empty();
        }
    }

    @Override
    public Class<T> getValueClass() {
        return enumClass;
    }
}
