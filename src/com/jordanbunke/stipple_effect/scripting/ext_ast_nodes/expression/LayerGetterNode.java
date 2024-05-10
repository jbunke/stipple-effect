package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.LayerTypeNode;
import com.jordanbunke.stipple_effect.scripting.util.LayerRep;

public abstract class LayerGetterNode extends SEExtExpressionNode {
    public LayerGetterNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args, LayerTypeNode.get());
    }

    @Override
    public Object evaluate(final SymbolTable symbolTable) {
        final ExpressionNode arg = getArgs()[0];
        final LayerRep layer = (LayerRep) arg.evaluate(symbolTable);

        if (layer.index() >= layer.project().getState().getLayers().size()) {
            ScriptErrorLog.fireError(ScriptErrorLog.Message.CUSTOM_RT,
                    arg.getPosition(), "The layer object \"" +
                            arg + "\" no longer references a valid layer");
            return null;
        }

        return getter(layer);
    }

    protected abstract Object getter(final LayerRep layer);
}
