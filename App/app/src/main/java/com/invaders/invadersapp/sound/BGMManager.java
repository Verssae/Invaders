package com.invaders.invadersapp.sound;

import android.content.Context;
import android.media.MediaPlayer;

import com.invaders.invadersapp.R;

public class BGMManager {
    private static BGMManager iBGMManager;
    public MediaPlayer mMediaPlayerForGameScreenBGM;
    public MediaPlayer mMediaPlayerForButtonClick;
    public MediaPlayer mMediaPlayerForButtonMove;

    public BGMManager(Context context) {
        mMediaPlayerForGameScreenBGM = MediaPlayer.create(context, R.raw.gamescreen_bgm);
        mMediaPlayerForButtonClick = MediaPlayer.create(context, R.raw.space_button);
        mMediaPlayerForButtonMove = MediaPlayer.create(context, R.raw.button_click);

        mMediaPlayerForGameScreenBGM.setLooping(true);
    }

    public static BGMManager getBGMManagerInstance(Context context) {
        if (iBGMManager == null) {
            iBGMManager = new BGMManager(context);
        }

        return iBGMManager;
    }
}
