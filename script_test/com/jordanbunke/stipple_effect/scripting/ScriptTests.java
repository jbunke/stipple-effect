package com.jordanbunke.stipple_effect.scripting;

import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.function.HeadFuncNode;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.util.Arrays;

public class ScriptTests {
    private static final Path SCRIPTS_FOLDER = Path.of("scripts");
    private static final String SUFFIX = ".ses";

    @Test
    public void reverse() {
        final HeadFuncNode script = getScript("reverse");

        final String arg = "something";
        Assert.assertEquals("gnihtemos", Script.run(script, arg));
    }

    @Test
    public void imageBounds() {
        final HeadFuncNode script = getScript("image_bounds");

        final int w = 12, h = 27;

        final Object[] results = new Object[] {
                Script.run(script, w, h),
                Script.run(script, 27, 27),
                Script.run(script, 284, 126)
        };

        Arrays.stream(results).forEach(System.out::println);

        Assert.assertEquals(
                "Image is 15 pixels higher than it is wide", results[0]);
    }

    @Test
    public void spiegelschrift() {
        final HeadFuncNode script = getScript("spiegelschrift");

        final Object result = Script.run(script, "E I SAW ELBA", "R");
        System.out.println(result);
    }

    private HeadFuncNode getScript(final String filename) {
        final Path path = SCRIPTS_FOLDER.resolve(filename + SUFFIX);
        final String content = FileIO.readResource(
                ResourceLoader.loadResource(path), "read script");

        final HeadFuncNode script = Script.build(content);
        Assert.assertNotNull(script);

        return script;
    }
}
