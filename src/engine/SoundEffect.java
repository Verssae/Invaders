package engine;

import java.io.File;
import java.util.TimerTask;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import java.util.Timer;
import engine.BGM;


public class SoundEffect {

    File shipshootingsound = new File("sound/soundEffect/Shipshooting.wav");
    File shipdestructionsound = new File("sound/soundEffect/Shipdestruction.wav");
    File shipcollisionsound = new File("sound/soundEffect/Shipcollision.wav");
    File enemydestructionsound = new File("sound/soundEffect/Enemydestruction.wav");
    File enemyshootingsound = new File("sound/soundEffect/Enemyshooting.wav");
    File buttonclicksound = new File("sound/soundEffect/ButtonClick.wav");
    File spacebuttonsound = new File("sound/soundEffect/SpaceButton.wav");
    File stagechangesound = new File("sound/soundEffect/StageChange.wav");
    File initialStartSound = new File("sound/soundEffect/initialStart.wav");
    File startSound = new File("sound/soundEffect/start.wav");
    File LaserSound = new File("sound/soundEffect/LaserLaunch.wav");
    File EMPSound = new File("sound/soundEffect/emp2.wav");
    File BlazeSound = new File("sound/soundEffect/flame.wav");
    File PoisonSound = new File("sound/soundEffect/poison.wav");
    File SmogSound = new File("sound/soundEffect/smog.wav");

    private static BGM bgm = new BGM();

    /**
     * Play ship's shooting sound
     */
    public void playStageChangeSound() {
        try{
            if (!bgm.getIsMuted()) {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(stagechangesound));
                clip.start();

                Thread.sleep(1);
            }
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Stage change sound does not played."); }
    }

    public void playShipShootingSound() {
        try{
            if(!bgm.getIsMuted()) {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(shipshootingsound));
                clip.start();

                Thread.sleep(1);
            }
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Missile shooting sound does not played."); }
    }

    /**
     * Play ship's destruction sound
     */
    public void playShipDestructionSound() {
        try{
            if(!bgm.getIsMuted()) {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(shipdestructionsound));
                clip.start();

                Thread.sleep(1);
            }
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Ship's destroyed sound does not played."); }
    }

    /**
     * Play ship's collision sound
     */
    public void playShipCollisionSound() {
        try{
            if(!bgm.getIsMuted()){
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(shipcollisionsound));
            clip.start();

            Thread.sleep(1);
            }
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Ship's attacked sound does not played."); }
    }
    /**
     * Play enemy's destruction sound
     */
    public void playEnemyDestructionSound() {
        try{
            if(!bgm.getIsMuted()) {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(enemydestructionsound));
                clip.start();

                Thread.sleep(1);
            }
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Enemy's destroyed sound does not played."); }
    }

    /**
     * Play enemy's shooting sound
     */
    public void playEnemyShootingSound() {
        try{
            if(!bgm.getIsMuted()) {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(enemyshootingsound));
                clip.start();

                Thread.sleep(1);
            }
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Enemy's bullet sound does not played."); }
    }
    // sound for button moving sound
    public void playButtonClickSound() {
        try{
            if(!bgm.getIsMuted()) {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(buttonclicksound));
                clip.start();

                Thread.sleep(1);
            }
        }
        catch(Exception e) { System.err.println("SOUND ERROR: Button Click sound error."); }
    }
    // sound for spacebar key
    public void playSpaceButtonSound() {
        try {
            if(!bgm.getIsMuted()) {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(spacebuttonsound));
                clip.start();

                Thread.sleep(1);
            }
        } catch (Exception e) {
            System.err.println("SOUND ERROR: Space Key sound error.");
        }
    }

    /**
     * Play Laser sound
     */
    public void playLaserSound() {
        try {
            if(!bgm.getIsMuted()) {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(LaserSound));
                clip.start();

                Thread.sleep(1);
            }
        } catch (Exception e) {
            System.err.println("SOUND ERROR: Laser sound error.");
        }
    }

    public void playEMPSound() {
        try {
            if(!bgm.getIsMuted()) {
                Clip empclip = AudioSystem.getClip();
                empclip.open(AudioSystem.getAudioInputStream(EMPSound));
                empclip.start();

                Thread.sleep(1);
            }
        } catch (Exception e) {
            System.err.println("SOUND ERROR: Laser sound error.");
        }
    }

    public void playBlazeSound() {
        try {
            if(!bgm.getIsMuted()) {
                Clip empclip = AudioSystem.getClip();
                empclip.open(AudioSystem.getAudioInputStream(BlazeSound));
                empclip.start();

                Thread.sleep(1);
            }
        } catch (Exception e) {
            System.err.println("SOUND ERROR: Laser sound error.");
        }
    }

    public void playPoisonSound() {
        try {
            if(!bgm.getIsMuted()) {
                Clip empclip = AudioSystem.getClip();
                empclip.open(AudioSystem.getAudioInputStream(PoisonSound));
                empclip.start();

                Thread.sleep(1);
            }
        } catch (Exception e) {
            System.err.println("SOUND ERROR: Laser sound error.");
        }
    }

    public void playSmogSound() {
        try {
            if(!bgm.getIsMuted()) {
                Clip empclip = AudioSystem.getClip();
                empclip.open(AudioSystem.getAudioInputStream(SmogSound));
                empclip.start();

                Thread.sleep(1);
            }
        } catch (Exception e) {
            System.err.println("SOUND ERROR: Laser sound error.");
        }
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
            if(!bgm.getIsMuted()) {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(initialStartSound));
                clip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Play game start sound
     *
     *
     */
    public void startSound() {
        try {
            if(!bgm.getIsMuted()) {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(startSound));
                clip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playInitialStartSoundWithDelay() {
        Timer timer = new Timer();
        timer.schedule(new InitialStartSoundTask(), 2000); // 2000ms = 2 sec
    }

    class InitialStartSoundTask extends TimerTask {
        @Override
        public void run() {
            initialStartSound();
        }
    }


    /**
     * Play Enemyshipspecial's destruction sound
     */
    public void enemyshipspecialDestructionSound(){
        try {
            if(!bgm.getIsMuted()) {
                String soundFilePath = "sound/soundEffect/enemyshipspecialdestructionsound.wav";
                File soundFile = new File(soundFilePath).getAbsoluteFile();
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile.getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
