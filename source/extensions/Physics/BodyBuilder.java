package source.extensions.Physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;

/**
 * @author Xico
 */

public class BodyBuilder {

    private BodyDef bodyDef;
    private Array<FixtureDef> fixtureDefs;

    private PhysicsWorld physicsWorld;

    public BodyBuilder(PhysicsWorld world) {
        this.physicsWorld = world;
        this.cleanup();
    }

    private void cleanup() {
        bodyDef = new BodyDef();
        fixtureDefs = new Array<FixtureDef>();
        for (FixtureDef fixtureDef : fixtureDefs) {
            fixtureDef.shape.dispose();
        }
    }

    public void setPhysicsWorld(PhysicsWorld world) {
        this.physicsWorld = world;
    }

    /**
     * Add a body def to this body, by calling {@link BodyDefBuilder} functions.
     *
     * @param builder {@link BodyDefBuilder}.
     * @return this instance.
     * @see BodyDefBuilder
     */
    public BodyBuilder withBodyDef(BodyDefBuilder builder) {
        bodyDef = builder.build();
        return this;
    }

    /**
     * Add a fixture def to this body, by calling {@link FixtureDefBuilder} functions.
     *
     * @param builder {@link FixtureDefBuilder}.
     * @return this instance.
     * @see FixtureDefBuilder
     */
    public BodyBuilder withFixtureDef(FixtureDefBuilder builder) {
        fixtureDefs.add(builder.build());
        return this;
    }

    /**
     * Builds and returns a body that's defined by a {@link BodyDef} and one or more {@link FixtureDef}.
     * This body is added onto the current {@link PhysicsWorld}.
     *
     * @return body created.
     */
    public Body build() {
        final Body body = this.physicsWorld.getWorld().createBody(this.bodyDef);
        for (FixtureDef fixtureDef : fixtureDefs) {
            body.createFixture(fixtureDef);
        }
        this.cleanup();
        return body;
    }
}

