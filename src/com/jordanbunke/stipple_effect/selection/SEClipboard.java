package com.jordanbunke.stipple_effect.selection;

import com.jordanbunke.delta_time.image.GameImage;
import com.jordanbunke.delta_time.io.ClipboardData;
import com.jordanbunke.delta_time.io.ClipboardUtils;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.state.ProjectState;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

public class SEClipboard {
    private static final SEClipboard INSTANCE;
    private static final DataFlavor NATIVE_DATA_FLAVOR, CELS_DATA_FLAVOR;

    static {
        INSTANCE = new SEClipboard();
        NATIVE_DATA_FLAVOR = new DataFlavor(SelectionContents.class,
                StippleEffect.PROGRAM_NAME + " selection");
        CELS_DATA_FLAVOR = new DataFlavor(CelSelection.class, "Cels");
    }

    public static SEClipboard get() {
        return INSTANCE;
    }

    public void sendSelectionToClipboard(final ProjectState state) {
        final SelectionContents contents = switch (state.getSelectionMode()) {
            case BOUNDS -> SelectionContents.make(
                    state.getActiveLayerFrame(), state.getSelection());
            case CONTENTS -> state.getSelectionContents();
        };

        ClipboardUtils.setContent(
                new ClipboardData(contents, NATIVE_DATA_FLAVOR));
    }

    public void sendCelsToClipboard(final ProjectState state) {
        final CelSelection cels = new CelSelection(state);
        ClipboardUtils.setContent(new ClipboardData(cels, CELS_DATA_FLAVOR));
    }

    public boolean hasContent() {
        return getContent() != null;
    }

    public Object getContent() {
        final Transferable content = ClipboardUtils.getContent();

        if (content == null)
            return null;

        if (content.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            final GameImage img = ClipboardUtils.getImage();

            if (img == null)
                return null;

            final int w = img.getWidth(), h = img.getHeight();
            final Selection all = Selection.allInBounds(w, h);

            // repackage clipboard contents as selection contents
            final SelectionContents res = SelectionContents.make(img, all);
            ClipboardUtils.setContent(
                    new ClipboardData(res, NATIVE_DATA_FLAVOR));

            return res;
        } else if (content.isDataFlavorSupported(NATIVE_DATA_FLAVOR)) {
            try {
                return content.getTransferData(NATIVE_DATA_FLAVOR);
            } catch (Exception e) {
                return null;
            }
        } else if (content.isDataFlavorSupported(CELS_DATA_FLAVOR)) {
            try {
                return content.getTransferData(CELS_DATA_FLAVOR);
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }
}
