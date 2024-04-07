# ![Stipple Effect](https://i.imgur.com/sDn5Bz5.gif)

[![Download](https://i.imgur.com/Ry12ica.png)](https://flinkerflitzer.itch.io/stipple-effect)
[![Changelog](https://i.imgur.com/83bOHrf.png)](https://github.com/jbunke/stipple-effect/blob/master/changelog.md)
[![Roadmap](https://i.imgur.com/7CECQB6.png)](https://github.com/jbunke/stipple-effect/blob/master/roadmap.md)

## About
**Stipple Effect** is an image editor designed for creating and animating pixel art. The program was first built to facilitate and improve my workflow for the pixel art that I produce as an [indie game developer](https://flinkerflitzer.itch.io/) and [freelancer](https://www.fiverr.com/jordanbunke).

## Features
* Projects consist of layers and frames
  * [Layers can be _static_ or _dynamic_ i.e. their contents can vary per frame or persist across the animation](https://i.imgur.com/kGmxS0q.gif)
* [Advanced brush modes that combine the primary and secondary color in interesting ways](https://i.imgur.com/rioexla.gif)
* Rigorous state management
  * [Granular undo/redo](https://i.imgur.com/0v1a0na.gif)
* Animation playback
  * [Onion skinning](https://i.imgur.com/FuhTIir.gif)
  * [Edit during playback](https://i.imgur.com/pNjlHng.gif)
* [Import and partition sprite sheets to animations directly](https://i.imgur.com/nzGI6z7.gif)
* Palettes
  * Import and export palettes
  * Sort palette colors by hue, saturation, or value
  * [Palettize project (entire project, layer, frame, or layer-frame) i.e. snap pixels in scope to nearest color in palette, measured by RGBA difference](https://i.imgur.com/j3W1fd9.gif)
  * Extract project contents to new palette
  * Easily modify palettes with color addition and removal options
* [Intuitive and powerful outlining utilities](https://i.imgur.com/Fp48y2v.gif)
* [Family of selection tools designed for pixel-perfect selection](https://i.imgur.com/U0qONz3.gif)
* And much more!

## External Dependencies
* [Delta Time](https://github.com/jbunke/delta-time) - my lightweight graphics library that handles GUI and execution loop boilerplate
* Animation Encoder - wrapper for Square's [gifencoder](https://github.com/square/gifencoder) and for [jcodec](https://github.com/jcodec/jcodec)
