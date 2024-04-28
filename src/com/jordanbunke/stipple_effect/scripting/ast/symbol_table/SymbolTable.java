package com.jordanbunke.stipple_effect.scripting.ast.symbol_table;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.ScrippleASTNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.ScriptFunctionNode;

import java.util.HashMap;
import java.util.Map;

public final class SymbolTable {
    private final ScrippleASTNode scope;
    private final SymbolTable parent;
    private final Map<ScrippleASTNode, SymbolTable> children;
    private final Map<String, Object> contents;

    public SymbolTable(
            final ScrippleASTNode scope,
            final SymbolTable parent
    ) {
        this.scope = scope;
        this.parent = parent;

        this.children = new HashMap<>();
        this.contents = new HashMap<>();

        if (parent != null)
            parent.addChild(this.scope, this);
    }

    public static SymbolTable root(
            final ScriptFunctionNode func
    ) {
        return new SymbolTable(func, null);
    }

    private void addChild(
            final ScrippleASTNode subScope, final SymbolTable child
    ) {
        children.put(subScope, child);
    }

    public boolean hasChild(final ScrippleASTNode subScope) {
        return children.containsKey(subScope);
    }

    public SymbolTable getChild(final ScrippleASTNode subScope) {
        return children.getOrDefault(subScope, null);
    }

    public Object get(final String ident) {
        if (contents.containsKey(ident))
            return ident;

        return parent != null ? parent.get(ident) : null;
    }
}
