@file:JvmName("Graphics")

package cyberpunk.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20

/**
 * Clears the frame and depth buffers with the selected color.
 * @param red optional red color value.
 * @param green optional green color value.
 * @param blue optional blue color value.
 * @param alpha optional color alpha value.
 */
@JvmOverloads
fun clearScreen(red: Float = 0f, green: Float = 0f, blue: Float = 0f, alpha: Float = 1f) {
  Gdx.gl.glClearColor(red, green, blue, alpha)
  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
}
