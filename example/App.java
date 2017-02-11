package example;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import example.state.MenuState;
import source.ImageManager.ImageManager;
import source.State.StateManager;
import source.WorldDimensions.WorldDimensions;

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
        Step 2.5: Optionally load the assets here.
        You may prefer to load them somewhere else, or maybe lazily.
        */
        ImageManager.loadAtlas("pack", "pack.pack", true);
        ImageManager.loadAtlas("heroFrames", "heroFrames.pack");

        /* Step 3: Create and set the initial state. */
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
