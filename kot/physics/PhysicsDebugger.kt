package kot.physics

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.Viewport

internal class PhysicsDebugger(worldWidth: Int, worldHeight: Int) {

  // TODO: Accept the injection of different viewports.
  // TODO: They will need to have their dimensions converted to B2D units, though.

  /**
   * The [Box2DDebugRenderer] instance that will allow the rendering
   * of the given [PhysicsWorld].
   */
  private val renderer: Box2DDebugRenderer

  /**
   * Dedicated [OrthographicCamera] for the Box2D rendering, because of the
   * scaling issues. Will be managed by a dedicated [Viewport] as well.
   */
  private val camera: OrthographicCamera

  /**
   * Dedicated [Viewport] that will manage the [camera] instance.
   */
  private val viewport: Viewport

  init {
    renderer = Box2DDebugRenderer()
    val viewportWidth: Float = (worldWidth.toFloat()).asBox2DUnits()
    val viewportHeight: Float = (worldHeight.toFloat()).asBox2DUnits()
    camera = OrthographicCamera().apply {
      setToOrtho(false, viewportWidth, viewportHeight)
    }
    viewport = ExtendViewport(viewportWidth, viewportHeight, camera)
  }

  /**
   * Called on [PhysicsWorld.resize] to make sure that the [viewport]
   * responds correctly to the screen resizing events.
   *
   * @param width   new screen width.
   * @param height  new screen height.
   */
  internal fun resize(width: Int, height: Int) = viewport.update(width, height)

  /**
   * Calls [Box2DDebugRenderer.render] on the given [PhysicsWorld.world], using the
   * dedicated [camera]'s projection and view matrix.
   *
   * @param world instance of the [PhysicsWorld] that will be rendered.
   */
  internal fun render(world: PhysicsWorld) = renderer.render(world.world, camera.combined)
}
