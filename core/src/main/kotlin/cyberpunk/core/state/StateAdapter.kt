package cyberpunk.core.state

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2

abstract class StateAdapter : State {

  /* State methods that absolutely must be overridden. */

  override abstract fun update(delta: Float)
  override abstract fun render(batch: Batch)
  override abstract fun dispose()

  /* State methods that may or may not be overridden. */

  override fun resize(width: Int, height: Int) = Unit
  override fun hide() = Unit
  override fun pause() = Unit
  override fun resume() = Unit

  /* State input conversion methods - should not be overridden. */

  final override fun unproject(screenCoordinates: Vector2) =
    StateManager.viewport.unproject(screenCoordinates)


  final override fun unproject(screenX: Float, screenY: Float) =
    this.unproject(Vector2(screenX, screenY))


  /* InputProcessor methods that may or may not be overridden. */

  override fun keyDown(keycode: Int) = false
  override fun keyUp(keycode: Int) = false
  override fun keyTyped(character: Char) = false
  override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int) = false
  override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int) = false
  override fun touchDragged(screenX: Int, screenY: Int, pointer: Int) = false
  override fun mouseMoved(screenX: Int, screenY: Int) = false
  override fun scrolled(amount: Int) = false

}