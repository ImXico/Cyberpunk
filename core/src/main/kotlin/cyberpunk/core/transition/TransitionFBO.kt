package cyberpunk.core.transition

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.FrameBuffer

/**
 * Holds the frame buffer objects (FBOs) and flipped regions
 * that are used during state transitions.
 */
internal class TransitionFBO {

  /**
   * [FrameBuffer] objects that are used to wrap render calls during state transitions.
   */
  private var currentFBO: FrameBuffer
  private var nextFBO: FrameBuffer

  /**
   * [TextureRegion] objects that represent frames before and after a transition.
   */
  private var currentFlippedRegion: TextureRegion
  private var nextFlippedRegion: TextureRegion

  init {
    val screenWidth = Gdx.graphics.width
    val screenHeight = Gdx.graphics.height
    currentFBO = FrameBuffer(Pixmap.Format.RGBA8888, screenWidth, screenHeight, false)
    nextFBO = FrameBuffer(Pixmap.Format.RGBA8888, screenWidth, screenHeight, false)
    currentFlippedRegion = TextureRegion(currentFBO.colorBufferTexture)
    currentFlippedRegion.flip(false, true)
    nextFlippedRegion = TextureRegion(nextFBO.colorBufferTexture)
    nextFlippedRegion.flip(false, true)
  }

  /**
   * Dispose the current and next FBOs.
   */
  internal fun dispose() {
    currentFBO.dispose()
    nextFBO.dispose()
  }

  /**
   * Resizes the [FrameBuffer] objects.
   * The [FrameBuffer] objects are disposed manually and re-defined.
   * @param width   new screen width.
   * @param height  new screen height.
   */
  internal fun resize(width: Int, height: Int) {
    this.dispose()
    currentFBO = FrameBuffer(Pixmap.Format.RGBA8888, width, height, false)
    nextFBO = FrameBuffer(Pixmap.Format.RGBA8888, width, height, false)
  }

  /**
   * Render an arbitrary block onto the currentFBO.
   * @param block a [Unit] returning function.
   */
  internal fun wrapCurrent(block: () -> Unit) {
    currentFBO.wrap { block() }
    currentFlippedRegion.texture = currentFBO.colorBufferTexture
  }

  /**
   * Render an arbitrary block onto the nextFBO.
   * @param block a [Unit] returning function.
   */
  internal fun wrapNext(block: () -> Unit) {
    nextFBO.wrap { block() }
    nextFlippedRegion.texture = nextFBO.colorBufferTexture
  }

  /**
   * Return the current and next flipped regions in an easy-to-destruct way.
   * @return a [Pair] holding both flipped regions.
   */
  internal fun getFlippedRegions() = Pair(currentFlippedRegion, nextFlippedRegion)

  /**
   * Utility extension function to wrap any [block] inside the
   * [FrameBuffer.begin] and [FrameBuffer.end] calls, for shortness.
   * @param block a [Unit] returning function.
   */
  private inline fun FrameBuffer.wrap(block: () -> Unit) {
    this.begin()
    block()
    this.end()
  }
}
