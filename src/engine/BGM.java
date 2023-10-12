package engine;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import java.io.File;

public class BGM {
    private static String BGM_FILE_PATH;

    static File bgm;
    static AudioInputStream stream;
    static AudioFormat format;
    static DataLine.Info info;
    static Clip bgmClip; // bgmClip 변수 추가

    File redenemyappearbgm = new File("sound_BackGroundMusic/monsterappear.wav");


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

    public void play_redenemybgm(){
        if(bgmClip != null){
            bgmClip.stop();
        }

        try{
            AudioInputStream redEnemyStream = AudioSystem.getAudioInputStream(redenemyappearbgm);
            AudioFormat redEnemyFormat = redEnemyStream.getFormat();
            DataLine.Info redEnemyInfo = new DataLine.Info(Clip.class, redEnemyFormat);

            bgmClip = (Clip) AudioSystem.getLine(redEnemyInfo);
            bgmClip.open(redEnemyStream);

            bgmClip.start();
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
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
