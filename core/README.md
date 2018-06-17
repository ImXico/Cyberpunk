## Contents
1. [State Management](#state-management)
2. [Transitions](#transitions)
3. [World Coordinates](#world-coordinates)
4. [The Base Application](#base-application)

## State Management

### State
A [`State`](https://github.com/ImXico/Cyberpunk/blob/master/core/src/main/kotlin/cyberpunk/core/state/State.kt) is, in some aspects, similar to a stage in Scene2D. All states should be capable of:
- Handling input (like touches and key presses).
- Updating their inner logic.
- Rendering their internal components.
- Disposing resources when they're not needed anymore.

### StateAdapter
The [`StateAdapter`](https://github.com/ImXico/Cyberpunk/blob/master/core/src/main/kotlin/cyberpunk/core/state/StateAdapter.kt) is a convenience implementation of the State interface. It contains input conversion methods, and every concrete state that you'd implement would inherit from it. When you create a new state that extends this class, you must *atleast* override these:

```kotlin
class MyState : StateAdapter(...) {
  override fun update(delta: Float) {}
  override fun render(batch: Batch) {}
  override fun dispose() {}
}
```

### StateManager
The [`StateManager`](https://github.com/ImXico/Cyberpunk/blob/master/core/src/main/kotlin/cyberpunk/core/state/StateManager.kt) is the entity that manages how the states flow. It requires minimal setup, and manages stuff like state updates, renders and transitions.

Usually, you will create an instance of ```StateManager``` in the base application, injecting it on the constructor of each concrete ```State``` (*i.e.* a class that extends StateAdapter).

When instantiating a ```StateManager```, you'll need to pass in a [`Camera`](https://github.com/libgdx/libgdx/wiki/Orthographic-camera) and a [`Viewport`](https://github.com/libgdx/libgdx/wiki/Viewports):

```kotlin
val stateManager = StateManager(camera, viewport)
```

Any state that you create should have this instance injected on its constructor:

```kotlin
class MenuState : StateAdapter(stateManager) { ... }
// ...
val menuState = new MenuState(stateManager)
```

To set the current state, is also very simple:

```kotlin
stateManager.go(otherState)
stateManager.go(otherState, someTransition)
```

Other methods available on the ```StateManager``` API should be called on the base app. After that, you won't need to bother with them anymore. This will be shown below, under [Base Application](#base-application).

## Transitions
A state [`Transition`](https://github.com/ImXico/Cyberpunk/blob/master/core/src/main/kotlin/cyberpunk/core/transition/Transition.kt) makes up for a smoother UX for state changes. There are currently two types of transitions available:

[`Fade`](https://github.com/ImXico/Cyberpunk/blob/master/core/src/main/kotlin/cyberpunk/core/transition/types/Fade.kt) - The screen fades from one state to another.

![fade-gif](https://zippy.gfycat.com/GlamorousExhaustedFrilledlizard.gif)

[`HorizontalSlide`](https://github.com/ImXico/Cyberpunk/blob/master/core/src/main/kotlin/cyberpunk/core/transition/types/HorizontalSlide.kt) - Left-to-Right or Right-to-Left. Watch in 60 fps [here](https://gfycat.com/HiddenTartIzuthrush).

![slide-gif](https://zippy.gfycat.com/HiddenTartIzuthrush.gif)

### Making Your Own Transitions
It's easy to make your own transitions - just make sure you implement the `Transition` interface! Besides the implementation *itself* of the transition, you don't need to worry about its API - the `StateManager` will take care of managing the transitions for you! However, the `Transition` API makes use of properties and default method implementations in interfaces. For the time being, [this is an issue](https://youtrack.jetbrains.com/issue/KT-4779) if you wish to create your own transitions in Java. Thus, the *only* way to create custom transitions is to create them in Kotlin and *then* call them normally from Java.

## World Coordinates
To keep a consistent application - that's independent of real device size or asset size - it's a good idea to:
- Define a virtual resolution (here called world dimensions), that can be whatever you want.
- Set a `Camera` to use that resolution.
- Use a `Viewport` to adapt the game screen to the different physical devices.

These virtual dimensions will, thus, be the *boundaries* of our game screen - they'll influence all of the game's rendering.
Because of this, two static variables, `WORLD_WIDTH` and `WORLD_HEIGHT` have been declared over at [`WorldConfig`](https://github.com/ImXico/Cyberpunk/blob/master/core/src/main/kotlin/cyberpunk/core/WorldConfig.kt) - important for the next step.

## Base Application
This is the final element of the core - our root/base class - the one that extends [`ApplicationAdapter`](https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/ApplicationAdapter.html). More on the life-cycle of a libGDX app can be seen [here](https://github.com/libgdx/libgdx/wiki/The-life-cycle). Now that we know about world coordinates, states and how the state manager controls how states flow, we can easily set it up:

```kotlin
class App : ApplicationAdapter()
```

Start out by initializing the `StateManager`:

```kotlin
private lateinit var stateManager: StateManager

override fun create() {
  // Have a Camera and a Viewport
  val camera = OrthographicCamera(WORLD_WIDTH.toFloat(), WORLD_HEIGHT.toFloat())
  camera.position.set(viewportWidth / 2f, viewportHeight / 2f, 0f)
  val viewport = createViewport(camera)
  
  // Initialize the StateManager
  stateManager = new StateManager(camera, viewport)
  
  // Initialize the first state of the app
  val myCoolState = new CoolState(stateManager)
  
  // Set the first state
  stateManager.go(myCoolState)
}
```

The `render` method can be implemented like this:

```kotlin
override fun render() {
  Gdx.gl.glClearColor(230f / 255f, 230f / 255f, 230f / 255f, 1f)
  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
  stateManager.update(Gdx.graphics.deltaTime)
  stateManager.render()
}
```

All other methods - `dispose`, `resize`, etc. - are as simple as it gets:

```kotlin
override fun dispose() = stateManager.dispose()
override fun resize(width: Int, height: Int) = stateManager.resize(width, height)
```
