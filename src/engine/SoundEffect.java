package engine;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundEffect implements SoundEffect_if {

    File destroyedSound = new File("res/testsound.wav");
    File missileShootingSound = new File("res/testsound.wav");

    public void playMissileShootingSound() {
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(missileShootingSound));
            clip.start();

            Thread.sleep(1);
        }
        catch(Exception e) { System.err.println(e); }
    }

    public void playDestroyedSound() {
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(destroyedSound));
            clip.start();

            Thread.sleep(1);
        }
        catch(Exception e) { System.err.println(e); }
    }
    public void SoundEffect_play(){

    }
    public void SoundEffect_stop(){

    }
}
