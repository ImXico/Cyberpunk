package kot.core.state

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2

abstract class StateAdapter : State {

  /* State methods that absolutely must be overriden. */

  override abstract fun update(delta: Float)
  override abstract fun render(batch: Batch)
  override abstract fun dispose()

  /* State methods that may or may not be overriden. */

  override fun resize(width: Int, height: Int) {}
  override fun hide() {}
  override fun pause() {}
  override fun resume() {}

  /* State input conversion methods - can't be overriden. */

  final override fun unproject(screenCoordinates: Vector2): Vector2 {
    return StateManager.viewport.unproject(screenCoordinates)
  }

  final override fun unproject(screenX: Float, screenY: Float): Vector2 {
    return this.unproject(Vector2(screenX, screenY))
  }

  /* InputProcessor methods that may or may not be overriden. */

  override fun keyDown(keycode: Int): Boolean = false
  override fun keyUp(keycode: Int): Boolean = false
  override fun keyTyped(character: Char): Boolean = false
  override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
  override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
  override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false
  override fun mouseMoved(screenX: Int, screenY: Int): Boolean = false
  override fun scrolled(amount: Int): Boolean = false

}