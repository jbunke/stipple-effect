package com.jordanbunke.stipple_effect.scripting;

import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.HeadFuncNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.ScriptVisitor;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.visual.DialogAssembly;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;
import java.util.Optional;

public final class Script {
    private static boolean printErrorsToDialog = false;

    public static void printErrorsToDialog() {
        printErrorsToDialog = true;
    }

    public static boolean validatePreviewScript(
            final HeadFuncNode script, final SEContext context
    ) {
        if (script == null)
            return false;

        final TypeNode
                IMG_TYPE = TypeNode.getImage(),
                IMG_ARRAY_TYPE = new CollectionTypeNode(
                        CollectionTypeNode.Type.ARRAY,
                        TypeNode.getImage()),
                returnType = script.getReturnType();

        final boolean animation = context.getState().getFrameCount() > 1;
        final TypeNode expectedParam = animation ? IMG_ARRAY_TYPE : IMG_TYPE;

        return script.paramsMatch(new TypeNode[] { expectedParam }) &&
                (returnType.equals(IMG_TYPE) ||
                        returnType.equals(IMG_ARRAY_TYPE));
    }

    public static Object run(
            final HeadFuncNode script, final Object... args
    ) {
        ScriptErrorLog.clearErrors();

        final SymbolTable scriptTable = SymbolTable.root(script);

        final boolean passedChecks = check(script, scriptTable);

        if (passedChecks) {
            final Optional<Object> result =
                    execute(script, scriptTable, args);

            if (result.isPresent())
                return result.get();
            else if (!ScriptErrorLog.hasNoErrors())
                displayErrors();
        } else
            displayErrors();

        return null;
    }

    public static HeadFuncNode build(final String content) {
        try {
            final CharStream input = CharStreams.fromString(content);

            final ScriptLexer lexer = new ScriptLexer(input);
            lexer.removeErrorListeners();

            final ScriptParser parser = new ScriptParser(
                    new CommonTokenStream(lexer));
            parser.removeErrorListeners();

            final ScriptVisitor visitor = new ScriptVisitor();

            return visitor.visitHead_rule(parser.head_rule());
        } catch (Exception e) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.COULD_NOT_READ,
                    TextPosition.N_A);
            displayErrors();
            return null;
        }
    }

    private static boolean check(
            final HeadFuncNode script,
            final SymbolTable scriptTable
    ) {
        try {
            script.semanticErrorCheck(scriptTable);
        } catch (Exception e) {
            return false;
        }

        return ScriptErrorLog.hasNoErrors();
    }

    private static Optional<Object> execute(
            final HeadFuncNode script,
            final SymbolTable scriptTable,
            final Object... args
    ) {
        try {
            final Object result = script.execute(scriptTable, args);

            return result != null
                    ? Optional.of(result)
                    : Optional.empty();
        } catch (Exception e) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.UNEXPECTED_RUNTIME_ERROR,
                    TextPosition.N_A);
            return Optional.empty();
        }
    }

    private static void displayErrors() {
        if (printErrorsToDialog) {
            DialogAssembly.setDialogToScriptErrors();
            return;
        }

        final List<String> errors = ScriptErrorLog.getErrors();

        for (String error : errors)
            System.out.println(error);
    }
}
