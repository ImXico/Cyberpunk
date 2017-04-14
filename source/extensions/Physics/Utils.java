package source.extensions.Physics;

import com.badlogic.gdx.math.Vector2;

/**
 * Notice how Box2D uses the MKS notation for its units:
 * M = Meters
 * K = Kilometers
 * S = Seconds
 * <p>
 * If no scale conversion is made, we are assuming that 1 pixel = 1 meter!
 * In other worlds, defining a 100 x 100 box is actually a 100 meters x 100 meters box,
 * which is huge and thus leaves to heavy calculations.
 * Thus, we scale all of our B2D values by a Pixels-Per-Meter (PPM) constant.
 *
 * @author Xico
 */

public class Utils {

    /**
     * Scaling: 100 pixels = 1 meter.
     */
    static final float PPM = 100f;

    /**
     * Turns pixel values into B2D units.
     *
     * @param value value in pixel units.
     * @return corresponding B2D units, based on the {@value PPM} scaling.
     */
    static float toB2DUnits(float value) {
        return value / PPM;
    }

    /**
     * Turns pixel values into B2D units.
     *
     * @param value value in pixel units.
     * @return corresponding B2D units, based on the {@value PPM} scaling.
     */
    static Vector2 toB2DUnits(Vector2 value) {
        final float x = value.x / PPM;
        final float y = value.y / PPM;
        return new Vector2(x, y);
    }

    /**
     * Turns B2D units into pixel units.
     *
     * @param value value in B2D units.
     * @return corresponding pixels, based on the {@value PPM} scaling.
     */
    static float toPixels(float value) {
        return value * PPM;
    }

    /**
     * Turns b2d units into pixel units.
     *
     * @param value value in B2D units.
     * @return corresponding pixels, based on the {@value PPM} scaling.
     */
    static Vector2 toPixels(Vector2 value) {
        final float x = value.x * PPM;
        final float y = value.y * PPM;
        return new Vector2(x, y);
    }
}

