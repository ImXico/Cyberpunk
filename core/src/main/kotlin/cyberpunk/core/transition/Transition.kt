package cyberpunk.core.transition

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion

interface Transition {

  /**
   * Abstract property - override in concrete implementations.
   */
  var running: Boolean

  /**
   * Starts the transition.
   */
  fun start() {
    running = true
  }

  /**
   * Finishes the transition.
   */
  fun finish() {
    running = false
  }

  /**
   * Checks whether or not this [Transition] is running.
   * @return true if it is running, false otherwise.
   */
  fun running() = running

  /**
   * Checks whether or not this [Transition] is completed.
   * @return true if it is completed, false otherwise.
   */
  fun completed() = !running

  /**
   * Updates the [Transition].
   * @param delta - time elapsed since the last call to update.
   */
  fun update(delta: Float)

  /**
   * Renders the current and next [TextureRegion]s to the given [Batch].
   * These textures were obtained using the Render-To-Texture (RTT) technique.
   * A [TextureRegion] is used instead of a texture because the framebuffer is upside down to normal textures,
   * as seen here: "http://www.gamefromscratch.com/post/2014/01/21/Rendering-a-3D-model-to-texture-in-LibGDX.aspx
   * @param batch   [Batch] used to render.
   * @param current current (or previous?) screen's [TextureRegion], rendered onto a frame buffer.
   * @param next    next screen's [TextureRegion], rendered onto a frame buffer.
   * @see "http://apprize.info/programming/libgdx/10.html"
   */
  fun render(batch: Batch, current: TextureRegion, next: TextureRegion)

}