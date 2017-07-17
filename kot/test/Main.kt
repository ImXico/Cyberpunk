package kot.test

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
import kot.extensions.image.ImageManager

class Main : ApplicationAdapter() {

  override fun create() {
    val camera: Camera = OrthographicCamera(WORLD_WIDTH.toFloat(), WORLD_HEIGHT.toFloat())
    camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f)
    val viewport: Viewport = ExtendViewport(WORLD_WIDTH.toFloat(), WORLD_HEIGHT.toFloat(), camera)

    ImageManager.load("NormalPack", "NormalPack.pack", true)
    ImageManager.load("HeroWalkingPack", "HeroWalkingPack.pack")

    StateManager.setup(camera, viewport, StateA())
  }

  override fun render() {
    Gdx.gl.glClearColor(200 / 255f, 200 / 255f, 200 / 255f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    StateManager.update(Gdx.graphics.deltaTime)
    StateManager.render()
  }

  override fun resize(width: Int, height: Int) = StateManager.resize(width, height)
}
