package kot.core.transition.types

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import kot.core.WORLD_HEIGHT
import kot.core.WORLD_WIDTH
import kot.core.transition.Transition

@JvmField val DEFAULT_SPEED: Float = 3f

class Fade(private var speed: Float = DEFAULT_SPEED) : Transition {

  private var running: Boolean = false
  private var alpha: Float = 0f

  override fun start() {
    running = true
  }

  override fun finish() {
    running = false
  }

  override fun running(): Boolean = running

  override fun completed(): Boolean = !running

  override fun update(delta: Float) {
    if (!running) return
    if (alpha <= 0.95f) {
      alpha += 0.05f
    } else {
      running = false
    }
  }

  override fun render(batch: Batch, current: TextureRegion, next: TextureRegion) {
    batch.begin()
    batch.setColor(1f, 1f, 1f, 1f - alpha)
    batch.draw(current, 0f, 0f, WORLD_WIDTH.toFloat(), WORLD_HEIGHT.toFloat())
    batch.setColor(1f, 1f, 1f, alpha)
    batch.draw(next, 0f, 0f, WORLD_WIDTH.toFloat(), WORLD_HEIGHT.toFloat())
    batch.end()
  }

}
