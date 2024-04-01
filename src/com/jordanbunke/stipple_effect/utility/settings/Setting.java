package com.jordanbunke.stipple_effect.utility.settings;

import com.jordanbunke.stipple_effect.utility.settings.types.SettingType;

import java.util.Optional;
import java.util.function.Consumer;

public class Setting<T> {
    public final SettingType<T> type;
    public final T defaultValue;
    public final Consumer<T> nonStartupBehaviour;

    private T value, assignment;

    public Setting(
            final SettingType<T> type, final T defaultValue
    ) {
        this(type, defaultValue, v -> {});
    }

    public Setting(
            final SettingType<T> type, final T defaultValue,
            final Consumer<T> nonStartupBehaviour
    ) {
        this.type = type;
        this.defaultValue = defaultValue;
        this.nonStartupBehaviour = nonStartupBehaviour;

        this.value = defaultValue;
        this.assignment = defaultValue;
    }

    public void setFromRead(final String value) {
        final Optional<T> ifValue = type.parseValueFromRead(value);
        set(ifValue.orElse(defaultValue));
    }

    public void initializeMenu() {
        assignment = value;
    }

    public void trySet(final Object value) {
        if (value.getClass().isAssignableFrom(type.getValueClass()))
            assignment = type.getValueClass().cast(value);
    }

    public void apply() {
        final boolean isChanging = !assignment.equals(value);

        set(assignment);

        if (isChanging)
            nonStartupBehaviour.accept(get());
    }

    private void set(final T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public T check() {
        return assignment;
    }
}
