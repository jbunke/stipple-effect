package com.jordanbunke.stipple_effect.tools;

import com.jordanbunke.delta_time.events.GameMouseEvent;
import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.context.SEContext;
import com.jordanbunke.stipple_effect.parse.ParserUtils;
import com.jordanbunke.stipple_effect.utility.GraphicsUtils;

public abstract class Tool {
    private final GameImage icon, highlightedIcon, selectedIcon;

    public static boolean canMoveSelection(
            final Tool tool
    ) {
        return tool.equals(MoveSelection.get()) || tool.equals(PencilSelect.get()) ||
                tool.equals(PickUpSelection.get()) || tool.equals(BoxSelect.get());
    }

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

    public String convertNameToFilename() {
        return getName().replace(" ", "_").toLowerCase();
    }

    private GameImage fetchIcon() {
        return GraphicsUtils.loadIcon(convertNameToFilename());
    }

    public final String[] getBlurb() {
        return ParserUtils.getBlurb(convertNameToFilename());
    }

    public String getCursorCode() {
        return convertNameToFilename();
    }

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
