# HandyGDX

A bunch of libraries for [libGDX](http://libgdx.badlogicgames.com/).

## Features
Navigate through the different sections with these links; each one will have a small overview, and some will have examples.
A very simple example project (that will contain most of the topics below in action) can be found [here](https://github.com/ImXico/HandyGDX/tree/master/example).

*Note:* There will be lots of references to the [official libGDX wiki](https://github.com/libgdx/libgdx). It is really complete and well-written, and there's also loads of documentation over its various APIs.

- [World Dimensions](#world-dimensions)
- [Resource Management](#resource-management)
  - [Image Manager](#image-manager)
  - [Audio Manager](#audio-manager)
- [State Management](#state-management)
  - [State](#state)
  - [Abstract State](#abstract-state)
  - [State Manager](#state-manager)
  - [Root App](#root-app)
  - [State Transitions](#state-transitions)
- [Camera Helper](#camera-helper)
- [Text Helper](#text-helper)
- [Sprite Helper](#sprite-helper)

--

### World Dimensions
To keep a consistent app that's independent of real device size or asset size, it's a good idea to:
- Define a virtual resolution (here it's called world dimensions) that can be whatever you want.
- Set a [Camera](https://github.com/libgdx/libgdx/wiki/Orthographic-camera) to use that resolution.
- Use a [Viewport](https://github.com/libgdx/libgdx/wiki/Viewports) to adapt the game screen to the different physical devices.

These virtual dimensions will, thus, be the *boundaries* of our game screen - they'll influence all of the game's rendering.
Because they'll be used in a lot of places (including on **many** other libs here!), an easy way to store & access them is by doing something like this:

```java
public class App extends ApplicationAdapter {

    @Override
    public void create() {
        /*
        First thing to do here!
        These values should also be passed in the DesktopLauncher's config width and height.
        */
        final int worldWidth = 700;
        final int worldHeight = 300;
        WorldDimensions.set(worldWidth, worldHeight);
        // ...
    }
}
```

### Resource Management
Keeping your assets organized.
#### Image Manager
This is made to be used with [TextureAtlases](https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/graphics/g2d/TextureAtlas.html). Information on how to use the TexturePacker to pack many smaller images onto larger images can be found [here](https://github.com/libgdx/libgdx/wiki/Texture-packer).

Say we've got `myPack.png` and `myPack.pack` from the Texture Packer inside our `assets` folder - we'll use that to explore this tiny API.

- `ImageManager.loadAtlas("myPack", "myPack.pack")` - Loads "myPack.pack" onto the app and keys it as "myPack".
- `ImageManager.loadAtlas("myPack", "myPack.pack", true)` - Loading + setting it as the default atlas.
- `ImageManager.getAtlas("myPack")` - Getting the "myPack" atlas.
- `ImageManager.take("regionName")` - Getting a region that's inside the default atlas.
- `ImageManager.take("regionName", "otherAtlasKey")` - Getting a region that's inside another (loaded) atlas.

That's it!

#### Audio Manager
Once again with an example, say we've got ourselves a sound file called `beep.ogg` located at `assets/sounds`.
With the [AudioManager](https://github.com/ImXico/HandyGDX/blob/master/source/AudioManager/AudioManager.java) we can:

- `AudioManager.loadSong("sounds/beep.mp3", "beep")` - Loads "beep.mp3" and keys it as "beep".
- `AudioManager.playSound("beep")` - Plays the "beep" sound at a default volume.
- `AudioManager.playSound("beep", 0.3f)` - Plays the "beep" sound at volume = 0.3f.
- `AudioManager.stopSound("beep")` - Stops the "beep" sound.
- `AudioManager.pauseSound("beep")` - Pauses the "beep" sound.
- `AudioManager.resumeSound("beep")` - Resumes the "beep" sound.
- `AudioManager.loopSound("beep")` - Loops the "beep" sound at a default volume.
- `AudioManager.loopSound("beep", 0.7f)` - Loops the "beep" sound at volume = 0.7f.

That's all...!

--

*Note: When it comes to assets, alongside the classes above, I'd recommend marking all assets' names with constants. It's common to use the same assets in multiple parts of the project - saving their names/paths in constants will make them alot more bearable to change around.*

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

A quick overview of the [StateManager](https://github.com/ImXico/HandyGDX/blob/master/source/State/StateManager.java) - it is a fairly important entity, and thus more detailed documentation can be found on the source):

- `static void init(Camera camera, Viewport viewport)` - Initializes the StateManager - call this once.
- `static StateManager getInstance()` - This is the way to access the StateManager (only after init(...) was called).
- `void setState(State nextState)` - Instantly change the current state to a given next state.
- `void setState(State nextState, Transition transition)` - Smoothly change the current state with a transition.
- `void update(float delta)` - Updates the currently running state and and any on-going transition, if there is one.
- `void render()` - Renders the currenly running state and any on-going transition, if there is one.
- `void resize(int width, int height)` -  Resizes the current + next state and the Viewport (passed on init(...)).
- `void dispose()` - Diposes the current (and also next, if it exists) state.

#### Root App
Now that we know how to load assets, how states work, and how the state manager controls the state flow, we can use them to easily setup our root/base class - the one that extends [ApplicationAdapter](https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/ApplicationAdapter.html).

More on the root class and the life cycle of a libGDX app can be seen [here](https://github.com/libgdx/libgdx/wiki/The-life-cycle).

This is how it generally will look like, using the States + StateManager approach:

````java
// ... imports ...

public class App extends ApplicationAdapter {

    @Override
    public void create() {
        /*
        Step 1: Initialize the world dimensions.
        These values should also be passed in the DesktopLauncher's config width and height.
        */
        final int worldWidth = 700;
        final int worldHeight = 300;
        WorldDimensions.set(worldWidth, worldHeight);
        
        /*
        Step 2: Initialize the StateManager with a Camera and a Viewport.
        In this example, an ExtendedViewport is used.
        */
        final Camera camera = new OrthographicCamera(worldWidth, worldHeight);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0F);
        final Viewport viewport = new ExtendViewport(worldWidth, worldHeight, camera);
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

When we run our desktop app with this ```App``` class as our root class, we can see the result so far:
![image](https://i.gyazo.com/560887c85670292227a49bba19cd88a1.png)











