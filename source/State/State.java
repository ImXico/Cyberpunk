package source.State;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

/**
 * A state can be a Play source.State, Game Over source.State, Menu source.State, and so on.
 *
 * @author Xico
 */

public interface State extends InputProcessor {

    /**
     * Updates the state.
     * This is called 60 times per second.
     *
     * @param delta time elapsed since the last call to this method.
     */
    void update(float delta);

    /**
     * Renders the state.
     * This is also called 60 times per second.
     * Always called before {@link State#update(float)}.
     *
     * @param batch {@link Batch} used to render the state.
     */
    void render(Batch batch);

    /**
     * Pauses the state.
     */
    void pause();

    /**
     * Resumes the state, after it being paused.
     */
    void resume();

    /**
     * Hides the state.
     */
    void hide();

    /**
     * Disposes the state and all of the state's {@link com.badlogic.gdx.utils.Disposable} components.
     */
    void dispose();

    /**
     * Resizes the state and any of its components.
     *
     * @param width  new width.
     * @param height new height.
     */
    void resize(int width, int height);

    /**
     * Unprojects screen coordinates into world coordinates.
     *
     * @param screenX X in screen coordinates.
     * @param screenY Y in screen coordinates.
     * @return world coordinates.
     */
    Vector2 unproject(float screenX, float screenY);

    /**
     * Unprojects screen coordinates into world coordinates.
     *
     * @param screenCoordinates (X, Y) screen coordinates.
     * @return world coordinates.
     */
    Vector2 unproject(Vector2 screenCoordinates);

}
