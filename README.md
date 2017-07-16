<p align="center">
  <img src="https://github.com/ImXico/handy-gdx/blob/master/logo.png" />
</p>

<p align="center">
  <img src="https://img.shields.io/badge/made%20with-love-red.svg">
  <img src="https://img.shields.io/badge/java-100%25-orange.svg">
  <a href="https://github.com/ImXico/HandyGDX/blob/master/LICENSE.md"><img src="https://img.shields.io/github/license/mashape/apistatus.svg"></a>
</p>

> 🎲 A bunch of libraries for libGDX development.

Handy is a collection of independent, pure-Java libraries to use on top of [libGDX](http://libgdx.badlogicgames.com/), aiming to speed up development and  be a useful resource for both experienced developers and newcomers.

---

## IMPORTANT - as of June, 30th

#### I. General API Rebuild
In the upcoming time, a big portion of this project will be essentially rebuilt from the ground up. This means that every single API will be reviewed, modified and have new additions to it.

#### II. Migration to Kotlin, but with Java in mind
I'll be porting the source code over to [Kotlin](https://kotlinlang.org/). I'm well aware that the majority of the LibGDX community uses Java - this is quite a big change, but I most definitely think it's for the best. I've been a big fan of the language for a while, and [seeing it mature](https://blog.jetbrains.com/kotlin/2017/05/kotlin-on-android-now-official/) this much made me think that this is the time to do it. If you haven't taken a look at it already I'd *strongly* suggest you doing so. But even with Java, you'll be granted total access to the whole API:

- Kotlin developers can profit directly from all of the awesome features of the language, like [these](https://frozenfractal.com/blog/2017/2/10/10-cool-things-about-kotlin/).
- Java developers will still be able to use **100% of the API**, thanks to the awesome [Java-Kotlin Interoperability!](https://kotlinlang.org/docs/reference/java-to-kotlin-interop.html)

There will also be **complete, working example mock projects** written in both languages. 

#### IV. Better Documentation
It's also planned to get a much better documentation with a dedicated wiki.

#### III. Many new features!
With these updates there will be many new features, focusing on fields such as screen flow, transitions, GUI elements and general utilities.

---

## Structure
The project is divided into two parts: core and extensions.
- The **core** is the base structure - it handles states, how they are managed and the base application. It's layed out intuitively, and is useful to get you up and running quickly, without having to deal with these aspects yourself.
- The **extensions** are the libraries and mini-libraries that you can fetch as you need. From asset managers to aesthetic utilities, it ranges between a (hopefully increasing) number of different fields.

Every extension library is **totally independent** of the core, meaning that you can use any extension without having used
the core in your project. The extensions themselves are also independent of each other - *handy*, huh?

## Contents
Each section here will have a small overview, and some will have examples.
A very simple example project (that will contain most of the topics below in action) can be found [here](https://github.com/ImXico/handy-gdx/tree/master/example). It also contains the assets used and a small walkthrough.

### [I. Core](#core)
- [State Management](#state-management)
- [World Coordinates](#world-coordinates)
- [Base App](#base-app)

### [II. Extensions](#extensions)
- [Image Manager](#image-manager)
- [Audio Manager](#audio-manager)
- [Camera Styles](#camera-styles)
- [Text Helper](#text-helper)
- [Sprite Helper](#sprite-helper)
- [Physics](#physics)

## Core
## State Management
**States** are, in some aspects, similar to Stages in [Scene2D](https://github.com/libgdx/libgdx/wiki/Scene2d).
All states should be capable of:
- Handling input (like touches and key presses).
- Updating their inner logic.
- Rendering their internal components.
- Disposing resources when they're not needed anymore.

**AbstractState** is a convenience implementation of the State interface, and every concrete state that you'd implement would inherit from it. Thus, every concrete state will necessarily implement **atleast** the following methods:

```java
#update(delta)
#render(batch)
#dispose()
```

**StateManager** is an entity that manages the states flow. You have global access to it, making it easy to use.
```java
#init(camera, viewport)       // Initializes the StateManager - call this once.
#getInstance()                // This is the way to access the StateManager (only after `#init` was called).
#setState(state)              // Instantly (abruptely) changes the current state to a given next state.
#setState(state, transition)  // Smoothly change the current state with a transition.
#update(delta)                // Updates the currently running state and any existant on-going transition.
#render()                     // Renders the currenly running state and any existant on-going transition.
#resize(width, height)        // Resizes the current + next state and the Viewport (passed on #init).
#dispose()                    // Diposes the current (and also next, if it exists) state(s).
```

**State transitions** make up for a smoother UX - instead of changing states abruptely, you can use a transition:

`FadingTransition` - The screen fades from one state to another.

![gid](https://zippy.gfycat.com/GlamorousExhaustedFrilledlizard.gif)

`HorizontalSlideTransition`: Left-to-Right or Right-to-Left. Watch in 60 fps [here](https://gfycat.com/HiddenTartIzuthrush).

![gif](https://zippy.gfycat.com/HiddenTartIzuthrush.gif)

Thankfully, it's easy to make your own transitions - just make sure you implement the [Transition](https://github.com/ImXico/HandyGDX/blob/master/source/Transition/Transition.java) interface!

## World Coordinates
To keep a consistent app that's independent of real device size or asset size, it's a good idea to:
- Define a virtual resolution (here it's called world dimensions), that can be whatever you want.
- Set a [Camera](https://github.com/libgdx/libgdx/wiki/Orthographic-camera) to use that resolution.
- Use a [Viewport](https://github.com/libgdx/libgdx/wiki/Viewports) to adapt the game screen to the different physical devices.

These virtual dimensions will, thus, be the *boundaries* of our game screen - they'll influence all of the game's rendering.
Because of this, we will have to define a `WORLD_WIDTH` and `WORLD_HEIGHT` somewhere - this will be done in the next section, the [Base App](#base-app).

## Base App
This is the final element of the core - our root/base class - the one that extends [ApplicationAdapter](https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/ApplicationAdapter.html). More on the life-cycle of a libGDX app can be seen [here](https://github.com/libgdx/libgdx/wiki/The-life-cycle). Now that we know about world coordinates, states and how the state manager controls how states flow, we can easily set it up very easily.

Start by defining the world coordinates - making them public will let us use them over at the `desktop config`.
```java
public class App extends ApplicationAdapter {
    public static final int WORLD_WIDTH = 700;
    public static final int WORLD_HEIGHT = 300;
```

Next up, initialize the `StateManager`, passing it a `Camera` and a `Viewport`.
```java
@Override 
public void create() {
    final Camera camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
    camera.pos1.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
    
    final Viewport viewport = new ExtendViewport(WORLD_HEIGHT, WORLD_HEIGHT, camera);
    StateManager.init(camera, viewport, WORLD_WIDTH, WORLD_HEIGHT);
    ...
```

Create and set an initial `state` that will be shown when the app launches.
```java
    ...
    final State myTestState = new TestState();
    StateManager.getInstance().setState(myTestState);
}
```

The `#render` method can come next!
```java
@Override
public void render() {
    Gdx.gl.glClearColor(200 / 255f, 200 / 255f, 200 / 255f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    StateManager.getInstance().update(Gdx.graphics.getDeltaTime());
    StateManager.getInstance().render();
}
```

Implementing the `#resize`, `#pause`, `#resume` and `#dispose` methods.
```java
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
```

## Extensions
## Image Manager
This is made to be used with [TextureAtlases](https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/graphics/g2d/TextureAtlas.html). Information on how to use the TexturePacker to pack many smaller images onto larger images can be found [here](https://github.com/libgdx/libgdx/wiki/Texture-packer).

Say we've got `myPack.png` and `myPack.pack` from the Texture Packer inside our `assets` folder:
```java
#loadAtlas("myPack", "myPack.pack")         // Loads "myPack.pack" onto the app and keys it as "myPack".
#loadAtlas("myPack", "myPack.pack", true)   // Loading + setting it as the default atlas.
#getAtlas("myPack")                         // Fetching the "myPack" atlas.
#take("regionName")                         // Getting a region that's inside the default atlas.
#take("regionName", "otherAtlasKey")        // Getting a region that's inside another (loaded) atlas.
```

*Note*: When it comes to assets, alongside the classes above, it's a good idea to mark all assets' names with constants. It's common to use the same assets in multiple parts of the project - saving their names/paths in constants will make them alot more bearable to change around. This is done in the example project.

## Audio Manager
Say we've got ourselves a sound file called `beep.ogg` located at `assets/sounds`. With the [AudioManager](https://github.com/ImXico/HandyGDX/blob/master/source/AudioManager/AudioManager.java) we can:
```java
#loadSong("beep", "sounds/beep.ogg")    // Loads "beep.ogg" and keys it as "beep".
#playSound("beep")                      // Plays the "beep" sound at a default volume.
#playSound("beep", 0.3f)                // Plays the "beep" sound at volume = 0.3.
#stopSound("beep")                      // Stops the "beep" sound.
#pauseSound("beep")                     // Pauses the "beep" sound.
#resumeSound("beep")                    // Resumes the "beep" sound.
#loopSound("beep")                      // Loops the "beep" sound at a default volume.
#loopSound("beep", 0.7f)                // Loops the "beep" sound at volume = 0.7.
```

## Camera Styles
There are a few camera styles available on-the-go:

`CameraStyles.centerOnScreen(camera)` - Watch in 60 fps [here](https://gfycat.com/GiantPowerfulGoa).

![gif](https://zippy.gfycat.com/GiantPowerfulGoa.gif)

`CameraStyles.lockOnTarget(camera, targetPosition)` - Watch in 60 fps [here](https://gfycat.com/HauntingCleverBarracuda).

![gif](https://zippy.gfycat.com/HauntingCleverBarracuda.gif)

`CameraStyles.lerpToTarget(camera, target, lerp)` - Watch in 60 fps [here](https://gfycat.com/DirtyInbornIberianbarbel).

![gif](https://fat.gfycat.com/DirtyInbornIberianbarbel.gif)

*Note*: The assets used here and in the example project are from [Kenney Assets](http://kenney.nl/assets). They are also available on the [example's assets folder](https://github.com/ImXico/handy-gdx/tree/master/example/assets).

## Text Helper
A bunch of helper functions to help you pos1 your text on the screen. All of these methods return a `Vector2` object, which you will then use to draw the text.
```java
#centerHorizontally(font, text, y, App.WORLD_WIDTH)
#centerOnScreen(font, text, App.WORLD_WIDTH, App.WORLD_HEIGHT)
#centerOnImage(font, text, imageWidth, imageHeight, imagePosition)
```

![img](https://i.gyazo.com/da82a22d207f6c07785f65026efff612.png)

## Sprite Helper
A bunch of helper functions to help you pos1 your sprites on the screen. Same as the text helper, these methods return `Vector2` coordinates.
```java
#centerOnScreen(imageWidth, imageHeight, App.WORLD_WIDTH, App.WORLD_HEIGHT)
#centerOnScreenX(imageWidth, y, App.WORLD_WIDTH)
#centerOnScreenY(imageHeight, x, App.WORLD_HEIGHT)
#centerOnImage(width, height, imagePosition, imageWidth, imageHeight)
```

![img](https://i.gyazo.com/b2b826b4cd321b6aa03a3cf97c36aa6b.png)

## Physics
The physics extension is a small wrapper around [libGDX' Box2D](https://github.com/libgdx/libgdx/wiki/Box2d) - thus, it requires having the Box2D dependency in the project. It is (currently) composed of the following components: 
 - [Utils](https://github.com/ImXico/HandyGDX/blob/master/source/extensions/Physics/Utils.java)
 - [Physics Debugger](https://github.com/ImXico/HandyGDX/blob/master/source/extensions/Physics/PhysicsDebugger.java)
 - [Physics World](https://github.com/ImXico/HandyGDX/blob/master/source/extensions/Physics/PhysicsWorld.java)
 - [Body Builder](https://github.com/ImXico/HandyGDX/blob/master/source/extensions/Physics/BodyBuilder.java)
 - [Body Def Builder](https://github.com/ImXico/HandyGDX/blob/master/source/extensions/Physics/BodyDefBuilder.java)
 - [Fixture Def Builder](https://github.com/ImXico/HandyGDX/blob/master/source/extensions/Physics/FixtureDefBuilder.java)

**Utils** contains utility conversion methods: from Box2D units to pixels and from pixels to Box2D units.

By using a pixels-to-meters conversion metric, we can **keep using pixel units in methods**, which is pretty useful. These conversions are done under the hood, meaning that you *probably* won't need to call any of this class' methods.

**PhysicsDebugger** is used to debug a given physics world (seen below), and is essentially a wrapper around [Box2DDebugRenderer](https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/physics/box2d/Box2DDebugRenderer.html).

Besides having an instance of Box2DDebugRenderer and calling its render method, the PhysicsDebugger also contains a dedicated [Camera](https://github.com/libgdx/libgdx/wiki/Orthographic-camera) and [Viewport](https://github.com/libgdx/libgdx/wiki/Viewports), which means that the debug renderer will still look good even after resize events.

**PhysicsWorld** encapsulates a [Box2D World](https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/physics/box2d/World.html). 

By calling `setDebugging(boolean)`, you can toggle whether or not you want a PhysicsDebugger to debug your world. Usually you'll want to have the `debugging` flag set to `false` on a released version of your app, as having the debugger running can be a bit CPU-heavy, especially on mobile devices.

**Body Builder** lets you build a customizable body, with one body def and one or more fixture defs.

It depends on two other builders: the BodyDefBuilder, that lets you build a customizable [BodyDef](https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/physics/box2d/BodyDef.html) and the FixtureDefBuilder, that lets you build a customizable [FixtureDef](https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/physics/box2d/FixtureDef.html).

It's important to note that the `BodyBuilder`'s constructor takes in a `PhysicsWorld` - this means that all bodies created with a given instance of `BodyBuilder` will be hosted in that `PhysicsWorld`. it is possible to change the current world by calling `setPhysicsWorld(physicsWorld)`.

This is how, in Box2D, we'd generally create a circle-shaped body, given a pos1, radius and restitution:

**Without** the body builder:

```java
// Create a body def...
final BodyDef bodyDef = new BodyDef();
bodyDef.type = BodyDef.BodyType.DyamicBody;
bodyDef.pos1 = some pos1

// Create a shape...
final CircleShape circleShape = new CircleShape();
circleShape.radius = some radius;

// Create a fixture def...
final FixtureDef fixtureDef = new FixtureDef();
fixtureDef.restitution = some restitution;

// Attach the shape to the fixture def...
fixtureDef.shape = circleShape;

// Create a body...
final Body myBody = world.createBody(bodyDef);
myBody.createFixture(fixtureDef);

// Dispose the shape...
circleShape.dispose();
```

**With** the body builder:

```java
myBody = bodyBuilder
            .withBodyDef(new BodyDefBuilder()
                .pos1(some pos1)
                .type(BodyDef.BodyType.DyamicBody))
            .withFixtureDef(new FixtureDefBuilder()
                .circleShape(some radius)
                .restitution(some restitution))
            .build();
```
This feels a lot more compact, and gives you (*almost!* - see below) the same level of customization of the first version!

*Note:* The physics library is still WIP - it's functional enough to be used for simple Box2D creations, but it's still lacking some functionality.

## Contributions
Feel free to use, ask, fork, star, report bugs, fix them, suggest enhancements, add functionality and point out any mistakes. Thanks!

## Authors
Developed and maintained by [Xico](https://github.com/ImXico) and other kind [contributors](https://github.com/ImXico/handy-gdx/graphs/contributors).

Released under the [MIT License](https://github.com/ImXico/HandyGDX/blob/master/LICENSE.md).
