package engine;

import javax.sound.sampled.*;
import java.io.File;

public class BGM {
    private static String BGM_FILE_PATH;

    static File bgm;
    static AudioInputStream stream;
    static AudioFormat format;
    static DataLine.Info info;
    static Clip bgmClip; // bgmClip 변수 추가
    static Clip enemyShipSpecialbgmCLip;
    private float originalVolume;
    File enemyShipSpecialappearbgm = new File("sound_BackGroundMusic/enemyshipspecial.wav");

    public BGM(){}

    // 디폴트 생성자 추가
    public BGM(String BGM_FILE_PATH) {
        try {
            bgm = new File(BGM_FILE_PATH); // 클래스 변수 할당은 정적 초기화 블록에서 수행
            stream = AudioSystem.getAudioInputStream(bgm);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            bgmClip = (Clip) AudioSystem.getLine(info);
            bgmClip.open(stream);

        } catch (Exception e) {
            System.out.println("err : " + e);
        }
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
            FloatControl control = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
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
            FloatControl control = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(originalVolume);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void bgm_play() {
        // BGM을 재생합니다.
        if (bgmClip != null && !bgmClip.isRunning()) {
            bgmClip.start();
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void bgm_stop() {
        // BGM 재생을 중지합니다.
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
        }
    }
}
