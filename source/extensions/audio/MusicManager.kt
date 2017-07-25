package source.extensions.audio

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music

object MusicManager {

  /**
   * Data structure that will hold all [Music] instances.
   * Note, however, as stated in the official LibGDX docs, that [Music] instances
   * are heavy, and you should not have more than one or two loaded at most.
   */
  private val tracks: MutableMap<String, Music> = mutableMapOf()

  /**
   * Creates and loads a [Music] object onto the [tracks] structure.
   *
   * @param trackName key that will identify this track in the map.
   * @param path      track's path inside the assets folder.
   */
  @JvmStatic
  fun load(trackName: String, path: String) {
    val track: Music = Gdx.audio.newMusic(Gdx.files.internal(path))
    tracks.put(trackName, track)
  }

  /**
   * Plays the given track, if present in the [tracks] map.
   * If no volume is specified (the second parameter), then the sound will be played with
   * the default volume, 1f.
   * Contrarily to the sound instances, this action doesn't return an unique id.
   *
   * @param trackName key that identifies the track in the map.
   * @param volume    audio volume; ranges between [0, 1] and it's optional.
   */
  @JvmStatic
  @JvmOverloads
  fun play(trackName: String, volume: Float = 1f) {
    tracks[trackName]?.let {
      it.volume = volume
      it.play()
    }
  }

  /**
   * Pauses the given track's playback.
   *
   * @param trackName key that identifies the track in the map.
   */
  @JvmStatic
  fun pause(trackName: String) = tracks[trackName]?.pause()

  /**
   * Stops the given track's playback.
   *
   * @param trackName key that identifies the track in the map.
   */
  @JvmStatic
  fun stop(trackName: String) = tracks[trackName]?.stop()

  /**
   * Repeats the given track's playback until [stop] is called.
   *
   * @param trackName key that identifies the track in the map.
   */
  @JvmStatic
  fun loop(trackName: String) {
    tracks[trackName]?.isLooping = true
  }

  /**
   * Whether or not the given track is playing.
   *
   * @param trackName key that identifies the track in the map.
   * @return whether or not the given track is playing.
   */
  @JvmStatic
  fun isPlaying(trackName: String): Boolean = tracks[trackName]?.isPlaying ?: false

  /**
   * Whether or not the given track is looping.
   *
   * @param trackName key that identifies the track in the map.
   * @return whether or not the given track is looping.
   */
  @JvmStatic
  fun isLooping(trackName: String): Boolean = tracks[trackName]?.isLooping ?: false

  /**
   * Gets this track's current position in seconds.
   *
   * @param trackName key that identifies the track in the map.
   * @return this track's current position in seconds.
   */
  @JvmStatic
  fun getPosition(trackName: String): Float = tracks[trackName]?.position ?: -1f

  /**
   * Sets this track's current position in seconds.
   *
   * @param trackName key that identifies the track in the map.
   * @param position  target position in seconds.
   */
  @JvmStatic
  fun setPosition(trackName: String, position: Float) {
    tracks[trackName]?.position = position
  }

  /**
   * Release resources, once you don't need the [Music] anymore.
   * Accessing the sound after you disposed of it will result in undefined errors.
   *
   * @param trackName key that identifies the track in the map.
   */
  @JvmStatic
  fun dispose(trackName: String) = tracks[trackName]?.dispose()

}