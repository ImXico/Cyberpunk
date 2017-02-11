package example.state;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import example.background.Background;
import example.background.Ground;
import example.hero.Hero;
import source.Camera.CameraStyles;
import source.State.AbstractState;
import source.State.StateManager;
import source.TextHelper.TextHelper;
import source.WorldDimensions.WorldDimensions;

public class PlayState extends AbstractState {

    private static final String SAMPLE_TEXT = "And this is the play state!";

    private final BitmapFont font;
    private final Vector2 textPosition;

    private final Hero hero;
    private final Background background;
    private final Ground ground;

    public PlayState() {
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        textPosition = TextHelper.centerHorizontally(font, SAMPLE_TEXT, 260f);
        hero = new Hero(new Vector2(30f, 80f));
        background = new Background(10);
        ground = new Ground();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            hero.moveRight();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            hero.setMoving(false);
            return true;
        }
        return false;
    }

    @Override
    public void update(float delta) {
        hero.update();
        /* Style 1: Center on screen. */
//        CameraStyles.centerOnScreen(StateManager.getInstance().getCamera(), WorldDimensions.WORLD_WIDTH, WorldDimensions.WORLD_HEIGHT);
        /* Style 2: Lock on target, ridigly. */
//        CameraStyles.lockOnTarget(StateManager.getInstance().getCamera(), hero.getPosition());
        /* Style 3: Smoothly lerp to target. */
        CameraStyles.lerpToTarget(StateManager.getInstance().getCamera(), hero.getPosition(), 0.075f);
    }

    @Override
    public void render(Batch batch) {
        batch.setProjectionMatrix(StateManager.getInstance().getCamera().combined);
        batch.begin();
//        font.draw(batch, SAMPLE_TEXT, textPosition.x, textPosition.y);
        background.render(batch);
        ground.render(batch);
        hero.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        /* Dispose stuff here... */
    }
}
