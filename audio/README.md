**Note**

> LibGDX provides [AssetManager](https://github.com/libgdx/libgdx/wiki/Managing-your-assets), which has a fully-fledged API for loading, storing and fetching assets (sounds, music, textures, fonts...). Using `AssetManager` (with maybe some DI framework) might be a more scalable and testable solution as projects grow in complexity. The manager modules (`ImageManager`, `SoundManger`, `MusicManager`...) may eventually become wrappers around `AssetManager`. Until then, they'll remain as singleton-based managers whose simplicity might come in handy for game jams or PoCs.

The audio package for Cyberpunk is split into two different utilities: [`SoundManager`](#soundmanager) and [`MusicManager`](#musicmanager).

### SoundManager

The [`SoundManager`](https://github.com/ImXico/Cyberpunk/blob/master/audio/src/main/kotlin/cyberpunk/audio/SoundManager.kt) will hold [`Sound`](https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/audio/Sound.html) instances. As stated in the official libGDX [wiki](https://github.com/libgdx/libgdx/wiki/Sound-effects):
> Sound effects are small audio samples, usually no longer than a few seconds, that are played back on specific game events such as a character jumping or shooting a gun. On Android, a Sound instance can not be over 1mb in size. If you have a bigger file, use Music.

Say we've got ourselves a sound file called `mysound.mp3`, located at assets/sounds.

You can load that sound into memory by calling:
```kotlin
SoundManager.load("mysound", "sounds/mysound.mp3")
```

Play it with a specified volume:
```kotlin
SoundManager.play("mysound", 0.7f)
```

Pause/resume it:
```kotlin
SoundManager.pause("mysound")
SoundManager.resume("mysound")
```

Dispose it when you don't need to use it anymore - hey, leave a good footprint!
```kotlin
SoundManager.dispose("mysound")
```

### MusicManager

The [`MusicManager`](https://github.com/ImXico/Cyberpunk/blob/master/audio/src/main/kotlin/cyberpunk/audio/MusicManager.kt) will hold [`Music`](https://github.com/libgdx/libgdx/wiki/Streaming-music) instances. As stated in the official libGDX [wiki](https://github.com/libgdx/libgdx/wiki/Streaming-music):
> For any sound that's longer than a few seconds, it is preferable to stream it from disk instead of fully loading it into RAM. Libgdx provides a Music interface that lets you do that.

Once again, with a music file called `mymusic.mp3`, located at assets/music, you'd load it the same way you'd do with the [`SoundManager`](#soundmanager):
```kotlin
MusicManager.load("mymusic", "music/mymusic.mp3")
```

After that, the API is pretty much the same as the sound manager's.

Remember that music instances are heavy, you should usually not have more than one or two at most loaded.
A music instance needs to be disposed if it is no longer needed, to free up resources:
```kotlin
MusicManager.dispose("mymusic")
```
