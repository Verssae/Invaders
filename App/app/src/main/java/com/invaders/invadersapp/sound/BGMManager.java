package com.invaders.invadersapp.sound;

import android.content.Context;
import android.media.MediaPlayer;

import com.invaders.invadersapp.R;

/**
 * This class manages the background music and sound effects for the game.
 * It includes MediaPlayer instances for different sound types such as game screen BGM,
 * button click, and button move sounds.
 */
public class BGMManager {
    private static BGMManager iBGMManager; // Singleton instance of BGMManager

    // MediaPlayer instances for different sounds
    public MediaPlayer mMediaPlayerForGameScreenBGM;
    public MediaPlayer mMediaPlayerForButtonClick;
    public MediaPlayer mMediaPlayerForButtonMove;

    /**
     * Constructor initializes the media players with the respective sound files.
     * Sets the game screen BGM to loop continuously.
     *
     * @param context The context used to create MediaPlayer instances.
     */
    public BGMManager(Context context) {
        mMediaPlayerForGameScreenBGM = MediaPlayer.create(context, R.raw.gamescreen_bgm);
        mMediaPlayerForButtonClick = MediaPlayer.create(context, R.raw.space_button);
        mMediaPlayerForButtonMove = MediaPlayer.create(context, R.raw.button_click);

        mMediaPlayerForGameScreenBGM.setLooping(true); // Setting the game BGM to loop
    }

    /**
     * Singleton pattern implementation for getting the instance of BGMManager.
     * Ensures that only one instance of BGMManager is created and used throughout the application.
     *
     * @param context The context used to create the BGMManager if it does not exist.
     * @return The singleton instance of BGMManager.
     */
    public static BGMManager getBGMManagerInstance(Context context) {
        if (iBGMManager == null) {
            iBGMManager = new BGMManager(context);
        }
        return iBGMManager;
    }
}
