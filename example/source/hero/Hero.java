package example.source.hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;

import example.source.PackValues;
import source.ImageManager.ImageManager;

public class Hero {

    private static final float MOVEMENT_AMOUNT = 4;
    private static final float HERO_SIZE = 64;
    private static final float FRAME_DURATION = 1 / 10f;

    /**
     * The hero's position.
     */
    private final Vector2 position;

    /**
     * The region that's rendered when the hero is not moving.
     */
    private final TextureRegion idleFrame;

    /**
     * Whether or not the hero is moving.
     * Defines if we render the idle frame or the animation frames.
     */
    private boolean isMoving;

    /**
     * Animation-related variables.
     */
    private Animation animation;
    private TextureAtlas walkingAnimationAtlas;
    private float elapsedTime;

    public Hero(Vector2 position) {
        this.position = position;
        isMoving = false;
        idleFrame = ImageManager.take(PackValues.HERO_IDLE);
        walkingAnimationAtlas = ImageManager.getAtlas(PackValues.HERO_WALKING_PACK);
        animation = new Animation(FRAME_DURATION, walkingAnimationAtlas.getRegions());
        elapsedTime = 0f;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void moveRight() {
        this.isMoving = true;
    }

    public void setMoving(boolean value) {
        this.isMoving = value;
    }

    public void update() {
        if (isMoving) {
            this.position.x += MOVEMENT_AMOUNT;
        }
        elapsedTime += Gdx.graphics.getDeltaTime();
    }

    public void render(Batch batch) {
        batch.setColor(Color.WHITE);
        final TextureRegion regionToRender = isMoving ? animation.getKeyFrame(elapsedTime, true) : idleFrame;
        batch.draw(regionToRender, position.x, position.y, HERO_SIZE, HERO_SIZE);
    }
}
