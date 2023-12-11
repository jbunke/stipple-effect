package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.ResourceLoader;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.context.ImageContext;

import java.nio.file.Path;

public abstract class Tool {
    private static final Path ICON_FOLDER = Path.of("tool_icons");
    private static final GameImage
            HIGHLIGHT_OVERLAY = ResourceLoader.loadImageResource(
                    ICON_FOLDER.resolve("highlighted.png")),
            SELECT_OVERLAY = ResourceLoader.loadImageResource(
                    ICON_FOLDER.resolve("selected.png"));

    private final GameImage icon, highlightedIcon, selectedIcon;

    Tool() {
        this.icon = fetchIcon();

        this.highlightedIcon = new GameImage(icon);
        highlightedIcon.draw(HIGHLIGHT_OVERLAY);

        this.selectedIcon = new GameImage(icon);
        selectedIcon.draw(SELECT_OVERLAY);
    }

    public abstract String getName();
    public abstract void onMouseDown(final ImageContext context, final GameMouseEvent me);
    public abstract void update(final ImageContext context, final Coord2D mousePosition);
    public abstract void onMouseUp(final ImageContext context, final GameMouseEvent me);

    private GameImage fetchIcon() {
        final Path iconFile = ICON_FOLDER.resolve(getName()
                .replace(" ", "_").toLowerCase() + ".png");

        return ResourceLoader.loadImageResource(iconFile);
    }

    // TODO - cursors

    // TODO tools - pen, eraser,
    //  color picker,
    //  stipple select, box select, move selection, move selected pixels, magic wand select

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
