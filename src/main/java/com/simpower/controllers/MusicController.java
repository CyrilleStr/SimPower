package com.simpower.controllers;

import com.simpower.Main;
import com.simpower.models.JSONReader;
import org.json.simple.JSONObject;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class MusicController {
    private JSONReader settingsData = new JSONReader();
    private Clip clip = null;

    public MusicController() {
        try {
            this.clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void setVolume(float volume) {
        if (volume < 0f || volume > 0.1f) throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(40f * (float) Math.log10(volume));
    }

    /**
     * Plays the background music
     */
    public void play() {
        AudioInputStream AIS = null;
        try {
            AIS = AudioSystem.getAudioInputStream(new File(Main.class.getResource("assets/music/main.wav").toURI()));
        } catch (IOException | URISyntaxException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

        try {
            this.clip.open(AIS);
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        JSONObject settings = this.settingsData.read("data/settings.json");
        this.setVolume(Float.parseFloat(settings.get("SoundVolume").toString())); // should be between -80 && 6.0206
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop()  {
        this.clip.stop();
    }
}
