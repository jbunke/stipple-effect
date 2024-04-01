package com.jordanbunke.stipple_effect.utility.settings.types;

import java.util.Optional;

public interface SettingType<T> {
    Optional<T> parseValueFromRead(final String value);
    Class<T> getValueClass();
}
