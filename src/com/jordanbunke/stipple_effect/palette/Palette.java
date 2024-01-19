package com.jordanbunke.stipple_effect.palette;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.image.ImageProcessing;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.delta_time.utility.MathPlus;
import com.jordanbunke.stipple_effect.utility.ColorMath;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Palette {
    private final String name;
    private final boolean mutable;
    private final List<Color> colorSequence;

    public Palette(final String name, final Color[] loaded) {
        this(name, loaded, true);
    }

    public Palette(
            final String name, final Color[] loaded, final boolean mutable
    ) {
        this.name = name;
        this.mutable = mutable;
        this.colorSequence = new ArrayList<>();

        for (Color c : loaded)
            if (!colorSequence.contains(c))
                colorSequence.add(c);
    }

    public void addColor(final Color c) {
        if (!mutable || colorSequence.contains(c))
            return;

        colorSequence.add(c);
    }

    public void removeColor(final Color c) {
        if (!mutable)
            return;

        colorSequence.remove(c);
    }

    public void sort(final PaletteSorter sorter) {
        colorSequence.sort(sorter.getComparator());
    }

    public GameImage palettize(final GameImage source) {
        return palettize(source, null);
    }

    public GameImage palettize(final GameImage source, final Set<Coord2D> pixels) {
        final Map<Color, Color> palettizationMap = new HashMap<>();
        final GameImage palettized = new GameImage(source.getWidth(),
                source.getHeight());

        for (int x = 0; x < source.getWidth(); x++) {
            for (int y = 0; y < source.getHeight(); y++) {
                final Color c = ImageProcessing.colorAtPixel(source, x, y);

                if (c.getAlpha() == 0)
                    continue;

                final Color cp = palettizationMap.getOrDefault(c,
                        nearest(c, palettizationMap));

                if (pixels != null && !pixels.contains(new Coord2D(x, y)))
                    palettized.setRGB(x, y, c.getRGB());
                else
                    palettized.dot(cp, x, y);
            }
        }

        return palettized.submit();
    }

    private Color nearest(
            final Color source, final Map<Color, Color> palettizationMap
    ) {
        if (colorSequence.isEmpty())
            return source;

        final Color arbitrary = colorSequence.get(0),
                nearest = MathPlus.findBest(source, arbitrary, c -> c,
                        (c1, c2) -> ColorMath.diff(c1, source) <
                                ColorMath.diff(c2, source),
                        colorSequence.toArray(Color[]::new));

        palettizationMap.put(source, nearest);

        return nearest;
    }

    public int size() {
        return colorSequence.size();
    }

    public Color[] getColors() {
        return colorSequence.toArray(Color[]::new);
    }

    public String getName() {
        return name;
    }

    public boolean isMutable() {
        return mutable;
    }
}
