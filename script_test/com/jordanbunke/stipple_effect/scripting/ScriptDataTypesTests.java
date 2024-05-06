package com.jordanbunke.stipple_effect.scripting;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.function.HeadFuncNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.jordanbunke.stipple_effect.scripting.ScriptTestHelper.getScript;

public final class ScriptDataTypesTests {
    private final Integer[]
            INT_DATA_SET_1 = new Integer[] { 77, 82 },
            INT_DATA_SET_2 = new Integer[0],
            INT_DATA_SET_3 = new Integer[] { -77, 45, 6, 42, -12, 69 };

    // TODO - test maps and collections

    @Test
    public void sizeTests() {
        collectionTests(this::arraySizeTest, Integer[]::new,
                this::listSizeTest, this::setSizeTest,
                INT_DATA_SET_1, INT_DATA_SET_2, INT_DATA_SET_3);
    }

    @Test
    public void sumTests() {
        collectionTests(this::arraySumTest, Integer[]::new,
                this::listSumTest, this::setSumTest,
                INT_DATA_SET_1, INT_DATA_SET_2, INT_DATA_SET_3);
    }

    @SafeVarargs
    private <T> void collectionTests(
            final BiConsumer<Stream<T>, IntFunction<T[]>> arrayTestMethod,
            final IntFunction<T[]> arrayGenerator,
            final Consumer<Stream<T>> listTestMethod,
            final Consumer<Stream<T>> setTestMethod,
            final T[]... dataSets
    ) {
        for (T[] dataSet : dataSets) {
            arrayTestMethod.accept(Arrays.stream(dataSet), arrayGenerator);
            listTestMethod.accept(Arrays.stream(dataSet));
            setTestMethod.accept(Arrays.stream(dataSet));
        }
    }

    private <T> void arraySumTest(
            final Stream<T> elements, final IntFunction<T[]> generator
    ) {
        sumTest(elements, s -> s.toArray(generator), a -> Arrays.stream(a)
                .map(e -> (Integer) e).reduce(0, Integer::sum));
    }

    private <T> void listSumTest(final Stream<T> elements) {
        sumTest(elements, Stream::toList, l -> l.stream()
                .map(e -> (Integer) e).reduce(0, Integer::sum));
    }

    private <T> void setSumTest(final Stream<T> elements) {
        sumTest(elements, s -> s.collect(Collectors.toSet()), s -> s.stream()
                .map(e -> (Integer) e).reduce(0, Integer::sum));
    }

    private <T> void arraySizeTest(
            final Stream<T> elements, final IntFunction<T[]> generator
    ) {
        sizeTest(elements, s -> s.toArray(generator), a -> a.length);
    }

    private <T> void listSizeTest(final Stream<T> elements) {
        sizeTest(elements, Stream::toList, List::size);
    }

    private <T> void setSizeTest(final Stream<T> elements) {
        sizeTest(elements, s -> s.collect(Collectors.toSet()), Set::size);
    }

    private <T, DT> void sizeTest(
            final Stream<T> elements,
            final Function<Stream<T>, DT> collector,
            final Function<DT, Integer> sizeFunction
    ) {
        final DT args = collector.apply(elements);
        test(getDataCode(args), "size", args, sizeFunction.apply(args));
    }

    private <T, DT> void sumTest(
            final Stream<T> elements,
            final Function<Stream<T>, DT> collector,
            final Function<DT, Integer> sumFunction
    ) {
        final DT args = collector.apply(elements);
        test(getDataCode(args), "sum", args, sumFunction.apply(args));
    }

    private <DT, ET> void test(
            final String dataCode, final String testCode,
            final DT args, final ET expected
    ) {
        final String scriptCode = dataCode + "_" + testCode;
        final HeadFuncNode script = getScript(scriptCode);

        Assert.assertNotNull(script);
        Assert.assertEquals(expected, Script.run(script, args));
    }

    private <DT> String getDataCode(final DT args) {
        if (args instanceof List<?>)
            return "list";
        else if (args instanceof Set<?>)
            return "set";
        else if (args.getClass().isArray())
            return "array";

        Assert.fail();
        return "invalid";
    }
}
