package com.jordanbunke.stipple_effect.scripting;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.function.HeadFuncNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.BinaryOperator;
import java.util.function.Function;

import static com.jordanbunke.stipple_effect.scripting.ScriptTestHelper.getScript;

public final class ScriptStringTests {
    private static final String[] ARGS = new String[] {
            "", "word", "EXPEDITIOUS"
    };

    private static final StringPair[] BIN_ARGS = new StringPair[] {
            new StringPair("e i saw elba", "r"),
            new StringPair("Jordan Bunke", ""),
            new StringPair("", "")
    };

    private record StringPair(String a, String b) {}

    // EXTEND - more tests

    @Test
    public void spiegelschriftTest() {
        stringBinOpTest("spiegelschrift", this::spiegelschrift);
    }

    @Test
    public void reverseTest() {
        stringOpTest("reverse", this::reverse);
    }

    @Test
    public void lengthTest() {
        stringOpTest("string_length", String::length);
    }

    @Test
    public void firstCharTest() {
        stringOpTest("first_char", s -> s.isEmpty() ? null : s.charAt(0));
    }

    public <T> void stringOpTest(
            final String scriptCode, final Function<String, T> operation
    ) {
        final HeadFuncNode script = getScript(scriptCode);

        for (String arg : ARGS)
            Assert.assertEquals(operation.apply(arg),
                    Script.run(script, arg));
    }

    public void stringBinOpTest(
            final String scriptCode, final BinaryOperator<String> operation
    ) {
        final HeadFuncNode script = getScript(scriptCode);

        for (StringPair arg : BIN_ARGS)
            Assert.assertEquals(operation.apply(arg.a, arg.b),
                    Script.run(script, arg.a, arg.b));
    }


    // Custom operations
    private String reverse(final String input) {
        final StringBuilder reversed = new StringBuilder();

        for (char c : input.toCharArray())
            reversed.insert(0, c);

        return reversed.toString();
    }

    private String spiegelschrift(final String text, final String pivot) {
        return reverse(text) + pivot + text;
    }
}
