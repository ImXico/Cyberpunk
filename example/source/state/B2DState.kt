package example.source.state

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import kot.core.WORLD_HEIGHT
import kot.core.WORLD_WIDTH
import kot.core.state.StateAdapter
import kot.extensions.physics.BodyBuilder
import kot.extensions.physics.BodyDefBuilder
import kot.extensions.physics.FixtureDefBuilder
import kot.extensions.physics.PhysicsWorld

class B2DState : StateAdapter() {

  val world: PhysicsWorld = PhysicsWorld(WORLD_WIDTH, WORLD_HEIGHT)
  val builder: BodyBuilder = BodyBuilder(world)
  val circle: Body

  init {
    val bodyDef = BodyDefBuilder().position(100f, 100f).type(BodyDef.BodyType.DynamicBody)
    val fixDef = FixtureDefBuilder().circle(10f)
    circle = builder.withBodyDef(bodyDef).withFixtureDef(fixDef).build()
  }

  override fun update(delta: Float) = world.update()

  override fun render(batch: Batch) = world.render()

  override fun resize(width: Int, height: Int) = world.resize(width, height)

  override fun dispose() {}
}