package com.blub.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.blub.Blub_Blub;

public class PetScreen implements Screen {

    Blub_Blub game;
    float x, y;

    private static final float PAUSE_BUTTON_WIDTH = 80;
    private static final float PAUSE_BUTTON_HEIGHT = 80;

    private static final float SPHEAL_WIDTH = 560;
    private static final float SPHEAL_HEIGHT = 560;

    Texture portrait;
    //Pet Standing idle, clicked on head, clicked on belly, clicked on face
    Texture petIdle;
    Texture petPatted;
    Texture petPoked;
    Texture petBooped;
    //Mini-game buttons
    Texture gameButtonActive;
    Texture gameButtonInactive;
    Texture cleanButtonActive;
    Texture cleanButtonInactive;
    Texture feedButtonActive;
    Texture feedButtonInactive;
    //Pause Menu button
    Texture pauseButtonActive;
    Texture pauseButtonInactive;

    //Spheal
    Texture sphealTest;
    Texture sphealPoke;


    public PetScreen(Blub_Blub game) {
        this.game = game;

        portrait = new Texture("PetPortrait.png");
        petIdle = new Texture(Gdx.files.internal("libgdx16.png"));
        petPatted = new Texture(Gdx.files.internal("libgdx16.png"));
        petPoked = new Texture(Gdx.files.internal("libgdx16.png"));
        petBooped = new Texture(Gdx.files.internal("libgdx16.png"));
        gameButtonActive = new Texture(Gdx.files.internal("libgdx16.png"));
        gameButtonInactive = new Texture(Gdx.files.internal("libgdx16.png"));
        cleanButtonActive = new Texture(Gdx.files.internal("libgdx16.png"));
        cleanButtonInactive = new Texture(Gdx.files.internal("libgdx16.png"));
        feedButtonActive = new Texture(Gdx.files.internal("libgdx16.png"));
        feedButtonInactive = new Texture(Gdx.files.internal("libgdx16.png"));
        pauseButtonActive = new Texture(Gdx.files.internal("PauseButtonActive.png"));
        pauseButtonInactive = new Texture(Gdx.files.internal("PauseButtonInactive.png"));
        sphealTest = new Texture(Gdx.files.internal("sphealTest.png"));
        sphealPoke = new Texture(Gdx.files.internal("sphealPoke.png"));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.99f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        //Portrait
        game.batch.draw(portrait, Blub_Blub.WIDTH / 2 - portrait.getWidth() / 4,
            Blub_Blub.HEIGHT / 2 - portrait.getHeight() / 6, portrait.getWidth() / 2, portrait.getHeight() / 2);

        //Sprite

        if((Gdx.input.getX() > Blub_Blub.WIDTH / 2 - SPHEAL_WIDTH / 2 && Gdx.input.getX() < Blub_Blub.WIDTH / 2 +
            SPHEAL_WIDTH / 2 && Blub_Blub.HEIGHT - Gdx.input.getY() > Blub_Blub.HEIGHT / 2 - SPHEAL_HEIGHT / 3
        && Blub_Blub.HEIGHT- Gdx.input.getY() < Blub_Blub.HEIGHT / 2 - SPHEAL_HEIGHT / 3 + SPHEAL_HEIGHT)){
            game.batch.draw(sphealTest, Blub_Blub.WIDTH / 2 - SPHEAL_WIDTH / 2,
                Blub_Blub.HEIGHT / 2 - SPHEAL_HEIGHT / 3, SPHEAL_WIDTH, SPHEAL_HEIGHT);
            if(Gdx.input.isTouched()){
                game.batch.draw(sphealPoke, Blub_Blub.WIDTH / 2 - SPHEAL_WIDTH / 2,
                    Blub_Blub.HEIGHT / 2 - SPHEAL_HEIGHT / 3, SPHEAL_WIDTH, SPHEAL_HEIGHT);
                game.playBonk();

            }
        } else {
            game.batch.draw(sphealTest, Blub_Blub.WIDTH / 2 - SPHEAL_WIDTH / 2,
                Blub_Blub.HEIGHT / 2 - SPHEAL_HEIGHT / 3, SPHEAL_WIDTH, SPHEAL_HEIGHT);
        }


        //Pause Button
        float pauseButtonX = Blub_Blub.WIDTH - 100;
        float pauseButtonY = Blub_Blub.HEIGHT - 100;

        if (Gdx.input.getX() > pauseButtonX && Gdx.input.getX() < pauseButtonX + PAUSE_BUTTON_WIDTH &&
            Blub_Blub.HEIGHT - Gdx.input.getY() > pauseButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
            < pauseButtonY + PAUSE_BUTTON_HEIGHT) {

            game.batch.draw(pauseButtonActive, pauseButtonX, pauseButtonY, PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT);

            //Pause button clicked
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new PauseMenuScreen(game)); //Closes (exits) game
            }
        } else {
            game.batch.draw(pauseButtonInactive, pauseButtonX, pauseButtonY, PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT);
        }if (Gdx.input.getX() > pauseButtonX && Gdx.input.getX() < pauseButtonX + PAUSE_BUTTON_WIDTH &&
            Blub_Blub.HEIGHT - Gdx.input.getY() > pauseButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
            < pauseButtonY + PAUSE_BUTTON_HEIGHT) {

            game.batch.draw(pauseButtonActive, pauseButtonX, pauseButtonY, PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT);

            //Pause button clicked
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.playClick();
                game.setScreen(new PauseMenuScreen(game));
            }
        } else {
            game.batch.draw(pauseButtonInactive, pauseButtonX, pauseButtonY, PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT);
        }
        game.batch.end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
