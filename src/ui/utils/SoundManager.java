package ui.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {

    private Clip clip;

    // Método para carregar e tocar sons curtos
    public void playSoundEffect(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip soundEffect = AudioSystem.getClip();
            soundEffect.open(audioStream);


            soundEffect.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Método para carregar e tocar música em loop
    public void loadAndPlayMusic(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Ajusta o volume
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(-20.0f); // Reduz o volume em decibéis (dB).

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playMusicInLoop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Tocar música em loop
            clip.start();
        }
    }
 
    public void stopMusic() { // Para a música
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}