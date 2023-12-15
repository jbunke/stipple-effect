package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.menus.Menu;
import com.jordanbunke.delta_time.menus.MenuBuilder;
import com.jordanbunke.delta_time.menus.menu_elements.MenuElement;
import com.jordanbunke.delta_time.menus.menu_elements.button.SimpleMenuButton;
import com.jordanbunke.delta_time.menus.menu_elements.container.MenuElementGrouping;
import com.jordanbunke.delta_time.menus.menu_elements.visual.StaticMenuElement;
import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.menu_elements.TextLabel;

public class DialogAssembly {
    public static void setDialogToSave() {
        // TODO
        final MenuElementGrouping contents = new MenuElementGrouping();
        setDialog(assembleDialog("Save Project...", contents));
    }

    public static void setDialogToResize() {
        // TODO
        final MenuElementGrouping contents = new MenuElementGrouping();
        setDialog(assembleDialog("Resize Canvas...", contents));
    }

    public static void setDialogToPad() {
        // TODO
        final MenuElementGrouping contents = new MenuElementGrouping();
        setDialog(assembleDialog("Pad Canvas...", contents));
    }

    public static void setDialogToNewProject() {
        // TODO
        final MenuElementGrouping contents = new MenuElementGrouping();
        setDialog(assembleDialog("New Project...", contents));
    }

    public static void setDialogToLayerSettings(final int index) {
        // TODO: for renaming and manually setting opacity
        final MenuElementGrouping contents = new MenuElementGrouping();
        setDialog(assembleDialog(StippleEffect.get().getContext().getState()
                .getLayers().get(index).getName() + " | Layer Settings",
                contents));
    }

    private static void setDialog(final Menu dialog) {
        StippleEffect.get().setDialog(dialog);
    }

    private static Menu assembleDialog(
            final String title, final MenuElementGrouping contents
    ) {
        final MenuBuilder mb = new MenuBuilder();

        // background

        final GameImage backgroundImage = new GameImage(
                Constants.DIALOG_W, Constants.DIALOG_H);
        backgroundImage.fillRectangle(Constants.ACCENT_BACKGROUND_DARK,
                0, 0, Constants.DIALOG_W, Constants.DIALOG_H);
        backgroundImage.drawRectangle(Constants.BLACK,
                2f * Constants.BUTTON_BORDER_PX, 0, 0,
                Constants.DIALOG_W, Constants.DIALOG_H);

        final StaticMenuElement background =
                new StaticMenuElement(Constants.getCanvasMiddle(),
                        new Coord2D(Constants.DIALOG_W, Constants.DIALOG_H),
                        MenuElement.Anchor.CENTRAL, backgroundImage.submit());
        mb.add(background);

        // title

        mb.add(TextLabel.make(background.getRenderPosition().displace(
                Constants.TOOL_NAME_X + Constants.BUTTON_BORDER_PX,
                        Constants.TEXT_Y_OFFSET + Constants.BUTTON_BORDER_PX),
                title, Constants.WHITE));

        // cancel button

        final GameImage baseImage = GraphicsUtils.drawTextButton(
                Constants.STD_TEXT_BUTTON_W, "Cancel", false, Constants.GREY),
                highlightedImage = GraphicsUtils.drawHighlightedButton(baseImage);

        final Coord2D cancelPos = background.getRenderPosition()
                .displace(background.getWidth(), background.getHeight())
                .displace(-Constants.TOOL_NAME_X, -Constants.TOOL_NAME_X);

        mb.add(new SimpleMenuButton(cancelPos,
                new Coord2D(baseImage.getWidth(), baseImage.getHeight()),
                MenuElement.Anchor.RIGHT_BOTTOM, true,
                () -> StippleEffect.get().clearDialog(),
                baseImage, highlightedImage));

        mb.add(contents);
        return mb.build();
    }
}
