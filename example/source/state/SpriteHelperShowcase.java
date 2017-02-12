package example.source.state;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import source.ImageManager.ImageManager;
import source.SpriteHelper.SpriteHelper;
import source.State.AbstractState;
import source.State.StateManager;

import static example.source.PackValues.*;

public class SpriteHelperShowcase extends AbstractState {

    private static final float ALIEN_WIDTH = 50f;
    private static final float ALIEN_HEIGHT = 50f;

    private final TextureRegion alienGreen;
    private final Vector2 alienGreenPos;

    private final TextureRegion alienBlue;
    private final Vector2 alienBluePos;

    private final TextureRegion alienPink;
    private final Vector2 alienPinkPos;

    private final TextureRegion alienBeige;
    private final Vector2 alienBeigePos;

    private final TextureRegion sampleQuad;
    private final float quadWidth;
    private final float quadHeight;
    private final Vector2 quadPosition;

    public SpriteHelperShowcase() {
        /* Green alien + its position. */
        alienGreen = ImageManager.take(ALIEN_GREEN);
        alienGreenPos = SpriteHelper.centerOnScreen(ALIEN_WIDTH, ALIEN_HEIGHT);
        /* Blue alien + its position. */
        alienBlue = ImageManager.take(ALIEN_BLUE);
        alienBluePos = SpriteHelper.centerOnScreenX(ALIEN_WIDTH, 220f);
        /* Pink alien + its position. */
        alienPink = ImageManager.take(ALIEN_PINK);
        alienPinkPos = SpriteHelper.centerOnScreenY(ALIEN_HEIGHT, 70f);
        /* Beige alien + its position + a sample quad image - the beige alien will be centered on it. */
        sampleQuad = ImageManager.take(BLANK_QUAD);
        quadWidth = 70f;
        quadHeight = 70f;
        quadPosition = new Vector2(500f, 120f);
        alienBeige = ImageManager.take(ALIEN_BEIGE);
        alienBeigePos = SpriteHelper.centerOnImage(ALIEN_WIDTH, ALIEN_HEIGHT, quadPosition, quadWidth, quadHeight);
    }

    @Override
    public void update(float delta) {
        /* Update stuff here... */
    }

    @Override
    public void render(Batch batch) {
        batch.begin();
        batch.draw(alienGreen, alienGreenPos.x, alienGreenPos.y, ALIEN_WIDTH, ALIEN_HEIGHT);
        batch.draw(alienBlue, alienBluePos.x, alienBluePos.y, ALIEN_WIDTH, ALIEN_HEIGHT);
        batch.draw(alienPink, alienPinkPos.x, alienPinkPos.y, ALIEN_WIDTH, ALIEN_HEIGHT);
        batch.draw(sampleQuad, quadPosition.x, quadPosition.y, quadWidth, quadHeight);
        batch.draw(alienBeige, alienBeigePos.x, alienBeigePos.y, ALIEN_WIDTH, ALIEN_HEIGHT);
        batch.end();
    }

    @Override
    public void dispose() {
        /* Dispose stuff here... */
    }
}
