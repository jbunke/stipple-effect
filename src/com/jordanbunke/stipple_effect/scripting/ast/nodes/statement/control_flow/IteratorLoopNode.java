package com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.control_flow;

import com.jordanbunke.stipple_effect.scripting.ast.collection.ScriptCollection;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.CollectionTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.SimpleTypeNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.stipple_effect.scripting.util.FuncControlFlow;
import com.jordanbunke.stipple_effect.scripting.util.ScriptErrorLog;
import com.jordanbunke.stipple_effect.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.declaration.DeclarationNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.stipple_effect.scripting.ast.nodes.statement.StatementNode;
import com.jordanbunke.stipple_effect.scripting.ast.symbol_table.SymbolTable;

public final class IteratorLoopNode extends StatementNode {
    private final DeclarationNode declaration;
    private final ExpressionNode collection;
    private final StatementNode loopBody;

    public IteratorLoopNode(
            final TextPosition position,
            final DeclarationNode declaration,
            final ExpressionNode collection,
            final StatementNode loopBody
    ) {
        super(position);

        this.declaration = declaration;
        this.collection = collection;
        this.loopBody = loopBody;
    }

    @Override
    public void semanticErrorCheck(final SymbolTable symbolTable) {
        final SymbolTable innerTable = new SymbolTable(this, symbolTable);

        declaration.semanticErrorCheck(innerTable);
        collection.semanticErrorCheck(innerTable);
        loopBody.semanticErrorCheck(innerTable);

        final SimpleTypeNode
                stringType = new SimpleTypeNode(SimpleTypeNode.Type.STRING);
        final TypeNode colType = collection.getType(symbolTable),
                varType = declaration.getType();

        if (colType instanceof CollectionTypeNode c) {
            final TypeNode elemType = c.getElementType();

            if (!varType.equals(elemType))
                ScriptErrorLog.fireError(
                        ScriptErrorLog.Message.ELEMENT_DOES_NOT_MATCH_COL,
                        declaration.getPosition(),
                        elemType.toString(), varType.toString());
        } else if (stringType.equals(colType)) {
            final SimpleTypeNode charType =
                    new SimpleTypeNode(SimpleTypeNode.Type.CHAR);

            if (!varType.equals(charType))
                ScriptErrorLog.fireError(
                        ScriptErrorLog.Message.ELEMENT_DOES_NOT_MATCH_COL,
                        declaration.getPosition(),
                        charType.toString(), varType.toString());
        } else
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.NOT_ITERABLE,
                    collection.getPosition(),
                    "string\", \"list - <>\" or \"array - []\"",
                    colType.toString());
    }

    @Override
    public FuncControlFlow execute(final SymbolTable symbolTable) {
        final SymbolTable innerTable = symbolTable.getChild(this);

        final Object col = collection.evaluate(innerTable);
        final int size = col instanceof ScriptCollection sc
                ? sc.size() : String.valueOf(col).length();

        FuncControlFlow status = FuncControlFlow.cont();

        for (int i = 0; i < size; i++) {
            final Object element = col instanceof ScriptCollection sc
                    ? sc.get(i) : String.valueOf(col).charAt(i);
            innerTable.update(declaration.getIdent(), element);

            status = loopBody.execute(innerTable);

            if (!status.cont)
                return status;
        }

        return status;
    }

    @Override
    public String toString() {
        return "for (" + declaration + " in " + collection + ")\n" + loopBody;
    }
}
