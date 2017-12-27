## Contents
1. [State Management](#state-management)
2. [Transitions](#transitions)
3. [World Coordinates](#world-coordinates)
4. [The Base Application](#base-application)

Punk offers a fairly simple and clean API, which makes it easy to use the. Throughout this Wiki page, you'll get to know all of the essential parts of the [`core`](http://www.google.pt) package.

## State Management

### State
A [`State`]() is, in some aspects, similar to a stage in Scene2D. All states should be capable of:
- Handling input (like touches and key presses).
- Updating their inner logic.
- Rendering their internal components.
- Disposing resources when they're not needed anymore.

### StateAdapter
The [`StateAdapter`]() is a convenience implementation of the State interface. It contains input conversion methods, and every concrete state that you'd implement would inherit from it. When you create a new state that extends this class, you must *atleast* override these:

```kotlin
class MyState : StateAdapter() {

  override fun update(delta: Float) {}
  override fun render(batch: Batch) {}
  override fun dispose() {}

}
```

### StateManager
The [`StateManager`]() is an entity that manages how the states flow. With minimal setup (as you'll see soon, in the [base application section](), it manages stuff like state updates, renders and transitions. You have global access to it, making it easy to use.

The two key functions that you should be aware off are `setup` and `go`.

`#setup` lets you initialize the state manager with a [`Camera`](https://github.com/libgdx/libgdx/wiki/Orthographic-camera) and a [`Viewport`](https://github.com/libgdx/libgdx/wiki/Viewports)- call this once:
```kotlin
val camera: Camera = ...
val viewport: Viewport = ...
StateManager.setup(camera, viewport)
```
`#go` lets you jump around different states:
```kotlin
StateManager.go(otherState)
StateManager.go(otherState, transition)
```

The API offers more, though, but after an initial setting up phase, you won't need to bother much with them!
```kotlin
fun update(delta: Float)
fun render()
fun resize(width: Int, height: Int)
fun pause()
fun resume()
fun dispose()
```

## Transitions
A state [`Transition`]() makes up for a smoother UX - instead of changing states abruptely, you can use a transition.

[`Fade`]() - The screen fades from one state to another.

![fade-gif](https://zippy.gfycat.com/GlamorousExhaustedFrilledlizard.gif)

[`HorizontalSlide`]() - Left-Right or Right-Left. Watch in 60 fps [here](https://gfycat.com/HiddenTartIzuthrush).

![slide-gif](https://zippy.gfycat.com/HiddenTartIzuthrush.gif)

It's easy to make your own transitions - just make sure you implement the [`Transition`]() interface! Besides the implementation *itself* of the transition, you don't need to worry about its API - the [`StateManager`]() will take care of managing the transitions for you!

## World Coordinates
To keep a consistent application - that's independent of real device size or asset size - it's a good idea to:
- Define a virtual resolution (here called world dimensions), that can be whatever you want.
- Set a [Camera](https://github.com/libgdx/libgdx/wiki/Orthographic-camera) to use that resolution.
- Use a [Viewport](https://github.com/libgdx/libgdx/wiki/Viewports) to adapt the game screen to the different physical devices.

These virtual dimensions will, thus, be the *boundaries* of our game screen - they'll influence all of the game's rendering.
Because of this, two static variables, `WORLD_WIDTH` and `WORLD_HEIGHT` have been declared over at [`WorldConfig`](https://github.com/ImXico/punk/blob/feature/kotlin/kot/core/WorldConfig.kt) - important for the next step.

## Base Application
This is the final element of the core - our root/base class - the one that extends [`ApplicationAdapter`](https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/ApplicationAdapter.html). More on the life-cycle of a libGDX app can be seen [here](https://github.com/libgdx/libgdx/wiki/The-life-cycle). Now that we know about world coordinates, states and how the state manager controls how states flow, we can easily set it up!

```kotlin
class App : ApplicationAdapter()
```

You can override whatever you want from the [`ApplicationAdapter`](). I'd recommend overriding atleast the following functions:

```kotlin
override fun create()
override fun render()
override fun dispose()
override fun resize(width: Int, height: Int)
```

Start out by initializing the [`StateManager`](), passing it a [`Camera`]() and a [`Viewport`]():

```kotlin
override fun create() {
  val camera: Camera = createCamera()
  val viewport: Viewport = createViewport(camera)
  StateManager.setup(camera, viewport)
}

// Private functions for an uncluttered #create.

private fun createCamera(): Camera {
  return OrthographicCamera(WORLD_WIDTH.toFloat(), WORLD_HEIGHT.toFloat()).apply {
    position.set(viewportWidth / 2f, viewportHeight / 2f, 0f)
  }
}

private fun createViewport(camera: Camera): Viewport {
  return ExtendViewport(WORLD_WIDTH.toFloat(), WORLD_HEIGHT.toFloat(), camera)
}
```

Usually, you'll want your game to start at some [`State`](). Imagine that we have a state called `MainMenu`:

```kotlin
val mainMenu: State = MenuState()

// Option 1
StateManager.setup(camera, viewport, mainMenu)

// Option 2
StateManager.setup(camera, viewport)
StateManager.go(mainMenu)
```

That's it for the `#create` method! Now, the `#render` method is very simple:

```kotlin
override fun render() {
  Gdx.gl.glClearColor(230f / 255f, 230f / 255f, 230f / 255f, 1f)
  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
  StateManager.update(Gdx.graphics.deltaTime)
  StateManager.render()
}
```

Finally, `#dispose` and `#resize` are as simple as it gets:

```kotlin
override fun dispose() = StateManager.dispose()
override fun resize(width: Int, height: Int) = StateManager.resize(width, height)
```
