package com.jordanbunke.stipple_effect.scripting.ast.nodes;

import com.jordanbunke.stipple_effect.scripting.ScrippleErrorListener;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.declaration.DeclarationNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.Variable;

public final class ParametersNode extends ASTNode {
    private final DeclarationNode[] params;

    public ParametersNode(
            final TextPosition position, final DeclarationNode[] params
    ) {
        super(position);

        this.params = params;
    }

    public void populateArgs(
            final SymbolTable symbolTable,
            final Object... args
    ) {
        if (params.length != args.length) {
            ScrippleErrorListener.fireError(
                    ScrippleErrorListener.Message.ARGS_PARAMS_MISMATCH,
                    getPosition(), String.valueOf(params.length),
                    String.valueOf(args.length));
            return;
        }

        for (int i = 0; i < params.length; i++) {
            final DeclarationNode param = params[i];
            final Object arg = args[i];

            symbolTable.put(param.getIdent(),
                    new Variable(param.isMutable(), param.getType(), arg));
        }
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        for (DeclarationNode param : params)
            param.semanticErrorCheck(symbolTable);
    }
}
