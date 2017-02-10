package source.WorldDimensions;

/**
 * Here are stored the virtual world width and height.
 * This should be the first thing to be called on the root App (the one that extends {@link com.badlogic.gdx.ApplicationAdapter})
 * because those dimensions will be used throughout the other libs.
 *
 * @author Xico
 */
public class WorldDimensions {

    public static int WORLD_WIDTH;
    public static int WORLD_HEIGHT;

    public static void set(int width, int height) {
        WORLD_WIDTH = width;
        WORLD_HEIGHT = height;
    }
}
