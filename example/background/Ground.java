package example.background;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import source.ImageManager.ImageManager;
import source.WorldDimensions.WorldDimensions;

public class Ground {

    private static final float GROUND_HEIGHT = 180f;
    private static final float GROUND_Y = -100f;
    private static final Color GRASS_COLOR = new Color(57 / 255f, 155 / 255f, 65 / 255f, 1f);

    private final TextureRegion groundRegion;

    public Ground() {
        groundRegion = ImageManager.take("blank-quad");
    }

    public void render(Batch batch) {
        batch.setColor(GRASS_COLOR);
        /* Huge width to showcase the camera styles. */
        batch.draw(groundRegion, 0f, GROUND_Y, WorldDimensions.WORLD_WIDTH * 3f, GROUND_HEIGHT);
    }
}
