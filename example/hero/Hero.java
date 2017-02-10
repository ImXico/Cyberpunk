package example.hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;

import source.ImageManager.ImageManager;

public class Hero {

    private static final float MOVEMENT_AMOUNT = 4;
    private static final float HERO_SIZE = 64;

    private final Vector2 position;

    private final TextureRegion idleFrame;

    private boolean isMoving;
    private Animation animation;
    private TextureAtlas atlas;
    private float elapsedTime;

    public Hero(Vector2 position) {
        this.position = position;
        isMoving = false;
        idleFrame = ImageManager.take("adventurer_stand");
        atlas = ImageManager.getAtlas("heroFrames");
        animation = new Animation(1 / 10f, atlas.getRegions());
        elapsedTime = 0f;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void moveRight() {
        setMoving(true);
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
