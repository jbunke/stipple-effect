package com.jordanbunke.stipple_effect.scripting;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.function.HeadFuncNode;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import org.junit.Assert;

import java.nio.file.Path;

public final class ScriptTestHelper {
    private static final Path
            SCRIPTS_FOLDER = Path.of("scripts"),
            IMG_FOLDER = Path.of("img");
    private static final String SUFFIX = ".ses";

    public static GameImage getImage(final String code) {
        final Path path = IMG_FOLDER.resolve(code + ".png");
        return ResourceLoader.loadImageResource(path);
    }

    public static HeadFuncNode getScript(final String filename) {
        final Path path = SCRIPTS_FOLDER.resolve(filename + SUFFIX);
        final String content = FileIO.readResource(
                ResourceLoader.loadResource(path), "read script");

        final HeadFuncNode script = Script.build(content);
        Assert.assertNotNull(script);
        Assert.assertTrue(ScriptErrorLog.hasNoErrors());
        printErrors();

        return script;
    }

    private static void printErrors() {
        final String[] errors = ScriptErrorLog.getErrors();

        for (String error : errors)
            System.out.println(error);
    }
}
