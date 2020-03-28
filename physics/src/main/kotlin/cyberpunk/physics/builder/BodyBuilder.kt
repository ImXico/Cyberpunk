package cyberpunk.physics.builder

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.FixtureDef
import cyberpunk.physics.PhysicsWorld

class BodyBuilder(private var world: PhysicsWorld) {

  /**
   * [BodyDef] for the body that will be built.
   * Gets reset after [BodyBuilder.build] or [BodyBuilder.cleanup].
   */
  private var bodyDef = BodyDef()

  /**
   * Optional [Body.userData].
   */
  private var userData: Any? = null

  /**
   * A [Body] may have multiple [FixtureDef]s.
   * Each [Fixture] will be composed by a [FixtureDef] and, optionally, a [Fixture.userData].
   * In [fixturesProps], a [FixtureDef] will be mapped to a [Fixture.userData] object,
   * that, being optional, can be null.
   */
  private val fixturesProps: MutableMap<FixtureDef, Any?> = mutableMapOf()

  /**
   * Change the currently attached [PhysicsWorld] to a new one, returning the old one.
   * All bodies/joints further created will be placed onto the new current [world].
   * @param newWorld the new world to be set.
   * @return the old world.
   */
  fun changeWorld(newWorld: PhysicsWorld): PhysicsWorld {
    val oldWorld = world
    world = newWorld
    return oldWorld
  }

  /**
   * Effectively destroys the [PhysicsWorld.world] that was currently being used by this instance.
   */
  fun disposeWorld() = world.dispose()

  /**
   * Adds a [BodyDef] to this body, by chaining the [BodyDefBuilder] methods.
   * @param builder [BodyDefBuilder].
   * @return this [BodyBuilder] instance.
   * @see BodyDefBuilder
   */
  fun withBodyDef(builder: BodyDefBuilder) = this.apply { bodyDef = builder.build() }

  /**
   * Adds a [FixtureDef] to this body, by chaining the [FixtureDefBuilder] methods.
   * @param builder {@link FixtureDefBuilder}.
   * @param fixtureUserData user data for the fixture that will have this [FixtureDef].
   * @return this [BodyBuilder] instance.
   * @see FixtureDefBuilder
   */
  @JvmOverloads
  fun withFixtureDef(builder: FixtureDefBuilder, fixtureUserData: Any? = null) =
    this.apply { fixturesProps[builder.build()] = fixtureUserData }

  /**
   * Builds and returns a [Body] that's defined by a [BodyDef] and one
   * or more [FixtureDef]. This body is added onto the current [world].
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