package source.extensions.ImageManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

/** @author Xico */

public class ImageManager {

    /**
     * Default key for the texture atlas.
     * It's not unusual to use just one texture atlas. If that's the case, change this field to the desired name
     * and {@link ImageManager#take(String)} can be called instead of {@link ImageManager#take(String, String)}.
     */
    private static String DEFAULT_ATLAS_KEY;

    /**
     * Holds all texture atlases.
     */
    private static Map<String, TextureAtlas> atlases = new HashMap<String, TextureAtlas>();

    /**
     * Loads an atlas stored in the assets folder and stores it into the atlases with the given key.
     *
     * @param key          - key that will identify this atlas in the {@link #atlases}.
     * @param path         - path of the atlas in the assets folder.
     * @param setAsDefault - {@code true} if this atlas should be set as the DEFAULT_ATLAS_KEY.
     */
    public static void loadAtlas(String key, String path, boolean setAsDefault) {
        atlases.put(key, new TextureAtlas(Gdx.files.internal(path)));
        if (setAsDefault) {
            DEFAULT_ATLAS_KEY = key;
        }
    }

    /**
     * Overloads {@link #loadAtlas(String, String, boolean)}.
     */
    public static void loadAtlas(String key, String path) {
        loadAtlas(key, path, false);
    }

    /**
     * Returns the atlas identified by the given key.
     *
     * @param key - key that identifies the atlas.
     * @return - texture atlas, or null if it could not be found.
     * @throws - No such atlas found.
     */
    public static TextureAtlas getAtlas(String key) {
        try {
            return atlases.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Takes a certain region inside a certain atlas.
     *
     * @param atlasKey   - key that identifies the atlas.
     * @param regionName - region name.
     * @return - {@link TextureRegion}, or null if it could not be found.
     * @throws - Region not found inside the atlas.
     */
    public static TextureRegion take(String regionName, String atlasKey) {
        try {
            return atlases.get(atlasKey).findRegion(regionName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Overloads the {@link #take(String, String)} method.
     * If not specified, the atlas searched is the one identified by {@link #DEFAULT_ATLAS_KEY}.
     */
    public static TextureRegion take(String regionName) {
        return take(regionName, DEFAULT_ATLAS_KEY);
    }
}
