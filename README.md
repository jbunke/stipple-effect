# ![Stipple Effect](https://i.imgur.com/sDn5Bz5.gif)

[![Download](https://i.imgur.com/Ry12ica.png)](https://flinkerflitzer.itch.io/stipple-effect)
[![Changelog](https://i.imgur.com/83bOHrf.png)](changelog.md)
[![Roadmap](https://i.imgur.com/7CECQB6.png)](roadmap.md)

## About
_Stipple Effect_ is an image editor designed for creating and animating pixel art. It is designed to facilitate a variety of workflows and to encourage rapid, iterative creation of pixel art.

SE was made by a solo indie game developer with the needs and skill sets of other indie devs in mind. It is lightweight and simple to learn and use, yet allows for considerable depth and complexity. SE has a feature-rich scripting API that can be harnessed for the automation of otherwise tedious and repetitive tasks.

![Preview](https://raw.githubusercontent.com/wiki/jbunke/stipple-effect/assets/complex-preview.gif)
<div align="center">A custom preview in action... one of many applications of scripting</div>

## Features
* Symbiotic relationship between layers and frames
    * [Linked and unlinked layers](https://raw.githubusercontent.com/wiki/jbunke/stipple-effect/assets/linked-layers.gif)
* [Two-color system: Stipple Effect's brush, pencil, and gradient tool allow for interesting combinations of the primary and secondary colors](https://raw.githubusercontent.com/wiki/jbunke/stipple-effect/assets/combination-modes.gif)
    * Dither mode
    * Blend mode
    * Noise mode
* State management
    * [Granular undo and redo](https://raw.githubusercontent.com/wiki/jbunke/stipple-effect/assets/granular-undo-redo.gif)
* Animation playback
    * [Onion skinning](https://raw.githubusercontent.com/wiki/jbunke/stipple-effect/assets/onion-skin.gif)
    * Edit during playback
* [Split/stitch](https://raw.githubusercontent.com/wiki/jbunke/stipple-effect/assets/split-stitch.gif)
    * Split a project into frames
    * Stitch frames together into a sprite sheet
* Palettes
    * Import and export palettes
    * [Palettization](https://raw.githubusercontent.com/wiki/jbunke/stipple-effect/assets/palettization.gif)
    * [Extract colors in a project to a palette](https://raw.githubusercontent.com/wiki/jbunke/stipple-effect/assets/extract-colors.gif)
* Selection
    * [Family of selection tools designed for pixel-perfect selection](https://raw.githubusercontent.com/wiki/jbunke/stipple-effect/assets/selection-tools.gif)
    * [Intuitive and powerful outlining utilities](https://raw.githubusercontent.com/wiki/jbunke/stipple-effect/assets/outline.gif)
* [Pixel grid](https://raw.githubusercontent.com/wiki/jbunke/stipple-effect/assets/pixel-grid.gif)
    * Easily enable/disable and modify the size of the pixel grid cells
    * The box select tool can snap to the grid
* And much more!

## Scripting
_Stipple Effect_ supports scripting for three different use cases: **automation**, **custom previews** and **color transformation**. It is highly recommended for users that want to get the most out of the program to read the API and familiarize themselves with its potential applications.
* [Scripting overview](https://github.com/jbunke/stipple-effect/wiki/Scripting)
* [API](https://github.com/jbunke/stipple-effect/wiki/Scripting-API)
* [Example scripts](https://github.com/jbunke/se-script-examples)

## External Dependencies
* [Delta Time](https://github.com/jbunke/delta-time) - my lightweight graphics library that handles GUI and execution loop boilerplate
  * Core module
  * Fonts module
  * Menu extension module
  * Script module
  * Sprite module
* [ANTLR v4](https://github.com/antlr/antlr4) - lexing and parsing library that powers the _DeltaScript_ interpreter
* Animation Encoder - my wrapper for Square's [gifencoder](https://github.com/square/gifencoder) and for [jcodec](https://github.com/jcodec/jcodec) (currently closed-source)
