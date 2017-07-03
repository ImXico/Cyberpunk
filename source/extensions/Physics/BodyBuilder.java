package source.extensions.Physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;

/**
 * @author Xico
 */

public class BodyBuilder {

    private BodyDef bodyDef;
    private Object userData;

    private final Array<FixtureDef> fixtureDefs;
    private final Array<Object> fixturesUserData;

    private PhysicsWorld physicsWorld;

    public BodyBuilder(PhysicsWorld world) {
        this.physicsWorld = world;
        bodyDef = new BodyDef();
        userData = null;
        fixtureDefs = new Array<FixtureDef>();
        fixturesUserData = new Array<Object>();
    }

    private void cleanup() {
        bodyDef = new BodyDef();
        userData = null;
        for (FixtureDef fixtureDef : fixtureDefs) {
            fixtureDef.shape.dispose();
        }
        fixtureDefs.clear();
        fixturesUserData.clear();
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
     * @param builder         {@link FixtureDefBuilder}.
     * @param fixtureUserData user data for the fixture that will be return after calling body#createFixture
     * @return this instance.
     */
    public BodyBuilder withFixtureDef(FixtureDefBuilder builder, Object fixtureUserData) {
        fixtureDefs.add(builder.build());
        fixturesUserData.add(fixtureUserData);
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
        return this.withFixtureDef(builder, null);
    }

    /**
     * Builds and returns a body that's defined by a {@link BodyDef} and one or more {@link FixtureDef}.
     * This body is added onto the current {@link PhysicsWorld}.
     *
     * @return body created.
     */
    public Body build() {
        final Body body = this.physicsWorld.getWorld().createBody(this.bodyDef);
        for (int i = 0; i < fixtureDefs.size; i++) {
            final Fixture fixture = body.createFixture(fixtureDefs.get(i));
            fixture.setUserData(fixturesUserData.get(i));
        }
        body.setUserData(userData);
        this.cleanup();
        return body;
    }
}

