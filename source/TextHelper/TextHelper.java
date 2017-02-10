package source.TextHelper;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import source.WorldDimensions.WorldDimensions;

/**
 * @author Xico
 */
public class TextHelper {

    /**
     * Centers a piece of text in the screen and returns it's coordinates under a {@link Vector2}.
     *
     * @param font {@link BitmapFont} used.
     * @param text text to be centered.
     * @return centered coordinates.
     */
    public static Vector2 centerOnScreen(BitmapFont font, String text) {
        final GlyphLayout layout = new GlyphLayout(font, text);
        final float x = (WorldDimensions.WORLD_WIDTH - layout.width) / 2;
        final float y = (WorldDimensions.WORLD_HEIGHT - layout.height) / 2;
        return new Vector2(x, y);
    }

    /**
     * Centers a piece of text horizontally, given the world's width. The text's Y is defined by
     * the given parameter.
     *
     * @param font {@link BitmapFont} used.
     * @param text text to be centered.
     * @param y    text's target Y.
     * @return centered coordinates.
     */
    public static Vector2 centerHorizontally(BitmapFont font, String text, float y) {
        final GlyphLayout layout = new GlyphLayout(font, text);
        final float x = (WorldDimensions.WORLD_WIDTH - layout.width) / 2;
        return new Vector2(x, y);
    }

    /**
     * Centers a piece of text inside an image.
     *
     * @param font      {@link BitmapFont} used.
     * @param text      text to be centered.
     * @param imgWidth  width of the image.
     * @param imgHeight height of the image.
     * @param imgPos    position of the image
     * @return centered coordinates.
     */
    public static Vector2 centerOnImage(BitmapFont font, String text, float imgWidth, float imgHeight, Vector2 imgPos) {
        final GlyphLayout layout = new GlyphLayout(font, text);
        final float x = imgPos.x + ((imgWidth - layout.width) / 2);
        final float y = imgPos.y + ((imgHeight / 2) + (layout.height / 2));
        return new Vector2(x, y);
    }
}
