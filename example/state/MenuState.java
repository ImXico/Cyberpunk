package example.state;

import com.badlogic.gdx.graphics.g2d.Batch;
import source.State.AbstractState;
import source.State.StateManager;
import source.Transition.FadingTransition;
import source.Transition.HorizontalSlideTransition.Motion;
import source.Transition.HorizontalSlideTransition;
import source.Transition.Transition;

public class MenuState extends AbstractState {

    public MenuState() {
        /* Initialize stuff here... */
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        /* Transition 1: Fading transition. */
//         final Transition transition = new FadingTransition(0.5f);
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
        batch.end();
    }

    @Override
    public void dispose() {
        /* Dispose stuff here... */
    }
}
