package source.SpriteHelper;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Xico
 */

public class SpriteHelper {

    /**
     * Centers a [imgWidth x imgHeight] image on the screen.
     *
     * @param imgWidth    image-to-center's width.
     * @param imgHeight   image-to-center's height.
     * @param worldWidth  width of the world (world coordinates).
     * @param worldHeight height of the world (world coordinates).
     * @return centered coordinates.
     */
    public static Vector2 centerOnScreen(float imgWidth, float imgHeight, int worldWidth, int worldHeight) {
        final float x = (worldWidth - imgWidth) / 2;
        final float y = ((worldHeight - imgHeight) / 2);
        return new Vector2(x, y);
    }

    /**
     * Centers a [imgWidth x ?] image horizontally, keeping the original Y.
     *
     * @param imgWidth   image-to-center's width.
     * @param y          image-to-center's Y.
     * @param worldWidth width of the world (world coordinates).
     * @return centered coordinates.
     */
    public static Vector2 centerOnScreenX(float imgWidth, float y, int worldWidth) {
        final float x = (worldWidth - imgWidth) / 2;
        return new Vector2(x, y);
    }

    /**
     * Centers a [? x imgHeight] image vertically, keeping the original X.
     *
     * @param imgHeight   image-to-center's height.
     * @param x           image-to-center's X.
     * @param worldHeight height of the world (world coordinates).
     * @return centered coordinates.
     */
    public static Vector2 centerOnScreenY(float imgHeight, float x, int worldHeight) {
        final float y = ((worldHeight - imgHeight) / 2);
        return new Vector2(x, y);
    }

    /**
     * Centers an [width x height] image inside a [imgW x imgH] image.
     *
     * @param width     image-to-center's width.
     * @param height    image-to-center's height.
     * @param imgPos    outer image's position.
     * @param imgWidth  outer image's width.
     * @param imgHeight outer image's height.
     * @return centered coordinates.
     */
    public static Vector2 centerOnImage(float width, float height, Vector2 imgPos, float imgWidth, float imgHeight) {
        final float x = imgPos.x + ((imgWidth - width) / 2);
        final float y = imgPos.y + ((imgHeight - height) / 2);
        return new Vector2(x, y);
    }

    /**
     * Centers a [size x size] image inside a [imgSize x imgSize] image.
     *
     * @param size    image-to-center's size (width and height are the same).
     * @param imgPos  outer image's position.
     * @param imgSize outer image's size (width and height are the same).
     * @return centered coordinates.
     */
    public static Vector2 centerOnImage(float size, Vector2 imgPos, float imgSize) {
        return centerOnImage(size, size, imgPos, imgSize, imgSize);
    }
}
