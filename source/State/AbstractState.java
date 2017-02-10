package source.State;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

/**
 * Convenience implementation of {@link State}. Derive from this and only override what you need.
 * However, {@link State#update(float)}}, {@link State#render(Batch)} and {@link State#dispose()} must be overridden.
 *
 * @author Xico
 */

abstract public class AbstractState implements State {

    protected AbstractState() {
    }

    @Override
    abstract public void update(float delta);

    @Override
    abstract public void render(Batch batch);

    @Override
    abstract public void dispose();

    @Override
    final public Vector2 unproject(float screenX, float screenY) {
        return unproject(new Vector2(screenX, screenY));
    }

    @Override
    final public Vector2 unproject(Vector2 screenCoordinates) {
        return StateManager.getInstance().getViewport().unproject(screenCoordinates);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
