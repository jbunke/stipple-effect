# ![Stipple Effect](https://raw.githubusercontent.com/jbunke/se-docs/master/assets/graphics/logo-anim.gif)

[![Website](https://raw.githubusercontent.com/jbunke/se-docs/master/assets/buttons/website.png)](https://jbunke.github.io/se)
[![Changelog](https://raw.githubusercontent.com/jbunke/se-docs/master/assets/buttons/changelog.png)](changelog.md)
[![Roadmap](https://raw.githubusercontent.com/jbunke/se-docs/master/assets/buttons/roadmap.png)](roadmap.md)
[![Buy on Itch.io](https://raw.githubusercontent.com/jbunke/se-docs/master/assets/buttons/itch.png)](https://flinkerflitzer.itch.io/stipple-effect)

## About
*Stipple Effect* is a pixel art editor that supports animation and scripting. It is designed to facilitate a variety of workflows and to encourage rapid, iterative creation of video game art assets and other types of artwork.

* made by a solo game developer with the needs and skill sets of other indie devs in mind
* lightweight and simple to learn and use, yet allows for considerable depth and complexity
* expressive scripting API that can be used to **automate program actions** and **create dynamic preview and color transformations with complex logic**

![Preview](https://raw.githubusercontent.com/jbunke/se-docs/master/assets/graphics/complex-preview.gif)
<div align="center">A preview script in action</div>

## Features
* Symbiotic relationship between [layers](https://jbunke.github.io/se/docs/layer) and [frames](https://jbunke.github.io/se/docs/frame)
    * Linked-cel layers (contents persist across frames)
* Two-color system: *Stipple Effect*'s brush, pencil, and gradient tool allow for interesting [combinations](https://jbunke.github.io/se/docs/color#combination-modes) of the [primary and secondary colors](https://jbunke.github.io/se/docs/interface#system-colors)
* Project [state control](https://jbunke.github.io/se/docs/state-control)
    * [Granular undo and redo](https://jbunke.github.io/se/docs/state-control#granularity)
    * History
    * Generate time lapses
* Animation
    * [Onion skinning](https://jbunke.github.io/se/docs/layer#onion-skin)
    * Edit during playback
* Split/stitch
    * [Split a sprite sheet into frames](https://jbunke.github.io/se/docs/sizing#split-a-sprite-sheet-into-frames)
    * [Stitch an animation into a sprite sheet](https://jbunke.github.io/se/docs/sizing#stitch-an-animation-into-a-sprite-sheet)
* Palettes
    * Import and export palettes
    * [Palettization](https://jbunke.github.io/se/docs/color-actions#palettization)
    * [Extract colors in a project to a palette](https://jbunke.github.io/se/docs/color-actions#extract-canvas-colors-to-palette)
* [Selection](https://jbunke.github.io/se/docs/selection)
    * [Family of selection tools](https://jbunke.github.io/se/docs/sel-area-tools) designed for pixel-perfect selection
    * Intuitive and powerful [outlining utilities](https://jbunke.github.io/se/docs/outline)
* [Pixel grid](https://jbunke.github.io/se/docs/pixel-grid)
    * Easily enable/disable and modify the size of the pixel grid's cells
    * The box select tool can snap to the grid
* And much more!

Find out more about the program by reading [the documentation](https://jbunke.github.io/se/docs/).

## Scripting
*Stipple Effect* scripts have three main uses: [**automation**](https://jbunke.github.io/se/docs/automation-scripts), [**custom previews**](https://jbunke.github.io/se/docs/preview-scripts) and [**color transformations**](https://jbunke.github.io/se/docs/color-scripts). It is highly recommended for users that want to get the most out of the program to read the API specification and familiarize themselves with its potential applications.
* [Scripting overview](https://jbunke.github.io/se/docs/scripting)
* [API specification](https://jbunke.github.io/se/api/)
* [Example scripts](https://github.com/jbunke/se-script-examples)

I have created [a VS Code extension](https://marketplace.visualstudio.com/items?itemName=jordanbunke.deltascript-for-stipple-effect) that provides syntax highlighting for *Stipple Effect* scripts.

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
