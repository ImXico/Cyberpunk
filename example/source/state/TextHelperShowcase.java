package example.source.state;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import example.source.App;
import example.source.PackValues;
import source.ImageManager.ImageManager;
import source.State.AbstractState;
import source.TextHelper.TextHelper;

public class TextHelperShowcase extends AbstractState {

    private final BitmapFont font;

    /* Centered text. */
    private final String centeredText;
    private final Vector2 centeredCoordinates;

    /* Horizonatlly centered text, arbitrary Y. */
    private final String centeredHorizonatllyText;
    private final Vector2 centeredHorizontallyCoordinates;

    /* Text centered inside some image. */
    private final TextureRegion sampleImage;
    private final float imageWidth;
    private final float imageHeight;
    private final Vector2 imagePosition;
    private final String centeredOnImageText;
    private final Vector2 centeredOnImageCoordinates;


    public TextHelperShowcase() {
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        /* Centered text. */
        centeredText = "Centered Text!";
        centeredCoordinates = TextHelper.centerOnScreen(font, centeredText, App.WORLD_WIDTH, App.WORLD_HEIGHT);
        /* Horizonatlly centered text, arbitrary Y. */
        centeredHorizonatllyText = "Horizontally Centered Text, Y = 200";
        centeredHorizontallyCoordinates = TextHelper.centerHorizontally(font, centeredHorizonatllyText, 200f, App.WORLD_WIDTH);
        /* Text centered inside some image. */
        sampleImage = ImageManager.take(PackValues.BLANK_QUAD);
        centeredOnImageText = "Centered!";
        imageWidth = 100f;
        imageHeight = 30f;
        imagePosition = new Vector2(300f, 60f);
        centeredOnImageCoordinates = TextHelper.centerOnImage(font, centeredOnImageText, imageWidth, imageHeight, imagePosition);
    }

    @Override
    public void update(float delta) {
        /* Update stuff here... */
    }

    @Override
    public void render(Batch batch) {
        batch.begin();
        /* Draw centered text. */
        font.draw(batch, centeredText, centeredCoordinates.x, centeredCoordinates.y);
        /* Draw text centered horizontally, but with Y = 200. */
        font.draw(batch, centeredHorizonatllyText, centeredHorizontallyCoordinates.x, centeredHorizontallyCoordinates.y);
        /* Draw the sample image and center some text inside it. */
        batch.setColor(Color.PINK);
        batch.draw(sampleImage, imagePosition.x, imagePosition.y, imageWidth, imageHeight);
        font.draw(batch, centeredOnImageText, centeredOnImageCoordinates.x, centeredOnImageCoordinates.y);
        batch.end();
    }

    @Override
    public void dispose() {
        /* Dispose stuff here... */
    }
}
