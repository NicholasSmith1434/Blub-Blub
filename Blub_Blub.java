package com.blub;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blub.Screens.MainMenuScreen;
import com.blub.Screens.PauseMenuScreen;
import com.blub.Screens.PetScreen;
import com.blub.Screens.SaveScreen;


public class  Blub_Blub extends Game {
    public static final int WIDTH = 1080; 
    public static final int HEIGHT = 1080;

    public SpriteBatch batch;

    public static Music backgroundMusic;
    public static Sound click;
    public static Sound jump;

    private PauseMenuScreen pauseMenuScreen;
    private MainMenuScreen mainMenuScreen;
    private SaveScreen saveScreen;
    private PetScreen petScreen;

    // different screens are assigned to numbers
    public final static int MENU = 0; 
    public final static int PAUSE = 1; 
    public final static int PET = 2; 
    public final static int SOUND = 3; // this line is not really needed since there is no different screen for sound
    public final static int SAVE = 4; 


    @Override
    public void create() {
        //Load music file (once)
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("LoungeLover.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.45f);
        backgroundMusic.play();

        //Load SFXs
        click = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
        jump = Gdx.audio.newSound(Gdx.files.internal("boing.mp3"));


        batch = new SpriteBatch();
        setScreen(new MainMenuScreen(this)); 

    }
    public void changeScreen(int screen){ // switchcase used to change screens 
        switch(screen){
            case MENU:
                if(mainMenuScreen == null) mainMenuScreen = new MainMenuScreen(this); // if there is no mainMenuScreen then a new MainMenuScreen is created
                this.setScreen(mainMenuScreen); // the screen is set to main menu screen
                break; 
            case PAUSE: 
                if(pauseMenuScreen == null) pauseMenuScreen = new PauseMenuScreen(this); // if there is no pauseMenuScreen then a new PauseMenuScreen is created
                this.setScreen(pauseMenuScreen); // the screen is set to pause menu screen
                break;
            case PET:
                if(petScreen == null) petScreen = new PetScreen(this);  // if there is no petScreen then a new PetScreen is created
                this.setScreen(petScreen);  // the screen is set to pet screen
                break;
            case SAVE:
                if(saveScreen == null) saveScreen = new SaveScreen(this);  // if there is no saveScreen then a new SaveScreen is created
                this.setScreen(saveScreen); // the screen is set to save screen
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
        //System.out.print("" + Gdx.graphics.getDeltaTime()); --> Used to count delta time
    }

    @Override
    public void dispose() {
        backgroundMusic.dispose();
        jump.dispose();
        click.dispose();
        batch.dispose();
    }

}
