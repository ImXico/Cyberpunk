package example.source;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import example.source.state.MenuState;
import kot.core.state.StateManager;
import source.extensions.ImageManager.ImageManager;

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

        /*
        Step 2.5: Optionally load the assets here.
        You may prefer to load them somewhere else, or maybe lazily.
        */
        ImageManager.loadAtlas("NormalPack", "NormalPack.pack", true);
        ImageManager.loadAtlas("HeroWalkingPack", "HeroWalkingPack.pack");

        /* Step 3: Create and set the initial state. */
        StateManager.INSTANCE.setup(camera, viewport, new MenuState());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(200 / 255f, 200 / 255f, 200 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        StateManager.INSTANCE.update(Gdx.graphics.getDeltaTime());
        StateManager.INSTANCE.render();
    }

    @Override
    public void resize(int width, int height) {
        StateManager.INSTANCE.resize(width, height);
    }

    @Override
    public void pause() {
        StateManager.INSTANCE.pause();
    }

    @Override
    public void resume() {
        StateManager.INSTANCE.resume();
    }

    @Override
    public void dispose() {
        StateManager.INSTANCE.dispose();
    }
}
