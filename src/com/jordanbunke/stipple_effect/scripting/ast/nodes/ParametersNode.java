package com.jordanbunke.stipple_effect.scripting.ast.nodes;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.declaration.DeclarationNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.Variable;

import java.util.Arrays;

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
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.ARGS_PARAMS_MISMATCH,
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

    public TypeNode[] getTypes() {
        return Arrays.stream(params)
                .map(DeclarationNode::getType)
                .toArray(TypeNode[]::new);
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        for (DeclarationNode param : params)
            param.semanticErrorCheck(symbolTable);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.length; i++) {
            sb.append(params[i]);

            if (i + 1 < params.length)
                sb.append(", ");
        }

        return sb.toString();
    }
}
