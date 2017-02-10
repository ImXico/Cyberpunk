package source.Transition;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import source.WorldDimensions.WorldDimensions;

/**
 * Transition where two {@link source.State}s are slided simultaneously in the same motion.
 *
 * @author Xico
 */

public class HorizontalSlideTransition implements Transition {

    /**
     * The Left-to-Right or Right-to-Left sliding motions for this transition.
     * Passed on the class' constructor.
     */
    public enum Motion {

        RIGHT_TO_LEFT(
                new Vector2(0f, 0f),
                new Vector2(WorldDimensions.WORLD_WIDTH, 0f),
                new Vector2(-WorldDimensions.WORLD_WIDTH, 0f),
                new Vector2(0f, 0f)
        ),
        LEFT_TO_RIGHT(
                new Vector2(0f, 0f),
                new Vector2(-WorldDimensions.WORLD_WIDTH, 0f),
                new Vector2(WorldDimensions.WORLD_WIDTH, 0f),
                new Vector2(0f, 0f)
        );

        private final Vector2 currentInitialPos;
        private final Vector2 nextInitialPos;
        private final Vector2 currentFinalPos;
        private final Vector2 nextFinalPos;

        Motion(Vector2 currInit, Vector2 nextInit, Vector2 currFinal, Vector2 nextFinal) {
            currentInitialPos = currInit;
            nextInitialPos = nextInit;
            currentFinalPos = currFinal;
            nextFinalPos = nextFinal;
        }
    }

    /**
     * Error margin to check if the target position of the next scene has been reached.
     * This is used because we use {@link Vector2#lerp(Vector, float)}, which makes it needed
     * to work with aproximated values.
     */
    private static final float ERROR_MARGIN = 0.15f;

    private boolean running;
    private float lerp;
    private final Vector2 currentStateCurrentPos;
    private final Vector2 nextStateCurrentPos;
    private final Motion motion;

    /**
     * Constructor for {@link HorizontalSlideTransition}.
     *
     * @param lerp speed of the sliding; ranges between slowest -> ]0, 1] <- fastest.
     */
    public HorizontalSlideTransition(Motion motion, float lerp) {
        this.motion = motion;
        this.lerp = lerp;
        running = false;
        currentStateCurrentPos = motion.currentInitialPos;
        nextStateCurrentPos = motion.nextInitialPos;
    }

    @Override
    public void start() {
        running = true;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    private boolean targetPositionReached() {
        return Math.abs(nextStateCurrentPos.x - 0f) <= ERROR_MARGIN;
    }

    @Override
    public boolean isCompleted() {
        return this.targetPositionReached();
    }

    @Override
    public void finish() {
        running = false;
    }

    @Override
    public void update(float delta) {
        if (!running) return;
        currentStateCurrentPos.lerp(motion.currentFinalPos, lerp);
        nextStateCurrentPos.lerp(motion.nextFinalPos, lerp);
    }

    @Override
    public void render(Batch batch, TextureRegion current, TextureRegion next) {
        batch.begin();
        batch.draw(current, currentStateCurrentPos.x, currentStateCurrentPos.y, WorldDimensions.WORLD_WIDTH, WorldDimensions.WORLD_HEIGHT);
        batch.draw(next, nextStateCurrentPos.x, nextStateCurrentPos.y, WorldDimensions.WORLD_WIDTH, WorldDimensions.WORLD_HEIGHT);
        batch.end();
    }
}
