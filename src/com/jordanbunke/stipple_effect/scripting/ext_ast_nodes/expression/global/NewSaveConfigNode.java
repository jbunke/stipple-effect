package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.global;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.project.SaveConfig;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.SaveConfigTypeNode;
import com.jordanbunke.stipple_effect.scripting.util.ScriptUtils;
import com.jordanbunke.stipple_effect.visual.menu_elements.dialog.Textbox;

import static com.jordanbunke.stipple_effect.project.SaveConfig.SaveType;

public final class NewSaveConfigNode extends GlobalExpressionNode {
    public static final String NAME = "new_save_config";

    public NewSaveConfigNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args, TypeNode.arrayOf(TypeNode.getString()),
                TypeNode.getString(), TypeNode.getInt());
    }

    @Override
    public SaveConfig evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.getValues(symbolTable);

        final ScriptArray folder = (ScriptArray) vs[0];
        final String name = (String) vs[1];
        final int saveTypeIndex = (int) vs[2];

        if (folder.size() <= 0) {
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.args()[0].getPosition(),
                    "the folder path supplied is empty");
            return null;
        } else if (!Textbox.validateAsFileName(name)) {
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.args()[1].getPosition(),
                    "\"" + name + "\" is not a valid filename");
            return null;
        } else if (saveTypeIndex < 0 || saveTypeIndex >= SaveType.values().length) {
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.args()[2].getPosition(),
                    saveTypeIndex + " is not a valid save type index (0 - " +
                            (SaveType.values().length - 1) + ")");
            return null;
        } else
            return new SaveConfig(ScriptUtils.scriptFolderToPath(folder),
                    name, SaveType.values()[saveTypeIndex]);
    }

    @Override
    public SaveConfigTypeNode getType(final SymbolTable symbolTable) {
        return SaveConfigTypeNode.get();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
