package source.Transition;

import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import source.State.StateManager;

/**
 * @author Xico
 */

public interface Transition {

    int WORLD_WIDTH = StateManager.getInstance().getWorldWidth();
    int WORLD_HEIGHT = StateManager.getInstance().getWorldHeight();

    /**
     * Starts the transition.
     */
    void start();

    /**
     * @return {@code true} if the transition is running, {@code false} otherwise.
     */
    boolean isRunning();

    /**
     * @return {@code true} if the transition has finished, {@code false} otherwise.
     */
    boolean isCompleted();

    /**
     * Finishes the transition.
     */
    void finish();

    /**
     * Updates the transition.
     *
     * @param delta - time elapsed since the last call to update.
     */
    void update(float delta);

    /**
     * Renders the current and next textures to the given {@link Batch}.
     * These textures were obtained using the Render-To-Texture (RTT) technique.
     * A {@link TextureRegion} is used instead of a {@link Texture} because the framebuffer is upside
     * down to normal textures, ...
     * ...as seen here: <a href="http://www.gamefromscratch.com/post/2014/01/21/Rendering-a-3D-model-to-texture-in-LibGDX.aspx"></a>
     *
     * @param batch   {@link Batch} used to render.
     * @param current current (or previous?) screen's {@link TextureRegion}, rendered onto a {@link FrameBuffer}.
     * @param next    next screen's {@link TextureRegion}, rendered onto a {@link FrameBuffer}.
     * @see <a href="http://apprize.info/programming/libgdx/10.html">Transitions</a>
     */
    void render(Batch batch, TextureRegion current, TextureRegion next);

}
