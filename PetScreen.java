package com.blub.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.blub.Blub_Blub;

import java.io.IOException;

public class PetScreen implements Screen {

        Blub_Blub game;
        float x, y;
        private static final float PAUSE_BUTTON_WIDTH = 80;
        private static final float PAUSE_BUTTON_HEIGHT = 80;

        Texture img;

        //Pet Standing idle, clicked on head, clicked on belly, clicked on face
        Texture petIdle;
        Texture petBack;
        Texture petLeft;
        Texture petRight;
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
        //Background
        Texture backgroundTexture;

        public PetScreen(Blub_Blub game) {
            this.game = game;

            petIdle = new Texture("Alien Fella Front.png");
            petBack = new Texture(Gdx.files.internal("Alien Fella Back.png"));
            petRight = new Texture(Gdx.files.internal("Alien Fella Right Side.png"));
            petLeft = new Texture(Gdx.files.internal("Alien Fella Left Side.png"));
            gameButtonActive = new Texture(Gdx.files.internal("libgdx16.png"));
            gameButtonInactive = new Texture(Gdx.files.internal("libgdx16.png"));
            cleanButtonActive = new Texture(Gdx.files.internal("libgdx16.png"));
            cleanButtonInactive = new Texture(Gdx.files.internal("libgdx16.png"));
            feedButtonActive = new Texture(Gdx.files.internal("libgdx16.png"));
            feedButtonInactive = new Texture(Gdx.files.internal("libgdx16.png"));
            pauseButtonActive = new Texture(Gdx.files.internal("ExitButtonActive.png"));
            pauseButtonInactive = new Texture(Gdx.files.internal("ExitButtonInactive.png"));
            backgroundTexture = new Texture("Blub_Blub_Background.png");

        }


        @Override
        public void show() {

        }

        @Override
        public void render(float v) {
            game.batch.begin();
            //background
            game.batch.draw(backgroundTexture, 0, 0, 1080, 1080);
            //jump when clicked mechanic
            if (Gdx.input.isTouched()){
                game.batch.draw(petIdle, 200, 300);
                game.playJump();

            }
            else {
                game.batch.draw(petIdle, 200, 100);
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
