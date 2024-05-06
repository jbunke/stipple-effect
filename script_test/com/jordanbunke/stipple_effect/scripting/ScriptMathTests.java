package com.jordanbunke.stipple_effect.scripting;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.function.HeadFuncNode;
import org.junit.Assert;
import org.junit.Test;

import static com.jordanbunke.stipple_effect.scripting.ScriptTestHelper.getScript;

public final class ScriptMathTests {
    // TODO - test operations, assignments

    @Test
    public void truncate() {
        final HeadFuncNode script = getScript("truncate");

        final double[] args = new double[]
                { 7.5, -18.1, 4f, 5.6, 7.8, 9.2, -1267.8, 13663.45262 };

        for (Double arg : args)
            Assert.assertEquals(arg.intValue(), Script.run(script, arg));
    }
}
