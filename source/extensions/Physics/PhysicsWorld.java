package source.extensions.Physics;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Xico
 */

public class PhysicsWorld {

    private static final Vector2 DEFAULT_GRAVITY = new Vector2(0, -9.8f);
    private static final float DEFAULT_TIMESTEP = 1 / 60f;
    private static final int DEFAULT_VELOCITY_ITR = 6;
    private static final int DEFAULT_POSITION_ITR = 2;

    /**
     * @see World#step(float, int, int)}
     */
    private final float timestep;
    /**
     * @see World#step(float, int, int)}
     */
    private final int velocityItr;
    /**
     * @see World#step(float, int, int)}
     */
    private final int positionItr;

    private final World world;

    private final PhysicsDebugger debugRenderer;
    private boolean debugging;

    /**
     * Constructor for {@link PhysicsWorld} where every config value is passed.
     *
     * @param worldWidth  width of the game world in pixels.
     * @param worldHeight height of the game world in pixels.
     * @param gravity     world's gravity.
     * @param timestep    {@link World#step(float, int, int)}'s timestep.
     * @param velItr      {@link World#step(float, int, int)}'s velocity iterations.
     * @param posItr      {@link World#step(float, int, int)}'s position iterations.
     */
    public PhysicsWorld(int worldWidth, int worldHeight, Vector2 gravity, float timestep, int velItr, int posItr) {
        this.timestep = timestep;
        this.velocityItr = velItr;
        this.positionItr = posItr;
        world = new World(gravity, true);
        debugRenderer = new PhysicsDebugger(worldWidth, worldHeight);
        debugging = true;
    }

    /**
     * Constructor for {@link PhysicsWorld} where the gravity is passed.
     *
     * @param worldWidth  width of the game world in pixels.
     * @param worldHeight height of the game world in pixels.
     * @param gravity     world's gravity.
     */
    public PhysicsWorld(int worldWidth, int worldHeight, Vector2 gravity) {
        this(worldWidth, worldHeight, gravity, DEFAULT_TIMESTEP, DEFAULT_VELOCITY_ITR, DEFAULT_POSITION_ITR);
    }

    /**
     * Constructor for {@link PhysicsWorld} where default config values are used.
     *
     * @param worldWidth  width of the game world in pixels.
     * @param worldHeight height of the game world in pixels.
     */
    public PhysicsWorld(int worldWidth, int worldHeight) {
        this(worldWidth, worldHeight, DEFAULT_GRAVITY, DEFAULT_TIMESTEP, DEFAULT_VELOCITY_ITR, DEFAULT_POSITION_ITR);
    }

    public World getWorld() {
        return world;
    }

    public void setDebugging(boolean value) {
        this.debugging = value;
    }

    /**
     * This is crucial to update the {@link PhysicsDebugger#b2dViewport}].
     * Should be called in the resize method of the state/screen that holds this instance.
     *
     * @param width  new width.
     * @param height new height.
     */
    public void resize(int width, int height) {
        debugRenderer.resize(width, height);
    }

    /**
     * @see World#step(float, int, int)
     */
    public void update() {
        world.step(timestep, velocityItr, positionItr);
    }

    public void render() {
        if (debugging) {
            debugRenderer.render(this);
        }
    }
}

