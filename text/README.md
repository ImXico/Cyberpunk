### TextHelper

The [`TextHelper`](https://github.com/ImXico/Cyberpunk/blob/master/text/src/main/kotlin/cyberpunk/text/TextHelper.kt) grants a bunch of positioning utilities for your images. All functions of this mini extension return Vector2 objects.

To center a piece of text (in a given font) on the screen, you'd call:
```kotlin
TextHelper.center(font, text, WORLD_WIDTH, WORLD_HEIGHT)
```

You can also choose to center only horizontally or vertically:
```kotlin
TextHelper.centerX(font, text, WORLD_WIDTH, targetY)
TextHelper.centerY(font, text, WORLD_HEIGHT, targetX)
```

Or even inside an image:
```kotlin
TextHelper.centerOnImage(font, text, imageWidth, imageHeight, imagePosition)
```

![img](https://i.gyazo.com/da82a22d207f6c07785f65026efff612.png)
