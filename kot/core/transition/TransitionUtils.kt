package kot.core.transition

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.FrameBuffer

internal class TransitionUtils {

  private val DISPLAY_WIDTH: Int = Gdx.graphics.width
  private val DISPLAY_HEIGHT: Int = Gdx.graphics.height

  var currentFBO: FrameBuffer
  var nextFBO: FrameBuffer

  private var currentFlippedRegion: TextureRegion
  private var nextFlippedRegion: TextureRegion

  init {
    currentFBO = FrameBuffer(Pixmap.Format.RGBA8888, DISPLAY_WIDTH, DISPLAY_HEIGHT, false)
    nextFBO = FrameBuffer(Pixmap.Format.RGBA8888, DISPLAY_WIDTH, DISPLAY_HEIGHT, false)
    currentFlippedRegion = TextureRegion(currentFBO.colorBufferTexture)
    currentFlippedRegion.flip(false, true)
    nextFlippedRegion = TextureRegion(nextFBO.colorBufferTexture)
    nextFlippedRegion.flip(false, true)
  }

  internal fun resetFBOs() {
    currentFBO.dispose()
    nextFBO.dispose()
    currentFBO = FrameBuffer(Pixmap.Format.RGBA8888, DISPLAY_WIDTH, DISPLAY_HEIGHT, false)
    nextFBO = FrameBuffer(Pixmap.Format.RGBA8888, DISPLAY_WIDTH, DISPLAY_HEIGHT, false)
  }
}

