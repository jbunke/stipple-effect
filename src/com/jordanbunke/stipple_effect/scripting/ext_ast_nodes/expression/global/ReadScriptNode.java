package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.global;

import com.jordanbunke.delta_time.io.FileIO;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.HeadFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.SEInterpreter;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.ScriptTypeNode;

import java.io.File;
import java.nio.file.Path;

public class ReadScriptNode extends GlobalExpressionNode {
    public static final String NAME = "read_script";

    public ReadScriptNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args, TypeNode.getString());
    }

    @Override
    public HeadFuncNode evaluate(final SymbolTable symbolTable) {
        final ExpressionNode arg = arguments.args()[0];
        final String scriptFP = (String) arg.evaluate(symbolTable);
        final Path scriptPath = Path.of(
                scriptFP.replace("/", File.separator)
                        .replace("\\\\", "\\")
                        .replace("\\", File.separator));
        final String content = FileIO.readFile(scriptPath);

        if (content == null)
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    arg.getPosition(), "No file to read at \"" +
                            scriptFP + "\"");

        return SEInterpreter.get().build(FileIO.readFile(scriptPath));
    }

    @Override
    public ScriptTypeNode getType(final SymbolTable symbolTable) {
        return ScriptTypeNode.get();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
