package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.context.SEContext;
import com.jordanbunke.stipple_effect.utility.Constants;
import com.jordanbunke.stipple_effect.utility.GraphicsUtils;

import java.nio.file.Path;

public abstract class Tool {
    private final GameImage icon, highlightedIcon, selectedIcon;

    Tool() {
        this.icon = fetchIcon();

        this.highlightedIcon = new GameImage(icon);
        highlightedIcon.draw(GraphicsUtils.HIGHLIGHT_OVERLAY);
        highlightedIcon.free();

        this.selectedIcon = new GameImage(icon);
        selectedIcon.draw(GraphicsUtils.SELECT_OVERLAY);
        selectedIcon.free();
    }

    public abstract String getName();
    public abstract void onMouseDown(final SEContext context, final GameMouseEvent me);
    public abstract void update(final SEContext context, final Coord2D mousePosition);
    public abstract void onMouseUp(final SEContext context, final GameMouseEvent me);

    private GameImage fetchIcon() {
        final Path iconFile = Constants.ICON_FOLDER.resolve(getName()
                .replace(" ", "_").toLowerCase() + ".png");

        return ResourceLoader.loadImageResource(iconFile);
    }

    public String getCursorCode() {
        return getName().replace(" ", "_").toLowerCase();
    }

    // TODO tools - stipple select, box select, move selection, move selected pixels, magic wand select

    // DONE tools
    // zoom, hand, stippler, pencil

    public String getBottomBarText() {
        return getName();
    }

    public GameImage getIcon() {
        return icon;
    }

    public GameImage getHighlightedIcon() {
        return highlightedIcon;
    }

    public GameImage getSelectedIcon() {
        return selectedIcon;
    }
}
