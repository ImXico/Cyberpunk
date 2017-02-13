package example.source.background;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import example.source.App;
import example.source.PackValues;
import source.ImageManager.ImageManager;

public class Ground {

    /**
     * The GROUND_WIDTH and GROUND_HEIGHT will be pretty big here to showcase the camera styles.
     */
    private static final float GROUND_WIDTH = App.WORLD_WIDTH * 3f;
    private static final float GROUND_HEIGHT = 220f;

    /**
     * The ground starts at Y = -100 once again to showcase the camera styles; for instance, if the camera
     * is locked on to the hero and the ground started at Y = 0 then it would just look weird.
     */
    private static final float GROUND_Y = -140f;
    private static final Color GROUND_COLOR = new Color(57 / 255f, 155 / 255f, 65 / 255f, 1f);

    private final TextureRegion groundRegion;

    public Ground() {
        groundRegion = ImageManager.take(PackValues.BLANK_QUAD);
    }

    public void render(Batch batch) {
        batch.setColor(GROUND_COLOR);
        batch.draw(groundRegion, 0f, GROUND_Y, GROUND_WIDTH, GROUND_HEIGHT);
    }
}
