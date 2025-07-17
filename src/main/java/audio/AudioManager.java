package audio; 

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioManager {
    private static Clip backgroundClip;
    private static Clip jumpClip;
    private static Clip oneShotClip;

    /**
     * Metodo di preload per il salto
     */
    public static void preloadJumpSound() {
        if (jumpClip == null) {
            try {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(AudioManager.class.getResource("/audio/jump.wav"));
                jumpClip = AudioSystem.getClip();
                jumpClip.open(audioIn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo per audio jump
     */
    public static void playJumpSound() {
        if (jumpClip != null) {
            if (jumpClip.isRunning()) {
                jumpClip.stop(); // resetta per evitare suoni accavallati
            }
            jumpClip.setFramePosition(0);
            jumpClip.start();
        }
    }


    /**
     *  Suona musica di gioco in loop
     * @param path path al file musica
     */
    public static void playBackgroundMusic(String path) {
        stopBackgroundMusic(); // ferma se già attiva
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(AudioManager.class.getResource(path));
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioIn);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  Ferma la musica di gioco
     */
    public static void stopBackgroundMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
            backgroundClip.close();
        }
    }

    /**
     *  Pausa (senza chiudere il Clip)
     */
    public static void pauseBackgroundMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
        }
    }

    /**
     *  Riprende da dove era stata messa in pausa
     */
    public static void resumeBackgroundMusic() {
        if (backgroundClip != null && !backgroundClip.isRunning()) {
            backgroundClip.start();
        }
    }

    /**
     *  Suona una musica **una sola volta** (senza interferire con la BGM)
     * @param path path al file musica
     */
    public static void playOneShotMusic(String path) {
        new Thread(() -> {
            try {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(AudioManager.class.getResource(path));
                Clip tempClip = AudioSystem.getClip();
                tempClip.open(audioIn);
                synchronized (AudioManager.class) {
                    if (oneShotClip != null && oneShotClip.isRunning()) {
                        oneShotClip.stop();
                        oneShotClip.close();
                    }
                    oneShotClip = tempClip;
                }
                tempClip.start();
                // attende che termini per liberare risorse
                Thread.sleep(tempClip.getMicrosecondLength() / 1000);
                tempClip.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Ferma tutta la musica in corso (background + one-shot)
     */
    public static void stopMusic() {
        stopBackgroundMusic();
        synchronized (AudioManager.class) {
            if (oneShotClip != null && oneShotClip.isRunning()) {
                oneShotClip.stop();
                oneShotClip.close();
                oneShotClip = null;
            }
        }
    }

}
