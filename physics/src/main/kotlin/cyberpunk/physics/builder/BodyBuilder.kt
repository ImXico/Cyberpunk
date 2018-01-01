package cyberpunk.physics.builder

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.FixtureDef
import cyberpunk.physics.PhysicsWorld
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class BodyBuilder(private var world: PhysicsWorld) {

  /**
   * [BodyDef] for the body that will be built.
   * Gets reset after [BodyBuilder.build] or [BodyBuilder.cleanup].
   */
  private var bodyDef: BodyDef = BodyDef()

  /**
   * Optional [Body.userData].
   */
  private var userData: JvmType.Object? = null

  /**
   * A [Body] may have multiple [FixtureDef]s.
   * Each [Fixture] will be composed by a [FixtureDef] and, optionally, a [Fixture.userData].
   * In [fixturesProps], a [FixtureDef] will be mapped to a [Fixture.userData] object,
   * that, being optional, can be null.
   */
  private val fixturesProps: MutableMap<FixtureDef, JvmType.Object?> = mutableMapOf()

  /**
   * Change the currently attached [PhysicsWorld].
   * All bodies created will be placed onto the current [world].
   */
  fun changeWorld(newWorld: PhysicsWorld) {
    world = newWorld
  }

  /**
   * Adds a [BodyDef] to this body, by chaining the [BodyDefBuilder] methods.
   *
   * @param builder [BodyDefBuilder].
   * @return this [BodyBuilder] instance.
   * @see BodyDefBuilder
   */
  fun withBodyDef(builder: BodyDefBuilder): BodyBuilder {
    return this.apply { bodyDef = builder.build() }
  }

  /**
   * Adds a [FixtureDef] to this body, by chaining the [FixtureDefBuilder] methods.
   *
   * @param builder {@link FixtureDefBuilder}.
   * @param fixtureUserData user data for the fixture that will have this [FixtureDef].
   * @return this [BodyBuilder] instance.
   * @see FixtureDefBuilder
   */
  @JvmOverloads
  fun withFixtureDef(builder: FixtureDefBuilder, fixtureUserData: JvmType.Object? = null): BodyBuilder {
    return this.apply { fixturesProps.put(builder.build(), fixtureUserData) }
  }

  /**
   * Builds and returns a [Body] that's defined by a [BodyDef] and one
   * or more [FixtureDef]. This body is added onto the current [world].
   *
   * @return [Body] created.
   */
  fun build(): Body {
    val body = world.world.createBody(bodyDef)
    for ((fixtureDef, fixtureData) in fixturesProps) {
      val fixture = body.createFixture(fixtureDef)
      fixtureData?.let { fixture.userData = it }
    }
    body.userData = userData
    cleanup()
    return body
  }

  /**
   * Cleans up this [BodyBuilder] instance, making
   * it ready to be reused.
   */
  private fun cleanup() {
    bodyDef = BodyDef()
    userData = null
    fixturesProps.keys.forEach { it.shape.dispose() }
    fixturesProps.clear()
  }
}