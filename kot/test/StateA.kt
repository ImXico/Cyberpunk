package kot.test

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import kot.core.WORLD_HEIGHT
import kot.core.state.StateAdapter
import kot.core.state.StateManager
import kot.core.transition.types.Fade
import kot.extensions.image.ImageManager
import kot.extensions.image.centerOnImage
import kot.extensions.image.centerY

class StateA : StateAdapter() {

  private val tex1: TextureRegion?
  private val pos1: Vector2

  private val tex2: TextureRegion?
  private val pos2: Vector2

  private val font: BitmapFont = BitmapFont().apply { color = Color.BLACK }
  private var textPos: Vector2 = Vector2()

  init {
    tex2 = ImageManager.take("alien-blue")
    pos2 = centerY(150f, WORLD_HEIGHT, 100f)
    tex1 = ImageManager.take("alien-green")
    pos1 = centerOnImage(50f, 50f, 150f, 150f, pos2)
  }

  override fun keyDown(keycode: Int): Boolean {
    println("Changing state...")
    StateManager.to(StateB(), Fade())
    return true
  }

  override fun update(delta: Float) {
  }

  override fun render(batch: Batch) {
    batch.projectionMatrix = StateManager.camera.combined
    batch.begin()

    batch.draw(tex2, pos2.x, pos2.y, 150f, 150f)
    textPos = kot.extensions.text.centerOnImage(font, "Hello!", 150f, 150f, pos2)
    font.draw(batch, "Hello!", textPos.x, textPos.y)

    batch.end()
  }

  override fun dispose() {
  }

}
