package source.AudioManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Xico
 */

public class AudioManager {

    /**
     * When not specified, all sounds are played with a default volume.
     * Volume should belong to the ]0, 1] interval.
     */
    private static final float DEFAULT_VOLUME = 0.5f;

    /**
     * Holds all sounds/songs.
     */
    private static Map<String, Sound> sounds = new HashMap<String, Sound>();

    /**
     * Loads a sound onto the {@link #sounds} map.
     *
     * @param soundName key that will identify this sound in the map.
     * @param path      sounds's path inside the assets folder.
     */
    public static void loadSound(String soundName, String path) {
        final Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
        sounds.put(soundName, sound);
    }

    /**
     * Plays the given sound at the specified volume.
     *
     * @param soundName key that identifies the sound in the map.
     * @param volume    audio volume; ranges between ]0, 1].
     * @throws - No such audio found.
     */
    public static void playSound(String soundName, float volume) {
        try {
            sounds.get(soundName).play(volume);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Overloads the {@link #playSound(String, float)} method.
     * When no volume is specified, use {@link #DEFAULT_VOLUME}.
     */
    public static void playSound(String soundName) {
        playSound(soundName, DEFAULT_VOLUME);
    }

    /**
     * Stops all instances of the sound identified by the given name.
     *
     * @param soundName key that identifies the sound in the map.
     * @throws - No such audio found exception.
     */
    public static void stopSound(String soundName) {
        try {
            sounds.get(soundName).stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Pauses all instances of the given sound.
     *
     * @param soundName key that identifies the sound in the map.
     * @throws - No such audio found exception.
     */
    public static void pauseSound(String soundName) {
        try {
            sounds.get(soundName).pause();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Resumes all (paused) instances of the given sound.
     *
     * @param soundName key that identifies the sound in the map.
     * @throws - No such audio found exception.
     */
    public static void resumeSound(String soundName) {
        try {
            sounds.get(soundName).resume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loops the given sound with the explicitly declared volume.
     *
     * @param soundName key that identifies the sound in the [sounds] map.
     * @param volume    volume for the sound to be played.
     * @throws - No such audio found exception.
     */
    public static void loopSound(String soundName, float volume) {
        try {
            sounds.get(soundName).loop(volume);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Overloads the {@link #loopSound(String, float)} methods, passing {@link #DEFAULT_VOLUME} as the volume.
     */
    public static void loopSound(String soundName) {
        loopSound(soundName, DEFAULT_VOLUME);
    }
}
