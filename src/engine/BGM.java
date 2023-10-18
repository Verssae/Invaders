package engine;

import javax.sound.sampled.*;
import java.io.File;

public class BGM {
//    private static String BGM_FILE_PATH;

    static Clip OutGame_bgmCLip; // bgmClip 변수 추가
    static Clip InGame_bgmCLip;

    static Clip enemyShipSpecialbgmCLip;

    private float originalVolume;

    File enemyShipSpecialappearbgm = new File("sound_BackGroundMusic/enemyshipspecial.wav");

    public BGM() {

        try {
            String OutGame_bgm_FilePATH = "sound_BackGroundMusic/gamescreen_bgm.wav";
            File OutGame_bgm = new File(OutGame_bgm_FilePATH).getAbsoluteFile();
            AudioInputStream OutGame_Stream = AudioSystem.getAudioInputStream(OutGame_bgm);
            AudioFormat OutGame_Format = OutGame_Stream.getFormat();
            DataLine.Info OutGame_Info = new DataLine.Info(Clip.class, OutGame_Format);

            OutGame_bgmCLip = (Clip) AudioSystem.getLine(OutGame_Info);
            OutGame_bgmCLip.open(OutGame_Stream);
//           bgm_volumedowm(); // 삭제 검토중
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            String InGame_bgm_FilePATH = "sound_BackGroundMusic/outside_screen_bgm.wav";
            File InGame_bgm = new File(InGame_bgm_FilePATH).getAbsoluteFile();
            AudioInputStream InGame_Stream = AudioSystem.getAudioInputStream(InGame_bgm);
            AudioFormat InGame_Format = InGame_Stream.getFormat();
            DataLine.Info InGame_Info = new DataLine.Info(Clip.class, InGame_Format);

            InGame_bgmCLip = (Clip) AudioSystem.getLine(InGame_Info);
            InGame_bgmCLip.open(InGame_Stream);
        } catch(Exception e) {
            e.printStackTrace();
        }
//        try {
//            bgm = new File(BGM_FILE_PATH); // 클래스 변수 할당은 정적 초기화 블록에서 수행
//            stream = AudioSystem.getAudioInputStream(bgm);
//            format = stream.getFormat();
//            info = new DataLine.Info(Clip.class, format);
//            bgmClip = (Clip) AudioSystem.getLine(info);
//            bgmClip.open(stream);
//
//        } catch (Exception e) {
//            System.out.println("err : " + e);
//        }
    }
    /**
     * Play enemyShipSpecial appear BGM
     */
    public void enemyShipSpecialbgm_play(){
        try{
            AudioInputStream enemyShipSpecialStream = AudioSystem.getAudioInputStream(enemyShipSpecialappearbgm);
            AudioFormat enemyShipSpecialFormat = enemyShipSpecialStream.getFormat();
            DataLine.Info enemyShipSpecialInfo = new DataLine.Info(Clip.class, enemyShipSpecialFormat);

            enemyShipSpecialbgmCLip = (Clip) AudioSystem.getLine(enemyShipSpecialInfo);
            enemyShipSpecialbgmCLip.open(enemyShipSpecialStream);
            bgm_volumedowm();
            enemyShipSpecialbgmCLip.start();
            enemyShipSpecialbgmCLip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Stop enemyShipSpecial appear BGM
     */
    public void enemyShipSpecialbgm_stop(){
        try {
            if (enemyShipSpecialbgmCLip != null && enemyShipSpecialbgmCLip.isRunning()) {
                enemyShipSpecialbgmCLip.stop();
                bgm_volumeup();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * BGM volume down
     */
    public void bgm_volumedowm(){
        try {
            FloatControl control = (FloatControl) InGame_bgmCLip.getControl(FloatControl.Type.MASTER_GAIN);
            originalVolume = control.getValue();
            control.setValue(-10.0f);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * BGM volume up
     */
    public void bgm_volumeup(){
        try {
            FloatControl control = (FloatControl) InGame_bgmCLip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(originalVolume);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void InGame_bgm_play(){
//            bgm_volumedowm();
        InGame_bgmCLip.start();
        InGame_bgmCLip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void OutGame_bgm_play() {

        if (OutGame_bgmCLip != null && !OutGame_bgmCLip.isRunning()) {
            OutGame_bgmCLip.start();
            OutGame_bgmCLip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

//    public void bgm_play() {
//        // BGM을 재생합니다.
//        if (bgmClip != null && !bgmClip.isRunning()) {
//            bgmClip.start();
//            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
//        }
//    }

    public void OutGame_bgm_stop() {
        // BGM 재생을 중지합니다.
        try {
            if (OutGame_bgmCLip != null && OutGame_bgmCLip.isRunning()) {
                OutGame_bgmCLip.stop();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void InGame_bgm_stop() {
        // BGM 재생을 중지합니다.
        try {
            if (InGame_bgmCLip != null && InGame_bgmCLip.isRunning()) {
                InGame_bgmCLip.stop();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}