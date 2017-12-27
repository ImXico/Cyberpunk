The [`physics `]() extension is a small wrapper around [libGDX' Box2D](https://github.com/libgdx/libgdx/wiki/Box2d) - thus, it requires having the Box2D dependency in the project. It is currently composed of the following components:

- [PhysicsUtility](#physicsutility)
- [PhysicsWorld](#physicsworld)
- [PhysicsConfig](#physicsconfig)
- [PhysicsDebugger](#physicsdebugger)
- [Builders](#builders)

### PhysicsUtility
The [`PhysicsUtility`]() class contains utility conversion methods: from Box2D units to pixels and from pixels to Box2D units. By using a pixels-to-meters conversion metric, we can **keep thinking in pixels**, which is pretty useful! These conversions are done under the hood, meaning that you most *probably* won't need to call any of this class' methods.

### PhysicsWorld
The [`PhysicsWorld`]() encapsulates a Box2D [`World`](https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/physics/box2d/World.html). 

With `debugMode` set to true (which is the default), you can debug your world with an inner `PhysicsDebugger` instance (explained below). However, you'll want to have this mode toggled *off* on a released version of your app, as having the debugger running can be a bit CPU-heavy, especially on mobile devices.

To instantiate a [`PhysicsWorld`](), you need to supply the world dimensions:
```kotlin
val world = PhysicsWorld(WORLD_WIDTH, WORLD_HEIGHT)
```

If you don't explicitly pass a third argument, the [`PhysicsConfig`](), then the default one will be used. You can always pass a custom one:
```kotlin
val customConfig = PhysicsConfig(...)
val world = PhysicsWorld(WORLD_WIDTH, WORLD_HEIGHT, customConfig)
```

### PhysicsConfig
The [`PhysicsConfig`]() class holds a bunch of values that will set the physics environment of some [`PhysicsWorld`](). The controllable values (which can't be changed in runtime) are the following:

- Gravity: Defaults to `(0f, -9.8f)`.
- Timestep: Default to `1 / 60f`.
- Velocity Iterations: Defaults to `6`.
- Position Iterations: Defaults to `2`.

### PhysicsDebugger
The [`PhysicsDebugger`]() is used to debug a given [`PhysicsWorld`](), and is essentially a wrapper around [`Box2DDebugRenderer`](https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/physics/box2d/Box2DDebugRenderer.html).

Besides having an instance of [`Box2DDebugRenderer`]() and calling its render method, the debugger also contains a dedicated [`Camera`](https://github.com/libgdx/libgdx/wiki/Orthographic-camera) and [`Viewport`](https://github.com/libgdx/libgdx/wiki/Viewports), which means that the output will still look good after resize events.

This class only used internally in the [`PhysicsWorld`](), meaning that you won't need to interact directly with any of its functions.

### Builders

The [`BodyBuilder`]() lets you build a customizable [`Body`](), with one [`BodyDef`]() and one or more [`FixtureDef`]().

It depends on two other builders: the [`BodyDefBuilder`](), that lets you build a customizable [`BodyDef`](https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/physics/box2d/BodyDef.html) and the [`FixtureDefBuilder`](), that lets you build one or more customizable [`FixtureDef`](https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/physics/box2d/FixtureDef.html).

It's important to note that the [`BodyBuilder`]() constructor takes in a [`PhysicsWorld`]() - this means that **all** bodies created with a given instance of [`BodyBuilder`]() will be hosted in that world. it is possible to change the current world by calling `#changeWorld`.

This is how, in Box2D, we'd generally create a dynamic circle-shaped body, given a position, radius and restitution:

**Without** the body builder it's rather unpractical and doesn't look too friendly:

```kotlin
val bodyDef = BodyDef().apply {
  type = BodyDef.BodyType.DynamicBody
  position = Vector2(20f, 30f)
}
val circleShape = CircleShape().apply {
  radius = 5f
}
val fixtureDef = FixtureDef().apply {
  restitution = 0.7f
  shape = circleShape
}
val body: Body = world.createBody(bodyDef)
body.createFixture(fixtureDef)
circleShape.dispose()
```

**With** the body builder, it's a lot easier. To start off, all builders are reusable after calling `#build`.
```kotlin
val builder = BodyBuilder(world)
val bodyDefBuilder = BodyDefBuilder()
val fixtureDefBuilder = FixtureDefBuilder()
```

With that in mind, to reproduce the creation process above, all we have to do is this:
```kotlin
var bodyDef: BodyDef = bodyDefBuilder
  .position(20f, 30f)
  .type(BodyDef.BodyType.DynamicBody)

var fixDef: FixtureDef = fixtureDefBuilder
  .circle(5f)
  .restitution(0.7f)

builder
  .withBodyDef(bodyDef)
  .withFixtureDef(fixDef)
  .build()
```

This feels a lot easier to read, and although not fully-featured, still grants a fair amount of customization options.
