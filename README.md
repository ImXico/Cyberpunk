# HandyGDX
A bunch of libraries for [libGDX](http://libgdx.badlogicgames.com/), aiming to be a useful resource for both experienced developers and newcomers.

## How is HandyGDX structured?
Essentially in two parts:

- The **core** is the base structure of the app - it handles states, how they are managed and the base app. This is useful to get up and running quickly, without having to deal with these
aspects.

- The **extensions** are the libs and mini-libs that you can fetch as you need. From asset managers to aesthetic utilities, it ranges between a (hopefully increasing) number
of different fields. It's important to references that *every* extension lib is **totally independent** of the core, meaning that you can use any extension without having
the core.

## Table of Contents
Each section here will have a small overview, and some will have examples.
A very simple example project (that will contain most of the topics below in action) can be found [here](https://github.com/ImXico/HandyGDX/tree/master/example). It also contains the assets used and a small walkthrough.

**Note:** There will be references to the [official libGDX wiki](https://github.com/libgdx/libgdx). It is really complete and well-written, and there's also loads of documentation over its various APIs.

### The Core
- [State Management](#state-management)
  - [State](#state)
  - [Abstract State](#abstract-state)
  - [State Manager](#state-manager)
  - [State Transitions](#state-transitions)
- [World Coordinates](#world-coordinates)
- [Base App](#base-app)

### The Extensions
- [Image Manager](#image-manager)
- [Audio Manager](#audio-manager)
- [Camera Styles](#camera-styles)
- [Text Helper](#text-helper)
- [Sprite Helper](#sprite-helper)

--

## The Core
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
The [StateManager](https://github.com/ImXico/HandyGDX/blob/master/source/State/StateManager.java) is exactly that - an entity that manages the states flow. Because it is a singleton, there is global access to it, making it easy to use.

- `static void init(Camera camera, Viewport viewport)` - Initializes the StateManager - call this once.
- `static StateManager getInstance()` - This is the way to access the StateManager (only after init(...) was called).
- `void setState(State nextState)` - Instantly change the current state to a given next state.
- `void setState(State nextState, Transition transition)` - Smoothly change the current state with a transition.
- `void update(float delta)` - Updates the currently running state and and any on-going transition, if there is one.
- `void render()` - Renders the currenly running state and any on-going transition, if there is one.
- `void resize(int width, int height)` -  Resizes the current + next state and the Viewport (passed on init(...)).
- `void dispose()` - Diposes the current (and also next, if it exists) state.

#### State Transitions
Currently there are only two transitions available:

- `FadingTransition`

![gif](https://zippy.gfycat.com/GlamorousExhaustedFrilledlizard.gif)

- `HorizontalSlideTransition` - Left-to-Right or Right-to-Left. Watch in 60 fps [here](https://gfycat.com/HiddenTartIzuthrush).

![gif](https://zippy.gfycat.com/HiddenTartIzuthrush.gif)

But it's easy to make your own - just make sure you implement [Transition](https://github.com/ImXico/HandyGDX/blob/master/source/Transition/Transition.java)!

### World Coordinates
To keep a consistent app that's independent of real device size or asset size, it's a good idea to:
- Define a virtual resolution (here it's called world dimensions) that can be whatever you want.
- Set a [Camera](https://github.com/libgdx/libgdx/wiki/Orthographic-camera) to use that resolution.
- Use a [Viewport](https://github.com/libgdx/libgdx/wiki/Viewports) to adapt the game screen to the different physical devices.

These virtual dimensions will, thus, be the *boundaries* of our game screen - they'll influence all of the game's rendering.
Because of this, we will have to define a `WORLD_WIDTH` and `WORLD_HEIGHT` somewhere - this will be done in the next section, the [Base App](#base-app).

### Base App
This is the final element of the core - our root/base class - the one that extends [ApplicationAdapter](https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/ApplicationAdapter.html). More on the life-cycle of a libGDX app can be seen [here](https://github.com/libgdx/libgdx/wiki/The-life-cycle).

Now that we know about world coordinates, states, how the state manager controls the states flow, we can easily set it up.

````java
// ... imports ...

public class App extends ApplicationAdapter {

    /*
    Step 1: Defining the world coordinates.
    Make them public so you can pass them over at the desktop config.
    */
    public static final int WORLD_WIDTH = 700;
    public static final int WORLD_HEIGHT = 300;

    @Override
    public void create() {
        /*
        Step 2: Initialize the StateManager with a Camera and a Viewport.
        In this example, an ExtendedViewport is used.
        */
        final Camera camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        final Viewport viewport = new ExtendViewport(WORLD_HEIGHT, WORLD_HEIGHT, camera);
        StateManager.init(camera, viewport);
        
        /*
        Step 3: Create and set an initial state, that will be shown when the app launches.
        Here a TestState is used - it's just an empty state that doesn't do/show anything at all.
        */
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

## The Extensions
### Image Manager
This is made to be used with [TextureAtlases](https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/graphics/g2d/TextureAtlas.html). Information on how to use the TexturePacker to pack many smaller images onto larger images can be found [here](https://github.com/libgdx/libgdx/wiki/Texture-packer).

Say we've got `myPack.png` and `myPack.pack` from the Texture Packer inside our `assets` folder - we'll use that as an example.

- `ImageManager.loadAtlas("myPack", "myPack.pack")` - Loads "myPack.pack" onto the app and keys it as "myPack".
- `ImageManager.loadAtlas("myPack", "myPack.pack", true)` - Loading + setting it as the default atlas.
- `ImageManager.getAtlas("myPack")` - Getting the "myPack" atlas.
- `ImageManager.take("regionName")` - Getting a region that's inside the default atlas.
- `ImageManager.take("regionName", "otherAtlasKey")` - Getting a region that's inside another (loaded) atlas.

**Note**: When it comes to assets, alongside the classes above, I'd recommend marking all assets' names with constants. It's common to use the same assets in multiple parts of the project - saving their names/paths in constants will make them alot more bearable to change around. This is shown in the example project.

### Audio Manager
Once again with an example, say we've got ourselves a sound file called `beep.ogg` located at `assets/sounds`.
With the [AudioManager](https://github.com/ImXico/HandyGDX/blob/master/source/AudioManager/AudioManager.java) we can:

- `AudioManager.loadSong("beep", "sounds/beep.ogg")` - Loads "beep.ogg" and keys it as "beep".
- `AudioManager.playSound("beep")` - Plays the "beep" sound at a default volume.
- `AudioManager.playSound("beep", 0.3f)` - Plays the "beep" sound at volume = 0.3f.
- `AudioManager.stopSound("beep")` - Stops the "beep" sound.
- `AudioManager.pauseSound("beep")` - Pauses the "beep" sound.
- `AudioManager.resumeSound("beep")` - Resumes the "beep" sound.
- `AudioManager.loopSound("beep")` - Loops the "beep" sound at a default volume.
- `AudioManager.loopSound("beep", 0.7f)` - Loops the "beep" sound at volume = 0.7f.

### Camera Styles
There are a few camera styles available on-the-go. Here are a few:

- `CameraStyles.centerOnScreen(camera)` - Watch in 60 fps [here](https://gfycat.com/GiantPowerfulGoa).

![gif](https://zippy.gfycat.com/GiantPowerfulGoa.gif)

- `CameraStyles.lockOnTarget(camera, targetPosition)` - Watch in 60 fps [here](https://gfycat.com/HauntingCleverBarracuda).

![gif](https://zippy.gfycat.com/HauntingCleverBarracuda.gif)

- `CameraStyles.lerpToTarget(camera, target, lerp)` - Watch in 60 fps [here](https://gfycat.com/DirtyInbornIberianbarbel).

![gif](https://fat.gfycat.com/DirtyInbornIberianbarbel.gif)

**Note**: The assets used here and in the example project are from [Kenney Assets](http://kenney.nl/assets). They are also available on the [example's assets folder](https://github.com/ImXico/HandyGDX/tree/master/example/assets).

### Text Helper
A bunch of helper functions that return `Vector2` coordinates.

- `TextHelper.centerHorizontally(font, "Horizontally Centered Text, Y = 200", 200)`
- `TextHelper.centerOnScreen(font, "Centered Text!")`
- `TextHelper.centerOnImage(font, "Centered!", imageWidth, imageHeight, imagePosition)`

![img](https://i.gyazo.com/da82a22d207f6c07785f65026efff612.png)

### Sprite Helper
A bunch of helper functions that return `Vector2` coordinates.

- `SpriteHelper.centerOnScreen(imageWidth, imageHeight)`
- `SpriteHelper.centerOnScreenX(imageWidth, y)`
- `SpriteHelper.centerOnScreenY(imageHeight, x)`
- `SpriteHelper.centerOnImage(width, height, imagePosition, imageWidth, imageHeight)`

![img](https://i.gyazo.com/b2b826b4cd321b6aa03a3cf97c36aa6b.png)
