package cyberpunk.core.transition.types

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import cyberpunk.core.WORLD_HEIGHT
import cyberpunk.core.WORLD_WIDTH
import cyberpunk.core.transition.Transition

const val DEFAULT_SPEED = 1.5f

class Fade
  @JvmOverloads
  constructor(private var speed: Float = DEFAULT_SPEED) : Transition {

  @JvmField val MAX_ALPHA = 1f
  @JvmField val DEFAULT_ALPHA_INC = 0.05f

  /**
   * Overrides the property defined at the [Transition] interface.
   * @property [Transition.running]
   */
  override var running = false

  /**
   * Current alpha, ranging from 0 to [MAX_ALPHA].
   * @see MAX_ALPHA
   */
  private var alpha = 0f

  /**
   * Alpha increment to be used at each frame
   */
  private val alphaInc = calculateAlphaInc()

  /**
   * @see [Transition.update]
   */
  override fun update(delta: Float) {
    if (!running) return
    if (alpha <= MAX_ALPHA) {
      alpha += alphaInc
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

  /**
   * Calculates the alpha increment to be used at each frame based on the [speed] property.
   * @return alpha increment.
   */
  private fun calculateAlphaInc() = this.speed * DEFAULT_ALPHA_INC / DEFAULT_SPEED
}