package example.source;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import example.source.state.MenuState;
import source.ImageManager.ImageManager;
import source.State.StateManager;

public class App extends ApplicationAdapter {

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
        final Viewport viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        StateManager.init(camera, viewport, WORLD_WIDTH, WORLD_HEIGHT);

        /*
        Step 2.5: Optionally load the assets here.
        You may prefer to load them somewhere else, or maybe lazily.
        */
        ImageManager.loadAtlas("NormalPack", "NormalPack.pack", true);
        ImageManager.loadAtlas("HeroWalkingPack", "HeroWalkingPack.pack");

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
