package example.source.state

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import kot.core.WORLD_HEIGHT
import kot.core.WORLD_WIDTH
import kot.core.state.StateAdapter
import kot.extensions.physics.builder.BodyBuilder
import kot.extensions.physics.builder.BodyDefBuilder
import kot.extensions.physics.builder.FixtureDefBuilder
import kot.extensions.physics.PhysicsWorld

class B2DState : StateAdapter() {

  val world: PhysicsWorld = PhysicsWorld(WORLD_WIDTH, WORLD_HEIGHT)
  val builder: BodyBuilder = BodyBuilder(world)

  val bodyDefBuilder: BodyDefBuilder = BodyDefBuilder()
  val fixtureDefBuilder: FixtureDefBuilder = FixtureDefBuilder()

  init {
    // Make a (dynamic) circle, 10 pixels of radius and positioned at (100, 100).
    var bodyDef = bodyDefBuilder.position(100f, 100f).type(BodyDef.BodyType.DynamicBody)
    var fixDef = fixtureDefBuilder.circle(10f).restitution(0.6f)
    builder.withBodyDef(bodyDef).withFixtureDef(fixDef).build()

    // Make the ground.
    bodyDef = bodyDefBuilder.position(10f, 5f)
    fixDef = fixtureDefBuilder.chain(arrayOf(Vector2(3f, 20f), Vector2(200f, 20f)))
    builder.withBodyDef(bodyDef).withFixtureDef(fixDef).build()

    // And a box.
    bodyDef = bodyDefBuilder.position(100f, 80f).angle(10f)
    fixDef = fixtureDefBuilder.box(50f, 50f).density(0.4f)
    builder.withBodyDef(bodyDef).withFixtureDef(fixDef).build()
  }

  override fun update(delta: Float) = world.update()

  override fun render(batch: Batch) = world.render()

  override fun resize(width: Int, height: Int) = world.resize(width, height)

  override fun dispose() {}
}