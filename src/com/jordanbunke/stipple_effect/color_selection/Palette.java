package com.jordanbunke.stipple_effect.color_selection;

import com.jordanbunke.stipple_effect.utility.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Palette {
    private final String name;
    private final Set<Color> colorSet;
    private final List<Color> colorSequence;

    private int index;

    public Palette(final String name, final Color[] loaded) {
        this.name = name;
        this.colorSet = new HashSet<>();
        this.colorSequence = new ArrayList<>();

        for (Color c : loaded)
            addColor(c);


        index = colorSequence.isEmpty() ? Constants.NO_SELECTION : 0;
    }

    public void addColor(final Color c) {
        if (colorSet.contains(c))
            return;

        colorSet.add(c);
        colorSequence.add(c);

        index = colorSequence.size() - 1;
    }

    // TODO - sort

    public int size() {
        return colorSet.size();
    }

    public void select(final Color selection) {
        index = colorSequence.indexOf(selection);
    }

    public boolean hasSelection() {
        return index != Constants.NO_SELECTION;
    }

    public Color getSelected() {
        return hasSelection() ? colorSequence.get(index) : null;
    }

    public boolean isSelected(final Color c) {
        return hasSelection() && getSelected().equals(c);
    }

    public Color[] getColors() {
        return colorSequence.toArray(Color[]::new);
    }

    public String getName() {
        return name;
    }
}
