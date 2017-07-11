package kot.physics

import com.badlogic.gdx.math.Vector2

/**
 * Scaling: 100 pixels = 1 meter.
 */
@JvmField var PPM: Float = 100f

/**
 * Takes in a [Float] value in pixels and returns the equivalent value of
 * Box2D units, after making proper scale conversion.
 *
 * @return corresponding Box2D units, based on the [PPM] scaling.
 */
internal fun Float.asBox2DUnits(): Float = this / PPM

/**
 * Takes in a [Float] value in Box2D units and returns the equivalent value of
 * pixels, after making proper scale conversion.
 *
 * @return corresponding pixel units, based on the [PPM] scaling.
 */
internal fun Float.asPixels(): Float = this * PPM

/**
 * Takes in a [Vector2] value in pixels and returns the equivalent value of
 * Box2D units, after making proper scale conversion.
 * Note that the original [Vector2] is not changed; a modified copy of it is
 * returned instead - hence the name *as* Box2DUnits and not *to* Box2DUnits.
 *
 * @return corresponding Box2D units, based on the [PPM] scaling.
 */
internal fun Vector2.asBox2DUnits(): Vector2 = this.cpy().scl(1 / PPM)

/**
 * Takes in a [Vector2] value in Box2D units and returns the equivalent value
 * of pixels after making proper scale conversion.
 * Note that the original [Vector2] is not changed; a modified copy of it is
 * returned instead - hence the name *as* Pixels and not *to* Pixels.
 *
 * @return corresponding pixel units, based on the [PPM] scaling.
 */
internal fun Vector2.asPixels(): Vector2 = this.cpy().scl(PPM)
