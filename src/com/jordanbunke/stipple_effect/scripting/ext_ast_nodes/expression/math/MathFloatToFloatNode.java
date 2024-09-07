package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.math;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.ExtExpressionNode;
import com.jordanbunke.stipple_effect.scripting.util.Arguments;
import com.jordanbunke.stipple_effect.utility.Constants;

import java.util.function.DoubleFunction;

public final class MathFloatToFloatNode extends ExtExpressionNode {
    public enum Function {
        COS(Math::cos), SIN(Math::sin), TAN(Math::tan),
        ACOS(Math::acos), ASIN(Math::asin), ATAN(Math::atan),
        SQRT(Math::sqrt), ROUND(n -> (double) Math.round(n)),
        FLOOR(Math::floor), CEIL(Math::ceil);

        private final DoubleFunction<Double> f;

        Function(final DoubleFunction<Double> f) {
            this.f = f;
        }

        public double run(final double n) {
            return f.apply(n);
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    private final Function func;

    public MathFloatToFloatNode(
            final TextPosition position, final Function func,
            final ExpressionNode[] args
    ) {
        super(position, new Arguments(args, TypeNode.getFloat()));

        this.func = func;
    }

    @Override
    public Double evaluate(final SymbolTable symbolTable) {
        final double n = (double) arguments.getValues(symbolTable)[0];
        return func.run(n);
    }

    @Override
    public TypeNode getType(final SymbolTable symbolTable) {
        return TypeNode.getFloat();
    }

    @Override
    protected String callName() {
        return func.toString();
    }

    @Override
    public String toString() {
        return "$" + Constants.MATH_NAMESPACE + "." + callName() + arguments;
    }
}
