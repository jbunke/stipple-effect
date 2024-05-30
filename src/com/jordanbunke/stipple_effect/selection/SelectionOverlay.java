package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.settings.Settings;
import com.jordanbunke.stipple_effect.visual.GraphicsUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.jordanbunke.stipple_effect.visual.GraphicsUtils.TRANSFORM_NODE;

public final class SelectionOverlay {
    private final Map<Float, GameImage>
            frontierMap, filledMap, transformMap;

    private float lastZ;
    private boolean wasFilled, couldTransform;
    private GameImage last;

    private long tempTime;

    public SelectionOverlay(final Set<Coord2D> selection) {
        tempTime = System.currentTimeMillis();

        frontierMap = new HashMap<>();
        filledMap = new HashMap<>();
        transformMap = new HashMap<>();

        final Coord2D tl = SelectionUtils.topLeft(selection),
                br = SelectionUtils.bottomRight(selection);

        final Set<Coord2D> adjusted = selection.parallelStream()
                .map(p -> p.displace(-tl.x, -tl.y))
                .collect(Collectors.toSet());
        final int w = br.x - tl.x, h = br.y - tl.y;

        System.out.println("Adjusted selection: " + updateTime());

        for (float z = Constants.ZOOM_FOR_OVERLAY;
             z <= Constants.MAX_ZOOM;
             z *= Constants.ZOOM_CHANGE_LEVEL)
            produce(w, h, z, adjusted);
    }

    private void produce(
            final int w, final int h, final float z,
            final Set<Coord2D> selection
    ) {
        final int zoomInc = (int)Math.max(Constants.ZOOM_FOR_OVERLAY, z),
                scaleUpW = Math.max(1, w * zoomInc),
                scaleUpH = Math.max(1, h * zoomInc);

        final GameImage frontier = new GameImage(
                scaleUpW + (2 * Constants.OVERLAY_BORDER_PX),
                scaleUpH + (2 * Constants.OVERLAY_BORDER_PX)),
                filled = new GameImage(frontier),
                transform = new GameImage(frontier);

        final Color inside = Settings.getTheme().buttonOutline.get(),
                outside = Settings.getTheme().highlightOutline.get(),
                fill = Settings.getTheme().selectionFill.get();

        final float threshold = Constants.MAX_ZOOM;

        if (z <= threshold)
            GraphicsUtils.populateOverlay(selection, zoomInc,
                    frontier, filled, inside, outside, fill);
        else {
            final int scaleUp = (int) (z / threshold);

            final GameImage refFrontier = frontierMap.get(threshold),
                    refFilled = filledMap.get(threshold);

            final Coord2D displacement = new Coord2D(
                    -(scaleUp - 1) * Constants.OVERLAY_BORDER_PX,
                    -(scaleUp - 1) * Constants.OVERLAY_BORDER_PX);

            frontier.draw(refFrontier, displacement.x, displacement.y,
                    refFrontier.getWidth() * scaleUp,
                    refFrontier.getHeight() * scaleUp);
            filled.draw(refFilled, displacement.x, displacement.y,
                    refFilled.getWidth() * scaleUp,
                    refFilled.getHeight() * scaleUp);
        }

        System.out.println("Drew frontier and fill for " + zoomInc + "x: " + updateTime());

        final Coord2D tl = SelectionUtils.topLeft(selection),
                br = SelectionUtils.bottomRight(selection);

        final int BEG = 0, MID = 1, END = 2;
        final int[] xs = new int[] {
                tl.x * zoomInc,
                (int)(((tl.x + br.x) / 2d) * zoomInc),
                br.x * zoomInc
        }, ys = new int[] {
                tl.y * zoomInc,
                (int)(((tl.y + br.y) / 2d) * zoomInc),
                br.y * zoomInc
        };

        for (int x = BEG; x <= END; x++)
            for (int y = BEG; y <= END; y++)
                if (x != MID || y != MID)
                    transform.draw(TRANSFORM_NODE, xs[x], ys[y]);

        System.out.println("Drew transformation nodes for " + zoomInc + "x: " + updateTime());

        frontierMap.put(z, frontier);
        filledMap.put(z, filled);
        transformMap.put(z, transform);

        System.out.println("Completed " + zoomInc + "x: " + updateTime());
    }

    private String updateTime() {
        final long now = System.currentTimeMillis(),
                elapsed = now - tempTime;
        tempTime = now;

        return elapsed + " ms";
    }

    public GameImage draw(
            final float z, final boolean isFilled, final boolean canTransform
    ) {
        if (z < Constants.ZOOM_FOR_OVERLAY)
            return GameImage.dummy();

        if (last != null && lastZ == z && wasFilled == isFilled &&
                couldTransform == canTransform)
            return last;

        final GameImage
                frontier = frontierMap.get(z),
                filled = filledMap.get(z),
                transform = transformMap.get(z);

        GameImage overlay = new GameImage(frontier.getWidth(), frontier.getHeight());

        if (isFilled)
            overlay.draw(filled, 0, 0);

        overlay.draw(frontier);

        if (canTransform)
            overlay.draw(transform, 0, 0);

        lastZ = z;
        wasFilled = isFilled;
        couldTransform = canTransform;
        last = overlay.submit();

        return overlay;
    }
}
