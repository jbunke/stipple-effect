package com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.expression.global;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.scripting.ast.nodes.expression.ExpressionNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.function.HeadFuncNode;
import com.jordanbunke.delta_time.scripting.ast.nodes.types.TypeNode;
import com.jordanbunke.delta_time.scripting.ast.symbol_table.SymbolTable;
import com.jordanbunke.delta_time.scripting.util.ScriptErrorLog;
import com.jordanbunke.delta_time.scripting.util.TextPosition;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.layer.SELayer;
import com.jordanbunke.stipple_effect.project.SEContext;
import com.jordanbunke.stipple_effect.scripting.SEInterpreter;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.ProjectTypeNode;
import com.jordanbunke.stipple_effect.scripting.ext_ast_nodes.type.ScriptTypeNode;
import com.jordanbunke.stipple_effect.scripting.util.ScriptUtils;
import com.jordanbunke.stipple_effect.state.ProjectState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public final class TransformNode extends GlobalExpressionNode {
    public static final String NAME = "transform";

    public TransformNode(
            final TextPosition position, final ExpressionNode[] args
    ) {
        super(position, args, ProjectTypeNode.get(), ScriptTypeNode.get(),
                TypeNode.getBool(), TypeNode.getBool());
    }

    @Override
    public SEContext evaluate(final SymbolTable symbolTable) {
        final Object[] vs = arguments.getValues(symbolTable);

        final SEContext c = (SEContext) vs[0];
        final HeadFuncNode script = (HeadFuncNode) vs[1];
        final boolean openInSE = (boolean) vs[2],
                runPerLayer = (boolean) vs[3];

        if (SEInterpreter.validatePreviewScript(script, c)) {
            final SEContext result = runPerLayer
                    ? runPerLayer(c, script) : runFlattened(c, script);

            if (openInSE && result != null)
                StippleEffect.get().addContext(result, true);

            return result;
        } else if (script != null)
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.args()[0].getPosition(),
                    "The script was not validated for this project");
        else
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT,
                    arguments.args()[1].getPosition(),
                    "Could not compile the script to perform the transformation");

        return null;
    }

    private SEContext runFlattened(
            final SEContext c, final HeadFuncNode script
    ) {
        final int srcFC = c.getState().getFrameCount();
        final GameImage[] input =IntStream.range(0, srcFC)
                .mapToObj(i -> c.getState().draw(false, false, i))
                .toArray(GameImage[]::new);

        final GameImage[] output = ScriptUtils.runPreviewScript(input, script);

        if (output == null) {
            ScriptErrorLog.fireError(
                    ScriptErrorLog.Message.CUSTOM_RT, TextPosition.N_A,
                    "Script failed to produce a result");
            return null;
        }

        final int fc = output.length;
        final int w = Arrays.stream(output).map(GameImage::getWidth)
                .reduce(0, Math::max),
                h = Arrays.stream(output).map(GameImage::getHeight)
                        .reduce(0, Math::max);

        final SELayer layer = SELayer.fromPreviewContent(output);
        final ProjectState state =
                ProjectState.makeFromRasterFile(w, h, layer, fc);

        return new SEContext(null, state, w, h);
    }

    private SEContext runPerLayer(
            final SEContext c, final HeadFuncNode script
    ) {
        int w = 1, h = 1, fc = 1;

        final int srcFC = c.getState().getFrameCount();

        final List<SELayer> layers = new ArrayList<>(),
                srcLayers = new ArrayList<>(c.getState().getLayers());

        for (final SELayer layer : srcLayers) {
            final GameImage[] layerContent = IntStream.range(0, srcFC)
                    .mapToObj(layer::getFrame).toArray(GameImage[]::new);

            final GameImage[] output =
                    ScriptUtils.runPreviewScript(layerContent, script);

            if (output == null) {
                ScriptErrorLog.fireError(
                        ScriptErrorLog.Message.CUSTOM_RT, TextPosition.N_A,
                        "Script failed to produce a result for layer \"" +
                                layer.getName() + "\"");
                return null;
            }

            fc = Math.max(fc, output.length);
            w = Math.max(w, Arrays.stream(output).map(GameImage::getWidth)
                    .reduce(0, Math::max));
            h = Math.max(h, Arrays.stream(output).map(GameImage::getHeight)
                    .reduce(0, Math::max));

            layers.add(SELayer.fromPreviewContent(output, layer));
        }

        final ProjectState state = ProjectState.makeFromNativeFile(
                w, h, layers, fc, ProjectState.defaultFrameDurations(fc));

        return new SEContext(null, state, w, h);
    }

    @Override
    public ProjectTypeNode getType(final SymbolTable symbolTable) {
        return ProjectTypeNode.get();
    }

    @Override
    protected String callName() {
        return NAME;
    }
}
