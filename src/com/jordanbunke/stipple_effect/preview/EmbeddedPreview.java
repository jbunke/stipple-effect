package com.jordanbunke.stipple_effect.preview;

import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.project.SEContext;

public final class EmbeddedPreview extends Preview {
    private EmbeddedPreview(
            final SEContext c, final Coord2D position,
            final Bounds2D dimensions
    ) {
        super(c, position, dimensions);
    }

    static EmbeddedPreview make(final SEContext c) {
        // TODO
        return null;
    }

    @Override
    protected void close() {
        kill();
    }

    @Override
    protected void executeOnFileDialogOpen() {}
}
