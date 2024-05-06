package com.jordanbunke.stipple_effect.scripting.ast.nodes.types;

import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.ASTNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.util.Set;

public abstract class TypeNode extends ASTNode {
    public TypeNode(final TextPosition position) {
        super(position);
    }

    @Override
    public final void semanticErrorCheck(final SymbolTable symbolTable) {}

    public boolean isNum() {
        return false;
    }

    public abstract boolean hasSize();

    public abstract boolean complies(final Object o);

    public static Set<TypeNode> numTypes() {
        return Set.of(getInt(), getFloat());
    }

    public static SimpleTypeNode getFloat() {
        return new SimpleTypeNode(SimpleTypeNode.Type.FLOAT);
    }

    public static SimpleTypeNode getInt() {
        return new SimpleTypeNode(SimpleTypeNode.Type.INT);
    }

    public static SimpleTypeNode getChar() {
        return new SimpleTypeNode(SimpleTypeNode.Type.CHAR);
    }

    public static SimpleTypeNode getBool() {
        return new SimpleTypeNode(SimpleTypeNode.Type.BOOL);
    }

    public static SimpleTypeNode getString() {
        return new SimpleTypeNode(SimpleTypeNode.Type.STRING);
    }

    public static SimpleTypeNode getColor() {
        return new SimpleTypeNode(SimpleTypeNode.Type.COLOR);
    }

    public static SimpleTypeNode getImage() {
        return new SimpleTypeNode(SimpleTypeNode.Type.IMAGE);
    }
    
    public static SimpleTypeNode wildcard() {
        return new SimpleTypeNode(SimpleTypeNode.Type.WILDCARD);
    }
}
