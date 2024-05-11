package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression;

import com.jordanbunke.delta_time.scripting.ast.collection.ScriptArray;
import com.jordanbunke.delta_time.scripting.ast.collection.ScriptSet;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.project.SEContext;

public final class SelectionGetter extends ProjectGetterNode {
    public static final String GET = "get_selection", HAS = "has_selection";

    private final boolean get;

    private SelectionGetter(
            final TextPosition position, final ExpressionNode[] args,
            final boolean get
    ) {
        super(position, args);

        this.get = get;
    }

    public static SelectionGetter newGet(
            final TextPosition position, final ExpressionNode[] args
    ) {
        return new SelectionGetter(position, args, true);
    }

    public static SelectionGetter newHas(
            final TextPosition position, final ExpressionNode[] args
    ) {
        return new SelectionGetter(position, args, false);
    }

    @Override
    protected Object getter(final SEContext project) {
        return get
                ? new ScriptSet(project.getState().getSelection().stream()
                        .map(c -> ScriptArray.of(c.x, c.y)))
                : project.getState().hasSelection();
    }

    @Override
    public TypeNode getType(SymbolTable symbolTable) {
        return get ? TypeNode.setOf(TypeNode.arrayOf(TypeNode.getInt()))
                : TypeNode.getBool();
    }

    @Override
    protected String callName() {
        return get ? GET : HAS;
    }
}
