package source.extensions.text

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.math.Vector2

/**
 * Centers a piece of [text] on the screen, given the [worldWidth], [worldHeight] and [font] used.
 *
 * @param font        the [BitmapFont] used.
 * @param text        the [String] text to be centered.
 * @param worldWidth  width of the game world (world coordinates).
 * @param worldHeight height of the game world (world coordinates).
 * @return the correct, centered coordinates, in the form of a [Vector2].
 */
fun center(font: BitmapFont, text: String, worldWidth: Int, worldHeight: Int): Vector2 {
  val layout: GlyphLayout = GlyphLayout(font, text)
  val x: Float = (worldWidth - layout.width) / 2f
  val y: Float = (worldHeight + layout.height) / 2f
  return Vector2(x, y)
}

/**
 * Centers a piece of [text] horizontally on the screen, given the [worldWidth],
 * target [y] and [font] used.
 *
 * @param font        the [BitmapFont] used.
 * @param text        the [String] text to be centered.
 * @param worldWidth  width of the game world (world coordinates).
 * @param y           y value of the text's position.
 * @return the correct, centered coordinates, in the form of a [Vector2].
 */
fun centerX(font: BitmapFont, text: String, worldWidth: Int, y: Float): Vector2 {
  val layout: GlyphLayout = GlyphLayout(font, text)
  val x: Float = (worldWidth - layout.width) / 2f
  return Vector2(x, y)
}

/**
 * Centers a piece of [text] horizontally on the screen, given the [worldHeight],
 * target [x] and [font] used.
 *
 * @param font          the [BitmapFont] used.
 * @param text          the [String] text to be centered.
 * @param worldHeight   width of the game world (world coordinates).
 * @param x             x value of the text's position.
 * @return the correct, centered coordinates, in the form of a [Vector2].
 */
fun centerY(font: BitmapFont, text: String, worldHeight: Int, x: Float): Vector2 {
  val layout: GlyphLayout = GlyphLayout(font, text)
  val y: Float = (worldHeight + layout.height) / 2f
  return Vector2(x, y)
}

/**
 * Centers a piece of [text] inside an image of dimensions <width x height>, located at [position].
 *
 * @param font      the [BitmapFont] used.
 * @param text      the [String] text to be centered.
 * @param width     the width of the outer image.
 * @param height    the height of the outer image.
 * @param position  the position of the outer image, in the form of a [Vector2].
 * @return the correct, centered coordinates, in the form of a [Vector2].
 */
fun centerOnImage(font: BitmapFont, text: String, width: Float, height: Float, position: Vector2): Vector2 {
  val layout: GlyphLayout = GlyphLayout(font, text)
  val x: Float = position.x + ((width - layout.width) / 2f)
  val y: Float = position.y + ((height / 2f) + (layout.height / 2f))
  return Vector2(x, y)
}