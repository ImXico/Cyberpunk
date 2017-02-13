package source.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.viewport.Viewport;

import source.Camera.CameraStyles;
import source.Transition.Transition;

/**
 * Manages the {@link State}s.
 * Holds:
 * .A {@link Camera}.
 * .A {@link Viewport}.
 *
 * @author Xico
 */

public class StateManager {

    private static StateManager instance = null;

    /**
     * The currently running {@link State}. May be {@code null}.
     */
    private State currentState;

    /**
     * The next {@link State}.
     * The only way this is not {@code null} is if there's call to {@link StateManager#setState(State, Transition)}
     * with a non {@code null} {@link Transition}.
     */
    private State nextState;

    /**
     * {@link source.Transition.Transition} used to go from one state to another. May be {@code null}.
     */
    private Transition transition;

    /**
     * FBOs for the {@link Transition}.
     */
    private FrameBuffer currentFBO;
    private FrameBuffer nextFBO;

    /**
     * Batch that's used to render everything.
     */
    private final Batch batch;

    /**
     * Game's camera - should remain immutable.
     * To modify the behavior of the camera, use the {@link CameraStyles} API.
     */
    private final Camera camera;

    /**
     * Game's viewport - manages the {@link Camera} and should also remain immutable.
     */
    private final Viewport viewport;

    /**
     * These two fields will be used in the transitions' code, as the transitions between states are
     * still part of the core.
     * This way there won't be the need to explicitly pass the world coordinates when using transitions.
     */
    private final int worldWidth;
    private final int worldHeight;

    /**
     * Constructor for the {@link StateManager}.
     *
     * @param camera      camera that will use a virtual resolution.
     * @param viewport    viewport that will adapt the game screen to the different physical devices.
     * @param worldWidth  virtual world width - used in the {@link Transition}s.
     * @param worldHeight virtual world height - used in the {@link Transition}s.
     * @see Pixmap.Format
     */
    private StateManager(Camera camera, Viewport viewport, int worldWidth, int worldHeight) {
        /* Initialize both states as null. */
        currentState = null;
        transition = null;
        /* Initialize the FBOs. */
        final int width = Gdx.graphics.getWidth();
        final int height = Gdx.graphics.getHeight();
        currentFBO = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
        nextFBO = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
        /* Initialize the viewport and respective camera. */
        this.camera = camera;
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        this.viewport = viewport;
        /* Initialize the world coordinates. */
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        /* Initialize the batch. */
        batch = new SpriteBatch();
    }

    /**
     * Call this once, in the beggining of the app.
     *
     * @param camera      camera that will use a virtual resolution.
     * @param viewport    viewport that will adapt the game screen to the different physical devices.
     * @param worldWidth  virtual world width - used in the {@link Transition}s.
     * @param worldHeight virtual world height - used in the {@link Transition}s.
     */
    public static void init(Camera camera, Viewport viewport, int worldWidth, int worldHeight) {
        instance = new StateManager(camera, viewport, worldWidth, worldHeight);
    }

    public static StateManager getInstance() {
        return instance;
    }

    public Camera getCamera() {
        return camera;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    /**
     * Sets the currently running {@link State} with a {@link source.Transition}.
     *
     * @param state      new current state.
     * @param transition transition used to go from the previous state to the next one.
     */
    public void setState(State state, Transition transition) {
        if (currentState == null) {
            currentState = state;
            Gdx.input.setInputProcessor(currentState);
        } else {
            /* Disable any input for when the transition is running. */
            Gdx.input.setInputProcessor(null);
            nextState = state;
            this.transition = transition;
            transition.start();
        }
        this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * Sets the currently running {@link State}.
     * No transition from the current state to the next state.
     *
     * @param state new current state.
     */
    public void setState(State state) {
        if (currentState != null) currentState.hide();
        currentState = state;
        if (state != null) {
            Gdx.input.setInputProcessor(state);
            this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    /**
     * Updates the current {@link State}, if there is one.
     * Also updates any on-going {@link source.Transition.Transition}.
     *
     * @param delta time elapsed between this moment and the last update call.
     */
    public void update(float delta) {
        if (currentState != null) currentState.update(delta);
        if (transition != null) {
            if (transition.isRunning()) {
                transition.update(delta);
            }
        }
    }

    /**
     * Handles rendering within three case scenarios:
     * 1. No next state.
     * 2. Transition was on-going but just completed.
     * 3. Transition is on-going.
     */
    public void render() {
        /* If there's only one screen - thus, no transition, just render it. */
        if (nextState == null) {
            currentState.render(batch);
        } else {
            if (transition.isCompleted()) {
                /* Set the current state to the next state. */
                currentState = nextState;
                /* Next state doesn't exist anymore. */
                nextState = null;
                transition.finish();
                /* Render the next (now, current!) state normally. */
                currentState.render(batch);
                Gdx.input.setInputProcessor(currentState);
            } else {
                /* Render the current state onto the current FBO. */
                currentFBO.begin();
                currentState.render(batch);
                currentFBO.end();
                /* Render the next state onto the next FBO. */
                nextFBO.begin();
                nextState.render(batch);
                nextFBO.end();
                final TextureRegion currentFlippedRegion = new TextureRegion(currentFBO.getColorBufferTexture());
                currentFlippedRegion.flip(false, true);
                final TextureRegion nextFlippedRegion = new TextureRegion(nextFBO.getColorBufferTexture());
                nextFlippedRegion.flip(false, true);
                transition.render(batch, currentFlippedRegion, nextFlippedRegion);
            }
        }
    }

    /**
     * Resizes the currently & next {@link State} & this class's {@link Viewport}.
     * Here, the FBOs are disposed manually and re-defined.
     *
     * @param width  new width.
     * @param height new height.
     */
    public void resize(int width, int height) {
        viewport.update(width, height);
        batch.setProjectionMatrix(camera.combined);
        if (currentState != null) currentState.resize(width, height);
        if (nextState != null) nextState.resize(width, height);
        /* Dispose the FBOs. */
        currentFBO.dispose();
        nextFBO.dispose();
        /* Re-define the FBOs. */
        currentFBO = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
        nextFBO = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
    }

    /**
     * Pauses the current & next {@link State}.
     */
    public void pause() {
        if (currentState != null) currentState.pause();
        if (nextState != null) nextState.pause();
    }

    /**
     * Resumes the current & next {@link State}.
     */
    public void resume() {
        if (currentState != null) currentState.resume();
        if (nextState != null) nextState.resume();
    }

    /**
     * Disposes the current & next {@link State} and {@link FrameBuffer}.
     */
    public void dispose() {
        if (currentState != null) currentState.dispose();
        if (nextState != null) nextState.dispose();
        /* Dispose the FBOs. */
        currentFBO.dispose();
        nextFBO.dispose();
    }
}
