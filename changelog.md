# Changelog

## Upcoming

### Add:
* Scripting system
    * Language
    * Interpreter
    * Preview scripts and transformation scripts
* Symmetry tools
### Fix:
* Performance optimizations
* Memory issues and related crashes

## **0.2.0.1** - 2024-02-26

### Fixed:
* Crash bug that was caused by undo/redo operations not executing their consequence (e.g. undoing "add layer" operation did not redraw the layer buttons)

## **0.2.0** - 2024-02-16

### Added:
* Added tools:
  * Line tool
  * Gradient tool
  * Shade brush
  * Polygon select
* Extended TextBox to support text selection
* Added status updates for actions without visual feedback
  * Saving... 
  * Color slider adjustments and color actions when color panel is hidden
  * Layer actions when layer panel is hidden
  * Frame actions when frame panel is hidden
* Pixel grid
  * Holding Shift while using box select snaps selection to pixel grid
* New fonts SE (default) and Pencil SE
* Snap option for moving (translating) selection bounds and contents
### Changed:
* Redesigned order and layout of layer-specific buttons
* Refactored much of ToolWithBreadth to interface HasBreadth
* Optimized STIP files to save static layers as single frame
  * Incremented STIP file standard from 1.0 to 1.1
* Cropping to selection bounds now snaps the image to the center of the screen
* Independent checkerboard settings for x and y that can be set to values between 1-256
* Updated splash screen
* Optimized palettization of frame-linked layers 
* Merging an unlinked layer onto a frame-linked layer in a project with multiple frames will now result in an unlinked layer that preserves each frame's contents
* Optimized and refactored state management system
### Fixed:
* Bug: Pasting onto new layer does not trigger a re-draw of the layer panel menu
* Bug: Nonsensical cursor positions suggest rotate selection operation instead of move selection

## **0.1.0** - 2024-01-22

### Added:
* Added tooltips for icon buttons and icon toggle buttons
* Added palettes
  * Palette files (.stippal)
  * Palettization (scope: layer/frame/entire project)
* Added native project file type (.stip)
* Added preview window
* Added customizable panel layout
* Added exit program button and corresponding are-you-sure dialog
* Added snap angles for image rotation (activated by holding Shift; multiples of 45 degrees)
### Changed:
* New file dialog now suggests project canvas size based on clipboard image size if present
* Canvas size limit is now 800x800
* Updated program icon and logo animation
* Changed settings dialog to an info-style dialog with multiple tabs
* Reworked panels and render scale to be dependent on the window and screen size
* Changed brush select behaviour to preview overlay without editing selection until unclick
* Brush select contents are now filtered by canvas bounds
* Made list buttons dynamic; now only redraws menus when corresponding collection size changes
### Fixed:
* HSV slider slippage
* Optimized selection overlays (still suboptimal)
* Fixed bug where closing project window doesn't update menu
* Fill behaviour now replaces color rather than filling over
### Removed:
* Remove Piccolo font; set Classic to default

## **0.0.2** - 2024-01-06

### Added:
* Added vertical and horizontal selection reflection 
* Added selection stretching 
* Added selection rotation 
* Added MP4 exporting
### Changed:
* Updated program icon
### Fixed:
* Fixed bug that would crash the program when attempting to call getFileName() on the root of a Path 
* Fixed bug that would crash the program when performing an undo/redo operation that resized the canvas - was caused by those operations not redrawing the checkerboard transparency background

## **0.0.1.3** - 2023-12-25

### Added:
* Added Piccolo font and set as default
* Added outlining system
### Changed:
* Changed default additional layer name prefix to "Layer " from "Ly. "
### Fixed:
* Fixed bug that would cause eraser to malfunction on non-square canvases

## **0.0.1.2** - 2023-12-24

### Added:
* Added the ability to import multiple files at once
### Fixed:
* Rendering onion skins and semi-transparent layers is now significantly more efficient and performant
* Typing in a textbox now locks off keyboard shortcuts while the textbox is enabled
* Fixed gaps on pencil select tool, renamed to brush select, made into a selection equivalent of brush tool
### Changed:
* GIF file exports are now processed in a new thread, so the program can be used while the process is ongoing
* Improved zooming by adding limits to anchor area and no longer adjusting the anchor on zoom outs
* Added customizable suffixes for PNG_SEPARATE save mode and associated settings to set defaults
### Removed:
* Mini opacity slider in the layers panel; layer buttons are now bigger and show more of the layer name, and the opacity can still be modified from the layer settings dialog

## **0.0.1.1** - 2023-12-23
### Added:
* Status updates for:
  * Project saved
### Fixed:
* Picked up selection previews no longer shroud pixels on other layers
* Any picked up selection is dropped onto editing layer before attempting pasting 
* Contents of paste are now snapped to the bounds of the canvas
* Opening pad dialog after previous pad operation no longer preserves previous pad values in the previewed bounds calculation
* Saving a file will now drop any picked up selection contents back onto the layer before saving the file

## **0.0.1** - 2023-12-23
### Added:
* First available version of Stipple Effect
