package cyberpunk.physics

import com.badlogic.gdx.math.Vector2

/**
 * Default value for the [PhysicsWorld.world] gravity.
 */
private val DEFAULT_GRAVITY = Vector2(0f, -9.8f)

/**
 * The amount of time to simulate, this should not vary.
 * @see PhysicsWorld.world#step
 */
private const val DEFAULT_TIMESTEP = 1 / 60f

/**
 * For the velocity constraint solver.
 * @see PhysicsWorld.world#step
 */
private const val DEFAULT_VELOCITY_ITERATIONS = 6

/**
 * For the position constraint solver.
 * @see PhysicsWorld.world#step
 */
private const val DEFAULT_POSITION_ITERATIONS = 2

/**
 * Configuration class to use when instantiating a new [PhysicsWorld].
 * Values that aren't explicitly passed will be set to the defaults, as seen above.
 * Once created, the attributes of a [PhysicsConfig] cannot be changed.
 */
data class PhysicsConfig @JvmOverloads constructor (
  val gravity: Vector2 = DEFAULT_GRAVITY,
  val timestep: Float = DEFAULT_TIMESTEP,
  val velocityIterations: Int = DEFAULT_VELOCITY_ITERATIONS,
  val positionIterations: Int = DEFAULT_POSITION_ITERATIONS
)