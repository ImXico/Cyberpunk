<p align="center">
  <img src="https://github.com/ImXico/Cyberpunk/blob/master/logo.png?raw=true" alt="Cyberpunk Logo"/>
</p>

<p align="center">
  <a href="https://travis-ci.org/ImXico/Cyberpunk">
    <img src="https://travis-ci.org/ImXico/Cyberpunk.svg?branch=master">
  </a>
  
  <a href="https://jitpack.io/#ImXico/Cyberpunk">
    <img src="https://jitpack.io/v/ImXico/Cyberpunk.svg">
  </a>

  <a href="http://libgdx.badlogicgames.com">
    <img src="https://img.shields.io/badge/libgdx-1.9.10-red.svg">
  </a>

  <a href="https://kotlinlang.org">
    <img src="https://img.shields.io/badge/kotlin-1.3.61-orange.svg">
  </a>

  <a href="https://github.com/ImXico/Cyberpunk/blob/master/LICENSE.md">
    <img src="https://img.shields.io/badge/license-MIT-green.svg">
  </a>
</p>

## Introduction

Cyberpunk is a collection of independent, modular, pure-Kotlin libraries to use on top of [libGDX](http://libgdx.badlogicgames.com/), tailored with Java interoperability in mind. By offering various utilities and boilerplates, it aims to speed up development and ease the making of prototypes, making it a great fit for game jams!

## Modules

Cyberpunk is all about small, independent modules. Because of the Gradle integration, you can import *just* the modules that you need. To properly serve that purpose, every single module is totally independent from all others:

| Module | Description |
| ------ | ----------- |
| [audio](https://github.com/ImXico/Cyberpunk/tree/master/audio)       | Music/Sound asset management and various utilities. |
| [camera](https://github.com/ImXico/Cyberpunk/tree/master/camera)     | Various camera styles on the go.
| [core](https://github.com/ImXico/Cyberpunk/tree/master/core)         | State management and transitions - get up and running quickly! |
| [image](https://github.com/ImXico/Cyberpunk/tree/master/image)       | Image asset management and various utilities. |
| [profiler](https://github.com/ImXico/Cyberpunk/tree/master/profiler) | Wrappers around libGDX profiling utilities. |
| [physics](https://github.com/ImXico/Cyberpunk/tree/master/physics)   | Friendly and practical Box2D constructs. |
| [text](https://github.com/ImXico/Cyberpunk/tree/master/text)         | Text positioning helpers. |

## Usage

JitPack is used here, so, if you want to import *any* module, add this to your root `build.gradle` file:

```Groovy
allprojects {
  repositories {
    // ...
    maven { url 'https://jitpack.io' }
  }
}
```

Now, in order to import single modules, add to the `dependencies` as follows:

```Groovy
compile 'com.github.imXico.cyberpunk:$module:$version'
```

Replace `$module` with the name of the desired library (see above). Likewise, `$version` should be replaced with the desired version. Check out the released versions [here](https://github.com/ImXico/Cyberpunk/releases).

## What About libKTX?

Hey, [libKTX](https://github.com/libktx/ktx) is an awesome project, and I recommend it for larger Kotlin projects. Moreover, Cyberpunk and libKTX *have* actually [collaborated](https://github.com/libktx/ktx/issues/183) in the past. libKTX provides an *incredible* range of features, all very high quality. That being said, if you're looking for a smoother Java interoperability, or are carrying out a game jam, give Cyberpunk a try!

## License

This project is released under the MIT License.
