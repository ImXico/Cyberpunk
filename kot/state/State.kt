package kot.state

import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2

internal interface State : InputProcessor {

  /**
   * Updates the state.
   * This is called every frame.
   *
   * @param delta time elapsed since the last call to this method.
   */
  fun update(delta: Float)

  /**
   * Renders the state.
   * This is also called every frame, always after [State.update].
   *
   * @param batch [Batch] used to render the state.
   */
  fun render(batch: Batch)

  /**
   * Pauses the state.
   */
  fun pause()

  /**
   * Resumes the state, after it being paused.
   */
  fun resume()

  /**
   * Hides the state.
   */
  fun hide()

  /**
   * Disposes the state and all of the state's [com.badlogic.gdx.utils.Disposable] components.
   */
  fun dispose()

  /**
   * Resizes the state and any of its components.
   *
   * @param width  new width.
   * @param height new height.
   */
  fun resize(width: Int, height: Int)

  /**
   * Unprojects screen coordinates into world coordinates.
   *
   * @param screenX X in screen coordinates.
   * @param screenY Y in screen coordinates.
   * @return world coordinates.
   */
  fun unproject(screenX: Float, screenY: Float): Vector2

  /**
   * Unprojects screen coordinates into world coordinates.
   *
   * @param screenCoordinates (X, Y) screen coordinates.
   * @return world coordinates.
   */
  fun unproject(screenCoordinates: Vector2): Vector2

}
