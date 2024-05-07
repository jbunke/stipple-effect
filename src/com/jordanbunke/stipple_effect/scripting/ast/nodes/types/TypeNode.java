package com.jordanbunke.stipple_effect.scripting.ast.nodes.types;

import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.ASTNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

import java.util.Set;

public abstract class TypeNode extends ASTNode {
    private static BaseTypeNode CHAR, INT, BOOL, FLOAT,
            STRING, COLOR, IMAGE, WILDCARD;

    public TypeNode(final TextPosition position) {
        super(position);
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {}

    public boolean isNum() {
        return false;
    }

    public abstract boolean hasSize();

    public abstract boolean complies(final Object o);

    public static Set<TypeNode> numTypes() {
        return Set.of(getInt(), getFloat());
    }

    public static BaseTypeNode getFloat() {
        if (FLOAT == null)
            FLOAT = new BaseTypeNode(BaseTypeNode.Type.FLOAT);

        return FLOAT;
    }

    public static BaseTypeNode getInt() {
        if (INT == null)
            INT = new BaseTypeNode(BaseTypeNode.Type.INT);

        return INT;
    }

    public static BaseTypeNode getChar() {
        if (CHAR == null)
            CHAR = new BaseTypeNode(BaseTypeNode.Type.CHAR);

        return CHAR;
    }

    public static BaseTypeNode getBool() {
        if (BOOL == null)
            BOOL = new BaseTypeNode(BaseTypeNode.Type.BOOL);

        return BOOL;
    }

    public static BaseTypeNode getString() {
        if (STRING == null)
            STRING = new BaseTypeNode(BaseTypeNode.Type.STRING);

        return STRING;
    }

    public static BaseTypeNode getColor() {
        if (COLOR == null)
            COLOR = new BaseTypeNode(BaseTypeNode.Type.COLOR);

        return COLOR;
    }

    public static BaseTypeNode getImage() {
        if (IMAGE == null)
            IMAGE = new BaseTypeNode(BaseTypeNode.Type.IMAGE);

        return IMAGE;
    }
    
    public static BaseTypeNode wildcard() {
        if (WILDCARD == null)
            WILDCARD = new BaseTypeNode(BaseTypeNode.Type.WILDCARD);

        return WILDCARD;
    }
}
