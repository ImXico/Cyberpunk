package example2

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.Viewport
import kot.core.WORLD_HEIGHT
import kot.core.WORLD_WIDTH
import kot.core.state.StateManager

class App : ApplicationAdapter() {

  override fun create() {
    val camera: Camera = createCamera()
    val viewport: Viewport = createViewport(camera)
    StateManager.setup(camera, viewport)
  }

  override fun render() {
    Gdx.gl.glClearColor(230f / 255f, 230f / 255f, 230f / 255f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    StateManager.update(Gdx.graphics.deltaTime)
    StateManager.render()
  }

  override fun resize(width: Int, height: Int) = StateManager.resize(width, height)

  override fun pause() = StateManager.pause()

  override fun resume() = StateManager.resume()

  override fun dispose() = StateManager.dispose()

  private fun createCamera(): Camera {
    return OrthographicCamera(WORLD_WIDTH.toFloat(), WORLD_HEIGHT.toFloat()).apply {
      position.set(viewportWidth / 2f, viewportHeight / 2f, 0f)
    }
  }

  private fun createViewport(camera: Camera): Viewport {
    return ExtendViewport(WORLD_WIDTH.toFloat(), WORLD_HEIGHT.toFloat(), camera)
  }
}
