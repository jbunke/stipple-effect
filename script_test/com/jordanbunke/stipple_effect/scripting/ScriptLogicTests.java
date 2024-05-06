package com.jordanbunke.stipple_effect.scripting;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.function.HeadFuncNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.BinaryOperator;

import static com.jordanbunke.stipple_effect.scripting.ScriptTestHelper.getScript;

public final class ScriptLogicTests {
    private record TruthTableEntry(boolean a, boolean b) {}

    private static final TruthTableEntry[] TRUTH_TABLE = new TruthTableEntry[] {
            new TruthTableEntry(true, true),
            new TruthTableEntry(false, false),
            new TruthTableEntry(true, false),
            new TruthTableEntry(false, true)
    };

    @Test
    public void binOpTests() {
        binOpTest("or", (a, b) -> a || b);
        binOpTest("and", (a, b) -> a && b);
        binOpTest("xor", (a, b) -> (a && !b) || (b && !a));
        binOpTest("bool_equality", (a, b) -> a == b);
        binOpTest("bool_inequality", (a, b) -> a != b);
    }

    private void binOpTest(
            final String code, final BinaryOperator<Boolean> binOp
    ) {
        final HeadFuncNode script = getScript(code);

        for (TruthTableEntry ab : TRUTH_TABLE) {
            final Object res = Script.run(script, ab.a, ab.b);
            final boolean expected = binOp.apply(ab.a, ab.b);

            Assert.assertNotNull(res);
            check(expected, (boolean) res);
        }
    }

    @Test
    public void andReduceTrue() {
        reduceTest(true, true, true);
        reduceTest(true, true, true, true, true, true, true);
    }

    @Test
    public void andReduceFalse() {
        reduceTest(true, false, true, true, true, false, true, true);
        reduceTest(true, false);
        reduceTest(true, false, false, false, false, false, false, false, true);
    }

    @Test
    public void orReduceTrue() {
        reduceTest(false, true, false, false, true, false, false);
        reduceTest(false, true, true, false, false, false, false);
        reduceTest(false, true, false, false, false, false, true);
        reduceTest(false, true, true, true, true, true, true);
    }

    @Test
    public void orReduceFalse() {
        reduceTest(false, false, false, false, false, false);
        reduceTest(false, false);
    }

    private void reduceTest(
            final boolean and, final boolean expected, final boolean... elems
    ) {
        final HeadFuncNode script =
                getScript((and ? "and" : "or") + "_reduce");
        final Object res = Script.run(script, fromVarArgs(elems));

        Assert.assertNotNull(res);
        check(expected, (boolean) res);
    }

    private void check(final boolean expected, final boolean actual) {
        Assert.assertEquals(expected, actual);
    }

    private Object fromVarArgs(final boolean... elems) {
        return elems;
    }
}
