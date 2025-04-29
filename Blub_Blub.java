package com.blub;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blub.Screens.*;

public class Blub_Blub extends Game{

    //Constants of screen size
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 1080;

    public SpriteBatch batch;

    public static Music backgroundMusic;
    public static Sound click;
    public static Sound jump;

    private PauseSettings pauseSettings;
    private PauseMenuScreen pauseMenuScreen;
    private PetScreen petScreen;
    private MainMenuScreen mainMenuScreen;
    private SaveScreen saveScreen;
    private BallScreen ballScreen;
    private FeedScreen feedScreen;
    private HygieneScreen hygieneScreen;
    private Player player; // added for leveling feature

    public final static int MENU = 0;
    public final static int PAUSE = 1;
    public final static int PLAY = 2;
    public final static int SOUND = 3;
    public final static int SAVE = 4;
    public final static int BALL = 5;
    public final static int FEED = 6;
    public final static int HYGIENE = 7;


    //Loads Main Menu off the start
    @Override
    public void create() {
        //Load music file (once)
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("LoungeLover.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.45f);
        backgroundMusic.play();

        //Load SFXs
        click = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
        jump = Gdx.audio.newSound(Gdx.files.internal("bonk.mp3"));

        pauseSettings = new PauseSettings();

        batch = new SpriteBatch();

        setScreen(new MainMenuScreen(this));
    }

    public PauseSettings getPauseSettings(){
        return this.pauseSettings;
    }

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
                if(petScreen == null) petScreen = new PetScreen(this);
                this.setScreen(petScreen);
                break;
            case SAVE:
                if(saveScreen == null) saveScreen = new SaveScreen(this);
                this.setScreen(saveScreen);
                break;
            case BALL:
                if(ballScreen == null) ballScreen = new BallScreen(this);
                this.setScreen(ballScreen);
                break;
            case FEED:
                if(feedScreen == null) feedScreen = new FeedScreen(this);
                this.setScreen(feedScreen);
                break;
            case HYGIENE:
                if(hygieneScreen == null) hygieneScreen = new HygieneScreen(this);
                this.setScreen(hygieneScreen);
                break;

        }
    }


    public void playClick(){
        click.play();
    }

    public void playJump(){
        long id = jump.play(1.0f);
        jump.setPitch(id, 1.5f);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        backgroundMusic.dispose();
        click.dispose();
        batch.dispose();
    }
}

