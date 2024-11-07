package ui.utils;

import javax.sound.sampled.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundManager {

    private Clip clip;

    // Método para carregar e tocar sons curtos
    public void playSoundEffect(String filePath) {
        try {
            InputStream audioSrc = getClass().getResourceAsStream(filePath);
            if (audioSrc == null) {
                System.out.println("Efeito sonoro não encontrado: " + filePath);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(audioSrc));
            Clip soundEffect = AudioSystem.getClip();
            soundEffect.open(audioStream);
            soundEffect.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Carregar e tocar a música de fundo
    public void loadAndPlayMusic(String path) {
        try {
            // Carrega o som usando getResourceAsStream
            InputStream audioSrc = getClass().getResourceAsStream(path);
            if (audioSrc == null) {
                System.out.println("Música não encontrada: " + path);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(audioSrc));
            clip = AudioSystem.getClip(); // Usa o clip da variável de classe
            clip.open(audioStream);

             // Ajusta o volume
             FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
             volumeControl.setValue(-10.0f); // Reduz o volume em decibéis (dB).
             
            clip.start();
        } catch (Exception e) {
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