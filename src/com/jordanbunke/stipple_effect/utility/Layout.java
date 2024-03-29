package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.utility.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;

import java.awt.*;

public class Layout {
    // panel variables
    private static boolean framesPanelShowing, layersPanelShowing,
            colorsPanelShowing, toolbarShowing, projectsExpanded;

    // layout constants
    private static final int TOOLS_W = 25, RIGHT_PANEL_W = 286,
            CONTEXTS_H = 84, COLLAPSED_CONTEXTS_H = 27;
    public static final int
            BOTTOM_BAR_H = 24, SCREEN_H_BUFFER = 120,
            MIN_WINDOW_H = 666, TEXT_Y_OFFSET = -4, TOOL_TIP_OFFSET = 8,
            CONTENT_BUFFER_PX = 8,
            DEFAULT_CHECKERBOARD_DIM = 4, CHECKERBOARD_MIN = 1, CHECKERBOARD_MAX = 256,
            DEFAULT_PIXEL_GRID_DIM = 1, PIXEL_GRID_MIN = 1, PIXEL_GRID_MAX = 64,
            PIXEL_GRID_IMAGE_DIM_MAX = 128, PIXEL_GRID_COLOR_ALT_DIVS = 4,
            CURSOR_DIM = 40, BUTTON_DIM = 20, BUTTON_OFFSET = 2, ICON_BUTTON_OFFSET_Y = 3,
            BUTTON_INC = BUTTON_DIM + BUTTON_OFFSET, BUTTON_BORDER_PX = 2,
            SEGMENT_TITLE_BUTTON_OFFSET_X = 84, SEGMENT_TITLE_CONTENT_OFFSET_Y = 30,
            LAYER_BUTTON_W = 158, LAYERS_ABOVE_TO_DISPLAY = 2, LAYER_NAME_LENGTH_CUTOFF = 12,
            FRAME_BUTTON_W = 40, FRAMES_BEFORE_TO_DISPLAY = 5,
            PX_PER_SCROLL = FRAME_BUTTON_W + BUTTON_OFFSET,
            PROJECT_NAME_BUTTON_PADDING_W = 20, SPACE_BETWEEN_PROJECT_BUTTONS_X = 8,
            PROJECTS_BEFORE_TO_DISPLAY = 1, DIALOG_CONTENT_INC_Y = 32,
            VERT_SCROLL_WINDOW_W = RIGHT_PANEL_W - (2 * CONTENT_BUFFER_PX),
            TOP_PANEL_SCROLL_WINDOW_H = (int)(CONTEXTS_H * 0.56),
            DIALOG_CONTENT_COMP_OFFSET_Y = 7, DIALOG_DYNAMIC_W_ALLOWANCE = 80,
            DIALOG_CONTENT_OFFSET_X = 150, DIALOG_CONTENT_BIG_OFFSET_X = DIALOG_CONTENT_OFFSET_X + 100,
            DIALOG_CONTENT_SMALL_W_ALLOWANCE = 180,
            SMALL_TEXT_BOX_W = 80, STD_TEXT_BUTTON_W = 88, STD_TEXT_BUTTON_H = 25,
            STD_TEXT_BUTTON_INC = STD_TEXT_BUTTON_H + BUTTON_OFFSET, BUTTON_TEXT_OFFSET_Y = -4,
            COLOR_SELECTOR_OFFSET_Y = 120, COLOR_TEXTBOX_AVG_C_THRESHOLD = 100, COLOR_TEXTBOX_W = 116,
            SLIDER_OFF_DIM = 20, SLIDER_BALL_DIM = 20, SLIDER_THINNING = 4,
            FULL_COLOR_SLIDER_W = RIGHT_PANEL_W - (SLIDER_BALL_DIM + 10),
            HALF_COLOR_SLIDER_W = (RIGHT_PANEL_W / 2) - (SLIDER_BALL_DIM + 10),
            COLOR_LABEL_OFFSET_Y = -18, DYNAMIC_LABEL_H = 40, DYNAMIC_LABEL_W_ALLOWANCE = 100;

    public static final Coord2D ICON_DIMS = new Coord2D(BUTTON_DIM, BUTTON_DIM),
            PALETTE_DIMS = new Coord2D(24, 24);

    private static Coord2D size;

    static {
        size = new Coord2D(Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height);

        projectsExpanded = true;
        framesPanelShowing = true;
        layersPanelShowing = true;
        colorsPanelShowing = true;
        toolbarShowing = true;
    }

    // panel display
    public static boolean isProjectsExpanded() {
        return projectsExpanded;
    }

    public static boolean isFramesPanelShowing() {
        return framesPanelShowing;
    }

    public static boolean isLayersPanelShowing() {
        return layersPanelShowing;
    }

    public static boolean isColorsPanelShowing() {
        return colorsPanelShowing;
    }

    public static boolean isToolbarShowing() {
        return toolbarShowing;
    }

    public static boolean areAllPanelsShowing() {
        return isProjectsExpanded() && isFramesPanelShowing() &&
                isToolbarShowing() && isLayersPanelShowing() &&
                isColorsPanelShowing();
    }

    public static void setProjectsExpanded(final boolean projectsExpanded) {
        if (!(projectsExpanded || isFramesPanelShowing()) || projectsExpanded)
            Layout.projectsExpanded = projectsExpanded;
    }

    public static void setFramesPanelShowing(final boolean framesPanelShowing) {
        if (framesPanelShowing)
            setProjectsExpanded(true);

        Layout.framesPanelShowing = framesPanelShowing;
    }

    public static void setLayersPanelShowing(final boolean layersPanelShowing) {
        Layout.layersPanelShowing = layersPanelShowing;
    }

    public static void setColorsPanelShowing(final boolean colorsPanelShowing) {
        Layout.colorsPanelShowing = colorsPanelShowing;
    }

    public static void setToolbarShowing(final boolean toolbarShowing) {
        Layout.toolbarShowing = toolbarShowing;
    }

    public static void togglePanels() {
        if (areAllPanelsShowing())
            minimalUI();
        else
            showAllPanels();
    }

    public static void minimalUI() {
        setAllPanels(false);
    }

    public static void showAllPanels() {
        setAllPanels(true);
    }

    public static void setAllPanels(final boolean showing) {
        adjustPanels(() -> {
            setLayersPanelShowing(showing);
            setColorsPanelShowing(showing);
            setToolbarShowing(showing);
            setFramesPanelShowing(showing);
            setProjectsExpanded(showing);
        });
    }

    public static void adjustPanels(final Runnable adjustment) {
        adjustment.run();
        StippleEffect.get().rebuildAllMenus();
    }

    // program canvas size
    public static int width() {
        return size.x;
    }

    public static int height() {
        return size.y;
    }

    public static void setSize(final int w, final int h) {
        Layout.size = new Coord2D(w, h);
    }

    // bottom bar layout
    public static int getBottomBarTargetPixelX() {
        return (int)(width() * 0.15);
    }

    public static int getBottomBarCanvasSizeX() {
        return (int)(width() * 0.225);
    }

    public static int getBottomBarZoomPercentageX() {
        return (int)(width() * 0.3);
    }

    public static int getBottomBarZoomSliderX() {
        return (int)(width() * 0.35);
    }

    public static int getUISliderWidth() {
        return width() / 10;
    }

    public static int getBottomBarToolWidth() {
        return getBottomBarTargetPixelX();
    }

    public static int getBottomBarTargetPixelWidth() {
        return getBottomBarCanvasSizeX() - getBottomBarTargetPixelX();
    }

    public static int getBottomBarCanvasSizeWidth() {
        return getBottomBarZoomPercentageX() - getBottomBarCanvasSizeX();
    }

    public static int getBottomBarZoomPercentageWidth() {
        return getBottomBarZoomSliderX() - getBottomBarZoomPercentageX();
    }

    // segments layout
    public static int getProjectsWidth() {
        final int w = width();
        return isFramesPanelShowing() ? w / 2 : w;
    }

    public static int getFramesWidth() {
        return width() - getProjectsWidth();
    }

    public static int getWorkspaceWidth() {
        return width() - (getToolsWidth() + Math.max(getLayersWidth(),
                getColorsWidth()));
    }

    public static int getToolsWidth() {
        return isToolbarShowing() ? TOOLS_W : 0;
    }

    public static int getLayersWidth() {
        return isLayersPanelShowing() ? RIGHT_PANEL_W : 0;
    }

    public static int getColorsWidth() {
        return isColorsPanelShowing() ? RIGHT_PANEL_W : 0;
    }

    public static int getTopPanelHeight() {
        return isProjectsExpanded() ? CONTEXTS_H : COLLAPSED_CONTEXTS_H;
    }

    public static int getWorkspaceHeight() {
        return height() - (getTopPanelHeight() + BOTTOM_BAR_H);
    }

    public static int getLayersHeight() {
        if (!isLayersPanelShowing())
            return 0;

        final int workspaceH = getWorkspaceHeight();

        return isColorsPanelShowing() ? workspaceH / 2 : workspaceH;
    }

    public static int getColorsHeight() {
        if (!isColorsPanelShowing())
            return 0;

        return getWorkspaceHeight() - getLayersHeight();
    }

    public static Coord2D getProjectsPosition() {
        return new Coord2D();
    }

    public static Coord2D getFramesPosition() {
        return getProjectsPosition().displace(getProjectsWidth(), 0);
    }

    public static Coord2D getToolsPosition() {
        return getProjectsPosition().displace(0, getTopPanelHeight());
    }

    public static Coord2D getWorkspacePosition() {
        return getToolsPosition().displace(getToolsWidth(), 0);
    }

    public static Coord2D getLayersPosition() {
        return getWorkspacePosition().displace(getWorkspaceWidth(), 0);
    }

    public static Coord2D getColorsPosition() {
        return getLayersPosition().displace(0, getLayersHeight());
    }

    public static Coord2D getBottomBarPosition() {
        return getToolsPosition().displace(0, getWorkspaceHeight());
    }

    public static Coord2D getSegmentContentDisplacement() {
        return new Coord2D(CONTENT_BUFFER_PX, SEGMENT_TITLE_CONTENT_OFFSET_Y);
    }

    public static Coord2D getCanvasMiddle() {
        return new Coord2D(width() / 2, height() / 2);
    }

    // misc. layout
    public static int getColorSelectorIncY() {
        return (int)(getColorsHeight() / 6.5);
    }

    public static int getVertScrollWindowHeight() {
        return getLayersHeight() - (CONTENT_BUFFER_PX + SEGMENT_TITLE_CONTENT_OFFSET_Y);
    }

    public static int getFrameScrollWindowWidth() {
        return getFramesWidth() - (2 * CONTENT_BUFFER_PX);
    }

    public static int getProjectScrollWindowWidth() {
        return getProjectsWidth() - (2 * CONTENT_BUFFER_PX);
    }

    // dialogs layout
    public static int getDialogWidth() {
        return width() / 2;
    }

    public static int getDialogHeight() {
        return height() / 2;
    }

    public static int getDialogContentWidthAllowance() {
        return getDialogWidth() - (DIALOG_DYNAMIC_W_ALLOWANCE + DIALOG_CONTENT_BIG_OFFSET_X);
    }

    public static Coord2D getDialogPosition() {
        return getCanvasMiddle().displace(-getDialogWidth() / 2,
                -getDialogHeight() / 2);
    }

    public static Coord2D getDialogContentInitial() {
        return getDialogPosition().displace(
                CONTENT_BUFFER_PX + BUTTON_BORDER_PX,
                TEXT_Y_OFFSET + BUTTON_BORDER_PX +
                        (int)(1.5 * STD_TEXT_BUTTON_INC));
    }
}
