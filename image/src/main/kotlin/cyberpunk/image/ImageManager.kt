package cyberpunk.image

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion

object ImageManager {

  /**
   * Key for the default [TextureAtlas].
   * It's not unusual to use just one texture atlas. If that's the case, change this field to the desired name,
   * via [load], and [take] can be called without supplying an atlas key.
   */
  @JvmStatic
  var DEFAULT_ATLAS_KEY: String? = null
    private set

  /**
   * Data structure that will hold all loaded [TextureAtlas].
   * When [load] is called, all [TextureAtlas] will be stored here.
   */
  private val atlases: MutableMap<String, TextureAtlas> = mutableMapOf()

  /**
   * Loads a [TextureAtlas] stored in the assets folder under [path] and stores it
   * into [atlases] with the given key.
   * @param key           key that will identify this [TextureAtlas] in the [atlases] structure.
   * @param path          path of the atlas, under the assets folder.
   * @param setAsDefault  whether or not this [TextureAtlas] should be set as the [DEFAULT_ATLAS_KEY].
   */
  @JvmStatic
  @JvmOverloads
  fun load(key: String, path: String, setAsDefault: Boolean = false) {
    val atlas = TextureAtlas(Gdx.files.internal(path))
    atlases[key] = atlas
    if (setAsDefault) {
      DEFAULT_ATLAS_KEY = key
    }
  }

  /**
   * Returns the [TextureAtlas] identified by the given key.
   * @param key key that identifies the [TextureAtlas] in the [atlases] structure.
   * @return the correct [TextureAtlas] or null, in case of failure.
   */
  @JvmStatic
  fun getAtlas(key: String): TextureAtlas? = atlases[key]

  /**
   * Disposes the given [TextureAtlas]. If none matches the supplied [key], this
   * action has no effect at all.
   * This releases all the textures backing all [TextureRegion] and sprites,
   * which should no longer be used after calling this method.
   * @param key key that identifies the [TextureAtlas] in the [atlases] structure.
   */
  @JvmStatic
  fun disposeAtlas(key: String) = atlases[key]?.dispose()

  /**
   * Fetches a [TextureRegion] identified by the given [region] name from a [TextureAtlas]
   * identified by the given [key].
   * If no atlas key is supplied, then [DEFAULT_ATLAS_KEY] will be used - which can be null!
   * @param region  region that identifies the [TextureRegion] wanted.
   * @param key     key that identifies the [TextureAtlas] in the [atlases] structure.
   * @return the [TextureRegion] wanted, or null, in case it was not found.
   */
  @JvmStatic
  @JvmOverloads
  fun take(region: String, key: String? = DEFAULT_ATLAS_KEY): TextureRegion? = atlases[key]?.findRegion(region)

}