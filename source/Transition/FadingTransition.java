package source.Transition;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import source.WorldDimensions.WorldDimensions;

/**
 * @author Xico
 */

public class FadingTransition implements Transition {

    /** The default fading speed. */
    private static final float DEFAULT_SPEED = 0.5f;

    private static final float DEFAULT_ALPHA_INC = 0.3f;

    private boolean running;
    private float speed;

    private float alpha;
    /** Alpha increment at each frame. */
    private float alphaInc;
    private float maxAlpha;

    /**
     * Constructor for {@link FadingTransition} where the speed is specified.
     *
     * @param speed how fast the fading goes.
     */
    public FadingTransition(float speed) {
        this.speed = speed;
        running = false;
        alpha = 0f;
        alphaInc = this.calculateAlphaInc();
        maxAlpha = 1f - alphaInc;
    }

    /**
     * Constructor for {@link FadingTransition} where the speed is the default one.
     *
     * @see {@value DEFAULT_SPEED}
     */
    public FadingTransition() {
       this(DEFAULT_SPEED);
    }

    /**
     * Calculates the alpha increment to be used at each frame based on the speed parameter.
     *
     * @return alpha increment.
     */
    private float calculateAlphaInc() {
        return (this.speed * DEFAULT_ALPHA_INC) / DEFAULT_SPEED;
    }

    @Override
    public void start() {
        running = true;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public boolean isCompleted() {
        return !running;
    }

    @Override
    public void finish() {
        running = false;
    }

    @Override
    public void update(float delta) {
        if (!running) return;
        if (alpha <= maxAlpha) {
            alpha += alphaInc;
        } else {
            running = false;
        }
    }

    @Override
    public void render(Batch batch, TextureRegion current, TextureRegion next) {
        batch.begin();
        batch.setColor(1f, 1f, 1f, 1f - alpha);
        batch.draw(current, 0, 0, WorldDimensions.WORLD_WIDTH, WorldDimensions.WORLD_HEIGHT);
        batch.setColor(1f, 1f, 1f, alpha);
        batch.draw(next, 0, 0, WorldDimensions.WORLD_WIDTH, WorldDimensions.WORLD_HEIGHT);
        batch.end();
    }
}
