package com.jordanbunke.stipple_effect.scripting;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.function.HeadFuncNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.jordanbunke.stipple_effect.scripting.ScriptTestHelper.getScript;

public final class RunScriptSTDIO {
    public static void main(final String[] o) {
        final Scanner scanner = new Scanner(System.in);

        System.out.print("Script code: ");
        final String code = scanner.nextLine();

        final HeadFuncNode script = getScript(code);

        if (script == null) {
            System.out.println("Failed to import a script for the code \"" +
                    code + "\"");
            return;
        }

        System.out.println("Args: (leave blank to finish)");
        final List<String> args = new ArrayList<>();
        String arg;

        do {
            arg = scanner.nextLine().trim();

            if (!arg.isEmpty())
                args.add(arg);
        } while (!arg.isEmpty());

        final Object res = Script.run(script,
                (Object[]) args.toArray(String[]::new));
        System.out.println(res);
    }
}
