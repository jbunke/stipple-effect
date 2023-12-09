package com.jordanbunke.stipple_effect;

import java.nio.file.Path;
import java.util.Arrays;

public class Launcher {
    public static void main(final String[] args) {
        if (args.length == 0)
            launchWithoutFile();
        else
            launchWithFile(
                    Path.of(Arrays.stream(args).reduce("", (a, b) -> a + b))
            );
    }

    private static void launchWithoutFile() {

    }

    private static void launchWithFile(final Path fp) {
        // TODO
    }
}
