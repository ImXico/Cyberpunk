**Note**

> LibGDX provides [AssetManager](https://github.com/libgdx/libgdx/wiki/Managing-your-assets), which has a fully-fledged API for loading, storing and fetching assets (sounds, music, textures, fonts...). Using `AssetManager` (with maybe some DI framework) might be a more scalable and testable solution as projects grow in complexity. The manager modules (`ImageManager`, `SoundManger`, `MusicManager`...) may eventually become wrappers around `AssetManager`. Until then, they'll remain as singleton-based managers whose simplicity might come in handy for game jams or PoCs.

The [`image`]() extension is split in two classes: [`ImageManager`](https://github.com/ImXico/Cyberpunk/blob/master/image/src/main/kotlin/cyberpunk/image/ImageManager.kt) and [`ImageHelper`](https://github.com/ImXico/Cyberpunk/blob/master/image/src/main/kotlin/cyberpunk/image/ImageHelper.kt).

### ImageManager
This is made to be used with [`TextureAtlas`](https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/graphics/g2d/TextureAtlas.html). Information on how to use the TexturePacker to pack many smaller images onto larger images can be found [here](https://github.com/libgdx/libgdx/wiki/Texture-packer).

Say we've got `mypack.png` and `mypack.pack` from the Texture Packer inside our `assets` folder.

To load this pack and key it as `mypack`, we'd do the following:
```kotlin
ImageManager.load("mypack", "mypack.pack")
```

Alternatively, if you want the pack being loaded to be your default one, you can do:
```kotlin
ImageManager.load("mypack", "mypack.pack", setAsDefault = true)
```

To fetch a [`TextureRegion`](https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/graphics/g2d/TextureAtlas.AtlasRegion.html) named `myregion` from the loaded `mypack` pack, you'd call:
```kotlin
ImageManager.take("myregion", "mypack")
```
If you set `mypack` as default, though, you'll be able to call `#take` without passing the atlas name explicitly:

```kotlin
ImageManager.take("myregion") // Implicit second argument: default pack key
```

Finally, you can dispose any atlas at any time:
```kotlin
ImageManager.disposeAtlas("mypack")
```

### ImageHelper
The [`ImageHelper`](https://github.com/ImXico/Cyberpunk/blob/master/image/src/main/kotlin/cyberpunk/image/ImageHelper.kt) grants a bunch of positioning utilities for your images. All functions of this mini extension return [`Vector2`](https://github.com/ImXico/Cyberpunk/blob/master/image/src/main/kotlin/cyberpunk/image/ImageHelper.kt) objects.

To center an image on the screen, you'd call:
```kotlin
ImageHelper.center(imageWidth, imageHeight, WORLD_WIDTH, WORLD_HEIGHT)
```

You can also choose to center only horizontally or vertically:
```kotlin
ImageHelper.centerX(imageWidth, WORLD_WIDTH, targetY)
ImageHelper.centerY(imageHeight, WORLD_HEIGHT, targetX)
```

Or even inside another image:
```kotlin
ImageHelper.centerOnImage(imageWidth, imageHeight, otherWidth, otherHeight, otherPosition)
```

![img](https://i.gyazo.com/b2b826b4cd321b6aa03a3cf97c36aa6b.png)
