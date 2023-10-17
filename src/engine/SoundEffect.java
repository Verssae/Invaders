package engine;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;

public class SoundEffect {

    File shipshootingsound = new File("sound/soundEffect/Shipshooting.wav");
    File shipdestructionsound = new File("sound/soundEffect/Shipdestruction.wav");
    File shipcollisionsound = new File("sound/soundEffect/Shipcollision.wav");
    File enemydestructionsound = new File("sound/soundEffect/Enemydestruction.wav");
    File enemyshootingsound = new File("sound/soundEffect/Enemyshooting.wav");

    /**
     * Play ship's shooting sound
     */
    public void playShipShootingSound() {
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(shipshootingsound));
            clip.start();

            Thread.sleep(1);
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Missile shooting sound does not played."); }
    }

    /**
     * Play ship's destruction sound
     */
    public void playShipDestructionSound() {
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(shipdestructionsound));
            clip.start();

            Thread.sleep(1);
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Ship's destroyed sound does not played."); }
    }

    /**
     * Play ship's collision sound
     */
    public void playShipCollisionSound() {
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(shipcollisionsound));
            clip.start();

            Thread.sleep(1);
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Ship's attacked sound does not played."); }
    }
    /**
     * Play enemy's destruction sound
     */
    public void playEnemyDestructionSound() {
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(enemydestructionsound));
            clip.start();

            Thread.sleep(1);
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Enemy's destroyed sound does not played."); }
    }

    /**
     * Play enemy's shooting sound
     */
    public void playEnemyShootingSound() {
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(enemyshootingsound));
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
