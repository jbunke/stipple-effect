# ![Stipple Effect](https://raw.githubusercontent.com/wiki/jbunke/stipple-effect/assets/logo-anim.gif)

[![Download](https://raw.githubusercontent.com/wiki/jbunke/stipple-effect/assets/buttons/download.png)](https://flinkerflitzer.itch.io/stipple-effect)
[![Changelog](https://raw.githubusercontent.com/wiki/jbunke/stipple-effect/assets/buttons/changelog.png)](changelog.md)
[![Roadmap](https://raw.githubusercontent.com/wiki/jbunke/stipple-effect/assets/buttons/roadmap.png)](roadmap.md)

## About
_Stipple Effect_ is a pixel art editor that supports animation and scripting. It is designed to facilitate a variety of workflows and to encourage rapid, iterative creation of video game art assets and other types of artwork.

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
* [ANTLR v4](https://github.com/antlr/antlr4) - lexing and parsing library that powers the _DeltaScript_ interpreter
* [Animation Encoder](https://github.com/jbunke/animation-encoder) - my wrapper for Square's [gifencoder](https://github.com/square/gifencoder) and for [jcodec](https://github.com/jcodec/jcodec)

## License

*Stipple Effect* is distributed under an end-user license agreement (EULA). Read it [here](LICENSE) to understand your rights and responsibilities as a user.

## Compile from source instructions

For those who do not have the means to buy *Stipple Effect*, the program can be compiled from source for free:

### Instructions

1. Clone this repository
2. Download and configure external dependencies in your development environment
   1. **Delta Time**: [download JAR - instructions in README](https://github.com/jbunke/delta-time)
   2. **Animation Encoder**: [download JAR - instructions in README](https://github.com/jbunke/animation-encoder)
   3. **ANTLR v4**: *Stipple Effect* uses version 4.13.1 of the ANTLR 4 Runtime. You can download the library from the Maven Repository or configure it manually. Find the artifact's dependency information [here](https://mvnrepository.com/artifact/org.antlr/antlr4-runtime/4.13.1).
3. Set up the project and run configuration with the following details:
   1. **JRE / JDK / Language level**: Java 17 or later
   2. **Main class**: `com.jordanbunke.stipple_effect.StippleEffect`
4. Build and run in the Java development environment of your choice!

### Note:
*Please keep in mind that you are liable to the terms of the EULA whether you buy the program or compile it from source.*
