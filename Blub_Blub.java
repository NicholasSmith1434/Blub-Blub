package com.blub_blub.ver01;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blub_blub.ver01.Screens.*;


public class Blub_Blub extends Game {
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 1080;

    public SpriteBatch batch;

    private PauseSettings pauseSettings;
    private PauseMenuScreen pauseMenuScreen;
    private PlayScreen playScreen;
    private MainMenuScreen mainMenuScreen;
    private SaveScreen saveScreen;
    private MusicANDSoundScreen soundScreen;

    public final static int MENU = 0;
    public final static int PAUSE = 1;
    public final static int PLAY = 2;
    public final static int SOUND = 3;
    public final static int SAVE = 4;

    public void changeScreen(int screen){
        switch(screen){
            case MENU:
                if(mainMenuScreen == null) mainMenuScreen = new MainMenuScreen(this);
                this.setScreen(mainMenuScreen);
                break;
            case PAUSE:
                if(pauseMenuScreen == null) pauseMenuScreen = new PauseMenuScreen(this);
                this.setScreen(pauseMenuScreen);
                break;
            case PLAY:
                if(playScreen == null) playScreen = new PlayScreen(this);
                this.setScreen(playScreen);
                break;
            case SAVE:
                if(saveScreen == null) saveScreen = new SaveScreen(this);
                this.setScreen(saveScreen);
                break;
            case SOUND:
                if(soundScreen == null) soundScreen = new MusicANDSoundScreen(this);
                this.setScreen(playScreen);
                break;
        }
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new PauseMenuScreen(this));

        pauseSettings = new PauseSettings();
    }

    public PauseSettings getPauseSettings() {
        return this.pauseSettings;
    }

}
