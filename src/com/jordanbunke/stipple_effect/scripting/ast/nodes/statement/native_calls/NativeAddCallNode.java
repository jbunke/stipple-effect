package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.native_calls;

import com.jordanbunke.stipple_effect.scripting.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public final class NativeAddCallNode extends StatementNode {
    private final ExpressionNode collection, toAdd, index;

    public NativeAddCallNode(
            final TextPosition position,
            final ExpressionNode collection,
            final ExpressionNode toAdd,
            final ExpressionNode index
    ) {
        super(position);

        this.collection = collection;
        this.toAdd = toAdd;
        this.index = index;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        // TODO
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        // TODO - resolve warning
        final Collection<Object> c =
                (Collection<Object>) collection.evaluate(symbolTable);
        final Object element = toAdd.evaluate(symbolTable);
        final Optional<Integer> i = index != null
                ? Optional.of((Integer) index.evaluate(symbolTable))
                : Optional.empty();

        if (i.isPresent() && c instanceof List<Object> l)
            l.add(i.get(), element);
        else
            c.add(element);

        return FuncControlFlow.cont();
    }
}
