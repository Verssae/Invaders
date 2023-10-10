package engine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;


public class SoundEffect implements SoundEffect_if {

    public void SoundEffect_play(){

    }
    public void SoundEffect_stop(){

    }


    /**
     * Play initial game start sound
     *
     *
     */
    public void initialStartSound() {
        try {
            String soundFilePath = "#";
            File soundFile = new File(soundFilePath).getAbsoluteFile();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile.getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Play game end sound - when lost all lives
     *
     *
     */
    public void endSound() {
        try {
            String soundFilePath = "sound/soundEffect/gameEnding.wav";
            File soundFile = new File(soundFilePath).getAbsoluteFile();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile.getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public void play() {
//        if (clip != null) {
//            clip.setFramePosition(0);
//            clip.start();
//        }
//    }
//
//    public void stop() {
//        if (clip != null) {
//            clip.stop();
//        }
//    }


}
