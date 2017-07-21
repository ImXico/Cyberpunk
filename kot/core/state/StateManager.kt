package kot.core.state

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.utils.viewport.Viewport
import kot.core.transition.Transition

object StateManager {

  /**
   * The currently running [State]. May be null.
   */
  private var currentState: State? = null

  /**
   * The next [State]. Null unless there's a [Transition] ongoing.
   */
  private var nextState: State? = null

  /**
   * [Transition] used to go from one [State] to another. May be null.
   */
  private var transition: Transition? = null

  /**
   * [FrameBuffer] objects and [TextureRegion]s for the transitions.
   * Initialized late over at [StateManager.setup], under [setupFBOs].
   */
  lateinit private var currentFBO: FrameBuffer
  lateinit private var nextFBO: FrameBuffer
  lateinit private var currentFlippedRegion: TextureRegion
  lateinit private var nextFlippedRegion: TextureRegion

  /**
   * [Batch] used to render everything.
   */
  private val batch: Batch = SpriteBatch()

  /**
   * Game's [Camera] - should remain immutable.
   * To modify the behavior of the camera, use the CameraStyles API.
   */
  lateinit var camera: Camera
    private set

  /**
   * Game's [Viewport] - manages the [Camera] and should also remain immutable.
   */
  lateinit var viewport: Viewport
    private set

  /**
   * Initializes the [StateManager], passing it a [Camera], [Viewport] and,
   * optionally, an initial [State].
   *
   * @param camera        camera that will use a virtual resolution.
   * @param viewport      viewport that will adapt the game screen to the physical devices.
   * @param initialState  optional initial state, that will be set straight away.
   */
  @JvmOverloads
  fun setup(camera: Camera, viewport: Viewport, initialState: State? = null) {
    this.camera = camera
    this.viewport = viewport
    camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f)
    setupFBOs()
    initialState?.let { go(it) }
  }

  /**
   * Sets a [State] to be the currently running one. Optionally, pass
   * a [Transition] to make this change more pleasant.
   *
   * @param state       new current state.
   * @param transition  optional transition used to move from states.
   */
  @JvmOverloads
  fun go(state: State, transition: Transition? = null) {
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
   * Updates the [currentState], if there is one.
   * Also updates any on-going [Transition].
   *
   * @param delta time elapsed between this moment and the last update call.
   */
  fun update(delta: Float) {
    currentState?.update(delta)
    transition?.let { if (it.running()) it.update(delta) }
  }

  /**
   * Handles rendering within three case scenarios:
   * I. There's no next state - and, thus, no transition ongoing.
   * II. Transition was ongoing but just completed.
   * III. Transition is still in progress.
   */
  fun render() {
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
        } else {
          currentFBO.wrap { currentState?.render(batch) }
          nextFBO.wrap { nextState?.render(batch) }
          currentFlippedRegion.texture = currentFBO.colorBufferTexture
          nextFlippedRegion.texture = nextFBO.colorBufferTexture
          transition?.render(batch, currentFlippedRegion, nextFlippedRegion)
        }
      }
    }
  }

  /**
   * Resizes the [currentState], [nextState] and this class' [Viewport].
   * Here, the [FrameBuffer] objects are disposed manually and re-defined.
   *
   * @param width  new screen width.
   * @param height new screen height.
   */
  fun resize(width: Int, height: Int) {
    viewport.update(width, height)
    batch.projectionMatrix = camera.combined
    currentState?.resize(width, height)
    nextState?.resize(width, height)
    resizeFBOs(width, height)
  }

  /**
   * Pauses the [currentState] and the [nextState].
   */
  fun pause() {
    currentState?.pause()
    nextState?.pause()
  }

  /**
   * Resumes the [currentState] and the [nextState].
   */
  fun resume() {
    currentState?.resume()
    nextState?.resume()
  }

  /**
   * Disposes the [currentState], [nextState] and the [FrameBuffer] objects.
   */
  fun dispose() {
    currentState?.dispose()
    nextState?.dispose()
    currentFBO.dispose()
    nextFBO.dispose()
  }

  /**
   * Utility extension function to wrap any [block] inside the
   * [FrameBuffer.begin] and [FrameBuffer.end] calls, for shortness.
   *
   * @param block a [Unit] returning function.
   */
  private inline fun FrameBuffer.wrap(block: () -> Unit) {
    this.begin()
    block()
    this.end()
  }

  /**
   * Initialize the [FrameBuffer] objects and the [TextureRegion]s that will
   * serve any running [Transition].
   */
  private fun setupFBOs() {
    val screenWidth: Int = Gdx.graphics.width
    val screenHeight: Int = Gdx.graphics.height
    currentFBO = FrameBuffer(Pixmap.Format.RGBA8888, screenWidth, screenHeight, false)
    nextFBO = FrameBuffer(Pixmap.Format.RGBA8888, screenWidth, screenHeight, false)
    currentFlippedRegion = TextureRegion(currentFBO.colorBufferTexture)
    currentFlippedRegion.flip(false, true)
    nextFlippedRegion = TextureRegion(nextFBO.colorBufferTexture)
    nextFlippedRegion.flip(false, true)
  }

  /**
   * Resizes the [FrameBuffer] objects in every [StateManager.resize] call.
   *
   * @param width   new screen width.
   * @param height  new screen height.
   */
  private fun resizeFBOs(width: Int, height: Int) {
    currentFBO.dispose()
    nextFBO.dispose()
    currentFBO = FrameBuffer(Pixmap.Format.RGBA8888, width, height, false)
    nextFBO = FrameBuffer(Pixmap.Format.RGBA8888, width, height, false)
  }
}