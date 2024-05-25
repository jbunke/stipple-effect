package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.FuncControlFlow;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.statement.global.GlobalStatementNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.LayerTypeNode;
import com.jordanbunke.stipple_effect.scripting.util.LayerRep;
import com.jordanbunke.stipple_effect.utility.StatusUpdates;

public abstract class LayerOpNode extends GlobalStatementNode {
    public LayerOpNode(
            final TextPosition position,
            final ExpressionNode[] args,
            final TypeNode... expectedTypes
    ) {
        super(position, args, expectedTypes);
        assert expectedTypes.length >= 1 &&
                expectedTypes[0].equals(LayerTypeNode.get());
    }

    @Override
    public final FuncControlFlow execute(final SymbolTable symbolTable) {
        final ExpressionNode arg = getArgs()[0];
        final LayerRep layer = (LayerRep) arg.evaluate(symbolTable);

        if (layer.index() >= layer.project().getState().getLayers().size())
            StatusUpdates.scriptActionNotPermitted(
                    "perform " + callName(), "the layer object \"" +
                            arg + "\" no longer references a valid layer",
                    arg.getPosition());
        else
            operation(layer, symbolTable);

        return FuncControlFlow.cont();
    }

    protected abstract void operation(
            final LayerRep layer, final SymbolTable symbolTable);
}
