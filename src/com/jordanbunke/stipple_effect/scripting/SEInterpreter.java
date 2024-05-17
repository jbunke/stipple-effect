package com.jordanbunke.stipple_effect.scripting;

import com.jordanbunke.delta_time.scripting.Interpreter;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.HeadFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.utility.StatusUpdates;
import com.jordanbunke.stipple_effect.visual.DialogAssembly;

public final class SEInterpreter extends Interpreter {
    private static boolean printErrorsToDialog = false;

    static {
        SEInterpreter.overrideVisitor(new SEScriptVisitor());
    }

    public static SEInterpreter get() {
        return new SEInterpreter();
    }

    public static void printErrorsToDialog() {
        printErrorsToDialog = true;
    }

    public void runAutomationScript(final String content) {
        final HeadFuncNode script = build(content);

        if (validateAutomationScript(script))
            run(script);
        else
            StatusUpdates.invalidAutomationScript();
    }

    private boolean validateAutomationScript(
            final HeadFuncNode script) {
        if (script == null)
            return false;

        return script.paramsMatch(new TypeNode[] {}) &&
                script.getReturnType() == null;
    }

    public static boolean validatePreviewScript(
            final HeadFuncNode script, final SEContext context
    ) {
        if (script == null)
            return false;

        final TypeNode IMG_TYPE = TypeNode.getImage(),
                IMG_ARRAY_TYPE = TypeNode.arrayOf(IMG_TYPE),
                returnType = script.getReturnType();

        final boolean animation = context.getState().getFrameCount() > 1;
        final TypeNode[] expectedParam = new TypeNode[]
                { animation ? IMG_ARRAY_TYPE : IMG_TYPE },
                arrayParam = new TypeNode[] { IMG_ARRAY_TYPE };

        return (script.paramsMatch(expectedParam) ||
                script.paramsMatch(arrayParam)) &&
                (returnType.equals(IMG_TYPE) ||
                        returnType.equals(IMG_ARRAY_TYPE));
    }

    @Override
    protected void displayErrors() {
        if (printErrorsToDialog) {
            DialogAssembly.setDialogToScriptErrors();
            return;
        }

        final String[] errors = ScriptErrorLog.getErrors();

        for (String error : errors)
            System.out.println(error);
    }
}
