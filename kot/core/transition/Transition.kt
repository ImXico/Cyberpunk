package kot.core.transition

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion

/**
 * The [StateManager] will take care of the [Transition] management, meaning that
 * you must probably won't need to explicitly call any of these functions.
 */
interface Transition {

  /**
   * Starts the transition.
   */
  fun start()

  /**
   * Finishes the transition.
   */
  fun finish()

  /**
   * Checks whether or not this [Transition] is running.
   *
   * @return true if it is running, false otherwise.
   */
  fun running(): Boolean

  /**
   * Checks whether or not this [Transition] is completed.
   *
   * @return true if it is completed, false otherwise.
   */
  fun completed(): Boolean

  /**
   * Updates the [Transition].
   *
   * @param delta - time elapsed since the last call to update.
   */
  fun update(delta: Float)

  /**
   * Renders the current and next [TextureRegion]s to the given [Batch].
   * These textures were obtained using the Render-To-Texture (RTT) technique.
   * A [TextureRegion] is used instead of a [Texture] because the framebuffer is upside down to normal textures,
   * as seen here: "http://www.gamefromscratch.com/post/2014/01/21/Rendering-a-3D-model-to-texture-in-LibGDX.aspx"
   *
   * @param batch   [Batch] used to render.
   * @param current current (or previous?) screen's [TextureRegion], rendered onto a [FrameBuffer].
   * @param next    next screen's [TextureRegion], rendered onto a [FrameBuffer].
   * @see "http://apprize.info/programming/libgdx/10.html"
   */
  fun render(batch: Batch, current: TextureRegion, next: TextureRegion)

}