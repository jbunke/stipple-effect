package com.jordanbunke.stipple_effect.utility;

import com.jordanbunke.delta_time.menu.menu_elements.MenuElement;
import com.jordanbunke.delta_time.utility.math.Bounds2D;
import com.jordanbunke.delta_time.utility.math.Coord2D;
import com.jordanbunke.stipple_effect.StippleEffect;
import com.jordanbunke.stipple_effect.visual.menu_elements.TextLabel;

import java.awt.*;

public final class Layout {
    // panel variables
    private static boolean flipbookPanelShowing, colorsPanelShowing,
            toolbarShowing, projectsExpanded, projectsRequiresScrolling;

    private static int flipbookHeight;
    private static SamplerMode samplerMode;

    // layout constants
    private static final double
            TOOL_OPTIONS_BAR_SECTION_DIVIDER_PROPORTION = 0.02;
    private static final int TOOLS_W = 25, RIGHT_PANEL_W = 286,
            PROJECTS_H = 84, NON_SCROLLING_SUB = 20,
            MIN_FLIPBOOK_H = 168, MAX_FLIPBOOK_H = 411;
    public static final int
            COLLAPSED_PROJECTS_H = 27, BOTTOM_BAR_H = 24, TOOL_OPTIONS_BAR_H = 30,
            COLOR_COMP_W_ALLOWANCE = RIGHT_PANEL_W, SCREEN_H_BUFFER = 120,
            MIN_WINDOW_H = 788,
            MAX_WINDOW_H = Math.max(MIN_WINDOW_H,
                    Toolkit.getDefaultToolkit().getScreenSize().height - SCREEN_H_BUFFER),
            MIN_WINDOW_W = (int)(MIN_WINDOW_H * (16 / 9.)),
            MAX_WINDOW_W = Math.max(MIN_WINDOW_W, (int)(MAX_WINDOW_H * (16 / 9.))),
            TEXT_Y_OFFSET = -4, TOOL_TIP_OFFSET = 8,
            TEXT_CARET_W = 1, TEXT_CARET_H = 23,
            TEXT_CARET_Y_OFFSET = -11, TEXT_LINE_PX_H = TEXT_CARET_H + 2,
            NAV_RENDER_ORDER = 1,
            DD_MENU_HEADER_RIGHT_BUFFER = 64, DD_MENU_LEAF_MIDDLE_BUFFER = 50,
            CONTENT_BUFFER_PX = 8,
            DEFAULT_CHECKERBOARD_DIM = 4, CHECKERBOARD_MIN = 1, CHECKERBOARD_MAX = 256,
            DEFAULT_PIXEL_GRID_DIM = 1, PIXEL_GRID_MIN = 1, PIXEL_GRID_MAX = 128,
            MAX_PIXEL_GRID_LINES = 4000, PIXEL_GRID_COLOR_ALT_DIVS = 4,
            PIXEL_GRID_ZOOM_DIM_MAX = 19200,
            CURSOR_DIM = 40, BUTTON_DIM = 20, BUTTON_OFFSET = 2, ICON_BUTTON_OFFSET_Y = 3,
            BUTTON_INC = BUTTON_DIM + BUTTON_OFFSET, BUTTON_BORDER_PX = 2,
            PANEL_TITLE_CONTENT_OFFSET_Y = 30,
            LAYER_BUTTON_W = 188, LAYERS_ABOVE_TO_DISPLAY = 2, LAYER_NAME_LENGTH_CUTOFF = 12,
            FRAME_BUTTON_W = 40, FRAMES_BEFORE_TO_DISPLAY = 5,
            PX_PER_SCROLL = FRAME_BUTTON_W + BUTTON_OFFSET,
            PROJECT_NAME_BUTTON_PADDING_W = 20, SPACE_BETWEEN_PROJECT_BUTTONS_X = 8,
            PROJECTS_BEFORE_TO_DISPLAY = 1, DIALOG_CONTENT_INC_Y = 32,
            DIALOG_CONTENT_COMP_OFFSET_Y = 5,
            DIALOG_CONTENT_OFFSET_X = 150, LONG_NAME_TEXTBOX_W = 400,
            DIALOG_CONTENT_SMALL_W_ALLOWANCE = 120,
            DIALOG_CONTENT_BIG_W_ALLOWANCE = LONG_NAME_TEXTBOX_W - DIALOG_CONTENT_SMALL_W_ALLOWANCE,
            SMALL_TEXT_BOX_W = 80, STD_TEXT_BUTTON_W = 88, STD_TEXT_BUTTON_H = 25,
            STD_TEXT_BUTTON_INC = STD_TEXT_BUTTON_H + BUTTON_OFFSET,
            PREV_MIN_W = 262, PREV_MIN_H = 334, PREV_TL_BUFFER = 20,
            PREV_CONTAINER_Y = PREV_TL_BUFFER + (BUTTON_INC * 3) +
                    (CONTENT_BUFFER_PX - BUTTON_OFFSET),
            EMBED_PREV_TOP_BAR_Y = STD_TEXT_BUTTON_H,
            COLOR_TEXTBOX_W = 116,
            SLIDER_OFF_DIM = 20, SLIDER_BALL_DIM = 20, SLIDER_THINNING = 4,
            TOP_PANEL_SCROLL_WINDOW_H = STD_TEXT_BUTTON_H + SLIDER_BALL_DIM,
            DYNAMIC_LABEL_H = 40, DYNAMIC_LABEL_W_ALLOWANCE = 100,
            MAX_ERROR_CHARS_PER_LINE = 60, CHARS_CUTOFF = MAX_ERROR_CHARS_PER_LINE - 5;

    public static final Bounds2D
            ICON_DIMS = new Bounds2D(BUTTON_DIM, BUTTON_DIM),
            OUTLINE_BUTTON_DIMS = new Bounds2D(STD_TEXT_BUTTON_H, STD_TEXT_BUTTON_H),
            PALETTE_DIMS = new Bounds2D(24, 24);

    private static Bounds2D size;

    static {
        size = new Bounds2D(Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height);

        projectsExpanded = true;
        flipbookPanelShowing = true;
        colorsPanelShowing = true;
        toolbarShowing = true;

        projectsRequiresScrolling = false;

        flipbookHeight = (MIN_FLIPBOOK_H + MAX_FLIPBOOK_H) / 2;

        samplerMode = SamplerMode.SV_MATRIX;
    }

    // panel display
    public static boolean isProjectsExpanded() {
        return projectsExpanded;
    }

    public static boolean isFlipbookPanelShowing() {
        return flipbookPanelShowing;
    }

    public static boolean isColorsPanelShowing() {
        return colorsPanelShowing;
    }

    public static boolean isToolbarShowing() {
        return toolbarShowing;
    }

    public static boolean areAllPanelsShowing() {
        return isProjectsExpanded() && isToolbarShowing() &&
                isColorsPanelShowing() && isFlipbookPanelShowing();
    }

    public static void setProjectsRequiresScrolling(final boolean projectsRequiresScrolling) {
        Layout.projectsRequiresScrolling = projectsRequiresScrolling;
    }

    public static void setProjectsExpanded(final boolean projectsExpanded) {
        Layout.projectsExpanded = projectsExpanded;
    }

    public static void setFlipbookPanelShowing(final boolean flipbookPanelShowing) {
        Layout.flipbookPanelShowing = flipbookPanelShowing;
    }

    public static void setColorsPanelShowing(final boolean colorsPanelShowing) {
        Layout.colorsPanelShowing = colorsPanelShowing;
    }

    public static void setToolbarShowing(final boolean toolbarShowing) {
        Layout.toolbarShowing = toolbarShowing;
    }

    public static void togglePanels() {
        setAllPanels(!areAllPanelsShowing());
    }

    public static void minimalUI() {
        setAllPanels(false);
    }

    public static void showAllPanels() {
        setAllPanels(true);
    }

    private static void setAllPanels(final boolean showing) {
        adjustPanels(() -> {
            setColorsPanelShowing(showing);
            setToolbarShowing(showing);
            setFlipbookPanelShowing(showing);
            setProjectsExpanded(showing);
        });
    }

    public static void adjustPanels(final Runnable adjustment) {
        adjustment.run();
        StippleEffect.get().rebuildAllMenus();
    }

    // program canvas size
    public static int width() {
        return size.width();
    }

    public static int height() {
        return size.height();
    }

    public static void setSize(final int w, final int h) {
        size = new Bounds2D(w, h);
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

    // general layout
    public static Coord2D contentPositionAfterLabel(final MenuElement label) {
        return label.getRenderPosition().displace(
                label.getWidth() + CONTENT_BUFFER_PX,
                DIALOG_CONTENT_COMP_OFFSET_Y);
    }

    // tool options bar layout
    public static int optionsBarSliderWidth() {
        return getToolOptionsBarWidth() / 12;
    }

    public static int optionsBarSectionBuffer() {
        return (int) (getToolOptionsBarWidth() *
                TOOL_OPTIONS_BAR_SECTION_DIVIDER_PROPORTION);
    }

    public static int optionsBarTextY() {
        return getToolOptionsBarPosition().y + TEXT_Y_OFFSET +
                optionsBarRelativeYOffsetToBottomBar();
    }

    public static int optionsBarButtonY() {
        return getToolOptionsBarPosition().y + BUTTON_OFFSET +
                optionsBarRelativeYOffsetToBottomBar();
    }

    private static int optionsBarRelativeYOffsetToBottomBar() {
        return ((TOOL_OPTIONS_BAR_H - BOTTOM_BAR_H) / 2);
    }

    public static int optionsBarNextElementX(
            final MenuElement preceding, final boolean sectionGap
    ) {
        return preceding.getX() + preceding.getWidth() + (sectionGap
                ? optionsBarSectionBuffer() : CONTENT_BUFFER_PX);
    }

    public static int optionsBarNextButtonX(
            final MenuElement preceding
    ) {
        return preceding.getX() + preceding.getWidth() + BUTTON_OFFSET;
    }

    public static TextLabel optionsBarNextSectionLabel(
            final MenuElement preceding, final String text
    ) {
        return TextLabel.make(new Coord2D(
                optionsBarNextElementX(preceding, true),
                optionsBarTextY()), text);
    }

    // segments layout
    public static int getProjectsWidth() {
        return width();
    }

    public static int getWorkspaceWidth() {
        return width() - (getToolsWidth() + getColorsWidth());
    }

    public static int getFlipbookWidth() {
        return isFlipbookPanelShowing() ? getWorkspaceWidth() : 0;
    }

    public static int getToolOptionsBarWidth() {
        return getToolsWidth() + getWorkspaceWidth();
    }

    public static int getToolsWidth() {
        return isToolbarShowing() ? TOOLS_W : 0;
    }

    public static int getColorsWidth() {
        return isColorsPanelShowing() ? RIGHT_PANEL_W : 0;
    }

    public static int getTopPanelHeight() {
        return isProjectsExpanded() ? PROJECTS_H -
                (doesProjectsRequireScrolling() ? 0 : NON_SCROLLING_SUB)
                : COLLAPSED_PROJECTS_H;
    }

    public static int getFlipbookHeight() {
        return isFlipbookPanelShowing() ? flipbookHeight : 0;
    }

    public static int getToolOptionsBarHeight() {
        return isToolbarShowing() && StippleEffect.get().getTool()
                .hasToolOptionsBar() ? TOOL_OPTIONS_BAR_H : 0;
    }

    public static int getToolsHeight() {
        return height() - (getTopPanelHeight() +
                getToolOptionsBarHeight() + BOTTOM_BAR_H);
    }

    public static int getWorkspaceHeight() {
        return getToolsHeight() - getFlipbookHeight();
    }

    public static int getColorsHeight() {
        if (!isColorsPanelShowing())
            return 0;

        return getToolsHeight() + getToolOptionsBarHeight();
    }

    public static Coord2D getProjectsPosition() {
        return new Coord2D();
    }

    public static Coord2D getToolsPosition() {
        return getToolOptionsBarPosition().displace(0, getToolOptionsBarHeight());
    }

    public static Coord2D getToolOptionsBarPosition() {
        return getProjectsPosition().displace(0, getTopPanelHeight());
    }

    public static Coord2D getWorkspacePosition() {
        return getToolsPosition().displace(getToolsWidth(), 0);
    }

    public static Coord2D getFlipbookPosition() {
        return getWorkspacePosition().displace(0, getWorkspaceHeight());
    }

    public static Coord2D getColorsPosition() {
        return getToolOptionsBarPosition().displace(getToolOptionsBarWidth(), 0);
    }

    public static Coord2D getBottomBarPosition() {
        return getToolsPosition().displace(0, getToolsHeight());
    }

    public static Coord2D getPanelContentDisplacement() {
        return new Coord2D(CONTENT_BUFFER_PX, PANEL_TITLE_CONTENT_OFFSET_Y);
    }

    public static Coord2D getCanvasMiddle() {
        return new Coord2D(width() / 2, height() / 2);
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

    public static boolean doesProjectsRequireScrolling() {
        return projectsRequiresScrolling;
    }

    public static int getFlipbookUpLeeway() {
        return MAX_FLIPBOOK_H - flipbookHeight;
    }

    public static int getFlipbookDownLeeway() {
        return flipbookHeight - MIN_FLIPBOOK_H;
    }

    public static void changeFlipbookHeight(final int deltaHeight) {
        flipbookHeight += deltaHeight;
    }

    public static SamplerMode getSamplerMode() {
        return samplerMode;
    }

    public static void setSamplerMode(final SamplerMode samplerMode) {
        Layout.samplerMode = samplerMode;
    }
}
