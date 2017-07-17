package kot.core.transition.types

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import kot.core.WORLD_HEIGHT
import kot.core.WORLD_WIDTH
import kot.core.transition.Transition

@JvmField val DEFAULT_SPEED: Float = 1.5f

class Fade(private var speed: Float = DEFAULT_SPEED) : Transition {

  @JvmField val MAX_ALPHA: Float = 1f

  /**
   * Overrides the property defined at the [Transition] interface.
   *
   * @property [Transition.running]
   */
  override var running: Boolean = false

  /**
   * Current alpha, ranging from 0 to [MAX_ALPHA].
   *
   * @see MAX_ALPHA
   */
  private var alpha: Float = 0f

  /**
   * @see [Transition.update]
   */
  override fun update(delta: Float) {
    if (!running) return
    if (alpha <= MAX_ALPHA) {
      alpha += 0.05f
    } else {
      running = false
    }
  }

  /**
   * @see [Transition.render]
   */
  override fun render(batch: Batch, current: TextureRegion, next: TextureRegion) {
    batch.begin()
    batch.setColor(1f, 1f, 1f, 1f - alpha)
    batch.draw(current, 0f, 0f, WORLD_WIDTH.toFloat(), WORLD_HEIGHT.toFloat())
    batch.setColor(1f, 1f, 1f, alpha)
    batch.draw(next, 0f, 0f, WORLD_WIDTH.toFloat(), WORLD_HEIGHT.toFloat())
    batch.end()
  }
}
