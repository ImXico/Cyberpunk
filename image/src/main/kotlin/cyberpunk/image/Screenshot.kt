@file:JvmName("ScreenShot")

package cyberpunk.image

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.PixmapIO
import com.badlogic.gdx.utils.BufferUtils
import com.badlogic.gdx.utils.ScreenUtils

/**
 * Takes a screenshot of the entire screen and saves the image with the given file name
 * into the external storage.
 *
 * @param fileName the name that you want your screenshot png to have.
 * @see <a href="https://github.com/libgdx/libgdx/wiki/File-handling">File Handling</a>
 */
fun takeScreenshot(fileName: String) {
  val bufferWidth = Gdx.graphics.backBufferWidth
  val bufferHeight = Gdx.graphics.backBufferHeight
  val pixels = ScreenUtils.getFrameBufferPixels(0, 0, bufferWidth, bufferHeight, true)

  var i = 4
  while (i < pixels.size) {
    pixels[i - 1] = 255.toByte()
    i += 4
  }

  val pixmap = Pixmap(bufferWidth, bufferHeight, Pixmap.Format.RGBA8888)
  BufferUtils.copy(pixels, 0, pixmap.pixels, pixels.size)
  PixmapIO.writePNG(Gdx.files.external(fileName), pixmap)
  pixmap.dispose()
}
