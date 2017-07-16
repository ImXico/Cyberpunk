package kot.core.state

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.Viewport
import kot.core.transition.Transition
import kot.core.transition.TransitionUtils

object StateManager {

  private var currentState: State? = null
  private var nextState: State? = null

  private var transition: Transition? = null
  private var utils: TransitionUtils = TransitionUtils()

  private val batch: Batch = SpriteBatch()
  lateinit var camera: Camera
  lateinit var viewport: Viewport

  /**
   *
   */
  @JvmOverloads
  fun setup(camera: Camera, viewport: Viewport, initialState: State? = null) {
    this.camera = camera
    this.viewport = viewport
    camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f)
    initialState?.let { to(it) }
  }

  /**
   *
   */
  @JvmOverloads
  fun to(state: State, transition: Transition? = null) {
    if (currentState == null) {
      currentState = state
      Gdx.input.inputProcessor = currentState
    } else {
      currentState?.dispose()
      Gdx.input.inputProcessor = null
      nextState = state
      this.transition = transition?.apply { start() }
    }
    resize(Gdx.graphics.width, Gdx.graphics.height)
  }

  /**
   *
   */
  fun update(delta: Float) {
    currentState?.update(delta)
    transition?.let {
      if (it.running()) {
        it.update(delta)
      }
    }
  }

  /**
   *
   */
  fun render(batch: Batch) {
    if (nextState == null) {
      currentState?.render(batch)
    } else {
      transition?.let {
        if (it.completed()) {
          currentState = nextState
          nextState = null
          it.finish()
          currentState?.render(batch)
          Gdx.input.inputProcessor = currentState
        }
      }
    }
  }

  /**
   *
   */
  fun resize(width: Int, height: Int) {
    viewport.update(width, height)
    batch.projectionMatrix = camera.combined
    currentState?.resize(width, height)
    nextState?.resize(width, height)
    utils.resetFBOs()
  }

  /**
   *
   */
  fun pause() {
    currentState?.pause()
    nextState?.pause()
  }

  /**
   *
   */
  fun resume() {
    currentState?.resume()
    nextState?.resume()
  }

  /**
   *
   */
  fun dispose() {
    currentState?.dispose()
    nextState?.dispose()
    utils.currentFBO.dispose()
    utils.nextFBO.dispose()
  }
}