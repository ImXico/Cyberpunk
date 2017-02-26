package source.extensions.Physics;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

/**
 * @author Xico
 */

public class BodyDefBuilder {

    private BodyDef bodyDef;

    public BodyDefBuilder() {
        this.cleanup();
    }

    private void cleanup() {
        bodyDef = new BodyDef();
    }

    /**
     * Sets the body's type.
     * Default: {@link BodyDef.BodyType#StaticBody}
     *
     * @param type body type - {@link BodyDef.BodyType}.
     * @return this instance.
     * @see BodyDef#type
     */
    public BodyDefBuilder type(BodyDef.BodyType type) {
        bodyDef.type = type;
        return this;
    }

    /**
     * Sets the body's position to a given position.
     * Default: (0,0)
     *
     * @param position position {@link Vector2} in pixels.
     * @return this instance.
     * @see BodyDef#position
     */
    public BodyDefBuilder position(Vector2 position) {
        bodyDef.position.set(Utils.toB2DUnits(position));
        return this;
    }

    /**
     * Sets the body's position to a given position.
     * Default: (0,0)
     *
     * @param x position x in pixels.
     * @param y position y in pixels.
     * @return this instance.
     * @see BodyDef#position
     */
    public BodyDefBuilder position(float x, float y) {
        return this.position(new Vector2(x, y));
    }

    /**
     * Sers the angle of the body in radians.
     *
     * @param angle angle in radians.
     * @return this instance.
     * @see BodyDef#angle
     */
    public BodyDefBuilder angle(float angle) {
        bodyDef.angle = angle;
        return this;
    }

    /**
     * Sets this bodydef's linear velocity.
     * Default: (0,0)
     *
     * @param linearVelocity target linear velocity.
     * @return this instance.
     * @see BodyDef#linearVelocity
     */
    public BodyDefBuilder linearVelocity(Vector2 linearVelocity) {
        bodyDef.linearVelocity.set(linearVelocity);
        return this;
    }

    /**
     * Sets this bodydef's linear velocity.
     * Default: (0,0)
     *
     * @param x linear velocity x component.
     * @param y linear velocity y component.
     * @return this instance.
     * @see BodyDef#linearVelocity
     */
    public BodyDefBuilder linearVelocity(float x, float y) {
        return linearVelocity(new Vector2(x, y));
    }

    /**
     * Sets this bodydef's angular velocity.
     * Default: 0
     *
     * @param angularVelocity angular velocity of the bodydef.
     * @return this instance.
     */
    public BodyDefBuilder angularVelocity(float angularVelocity) {
        bodyDef.angularVelocity = angularVelocity;
        return this;
    }

    /**
     * Sets the linear damping for this body (used to reduce linear velocity).
     * Default: 0
     *
     * @param linearDamping linear damping amount.
     * @return this instance.
     * @see BodyDef#linearDamping
     */
    public BodyDefBuilder linearDamping(float linearDamping) {
        bodyDef.linearDamping = linearDamping;
        return this;
    }

    /**
     * Sets the angular damping for this body (used to reduce angular velocity).
     * Default: 0
     *
     * @param angularDamping angular damping amount.
     * @return this instance.
     * @see BodyDef#angularDamping
     */
    public BodyDefBuilder angularDamping(float angularDamping) {
        bodyDef.angularDamping = angularDamping;
        return this;
    }

    /**
     * Never fall asleep - increases CPU usage.
     *
     * @return this instance.
     * @see BodyDef#allowSleep
     */
    public BodyDefBuilder dontAllowSleep() {
        bodyDef.allowSleep = false;
        return this;
    }

    /**
     * Sets the body to not be awake when it's spawned.
     * Default: true
     *
     * @return this instance.
     * @see BodyDef#awake
     */
    public BodyDefBuilder notAwakeOnSpawn() {
        bodyDef.awake = false;
        return this;
    }

    /**
     * This bodydef's rotation is set to be fixed.
     * Default: false
     *
     * @return this instance
     * @see BodyDef#fixedRotation
     */
    public BodyDefBuilder fixedRotation() {
        bodyDef.fixedRotation = true;
        return this;
    }

    /**
     * Sets this bodydef's bullet flag to true.
     * This increases CPU usage.
     * Default: false
     *
     * @return this instance.
     * @see BodyDef#bullet
     */
    public BodyDefBuilder bullet() {
        bodyDef.bullet = true;
        return this;
    }

    /**
     * Sets the body to not be active when it's spawned.
     * Default: true
     *
     * @return this instance.
     * @see BodyDef#active
     */
    public BodyDefBuilder notActiveOnSpawn() {
        bodyDef.active = false;
        return this;
    }

    /**
     * Scales the gravity applied to this body.
     * Default: 1
     *
     * @param gravityScale gravity scale.
     * @return this instance.
     * @see BodyDef#gravityScale
     */
    public BodyDefBuilder gravityScale(float gravityScale) {
        bodyDef.gravityScale = gravityScale;
        return this;
    }

    /**
     * Finish building this {@link BodyDef} and cleanup for later use.
     *
     * @return {@link BodyDef} that has been edited.
     */
    public BodyDef build() {
        final BodyDef toReturn = this.bodyDef;
        this.cleanup();
        return toReturn;
    }
}

