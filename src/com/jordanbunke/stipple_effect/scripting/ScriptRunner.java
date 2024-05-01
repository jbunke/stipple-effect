package com.jordanbunke.stipple_effect.scripting;

import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptArray;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.ScriptFunctionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.util.ScrippleVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public final class ScriptRunner {
    public static void main(final String[] args) {
        // TODO - temp
        final Path tempScript = Path.of("data", "script.sc");

        final Integer sum = (Integer) run(tempScript,
                ScriptArray.of(131, 642, 68));
        System.out.println(sum);
    }

    public static boolean validatePreviewScript(
            final Path scriptPath, final SEContext context
    ) {
        final ScriptFunctionNode script = getScript(scriptPath);

        if (script == null)
            return false;

        final TypeNode
                IMG_TYPE = new SimpleTypeNode(SimpleTypeNode.Type.IMAGE),
                IMG_ARRAY_TYPE = new CollectionTypeNode(
                        CollectionTypeNode.Type.ARRAY,
                        new SimpleTypeNode(SimpleTypeNode.Type.IMAGE)),
                returnType = script.getReturnType();

        final boolean animation = context.getState().getFrameCount() > 1;
        final TypeNode expectedParam = animation ? IMG_ARRAY_TYPE : IMG_TYPE;

        return script.paramsMatch(new TypeNode[] { expectedParam }) &&
                (returnType.equals(IMG_TYPE) ||
                        returnType.equals(IMG_ARRAY_TYPE));
    }

    public static Object run(
            final Path filepath, final Object... args
    ) {
        ScrippleErrorListener.clearErrors();

        final ScriptFunctionNode script = getScript(filepath);

        if (script == null) {
            // TODO - couldn't read script case
            displayErrors();
            return null;
        }

        final SymbolTable scriptTable = SymbolTable.root(script);

        final boolean passedChecks = runChecks(script, scriptTable);

        if (passedChecks) {
            final Optional<Object> result =
                    runScript(script, scriptTable, args);

            if (result.isEmpty())
                displayErrors();
            else
                return result.get();
        } else
            displayErrors();

        return null;
    }

    public static ScriptFunctionNode getScript(final Path filepath) {
        try {
            final CharStream input = CharStreams.fromPath(filepath);

            final ScrippleLexer lexer = new ScrippleLexer(input);
            lexer.removeErrorListeners();

            final ScrippleParser parser = new ScrippleParser(
                    new CommonTokenStream(lexer));
            parser.removeErrorListeners();

            final ScrippleVisitor visitor = new ScrippleVisitor();

            return visitor.visitHead_rule(parser.head_rule());
        } catch (IOException e) {
            return null;
        }
    }

    private static boolean runChecks(
            final ScriptFunctionNode script,
            final SymbolTable scriptTable
    ) {
        try {
            script.semanticErrorCheck(scriptTable);
        } catch (Exception e) {
            return false;
        }

        return ScrippleErrorListener.hasNoErrors();
    }

    private static Optional<Object> runScript(
            final ScriptFunctionNode script,
            final SymbolTable scriptTable,
            final Object... args
    ) {
        try {
            final Object result = script.execute(scriptTable, args);

            return result != null
                    ? Optional.of(result)
                    : Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static void displayErrors() {
        // TODO
    }
}
