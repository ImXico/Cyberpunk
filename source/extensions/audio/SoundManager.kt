package source.extensions.audio

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound

object SoundManager {

  /**
   * Data structure that will hold every single [Sound].
   * When [load] is called, the sounds get stored here.
   */
  private val sounds: MutableMap<String, Sound> = mutableMapOf()

  /**
   * Creates and loads a [Sound] onto the [sounds] structure.
   *
   * @param soundName key that will identify this sound in the map.
   * @param path      sounds's path inside the assets folder.
   */
  fun load(soundName: String, path: String) {
    val sound: Sound = Gdx.audio.newSound(Gdx.files.internal(path))
    sounds.put(soundName, sound)
  }

  /**
   * Plays the given sound, if present in the [sounds] map.
   * If no volume is specified (the second parameter), then the sound will be played with
   * the default volume, 1f.
   *
   * @param soundName key that identifies the sound in the map.
   * @param volume    audio volume; ranges between [0, 1] and it's optional.
   * @param pitch     the pitch multiplier; ranges between [0.5 (slower), 2.0 (faster)] and it's optional.
   * @param pan       sound panning; ranges between [-1 (full left), 1 (full right)] and it's optional.
   * @return a [Long], id, which is unique for every call to [play]. In case of failure, returns -1.
   */
  @JvmOverloads
  fun play(soundName: String, volume: Float = 1f, pitch: Float = 1f, pan: Float = 0f): Long {
    return sounds[soundName]?.play(volume, pitch, pan) ?: -1
  }

  /**
   * Plays the given sound, if present in the [sounds] map.
   * If no volume is specified (the second parameter), then the sound will be played with
   * the default volume, 1f.
   *
   * @param soundName key that identifies the sound in the map.
   * @param volume    audio volume; ranges between [0, 1] and it's optional.
   * @param pitch     the pitch multiplier; ranges between [0.5 (slower), 2.0 (faster)] and it's optional.
   * @param pan       sound panning; ranges between [-1 (full left), 1 (full right)] and it's optional.
   * @return a [Long], id, which is the id of the sound instance, if successful, or -1 on failure.
   */
  @JvmOverloads
  fun loop(soundName: String, volume: Float = 1f, pitch: Float = 1f, pan: Float = 0f): Long {
    return sounds[soundName]?.loop(volume, pitch, pan) ?: -1
  }

  /**
   * Stops all instances of the sound identified by the given [soundName].
   * If the instance doesn't exist, this method has no effect.
   *
   * @param soundName key that identifies the sound in the map.
   */
  fun stop(soundName: String) = sounds[soundName]?.stop()

  /**
   * Pauses all instances of the sound identified by the given [soundName].
   * If the instance doesn't exist, this method has no effect.
   *
   * @param soundName key that identifies the sound in the map.
   */
  fun pause(soundName: String) = sounds[soundName]?.pause()

  /**
   * Resumes all instances of the sound identified by the given [soundName].
   * If the instance doesn't exist, this method has no effect.
   *
   * @param soundName key that identifies the sound in the map.
   */
  fun resume(soundName: String) = sounds[soundName]?.resume()

  /**
   * Release resources, once you don't need the [Sound] anymore.
   * Accessing the sound after you disposed of it will result in undefined errors.
   *
   * @param soundName key that identifies the sound in the map.
   */
  fun dispose(soundName: String) = sounds[soundName]?.dispose()

}
