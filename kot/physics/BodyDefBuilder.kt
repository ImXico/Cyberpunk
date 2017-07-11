package kot.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef

class BodyDefBuilder {

  /**
   * The [BodyDef] instance that we'll be building on.
   * Gets cleaned up after calling [BodyDefBuilder.build],
   * meaning that the same instance can be recycled.
   */
  private var bodyDef: BodyDef = BodyDef()

  /**
   * Sets this [BodyDef]'s type.
   * If not called explicitly, the default type is [BodyDef.BodyType.StaticBody].
   *
   * @param bodyType the desired [BodyDef.BodyType].
   * @return this [BodyDefBuilder] instance.
   * @see [BodyDef.BodyType]
   */
  fun type(bodyType: BodyDef.BodyType): BodyDefBuilder {
    return this.apply { bodyDef.type = bodyType }
  }

  /**
   * Sets the world angle of the body, in radians.
   * If not called explicitly, the default value is 0f.
   *
   * @param bodyAngle the desired [BodyDef.angle], in radians.
   * @return this [BodyDefBuilder] instance.
   */
  fun angle(bodyAngle: Float): BodyDefBuilder {
    return this.apply { bodyDef.angle = bodyAngle }
  }

  /**
   * Sets the linear velocity of the body's origin, in world coordinates.
   * If not called explicitly, the default value is (0,0).
   *
   * @param velocity desired [BodyDef.linearVelocity], in the form of a [Vector2].
   * @return this [BodyDefBuilder] instance.
   */
  fun linearVelocity(velocity: Vector2): BodyDefBuilder {
    return this.apply { bodyDef.linearVelocity.set(velocity) }
  }

  /**
   * Sets the linear velocity of the body's origin, in world coordinates.
   * If not called explicitly, the default value is (0,0).
   *
   * @param x desired [BodyDef.linearVelocity] X.
   * @param y desired [BodyDef.linearVelocity] Y.
   * @return this [BodyDefBuilder] instance.
   */
  fun linearVelocity(x: Float, y: Float): BodyDefBuilder {
    return this.apply { bodyDef.linearVelocity.set(Vector2(x, y)) }
  }

  /**
   *
   * @param velocity
   * @return this [BodyDefBuilder] instance.
   */
  fun angularVelocity(velocity: Float): BodyDefBuilder {
    return this.apply { bodyDef.angularVelocity = velocity }
  }

  /**
   *
   * @param damping
   * @return this [BodyDefBuilder] instance.
   */
  fun linearDamping(damping: Float): BodyDefBuilder {
    return this.apply { bodyDef.linearDamping = damping }
  }

  /**
   *
   * @param damping
   * @return this [BodyDefBuilder] instance.
   */
  fun angularDamping(damping: Float): BodyDefBuilder {
    return this.apply { bodyDef.angularDamping = damping }
  }

  /**
   *
   * @return this [BodyDefBuilder] instance.
   */
  fun noSleep(): BodyDefBuilder {
    return this.apply { bodyDef.allowSleep = false }
  }

  /**
   *
   * @return this [BodyDefBuilder] instance.
   */
  fun notAwakeOnSpawn(): BodyDefBuilder {
    return this.apply { bodyDef.awake = false }
  }

  /**
   *
   * @return this [BodyDefBuilder] instance.
   */
  fun notActiveOnSpawn(): BodyDefBuilder {
    return this.apply { bodyDef.active = false }
  }

  /**
   *
   * @return this [BodyDefBuilder] instance.
   */
  fun fixedRotation(): BodyDefBuilder {
    return this.apply { bodyDef.fixedRotation = true }
  }

  /**
   *
   * @return this [BodyDefBuilder] instance.
   */
  fun asBullet(): BodyDefBuilder {
    return this.apply { bodyDef.bullet = true }
  }

  /**
   *
   * @param scale
   * @return this [BodyDefBuilder] instance.
   */
  fun gravityScale(scale: Float): BodyDefBuilder {
    return this.apply { bodyDef.gravityScale = scale }
  }

  /**
   *
   * @param bodyPosition
   * @return this [BodyDefBuilder] instance.
   */
  fun position(bodyPosition: Vector2): BodyDefBuilder {
    return this.apply { bodyDef.position.set(bodyPosition.asBox2DUnits()) }
  }

  /**
   *
   * @param x
   * @param y
   * @return this [BodyDefBuilder] instance.
   */
  fun position(x: Float, y: Float): BodyDefBuilder {
    return this.apply { bodyDef.position.set(Vector2(x, y)) }
  }

  /**
   *
   * @return
   */
  fun build(): BodyDef {
    val result = bodyDef
    cleanup()
    return result
  }

  /**
   *
   */
  private fun cleanup() {
    bodyDef = BodyDef()
  }
}