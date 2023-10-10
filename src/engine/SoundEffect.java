package engine;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;

public class SoundEffect implements SoundEffect_if {

    File missileshootingsound = new File("res/Bullet.wav");
    File shipdestroyedsound = new File("res/Shipdestroyed.wav");
    File shipdeattackedsound = new File("res/Shipattacked.wav");
    File enemydestroyedsound = new File("res/Enemydestroyed.wav");
    File enemybulletsound = new File("res/Enemybullet.wav");


    public void playMissileShootingSound() {
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(missileshootingsound));
            clip.start();

            Thread.sleep(1);
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Missile shooting sound does not played."); }
    }

    public void playShipDestroyedSound() {
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(shipdestroyedsound));
            clip.start();

            Thread.sleep(1);
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Ship's destroyed sound does not played."); }
    }

    public void playShipAttackedSound() {
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(shipdeattackedsound));
            clip.start();

            Thread.sleep(1);
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Ship's attacked sound does not played."); }
    }

    public void playEnemyDestroyedSound() {
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(enemydestroyedsound));
            clip.start();

            Thread.sleep(1);
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Enemy's destroyed sound does not played."); }
    }

    public void playEnemyBulletSound() {
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(enemybulletsound));
            clip.start();

            Thread.sleep(1);
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Enemy's bullet sound does not played."); }
    }
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
