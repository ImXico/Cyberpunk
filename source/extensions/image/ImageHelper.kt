package source.extensions.image

import com.badlogic.gdx.math.Vector2

/**
 * Centers an image with dimensions <width x height> on the screen, given the
 * world's dimensions, [worldWidth] and [worldHeight].
 *
 * @param width       width of the image you want to center.
 * @param height      height of the image you want to center.
 * @param worldWidth  width of the game world (world coordinates).
 * @param worldHeight height of the game world (world coordinates).
 * @return the correct, centered coordinates, in the form of a [Vector2].
 */
fun center(width: Float, height: Float, worldWidth: Int, worldHeight: Int): Vector2 {
  val x: Float = (worldWidth - width) / 2f
  val y: Float = (worldHeight - height) / 2f
  return Vector2(x, y)
}

/**
 * Centers an image with dimensions <width x height> on the screen horizontally, keeping
 * the same, original Y value.
 *
 * @param width       width of the image you want to center.
 * @param worldWidth  width of the game world (world coordinates).
 * @param y           y value of the image-to-center's position.
 * @return the correct, centered coordinates, in the form of a [Vector2].
 */
fun centerX(width: Float, worldWidth: Int, y: Float): Vector2 {
  val x: Float = (worldWidth - width) / 2f
  return Vector2(x, y)
}

/**
 * Centers an image with dimensions <width x height> on the screen vertically, keeping
 * the same, original X value.
 *
 * @param height       height of the image you want to center.
 * @param worldHeight  height of the game world (world coordinates).
 * @param x            x value of the image-to-center's position.
 * @return the correct, centered coordinates, in the form of a [Vector2].
 */
fun centerY(height: Float, worldHeight: Int, x: Float): Vector2 {
  val y: Float = (worldHeight - height) / 2f
  return Vector2(x, y)
}

/**
 * Centers an image with dimensions <width x height> inside an image of <otherWidth x otherHeight>,
 * that's located at a given [otherPosition].
 *
 * @param width         width of the image you want to center.
 * @param height        height of the image you want to center.
 * @param otherWidth    width of the outer image.
 * @param otherHeight   height of the outer image.
 * @param otherPosition position, in the form of a [Vector2], of the outer image.
 * @return the correct, centered coordinates, in the form of a [Vector2].
 */
fun centerOnImage(width: Float, height: Float, otherWidth: Float, otherHeight: Float, otherPosition: Vector2): Vector2 {
  val x: Float = otherPosition.x + ((otherWidth - width) / 2f)
  val y: Float = otherPosition.y + ((otherHeight - height) / 2f)
  return Vector2(x, y)
}

/**
 * Centers an image with dimensions <size x size> inside an image of <otherSize x otherSize>,
 * that's located at a given [otherPosition].
 *
 * @param size          size (both width and height) of the image you want to center.
 * @param otherSize     size (both width and height) of the outer image.
 * @param otherPosition position, in the form of a [Vector2], of the outer image.
 * @return the correct, centered coordinates, in the form of a [Vector2].
 */
fun centerOnImage(size: Float, otherSize: Float, otherPosition: Vector2): Vector2 {
  return centerOnImage(size, size, otherSize, otherSize, otherPosition)
}
