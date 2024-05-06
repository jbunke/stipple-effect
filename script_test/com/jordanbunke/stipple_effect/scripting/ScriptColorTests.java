package com.jordanbunke.stipple_effect.scripting;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.function.HeadFuncNode;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

import static com.jordanbunke.stipple_effect.scripting.ScriptTestHelper.getScript;

public final class ScriptColorTests {
    private final Color
            BLACK = new Color(0, 0, 0),
            WHITE = new Color(255, 255, 255),
            RED = new Color(255, 0, 0),
            GREEN = new Color(0, 255, 0),
            BLUE = new Color(0, 0, 255);

    // TODO

    @Test
    public void colorSummationTest() {
        final HeadFuncNode script = getScript("color_sum");

        Assert.assertNotNull(script);
        Assert.assertEquals(BLACK, Script.run(script, of(BLACK, BLACK, BLACK)));
        Assert.assertEquals(WHITE, Script.run(script, of(RED, GREEN, BLUE)));
    }

    private Object of(final Color... cs) {
        return cs;
    }
}
