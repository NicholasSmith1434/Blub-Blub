package com.blub;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.blub.Screens.PetScreen;

public class MainMenuScreen implements Screen {

    Blub_Blub game;

    //Button width-height constants
    private static final int PLAY_BUTTON_WIDTH = 432;
    private static final int PLAY_BUTTON_HEIGHT = 216;
    private static final int EXIT_BUTTON_WIDTH = 216;
    private static final int EXIT_BUTTON_HEIGHT = 108;
    private static final int TITLE_WIDTH = 810;
    private static final int TITLE_HEIGHT = 280;

    //Texture objects for images
    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture title;

    public MainMenuScreen(Blub_Blub game) {
        this.game = game;

        //Start / Exit buttons that will be loaded
        playButtonActive = new Texture(Gdx.files.internal("PlayButtonActive.png")); //Image names must be filled
        playButtonInactive = new Texture(Gdx.files.internal("PlayButtonInactive.png"));
        exitButtonActive = new Texture(Gdx.files.internal("ExitButtonActive.png"));
        exitButtonInactive = new Texture(Gdx.files.internal("ExitButtonInactive.png"));
        title = new Texture(Gdx.files.internal("BlubBlubTitle.png"));
    }

    @Override
    public void show() {

    }

    //Loads images/Screens
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.99F, 0.9F, 0.9F, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.batch.begin();

        //X and Y serve as coordinates to where the images will be in game
        //NOTE: 540 is half of 1080. Parameters after X and Y are Width and Height for scaling.
        //To center an image, image width divided by 2, and subtract the image's width (vice versa height)
        //Y = 0 is at the TOP of the screen

        //Title
        game.batch.draw(title, (float) Blub_Blub.WIDTH / 2 - TITLE_WIDTH / 2, Blub_Blub.HEIGHT / 2f + TITLE_HEIGHT / 2, TITLE_WIDTH, TITLE_HEIGHT);

        //Exit button
        //Calc the x to center the Exit button horizontally
        float exitButtonX = Blub_Blub.WIDTH / 2f - EXIT_BUTTON_WIDTH / 2f;

        //Calc the y for Exit button
        float playButtonY = Blub_Blub.HEIGHT / 2f - PLAY_BUTTON_HEIGHT / 2f;
        float exitButtonY = playButtonY - PLAY_BUTTON_HEIGHT / 2f - EXIT_BUTTON_HEIGHT;

        //Mouse over Exit button
        if (Gdx.input.getX() > exitButtonX && Gdx.input.getX() < exitButtonX + EXIT_BUTTON_WIDTH &&
            Blub_Blub.HEIGHT - Gdx.input.getY() > exitButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
            < exitButtonY + EXIT_BUTTON_HEIGHT) {

            game.batch.draw(exitButtonActive, exitButtonX, exitButtonY, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);

            //Exit button clicked
            if (Gdx.input.isTouched()) {
                Gdx.app.exit(); //Closes (exits) game
            }
        } else {
            game.batch.draw(exitButtonInactive, exitButtonX, exitButtonY, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }

        //Play button
        //Calc x and y positions to center Play button
        float playButtonX = Blub_Blub.WIDTH / 2f - PLAY_BUTTON_WIDTH / 2f;

        //Mouse over Play button
        if (Gdx.input.getX() > playButtonX && Gdx.input.getX() < playButtonX + PLAY_BUTTON_WIDTH &&
            Blub_Blub.HEIGHT - Gdx.input.getY() > playButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
            < playButtonY + PLAY_BUTTON_HEIGHT) {

            game.batch.draw(playButtonActive, playButtonX, playButtonY, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);

            //Play button clicked
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new PetScreen(game));
            }
        } else {
            game.batch.draw(playButtonInactive, playButtonX, playButtonY, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
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
