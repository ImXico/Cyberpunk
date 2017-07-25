@file:JvmName("CameraStyles")

package source.extensions.camera

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

/**
 * Centers the [Camera] in the middle of the screen, fiven the world coordinates.
 *
 * @param worldWidth  width of the world (world coordinates).
 * @param worldHeight height of the world (world coordinates).
 */
fun Camera.center(worldWidth: Int, worldHeight: Int) {
  val newPosition: Vector3 = Vector3(worldWidth / 2f, worldHeight / 2f, 0f)
  this.refresh(newPosition)
}

/**
 * The [Camera] will follow the target coordinates rigidly.
 * Not suitable for all cases, as it may cause a very rough motion.
 *
 * @param targetPosition position of the target, where the camera will be also positioned to.
 */
fun Camera.lockOn(targetPosition: Vector2) {
  val newPosition: Vector3 = Vector3(targetPosition.x, targetPosition.y, 0f)
  this.refresh(newPosition)
}

/**
 * The [Camera] will rigidly follow the [targetPosition] horizontally, but will be
 * offset vertically by a [yOffset].
 *
 * @param targetPosition position of the target, where the camera will be also positioned to (horizontally).
 * @param yOffset camera's Y offset, relatively to the [targetPosition] Y.
 */
fun Camera.lockOnX(targetPosition: Vector2, yOffset: Float) {
  val newPosition: Vector3 = Vector3(targetPosition.x, targetPosition.y + yOffset, 0f)
  this.refresh(newPosition)
}

/**
 * The [Camera] will rigidly follow the [targetPosition] vertically, but will be
 * offset horizontally by a [xOffset].
 *
 * @param targetPosition position of the target, where the camera will be also positioned to (horizontally).
 * @param xOffset camera's X offset, relatively to the [targetPosition] X.
 */
fun Camera.lockOnY(targetPosition: Vector2, xOffset: Float) {
  val newPosition: Vector3 = Vector3(targetPosition.x + xOffset, targetPosition.y, 0f)
  this.refresh(newPosition)
}

/**
 * The [Camera] will follow a [targetPosition] with a smooth linear interpolation, based on a [lerp] amount.
 * The lower the lerp amount, the smoother - and thus slower - the following motion.
 * The camera can also be offset by an [xOffset] and [yOffset] amounts, but that's optional.
 *
 * @param targetPosition  position of the target that the camera will be set on.
 * @param lerp            defines how smoothly the camera follows; ]0 (slowest), 1 (fastest)].
 * @param xOffset         camera's X offset, relatively to the [targetPosition] X.
 * @param yOffset         camera's Y offset, relatively to the [targetPosition] Y.
 */
@JvmOverloads
fun Camera.lerpTo(targetPosition: Vector2, lerp: Float, xOffset: Float = 0f, yOffset: Float = 0f) {
  val newX: Float = this.position.x + ((targetPosition.x - position.x) * lerp) + xOffset
  val newY: Float = this.position.y + ((targetPosition.y - position.y) * lerp) + yOffset
  val newPosition: Vector3 = Vector3(newX, newY, 0f)
  this.refresh(newPosition)
}

/**
 * Sets the [Camera] position to the given one and updates it.
 * Won't be called explicitly anywhere besides this class.
 *
 * @param newPosition new [Vector3] position for the camera.
 */
private fun Camera.refresh(newPosition: Vector3) {
  this.apply {
    position.set(newPosition)
    update()
  }
}