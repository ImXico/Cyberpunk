package source.extensions.Camera;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Holds a bunch of camera styles.
 *
 * @author Xico
 * @see com.badlogic.gdx.graphics.Camera
 */

public class CameraStyles {

    /**
     * Sets the {@link Camera} position to a new position and updates it.
     *
     * @param camera   camera used.
     * @param position new camera position.
     */
    private static void setAndUpdate(Camera camera, Vector3 position) {
        camera.position.set(position);
        camera.update();
    }

    /**
     * Centers the {@link Camera} in the middle of the screen.
     *
     * @param camera      camera used.
     * @param worldWidth  width of the world (world coordinates).
     * @param worldHeight height of the world (world coordinates).
     */
    public static void centerOnScreen(Camera camera, int worldWidth, int worldHeight) {
        final Vector3 position = camera.position;
        position.x = worldWidth / 2f;
        position.y = worldHeight / 2f;
        setAndUpdate(camera, position);
    }

    /**
     * The {@link Camera} will be 'stuck' to the target, following it rigidly.
     * Not suitable for all cases, as it will cause a rough motion.
     *
     * @param camera         camera used.
     * @param targetPosition position of the target.
     */
    public static void lockOnTarget(Camera camera, Vector2 targetPosition) {
        final Vector3 position = camera.position;
        position.x = targetPosition.x;
        position.y = targetPosition.y;
        setAndUpdate(camera, position);
    }

    /**
     * The {@link Camera} X will be exactly the same as the target's X, but the Y will be offset by a given amount.
     *
     * @param camera         camera used.
     * @param targetPosition position of the target.
     * @param yOffset        camera's Y offset.
     */
    public static void lockOnTargetX(Camera camera, Vector2 targetPosition, float yOffset) {
        final Vector3 position = camera.position;
        position.x = targetPosition.x;
        position.y = targetPosition.y + yOffset;
        setAndUpdate(camera, position);
    }

    /**
     * The {@link Camera} Y will be exactly the same as the target's Y, but the X will be offset by a given amount.
     *
     * @param camera         camera used.
     * @param targetPosition position of the target.
     * @param xOffset        camera's X offset.
     */
    public static void lockOnTargetY(Camera camera, Vector2 targetPosition, float xOffset) {
        final Vector3 position = camera.position;
        position.x = targetPosition.x + xOffset;
        position.y = targetPosition.y;
        setAndUpdate(camera, position);
    }

    /**
     * The {@link Camera} will follow a target with a smooth linear interpolation, based on a lerp amount.
     * The lower the lerp amount, the smoother - and thus slower - the following motion.
     *
     * @param targetPosition position of the target.
     * @param lerp           defines how smoothly the camera follows; slowest -> ]0, 1] <- fastest.
     * @param xOffset        camera's X offset.
     * @param yOffset        camera's Y offset.
     */
    public static void lerpToTarget(Camera camera, Vector2 targetPosition, float lerp, float xOffset, float yOffset) {
        final Vector3 position = camera.position;
        position.x += ((targetPosition.x - position.x) * lerp) + xOffset;
        position.y += ((targetPosition.y - position.y) * lerp) + yOffset;
        setAndUpdate(camera, position);
    }

    /**
     * Overload of the {@link #lerpToTarget(Camera, Vector2, float, float, float)} method.
     */
    public static void lerpToTarget(Camera camera, Vector2 targetPosition, float lerp) {
        lerpToTarget(camera, targetPosition, lerp, 0f, 0f);
    }
}
