package com.pockball.pockball.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.pockball.pockball.game_states.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SoundController {
    private static SoundController soundControllerInstance;

    private final Map<String, Sound> soundLibrary;
    private float gameVolume;

    private final Map<Sound, Long> lastTimePlayed;

    private SoundController () {
        soundLibrary = new HashMap<>();
        lastTimePlayed = new HashMap<>();

        // Initialize all sounds
        soundLibrary.put("ballHit", Gdx.audio.newSound(Gdx.files.internal("sounds/ballHit.wav")));
        soundLibrary.put("cueHit", Gdx.audio.newSound(Gdx.files.internal("sounds/cueHit.wav")));
        soundLibrary.put("holeHit", Gdx.audio.newSound(Gdx.files.internal("sounds/holeHit.wav")));
        soundLibrary.put("wallHit", Gdx.audio.newSound(Gdx.files.internal("sounds/wallHit.wav")));
    }

    public static SoundController getInstance() {
        if (soundControllerInstance == null) {
            soundControllerInstance = new SoundController();
            soundControllerInstance.changeGameVolume(100);
        }
        return soundControllerInstance;
    }

    public void playSound (String sound, float volume) {
        Sound snd = soundLibrary.get(sound);

        long time = System.currentTimeMillis();
        if (!lastTimePlayed.containsKey(snd) || time - lastTimePlayed.get(snd) > 10) {
            snd.play(volume*gameVolume);
            lastTimePlayed.put(snd, time);
        }
    }

    public void changeGameVolume(float gameVolume) {
        this.gameVolume = gameVolume;
    }

    public float getGameVolume() {
        return gameVolume;
    }
}
