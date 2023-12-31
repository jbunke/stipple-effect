# Changelog

## Upcoming

### Add:
* Rotating and stretching selections 
* Outline selection shortcuts 
* Native file type 
* Larger canvas sizes and necessary performance optimizations to facilitate them 
* Scripting language, system and previews 
* Adjustable panels 
* Palettes 
* More settings read from file on startup, including:
  * Toggle for rendering at true pixel size 
  * Delta Time canvas size
### Change:
* Remap subtractive mode from S to ALT key once correctly implemented in Delta Time

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
