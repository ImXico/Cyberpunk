package source.extensions.Physics;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * @author Xico
 */

public class FixtureDefBuilder {

    private FixtureDef fixtureDef;

    public FixtureDefBuilder() {
        this.cleanup();
    }

    private void cleanup() {
        this.fixtureDef = new FixtureDef();
    }

    /**
     * Sets this fixture def's shape to a {@link CircleShape}, given a radius.
     *
     * @param radius circle shape's radius in pixels.
     * @return this instance.
     * @see FixtureDef#shape
     * @see CircleShape
     */
    public FixtureDefBuilder circleShape(float radius) {
        final float radiusToB2D = Utils.toB2DUnits(radius);
        final CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radiusToB2D);
        fixtureDef.shape = circleShape;
        return this;
    }

    /**
     * Generates a {@link PolygonShape} given its vertices.
     *
     * @param vertices shape's vertices, in pixels.
     * @return this instance.
     * @see PolygonShape
     */
    public FixtureDefBuilder polygonShape(Vector2[] vertices) {
        for (Vector2 vertex : vertices) {
            /* Same effect as calling toB2DUnits. */
            vertex.scl(1 / Utils.PPM);
        }
        final PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices);
        fixtureDef.shape = polygonShape;
        return this;
    }

    /**
     * Generates a {@link PolygonShape} and sets it as a box, given its dimensions.
     *
     * @param width  shape's total width, in pixels.
     * @param height shape's total height, in pixels.
     * @return this instance.
     * @see FixtureDef#shape
     * @see PolygonShape
     */
    public FixtureDefBuilder boxShape(float width, float height) {
        final float halfWidth = Utils.toB2DUnits(width / 2f);
        final float halfHeight = Utils.toB2DUnits(height / 2f);
        final PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(halfWidth, halfHeight);
        fixtureDef.shape = polygonShape;
        return this;
    }

    /**
     * Generates a {@link PolygonShape} and sets it as a box, given its dimensions, center and angle.
     *
     * @param width  shape's total width, in pixels.
     * @param height shape's total height, in pixels.
     * @param center center of the box, in pixel coordinates.
     * @param angle  shape's angle.
     * @return this instance.
     * @see FixtureDef#shape
     * @see PolygonShape
     */
    public FixtureDefBuilder boxShape(float width, float height, Vector2 center, float angle) {
        final float halfWidth = Utils.toB2DUnits(width / 2f);
        final float halfHeight = Utils.toB2DUnits(height / 2f);
        final Vector2 centerToB2D = Utils.toB2DUnits(center);
        final PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(halfWidth, halfHeight, centerToB2D, angle);
        fixtureDef.shape = polygonShape;
        return this;
    }

    /**
     * Sets this fixture def's shape to a {@link ChainShape}, given its vertices.
     *
     * @param vertices array of {@link Vector2} vertices, in pixels.
     * @return this instance.
     * @see ChainShape
     */
    public FixtureDefBuilder chainShape(Vector2[] vertices) {
        for (Vector2 vertex : vertices) {
            /* Same effect as calling toB2DUnits. */
            vertex.scl(1 / Utils.PPM);
        }
        final ChainShape chainShape = new ChainShape();
        chainShape.createChain(vertices);
        fixtureDef.shape = chainShape;
        return this;
    }

    /**
     * Sets the fixture def's friction. Usually between [0,1].
     * Default: 0.2
     *
     * @param friction fixture def's friction.
     * @return this instance.
     * @see FixtureDef#friction
     */
    public FixtureDefBuilder withFriction(float friction) {
        fixtureDef.friction = friction;
        return this;
    }

    /**
     * Sets the restitution (elasticity), usually in the range [0,1].
     * Default: 0
     *
     * @param restitution restitution value, [0,1] range usually.
     * @return this instance.
     * @see FixtureDef#restitution
     */
    public FixtureDefBuilder restitution(float restitution) {
        fixtureDef.restitution = restitution;
        return this;
    }

    /**
     * Sets the fixture def's density. Usually, this value is in kg/m^2.
     * Default: 0f
     *
     * @param density fixture def's density.
     * @return this instance.
     * @see FixtureDef#density
     */
    public FixtureDefBuilder density(float density) {
        fixtureDef.density = density;
        return this;
    }

    /**
     * Sets whether or not this fixture def is a sensor.
     * Default: false
     *
     * @param isSensor whether or not it is a sensor.
     * @return this instance.
     * @see FixtureDef#isSensor
     */
    public FixtureDefBuilder isSensor(boolean isSensor) {
        fixtureDef.isSensor = isSensor;
        return this;
    }

    /**
     * Finish building this {@link FixtureDef} and cleanup for later use.
     *
     * @return {@link FixtureDef} that has been edited.
     */
    public FixtureDef build() {
        final FixtureDef toReturn = this.fixtureDef;
        this.cleanup();
        return toReturn;
    }
}

