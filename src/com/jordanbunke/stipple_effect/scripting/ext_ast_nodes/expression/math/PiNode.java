package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.math;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.ExtExpressionNode;
import com.jordanbunke.stipple_effect.scripting.util.Arguments;
import com.jordanbunke.stipple_effect.utility.Constants;

public class PiNode extends ExtExpressionNode {
    public static final String PI = "PI";

    public PiNode(
            final TextPosition position
    ) {
        super(position, new Arguments(new ExpressionNode[0]));
    }

    @Override
    public Double evaluate(final SymbolTable symbolTable) {
        return Math.PI;
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getFloat();
    }

    @Override
    protected String callName() {
        return PI;
    }

    @Override
    public String toString() {
        return "$" + Constants.MATH_NAMESPACE + "." + callName();
    }
}
