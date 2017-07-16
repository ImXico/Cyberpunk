package kot.extensions.physics

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
   * @param velocity the desired [BodyDef.linearVelocity], in the form of a [Vector2].
   * @return this [BodyDefBuilder] instance.
   */
  fun linearVelocity(velocity: Vector2): BodyDefBuilder {
    return this.apply { bodyDef.linearVelocity.set(velocity) }
  }

  /**
   * Sets the linear velocity of the body's origin, in world coordinates.
   * If not called explicitly, the default value is (0,0).
   *
   * @param x the desired [BodyDef.linearVelocity] X.
   * @param y the desired [BodyDef.linearVelocity] Y.
   * @return this [BodyDefBuilder] instance.
   */
  fun linearVelocity(x: Float, y: Float): BodyDefBuilder {
    return this.apply { bodyDef.linearVelocity.set(Vector2(x, y)) }
  }

  /**
   * Sets the angular velocity of the body.
   * If not called explicitly, the default value is 0.
   *
   * @param velocity the desired [BodyDef.angularVelocity].
   * @return this [BodyDefBuilder] instance.
   */
  fun angularVelocity(velocity: Float): BodyDefBuilder {
    return this.apply { bodyDef.angularVelocity = velocity }
  }

  /**
   * Sets the linear damping of the body.
   * If not called explicitly, the default value is 0.
   *
   * @param damping the desired [BodyDef.linearDamping].
   * @return this [BodyDefBuilder] instance.
   */
  fun linearDamping(damping: Float): BodyDefBuilder {
    return this.apply { bodyDef.linearDamping = damping }
  }

  /**
   * Sets the angular damping of the body.
   * If not called explicitly, the default value is 0.
   *
   * @param damping the desired [BodyDef.angularDamping]-
   * @return this [BodyDefBuilder] instance.
   */
  fun angularDamping(damping: Float): BodyDefBuilder {
    return this.apply { bodyDef.angularDamping = damping }
  }

  /**
   * Never fall asleep - note that this WILL increase CPU usage.
   * The default value is false (i.e. the body can fall asleep).
   *
   * @return this [BodyDefBuilder] instance.
   */
  fun noSleep(): BodyDefBuilder {
    return this.apply { bodyDef.allowSleep = false }
  }

  /**
   * Sets this body to not be awake on spawn.
   * The default value is true (i.e. the body is awake on spawn).
   *
   * @return this [BodyDefBuilder] instance.
   */
  fun notAwakeOnSpawn(): BodyDefBuilder {
    return this.apply { bodyDef.awake = false }
  }

  /**
   * Sets this body to not be active on spawn.
   * The default value is true (i.e. the body is active on spawn).
   *
   * @return this [BodyDefBuilder] instance.
   */
  fun notActiveOnSpawn(): BodyDefBuilder {
    return this.apply { bodyDef.active = false }
  }

  /**
   * Prevents this body from rotating.
   * The default value is false (i.e. the body can rotate).
   *
   * @return this [BodyDefBuilder] instance.
   */
  fun fixedRotation(): BodyDefBuilder {
    return this.apply { bodyDef.fixedRotation = true }
  }

  /**
   * Turns this flah on in case this is a fast moving body
   * that should be prevented from tunneling through other moving bodies.
   * Note that this WILL increase CPU usage.
   * The default value is false.
   *
   * @return this [BodyDefBuilder] instance.
   */
  fun asBullet(): BodyDefBuilder {
    return this.apply { bodyDef.bullet = true }
  }

  /**
   * Scales the gravity that will be applied to this body.
   * The default value is 1f.
   *
   * @param scale the desired [BodyDef.gravityScale].
   * @return this [BodyDefBuilder] instance.
   */
  fun gravityScale(scale: Float): BodyDefBuilder {
    return this.apply { bodyDef.gravityScale = scale }
  }

  /**
   * Sets the *world* position of the body.
   * The conversion from screen to world coordinates is made implicitly,
   * so you can simply pass a [Vector2] of screen coordinates.
   *
   * @param bodyPosition the desired [BodyDef.position].
   * @return this [BodyDefBuilder] instance.
   */
  fun position(bodyPosition: Vector2): BodyDefBuilder {
    return this.apply { bodyDef.position.set(bodyPosition.asBox2DUnits()) }
  }

  /**
   * Sets the *world* position of the body.
   * The conversion from screen to world coordinates is made implicitly,
   * so you can simply pass an X and Y in screen coordinates.
   *
   * @param x the desired [BodyDef.position] X.
   * @param y the desired [BodyDef.position] Y.
   * @return this [BodyDefBuilder] instance.
   */
  fun position(x: Float, y: Float): BodyDefBuilder {
    return this.apply { bodyDef.position.set(Vector2(x, y)) }
  }

  /**
   * Finish building this [BodyDef].
   * The [bodyDef] instance will be cleaned up,
   * making it suitable for reuse later.
   *
   * @return the [BodyDef] that has been worked on.
   */
  fun build(): BodyDef {
    val result = bodyDef
    cleanup()
    return result
  }

  /**
   * Cleans up this class' [bodyDef], making it
   * suitable for reuse later.
   */
  private fun cleanup() {
    bodyDef = BodyDef()
  }
}