The audio package for Cyberpunk is split into two different utilities: [`SoundManager`]() and [`MusicManager`]().

### SoundManager

The [`SoundManager`]() will hold [`Sound`]() instances. As stated in the official libGDX [wiki]():
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

Diposose it when you don't need to use it anymore - hey, leave a good footprint!
```kotlin
SoundManager.dispose("mysound")
```

### MusicManager
The [`MusicManager`]() will hold [`Music`]() instances. As stated in the official libGDX [wiki]():
> For any sound that's longer than a few seconds, it is preferable to stream it from disk instead of fully loading it into RAM. Libgdx provides a Music interface that lets you do that.

Once again, with a music file called `mymusic.mp3`, located at assets/music, you'd load it the same way you'd do with the [`SoundManager`]():
```kotlin
MusicManager.load("mymusic", "music/mymusic.mp3")
```

After that, the API is pretty much the same as the sound manager's.

Remember that music instances are heavy, you should usually not have more than one or two at most loaded.
A music instance needs to be disposed if it is no longer needed, to free up resources:
```kotlin
MusicManager.dispose("mymusic")
```
