package com.jordanbunke.stipple_effect.scripting.ast.symbol_table;

import com.jordanbunke.stipple_effect.scripting.ast.nodes.ASTNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.function.FuncNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.function.HeadFuncNode;

import java.util.HashMap;
import java.util.Map;

public final class SymbolTable {
    private final ASTNode scope;
    private final SymbolTable parent;
    private final Map<ASTNode, SymbolTable> children;
    private final Map<String, Variable> contents;

    public SymbolTable(
            final ASTNode scope,
            final SymbolTable parent
    ) {
        this.scope = scope;
        this.parent = parent;

        this.children = new HashMap<>();
        this.contents = new HashMap<>();

        if (parent != null)
            parent.addChild(this.scope, this);
    }

    public static String funcWithName(final String name) {
        return "$" + name;
    }

    public static SymbolTable root(
            final HeadFuncNode func
    ) {
        return new SymbolTable(func, null);
    }

    public SymbolTable getRoot() {
        return parent != null ? parent.getRoot() : this;
    }

    public FuncNode getFunc() {
        if (scope instanceof FuncNode func)
            return func;

        return parent != null ? parent.getFunc() : null;
    }

    private void addChild(
            final ASTNode subScope, final SymbolTable child
    ) {
        children.put(subScope, child);
    }

    public SymbolTable getChild(final ASTNode subScope) {
        return children.getOrDefault(subScope, null);
    }

    public boolean hasVarAtLevel(final String ident) {
        return contents.containsKey(ident);
    }

    public Variable get(final String ident) {
        if (contents.containsKey(ident))
            return contents.get(ident);

        return parent != null ? parent.get(ident) : null;
    }

    public void put(final String ident, final Variable var) {
        contents.put(ident, var);
    }

    public void update(final String ident, final Object value) {
        if (!contents.containsKey(ident)) {
            if (parent != null)
                parent.update(ident, value);

            return;
        }

        contents.get(ident).set(value);
    }
}
