# HandyGDX

A bunch of libraries for [libGDX](http://libgdx.badlogicgames.com/).

## Features
Navigate through the different sections with these links; each one will have a small overview, and some will have examples.
A very simple example project (that will contain most of the topics below in action) can be found [here](https://github.com/ImXico/HandyGDX/tree/master/example).

- [World Dimensions](#world-dimensions)
- [State Management](#state-management)
  - [State](#state)
  - [Abstract State](#abstract-state)
  - [State Manager](#state-manager)
  - [Root App](#root-app)
  - [State Transitions](#state-transitions)
- [Resource Management](#resource-management)
  - [Image Manager](#image-manager)
  - [Audio Manager](#audio-manager)
- [Camera Helper](#camera-helper)
- [Text Helper](#text-helper)
- [Sprite Helper](#sprite-helper)

--

### World Dimensions
To keep a consistent app that's independent of real device size or asset size, it's a good idea to:
- Define a virtual resolution (here it's called world dimensions).
- Set a [Camera](https://github.com/libgdx/libgdx/wiki/Orthographic-camera) to use that resolution.
- Use a [Viewport](https://github.com/libgdx/libgdx/wiki/Viewports) to adapt the game screen to the different physical devices.

These virtual dimensions will, thus, be the *boundaries* of our game screen - they'll influence all of the game's rendering.
Because they'll be used in a lot of places (including on **many** other libs here!), an easy way to store & access them is by doing something like this:

```java
public class App extends ApplicationAdapter {

    @Override
    public void create() {
        /* First thing to do here! */
        /* These values should also be passed in the DesktopLauncher's config width and height. */
        final int worldWidth = 700;   // These values are random, for the example!
        final int worldHeight = 300;  // These values are random, for the example!
        WorldDimensions.set(worldWidth, worldHeight);
        // ...
    }
}
```

### State Management
#### State
States are, in some aspects, similar to Stages in [Scene2D](https://github.com/libgdx/libgdx/wiki/Scene2d).
In a game there would typically be a Play state, a Menu state, Game-Over state, etc, etc.
The most important information about the State is that it is capable of:
- Handling input (touches, key presses, ...).
  - And the touch input can be converted between *screen* <--> *world* coordinates.
- Updating its logic.
- Rendering its internal components.
- Disposing resources when their not needed anymore.

#### Abstract State
A convenience implementation of the State interface.

Every concrete state would be a subclass of [AbstractState](https://github.com/ImXico/HandyGDX/blob/master/source/State/AbstractState.java).
Thus, every concrete state will necessarily implement **atleast** the following methods:
```java
public void update(float delta) { ... }
public void render(Batch batch) { ... }
public void dispose() { ... }
```

#### State Manager
The state manager is exactly that - an entity that manages the states flow. Because it is a singleton, there is global access to its API, making it 
super easy to use.

A quick overview of the [StateManager](https://github.com/ImXico/HandyGDX/blob/master/source/State/StateManager.java):

```java
/**
 * Initializes the StateManager.
 * A camera and viewport to handle the game screen should be passed - their importance has been mentioned 
 * in the "World Dimensions" section.
 * Should be called **once**, in the {@link App#create()} method of the root/base app.
 */
void init(Camera camera, Viewport viewport)
```

```java
/**
 * This is the way to access the StateManager instance.
 * Should only be called after init(camera, viewport) had been called.
 */
StateManager getInstance()
```

```java
/**
 * Change the current state to a next state.
 * This is done instantly, without a transition of any kind.
 */
void setState(State nextState)
```

````java
/**
 * Change the current state to a next state with a transition.
 * Transitions will be discussed about below.
 */
void setState(State nextState, Transition transition)
```

```java
/**
 * Updates the currently running state and and any on-going transition, if there is one.
 * Called every frame, as it will be seen on the "Root App" section below.
 */
void update(float delta)
```

```java
/**
 * Renders the currenly running state and any on-going transition, if there is one.
 * Like update, also called every frame.
 * Similarly to what happens in the State, render will be called always **after** update. 
 */
void render()
```

```java
/**
 * Resizes the current (and also next, if it exists) state.
 * Also resizes the Viewport (the one passed on init(...)).
 * This will be called on the root/base class' resize(...).
 */
void resize(int width, int height)
```

```java
/**
 * Diposes the current (and also next, if it exists) state.
 */
void dispose()
```

#### Root App
Now that we know how States work, and how they're controlled by the StateManager, we can use them to easily setup our root/base class - the one that
extends [ApplicationAdapter](https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/ApplicationAdapter.html).

More on the root class and the life cycle of a libGDX app can be seen [here](https://github.com/libgdx/libgdx/wiki/The-life-cycle).

This is how it generally will look like, using the States + StateManager approach:

````java
// ... imports ...

public class App extends ApplicationAdapter {

    @Override
    public void create() {
        /* Step 1: Initialize the world dimensions. */
        /* These values should also be passed in the DesktopLauncher's config width and height. */
        final int worldWidth = 700;
        final int worldHeight = 300;
        WorldDimensions.set(worldWidth, worldHeight);
        
        /* Step 2: Initialize the StateManager with a Camera and a Viewport. */
        /* In this example, an ExtendedViewport is used. */
        final Camera camera = new OrthographicCamera(worldWidth, worldHeight);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0F);
        final Viewport viewport = new ExtendViewport(worldWidth, worldHeight, camera);
        StateManager.init(camera, viewport);
        
        /* Step 3: Create and set an initial state, that will be shown when the app launches. */
        /* Here a TestState is used - it's just an empty state that doesn't do/show anything at all. */
        final State myTestState = new TestState();
        StateManager.getInstance().setState(myTestState);
    }
    
    @Override
    public void render() {
        Gdx.gl.glClearColor(200 / 255f, 200 / 255f, 200 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        StateManager.getInstance().update(Gdx.graphics.getDeltaTime());
        StateManager.getInstance().render();
    }
    
    @Override
    public void resize(int width, int height) {
        StateManager.getInstance().resize(width, height);
    }
    
    @Override
    public void pause() {
        StateManager.getInstance().pause();
    }
    
    @Override
    public void resume() {
        StateManager.getInstance().resume();
    }
    
    @Override
    public void dispose() {
        StateManager.getInstance().dispose();
    }
}
```

When we run our desktop app with this ```App``` class as our root class, we can see the result so far:
![image](https://i.gyazo.com/560887c85670292227a49bba19cd88a1.png)











