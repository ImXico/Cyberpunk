package source.extensions.Physics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author Xico
 */

public class PhysicsDebugger {

    private final Box2DDebugRenderer renderer;

    /**
     * As long as {@link PhysicsDebugger#resize(int, int)} gets called, the b2dCamera + b2dViewport will
     * keep the display working as intended, even after resize events.
     */
    private final OrthographicCamera b2dCamera;
    private final Viewport b2dViewport;

    public PhysicsDebugger(int worldWidth, int worldHeight) {
        renderer = new Box2DDebugRenderer();
        final float vpWidth = Utils.toB2DUnits(worldWidth);
        final float vpHeight = Utils.toB2DUnits(worldHeight);
        b2dCamera = new OrthographicCamera();
        b2dCamera.setToOrtho(false, vpWidth, vpHeight);
        b2dViewport = new ExtendViewport(vpWidth, vpHeight, b2dCamera);
    }

    /**
     * This is crucial to update the [b2dViewport] when a resize event occurs.
     *
     * @param width new width.
     * @param height new height.
     */
    void resize(int width, int height) {
        b2dViewport.update(width, height);
    }

    void render(PhysicsWorld physicsWorld) {
        renderer.render(physicsWorld.getWorld(), b2dCamera.combined);
    }
}

