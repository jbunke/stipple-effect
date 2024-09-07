package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.error.GameError;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class EnumUtils {
    public static <T extends Enum<T>> T next(final T previous) {
        final Class<T> enumClass = previous.getDeclaringClass();

        final T[] values = enumClass.getEnumConstants();

        if (values == null || values.length == 0) {
            GameError.send("The generic attempt to fetch the next enum element failed");
            return previous;
        }

        final List<T> vList = Arrays.stream(values).toList();

        final int index = vList.indexOf(previous),
                nextIndex = (index + 1) % values.length;

        return values[nextIndex];
    }

    public static <T extends Enum<T>> String formattedName(final T enumConst) {
        final String name = enumConst.name();

        return Arrays.stream(name.split("_"))
                .map(EnumUtils::capitalizeFirstLetter)
                .reduce((a, b) -> a + " " + b).orElse(name);
    }

    private static String capitalizeFirstLetter(final String word) {
        return word.charAt(0) + word.substring(1).toLowerCase();
    }

    public static <T extends Enum<T>> Stream<T> stream(final Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants());
    }

    public static <T extends Enum<T>> boolean matches(
            final String name, final Class<T> enumClass,
            final Function<T, String> f
    ) {
        return stream(enumClass)
                .map(e -> f.apply(e).equals(name))
                .reduce(false, Boolean::logicalOr);
    }

    public static <T extends Enum<T>> boolean matches(
            final String name, final Class<T> enumClass
    ) {
        return matches(name, enumClass, T::name);
    }
}
