@file:JvmName("Graphics")

package cyberpunk.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20

/**
 * Clears the frame and depth buffers with the selected color.
 *
 * @param red red color value.
 * @param green green color value.
 * @param blue blue color value.
 * @param alpha optional color alpha value.
 */
fun clearScreen(red: Float, green: Float, blue: Float, alpha: Float = 1f) {
  Gdx.gl.glClearColor(red, green, blue, alpha)
  Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
}