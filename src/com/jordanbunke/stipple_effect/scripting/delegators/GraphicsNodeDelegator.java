package com.jordanbunke.stipple_effect.scripting.delegators;

import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.IllegalExpressionNode;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.graphics.*;
import com.jordanbunke.stipple_effect.utility.Constants;

public final class GraphicsNodeDelegator {
    public static ExpressionNode expression(
            final TextPosition position, final String fID,
            final ExpressionNode[] args
    ) {
        return switch (fID) {
            case UVMappingNode.NAME -> new UVMappingNode(position, args);
            case GenLookupNode.NAME -> new GenLookupNode(position, args);
            case LerpColorNode.NAME -> new LerpColorNode(position, args);
            case LightMaskNode.NAME -> new LightMaskNode(position, args);
            default -> new IllegalExpressionNode(position, "$" +
                    Constants.GRAPHICS_NAMESPACE + " does not define a function \"" +
                    fID + "()\"");
        };
    }
}