package com.jordanbunke.stipple_effect.color_selection;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Palette {
    private final String name;
    private final List<Color> colorSequence;

    public Palette(final String name, final Color[] loaded) {
        this.name = name;
        this.colorSequence = new ArrayList<>();

        for (Color c : loaded)
            addColor(c);
    }

    public void addColor(final Color c) {
        if (colorSequence.contains(c))
            return;

        colorSequence.add(c);
    }

    public void removeColor(final Color c) {
        colorSequence.remove(c);
    }

    public void sort(final PaletteSorter sorter) {
        colorSequence.sort(sorter.getComparator());
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
}
