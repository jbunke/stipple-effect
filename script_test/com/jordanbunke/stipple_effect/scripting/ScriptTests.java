package com.jordanbunke.stipple_effect.scripting;

import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.ScriptFunctionNode;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;

public class ScriptTests {
    private static final Path SCRIPTS_FOLDER = Path.of("scripts");

    @Test
    public void reverse() {
        final Path path = SCRIPTS_FOLDER.resolve("reverse.ses");
        final String content = FileIO.readResource(
                ResourceLoader.loadResource(path), "read script");

        final ScriptFunctionNode script = Script.build(content);
        Assert.assertNotNull(script);

        final String arg = "something";

        Assert.assertEquals("gnihtemos", Script.run(script, arg));
    }
}
