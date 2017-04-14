package source.extensions.TextHelper;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Xico
 */

public class TextHelper {

    /**
     * Centers a piece of text in the screen and returns it's coordinates under a {@link Vector2}.
     *
     * @param font        {@link BitmapFont} used.
     * @param text        text to be centered.
     * @param worldWidth  width of the world (world coordinates).
     * @param worldHeight height of the world (world coorinates).
     * @return centered coordinates.
     */
    public static Vector2 centerOnScreen(BitmapFont font, String text, int worldWidth, int worldHeight) {
        final GlyphLayout layout = new GlyphLayout(font, text);
        final float x = (worldWidth - layout.width) / 2;
        final float y = (worldHeight - layout.height) / 2;
        return new Vector2(x, y);
    }

    /**
     * Centers a piece of text horizontally, given the world's width. The text's Y is defined by
     * the given parameter.
     *
     * @param font       {@link BitmapFont} used.
     * @param text       text to be centered.
     * @param y          text's target Y.
     * @param worldWidth width of the world (world coordinates).
     * @return centered coordinates.
     */
    public static Vector2 centerHorizontally(BitmapFont font, String text, float y, int worldWidth) {
        final GlyphLayout layout = new GlyphLayout(font, text);
        final float x = (worldWidth - layout.width) / 2;
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
