# Example Walkthrough
Getting our basic app running on both desktop and android.

## Table of Contents

- [Part 1: Setting Up](#part-1-setting-up)
  - [Desktop Config](#desktop-config)
  - [Android Config](#android-config)
  - [Root App Setup](#root-app-setup)
- [Part 2: Loading Assets](#part-2-loading-assets)
- [Part 3: MenuState](#menu-state)
- [Part 4: PlayState](#play-state)

--

### Part 1: Setting up
We won't dive into much detail here; check out the [official docs](https://github.com/libgdx/libgdx/wiki/Starter-classes-&-configuration) if you're interested.
#### Desktop Config

All we have to do in the `DesktopLauncher` is set up the following:
- `config.width` - Should be the same as `WorldDimensions.WORLD_WIDTH`
- `config.height` - Should be the same as `WorldDimensions.WORLD_HEIGHT`

Optionally, give it a `config.title`.

There are a lot more configuration settings - please refer to the [libGDX documentation](https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/backends/lwjgl/LwjglApplicationConfiguration.html).

```java
package com.handy.desktop;

// ... imports ...

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 700;
        config.height = 300;
        config.title = "Handy!";
        new LwjglApplication(new App(), config);
    }
}
```

#### Android Config
Once again nothing complicated, the key points here would be:
- Pass our root class `App` to as the one class implementing the program logic.
- Force `landscape` mode, as this app is meant to be run in landscape.
- Optionally use `sticky immersion` - see more about it [here](https://developer.android.com/training/system-ui/immersive.html).

```java
package com.handy.android;

// ... imports ...

public class AndroidLauncher extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Delete this line if you don't want immersive fullscreen mode. */
        this.setImmersiveFullscreen();
        /* Force landscape mode for this example. */
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new App(), config);
    }

    /**
     * Devices running on API 19+ can use Immersive Full-Screen Mode.
     *
     * @see <a href="https://developer.android.com/training/system-ui/immersive.html">Immersive Mode</a>
     */
    private void setImmersiveFullscreen() {
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
```

#### Root App Setup
This is the last thing we need to get our app 100% ready - setting up our `App` class.
This is out it would generally look like.

```java
// ... imports ...

public class App extends ApplicationAdapter {

    @Override
    public void create() {
        /* Setting the world dimensions. */
        final int worldWidth = 700;
        final int worldHeight = 300;
        WorldDimensions.set(worldWidth, worldHeight);
        
        /* Initializing the StateManager with a camera + viewport. */
        final Camera camera = new OrthographicCamera(worldWidth, worldHeight);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        final Viewport viewport = new ExtendViewport(worldWidth, worldHeight, camera);
        StateManager.init(camera, viewport);
        
        /* The MenuState doesn't exist yet - we will make it later! */
        StateManager.getInstance().setState(new MenuState());
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

### Part 2: Loading Assets
Hey, this is going to be quick!
With the help of [Kenney Assets](http://kenney.nl/assets) and [TexturePacker](https://github.com/libgdx/libgdx/wiki/Texture-packer) we managed to get some cool assets,
that can be found [here](https://github.com/ImXico/HandyGDX/tree/master/example/assets).

Because this example is meant to be ran in both desktop and android, those assets are located under `android > assets`.

There's no specific place to load these up , but I tend to just load them on the `App`'s `create` method.
I'm honestly not sure on how much heavier resources would impact the startup performance, but I'd consider loading heavier resources *lazily* elsewhere, maybe.

Anyway here's how one would load our tetxure atlases:

```java

// On the App class

@Override
public void create() {
    // ...
    
    // Load "NormalPack.pack", key it as "NormalPack" and set it as the default atlas.
    ImageManager.loadAtlas("NormalPack", "NormalPack.pack", true);
    // Load "HeroWalkingPack.pack", key it as "HeroWalkingPack".
    ImageManager.loadAtlas("HeroWalkingPack", "HeroWalkingPack.pack");
}

```

For this example there are no audio assets, but it would be simple - to load `beep.mp3` located at `android > assets > sounds`, one would
call `AudioManager.loadSound("beep", "sounds/beep.mp3")`.

Finally, a nice touch is to keep all our assets name references stored in constants somewhere.

We have this over on the [PackValues](https://github.com/ImXico/HandyGDX/blob/master/example/source/PackValues.java) class:

```java
/**
 * Here we store all of the packs' region names, so we easily swap them around.
 * One should refer to these constants instead of using the String names directly.
 */
public interface PackValues {

    /* NormalPack constants. */

    String NORMAL_PACK = "NormalPack";

    String TREE_1 = "tree1";
    String TREE_2 = "tree2";
    String HERO_IDLE = "adventurer_stand";
    String ALIEN_GREEN = "alien-green";
    String ALIEN_BLUE = "alien-blue";
    String ALIEN_BEIGE = "alien-beige";
    String ALIEN_PINK = "alien-pink";
    String BLANK_QUAD = "blank-quad";


    /* HeroPack constants. */

    String HERO_WALKING_PACK = "HeroWalkingPack";

    String WALKING_1 = "adventurer_walk1";
    String WALKING_2 = "adventurer_walk2";
}
```
And we're set! Now we can start to actually create stuff.

### Part 3: Menu State

Our [MenuState](https://github.com/ImXico/HandyGDX/blob/master/example/source/state/MenuState.java) will be simple: 
- It will display some text on the screen.
- When you touch the screen, we will set the state to a new state - the `PlayState`, that we'll create next.

You can comment/uncomment the code at `touchDown` to try the different state transitions:
```java
@Override
public boolean touchDown(int x, int y, int pointer, int button) {
    /* Transition 1: Fading transition. */
    // final Transition transition = new FadingTransition();
    
    /* Transition 2: HorizontalSlide transition, left-to-right. */
    final Transition transition = new HorizontalSlideTransition(Motion.LEFT_TO_RIGHT, 0.2f);
    
    StateManager.getInstance().setState(new PlayState(), transition);
    return true;
}
```

### Part 4: Play State
On our [PlayState](https://github.com/ImXico/HandyGDX/blob/master/example/source/state/PlayState.java) we have:
- A hero that can run - see [Hero](https://github.com/ImXico/HandyGDX/blob/master/example/source/hero/Hero.java).
- Some trees in the background, because why not - see [Tree](https://github.com/ImXico/HandyGDX/blob/master/example/source/background/Tree.java) and [Background](https://github.com/ImXico/HandyGDX/blob/master/example/source/background/Background.java).
- Some ground for the hero to walk on - see [Ground](https://github.com/ImXico/HandyGDX/blob/master/example/source/background/Ground.java).

To change between `CameraStyles`, comment/uncomment the code down at `update`:
```java
@Override
public void update(float delta) {
    hero.update();
    /* Style 1: Center on screen. */
    // CameraStyles.centerOnScreen(StateManager.getInstance().getCamera(), WorldDimensions.WORLD_WIDTH, WorldDimensions.WORLD_HEIGHT);
    
    /* Style 2: Lock on target, ridigly. */
    // CameraStyles.lockOnTarget(StateManager.getInstance().getCamera(), hero.getPosition());
    
    /* Style 3: Smoothly lerp to target with a lerp amount = 0.075f. */
    CameraStyles.lerpToTarget(StateManager.getInstance().getCamera(), hero.getPosition(), 0.075f);
}
```
One important aspect of this is that when you're playing with the camera (via `CameraStyles`, in this case), you need to explicitly call
`batch.setProjectionMatrix(StateManager.getInstance().getCamera().combined);` on the `render` method - as the camera gets updates, you need
to update the batch's projection matrix aswell.
This is not needed in states where the camera is fixed (i.e. this example's `MenuState`).
