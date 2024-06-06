package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.ClipboardData;
import com.jordanbunke.delta_time.io.ClipboardUtils;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.state.ProjectState;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.HashSet;
import java.util.Set;

public class SEClipboard {
    private static final SEClipboard INSTANCE;
    private static final DataFlavor NATIVE_DATA_FLAVOR;

    private SelectionContents contents;

    private SEClipboard() {
        contents = null;
    }

    static {
        INSTANCE = new SEClipboard();
        NATIVE_DATA_FLAVOR = new DataFlavor(SelectionContents.class,
                StippleEffect.PROGRAM_NAME + " selection");
    }

    public static SEClipboard get() {
        return INSTANCE;
    }

    public void sendSelectionToClipboard(final ProjectState state) {
        contents = switch (state.getSelectionMode()) {
            case BOUNDS -> new SelectionContents(
                    state.getActiveLayerFrame(), state.getSelection());
            case CONTENTS -> state.getSelectionContents();
        };

        ClipboardUtils.setContent(
                new ClipboardData(contents, NATIVE_DATA_FLAVOR));
    }

    public boolean hasContent() {
        return getContent() != null;
    }

    public SelectionContents getContent() {
        final Transferable content = ClipboardUtils.getContent();

        if (content == null)
            return null;

        if (content.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            final GameImage img = ClipboardUtils.getImage();

            if (img == null)
                return null;

            final int w = img.getWidth(), h = img.getHeight();
            final Set<Coord2D> pixels = new HashSet<>();

            for (int x = 0; x < w; x++)
                for (int y = 0; y < h; y++)
                    pixels.add(new Coord2D(x, y));

            return new SelectionContents(img, pixels);
        } else if (content.isDataFlavorSupported(NATIVE_DATA_FLAVOR)) {
            try {
                return (SelectionContents) content
                        .getTransferData(NATIVE_DATA_FLAVOR);
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }
}
