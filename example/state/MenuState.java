package example.state;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import source.State.AbstractState;
import source.State.StateManager;
import source.TextHelper.TextHelper;
import source.Transition.FadingTransition;
import source.Transition.HorizontalSlideTransition.Motion;
import source.Transition.HorizontalSlideTransition;
import source.Transition.Transition;

public class MenuState extends AbstractState {

    private static final String SAMPLE_TEXT = "This is the menu state!";

    private final BitmapFont font;
    private final Vector2 textPosition;

    public MenuState() {
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        textPosition = TextHelper.centerOnScreen(font, SAMPLE_TEXT);
    }

    @Override
    public boolean keyDown(int keycode) {
        /* Transition 1: Fading transition. */
//        final Transition transition = new FadingTransition();
        /* Transition 2: HorizontalSlide transition, left-to-right. */
        final Transition transition = new HorizontalSlideTransition(Motion.LEFT_TO_RIGHT, 0.2f);
        StateManager.getInstance().setState(new PlayState(), transition);
        return true;
    }

    @Override
    public void update(float delta) {
        /* Update stuff here... */
    }

    @Override
    public void render(Batch batch) {
        batch.setProjectionMatrix(StateManager.getInstance().getCamera().combined);
        batch.begin();
        font.draw(batch, SAMPLE_TEXT, textPosition.x, textPosition.y);
        batch.end();
    }

    @Override
    public void dispose() {
        /* Dispose stuff here... */
    }
}
