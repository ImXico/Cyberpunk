package cyberpunk.physics

import com.badlogic.gdx.physics.box2d.World

class PhysicsWorld
  @JvmOverloads
  constructor(worldWidth: Int, worldHeight: Int, private val config: PhysicsConfig = PhysicsConfig()) {

  /**
   * The [World] instance of this [PhysicsWorld].
   */
  val world = World(config.gravity, true)

  /**
   * The [PhysicsDebugger] that will render this [PhysicsWorld].
   * Essentially a wrapper around the Box2DDebugRenderer.
   */
  private val debugger = PhysicsDebugger(worldWidth, worldHeight)

  /**
   * Whether or not the [PhysicsDebugger] should render.
   */
  var debugMode = true

  /**
   * Should be called in the resize method of the state/screen that holds this instance.
   * Crucial to update the viewport of the [PhysicsDebugger] after all resize events.
   * @param width   new screen width.
   * @param height  new screen height.
   */
  fun resize(width: Int, height: Int) = debugger.resize(width, height)

  /**
   * Dispose the entire [World] associated with this instance. When a world is destroyed
   * all the bodies and joints in it are destroyed as well.
   */
  fun dispose() = world.dispose()

  /**
   * Calls the [World.step] with the configurations of this instance's [PhysicsConfig].
   */
  fun update() = world.step(config.timestep, config.velocityIterations, config.positionIterations)

  /**
   * Calls [PhysicsDebugger.renderer] if the [debugMode] is set to true.
   * Otherwise, doesn't do anything at all.
   */
  fun render() {
    if (debugMode) {
      debugger.render(this)
    }
  }
}