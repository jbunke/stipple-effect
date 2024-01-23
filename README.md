# ![Stipple Effect](https://i.imgur.com/CZhtaSP.gif)

[![Download](https://i.imgur.com/X6ClI06.png)](https://flinkerflitzer.itch.io/stipple-effect)
[![Changelog](https://i.imgur.com/IhJsonn.png)](https://github.com/jbunke/stipple-effect/blob/master/changelog.md)

## About
**Stipple Effect** is an image editor designed for creating and animating pixel art. The program was first built to facilitate and improve my workflow for the pixel art that I produce as an [indie game developer](https://flinkerflitzer.itch.io/) and [freelancer](https://www.fiverr.com/jordanbunke).

## Features
* Projects consist of layers and frames
  * Layers can be _static_ or _dynamic_ i.e. their contents can vary per frame or persist across the animation
* [Advanced brush modes that combine the primary and secondary color in interesting ways](https://i.imgur.com/USum331.gif)
* Rigorous state management
  * [Granular undo/redo](https://i.imgur.com/kL84ffp.gif)
* Animation playback
  * Onion skinning
  * [Edit during playback](https://i.imgur.com/n2H93Xr.gif)
* [Import and partition sprite sheets to animations directly](https://i.imgur.com/4OoRW4Q.gif)
* Palettes
  * Import and export palettes
  * Sort palette colors by hue, saturation, or value
  * Palettize project (entire project, layer, frame, or layer-frame) i.e. snap pixels in scope to nearest color in palette, measured by RGBA difference
  * Extract project contents to new palette
  * Easily modify palettes with color addition and removal options
* Intuitive and powerful outlining utilities
* Family of selection tools designed for pixel-perfect selection
* And much more!

## External Dependencies
* [Delta Time](https://github.com/jbunke/delta-time) - my lightweight graphics library that handles GUI and execution loop boilerplate
* Animation Encoder - wrapper for Square's [gifencoder](https://github.com/square/gifencoder) and for [jcodec](https://github.com/jcodec/jcodec)
