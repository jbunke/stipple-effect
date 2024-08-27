package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement.save_config;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.project.SaveConfig;
import com.jordanbunke.stipple_effect.scripting.util.ScriptUtils;
import com.jordanbunke.stipple_effect.utility.StatusUpdates;

public final class SetFolderNode extends SaveConfigStatementNode {
    public static final String NAME = "set_folder";

    public SetFolderNode(
            final TextPosition position, final ExpressionNode scope,
            final ExpressionNode[] args
    ) {
        super(position, scope, args, TypeNode.arrayOf(TypeNode.getString()));
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final SaveConfig sc = getSaveConfig(symbolTable);
        final ScriptArray folder =
                (ScriptArray) arguments.getValues(symbolTable)[0];

        if (folder.size() <= 0)
            StatusUpdates.scriptActionNotPermitted(
                    "set the folder of this save configuration",
                    "the path supplied is empty",
                    arguments.args()[0].getPosition());
        else
            sc.setFolder(ScriptUtils.scriptFolderToPath(folder));

        return FuncControlFlow.cont();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
