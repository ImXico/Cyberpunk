package cyberpunk.physics.builder

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.ChainShape
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import cyberpunk.physics.asBox2DUnits

class FixtureDefBuilder {

  // TODO: box(width, height, center, angle).

  /**
   * The [FixtureDef] instance that we'll be building on.
   * Gets cleaned up after calling [FixtureDefBuilder.build],
   * meaning that the same instance can be recycled.
   */
  private var fixtureDef = FixtureDef()

  /**
   * Sets this fixture def's shape to a [CircleShape], given the desired radius in pixels.
   * @param radius circle shape's radius in pixels.
   * @return this [FixtureDefBuilder] instance.
   * @see CircleShape
   */
  fun circle(radius: Float): FixtureDefBuilder {
    val radiusB2D = radius.asBox2DUnits()
    val shape = CircleShape()
    return this.apply {
      shape.radius = radiusB2D
      fixtureDef.shape = shape
    }
  }

  /**
   * Generates a [PolygonShape] given its vertices.
   * Both the X and Y components of these [Vector2] should be in pixel coordinates.
   * @param vertices shape's vertices; components in pixels.
   * @return this [FixtureDefBuilder] instance.
   * @see PolygonShape
   */
  fun polygon(vertices: Array<Vector2>): FixtureDefBuilder {
    val verticesB2D = vertices.map { it.asBox2DUnits() }.toTypedArray()
    val shape = PolygonShape()
    return this.apply {
      shape.set(verticesB2D)
      fixtureDef.shape = shape
    }
  }

  /**
   * Generates a [PolygonShape] and sets it as a box, given its dimensions in pixel values.
   * @param width the desired box width in pixels.
   * @param height the desired box height in pixels.
   * @return this [FixtureDefBuilder] instance.
   * @see PolygonShape
   */
  fun box(width: Float, height: Float): FixtureDefBuilder {
    val halfWidth = (width / 2f).asBox2DUnits()
    val halfHeight = (height / 2f).asBox2DUnits()
    val shape = PolygonShape()
    return this.apply {
      shape.setAsBox(halfWidth, halfHeight)
      fixtureDef.shape = shape
    }
  }

  /**
   * Sets this fixture def's shape to a [ChainShape], given its vertices in pixel coordinates.
   * @param vertices array of [Vector2] vertices, in pixels.
   * @return this [FixtureDefBuilder] instance.
   * @see ChainShape
   */
  fun chain(vertices: Array<Vector2>): FixtureDefBuilder {
    val verticesB2D = vertices.map { it.asBox2DUnits() }.toTypedArray()
    val shape = ChainShape()
    return this.apply {
      shape.createChain(verticesB2D)
      fixtureDef.shape = shape
    }
  }

  /**
   * Sets the fixture def's friction coefficient.
   * As stated in the docs, should be in the [0,1] interval.
   * The default value for this property is 0.2f.
   * @param friction desired friction.
   * @return this [FixtureDefBuilder] instance.
   */
  fun friction(friction: Float): FixtureDefBuilder =
    this.apply { fixtureDef.friction = friction }

  /**
   * Sets the fixture def's restitution (elasticity/bounciness).
   * As stated in the docs, should be in the [0,1] interval.
   * The default value for this property is 0.
   * @param restitution desired restitution.
   * @return this [FixtureDefBuilder] instance.
   */
  fun restitution(restitution: Float): FixtureDefBuilder =
    this.apply { fixtureDef.restitution = restitution }

  /**
   * Sets the fixture def's density.
   * As stated in the docs, should be in kg/m^2.
   * The default value for this property is 0.
   * @param density desired density.
   * @return this [FixtureDefBuilder] instance.
   */
  fun density(density: Float): FixtureDefBuilder =
    this.apply { fixtureDef.density = density }

  /**
   * Set this fixture def to be a sensor.
   * As stated in the docs, a sensor shape collects contact
   * information but never generates a collision response.
   * The default value for this property is false.
   * @return this [FixtureDefBuilder] instance.
   */
  fun asSensor(): FixtureDefBuilder =
    this.apply { fixtureDef.isSensor = true }

  /**
   * Finish building this [FixtureDef].
   * The [fixtureDef] instance will be cleaned up,
   * making it suitable for reuse later.
   * @return the [FixtureDef] that has been worked on.
   */
  fun build(): FixtureDef {
    val result = fixtureDef
    cleanup()
    return result
  }

  /**
   * Cleans up this class' [FixtureDef], making it suitable for reuse later.
   */
  private fun cleanup() {
    fixtureDef = FixtureDef()
  }
}