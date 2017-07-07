package kot.physics

import com.badlogic.gdx.math.Vector2

/**
 * Default value for the [World] gravity.
 */
val DEFAULT_GRAVITY: Vector2 = Vector2(0f, -9.8f)

/**
 * The amount of time to simulate, this should not vary.
 * @see World#step
 */
val DEFAULT_TIMESTEP: Float = 1 / 60f

/**
 * For the velocity constraint solver.
 * @see World#step
 */
val DEFAULT_VELOCITY_ITERATIONS: Float = 6f

/**
 * For the position constraint solver.
 * @see World#step
 */
val DEFAULT_POSITION_ITERATIONS: Float = 2f

/**
 * Configuration class to use when instantiating a new [PhysicsWorld].
 * Values that aren't explicitly passed will be set to the defaults,
 * as seen above.
 * Once created, the attributes of a [PhysicsConfig] cannot be changed.
 */
data class PhysicsConfig(
        val gravity: Vector2 = DEFAULT_GRAVITY,
        val timestep: Float = DEFAULT_TIMESTEP,
        val velocityIterations: Float = DEFAULT_VELOCITY_ITERATIONS,
        val positionIterations: Float = DEFAULT_POSITION_ITERATIONS
)
