package com.blub.lwjgl3.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.blub.lwjgl3.Blub_Blub;

public class MainMenuScreen implements Screen {

    Blub_Blub game;

    //Button width-height constants
    private static final int PLAY_BUTTON_WIDTH = 432;
    private static final int PLAY_BUTTON_HEIGHT = 216;
    private static final int EXIT_BUTTON_WIDTH = 216;
    private static final int EXIT_BUTTON_HEIGHT = 108;
    private static final int EXIT_BUTTON_Y = 108;
    private static final int PLAY_BUTTON_Y = 540;

    //Texture objects for images
    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture title;

    public MainMenuScreen(Blub_Blub game) {
        this.game = game;

        //Start / Exit buttons that will be loaded
        playButtonActive = new Texture(Gdx.files.internal("blank")); //Image names must be filled
        playButtonInactive = new Texture(Gdx.files.internal("blank"));
        exitButtonActive = new Texture(Gdx.files.internal("blank"));
        exitButtonInactive = new Texture(Gdx.files.internal("blank"));
        title = new Texture(Gdx.files.internal("blank"));
    }

    @Override
    public void show() {

    }

    //Loads images/Screens
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5F, 0.5F, 0.5F, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.batch.begin();

        //X and Y serve as coordinates to where the images will be in game
        //NOTE: 540 is half of 1080. Parameters after X and Y are Width and Height for scaling.
        //To center an image, image width divided by 2, and subtract the image's width (vice versa height)
        //Y = 0 is at the TOP of the screen

        game.batch.draw(title, (float) Blub_Blub.WIDTH / 2, Blub_Blub.HEIGHT);

        //Exit button
        int x = Blub_Blub.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;
        if(Gdx.input.getX() > x && Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Blub_Blub.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y && Blub_Blub.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT) {
            game.batch.draw(exitButtonActive, (float) Blub_Blub.WIDTH / 2 - EXIT_BUTTON_WIDTH, (float) Blub_Blub.HEIGHT / 2 - EXIT_BUTTON_HEIGHT, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(exitButtonInactive, (float) Blub_Blub.WIDTH / 2 - EXIT_BUTTON_WIDTH, (float) Blub_Blub.HEIGHT / 2 - EXIT_BUTTON_HEIGHT, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }

        //Play button
        x = Blub_Blub.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
        if(Gdx.input.getX() > x && Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Blub_Blub.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y && Blub_Blub.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT) {
            game.batch.draw(playButtonActive, (float) Blub_Blub.WIDTH / 2 - PLAY_BUTTON_WIDTH, (float) Blub_Blub.HEIGHT / 2 - PLAY_BUTTON_HEIGHT, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new PetScreen(game));
            }
        } else {
            game.batch.draw(playButtonInactive, (float) Blub_Blub.WIDTH / 2 - PLAY_BUTTON_WIDTH, (float) Blub_Blub.HEIGHT / 2 - PLAY_BUTTON_HEIGHT, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }
        game.batch.end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {

    }
}

//To put in the images manually, go to the Android, then Assets folder
