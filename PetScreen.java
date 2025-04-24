package com.blub.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.blub.Blub_Blub;



import java.io.IOException;

public class PetScreen implements Screen {


    Blub_Blub game;
        int x, y;
        int xSpeed;
        long lastTapTime = 0;
        boolean isMoving = true;
        private static final float ALIEN_WIDTH = 303;
        private static final float ALIEN_HEIGHT = 717;
        private static final float PAUSE_BUTTON_WIDTH = 80;
        private static final float PAUSE_BUTTON_HEIGHT = 80;
        private static final float BALL_BUTTON_WIDTH = 80;
        private static final float BALL_BUTTON_HEIGHT = 80;
        float stateTime = 0;

        Texture img;

        //Pet Standing idle, clicked on head, clicked on belly, clicked on face\
       // Texture alienWalkSheet;
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
        Texture ballButtonActive;
        Texture ballButtonInactive;
        //Pause Menu button
        Texture pauseButtonActive;
        Texture pauseButtonInactive;
        //Background
        Texture backgroundTexture;
        SpriteBatch batch;
        Texture alienWalkSheet;
        TextureRegion[] animationFrames;
        float elaspedTime;

        public PetScreen(Blub_Blub game) {
            this.game = game;
           // alienWalkSheet = new Texture("Alien_Fella_Sprite.png");
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
            pauseButtonActive = new Texture(Gdx.files.internal("Pause-Button-PNG.png"));
            pauseButtonInactive = new Texture(Gdx.files.internal("Pause-Button-PNG.png"));
            backgroundTexture = new Texture("Blub_Blub_Background.png");
            ballButtonActive = new Texture(Gdx.files.internal("ball.png"));
            ballButtonInactive = new Texture(Gdx.files.internal("ball.png"));



        }



        @Override
        public void show() {
            y = 100;

        }


        @Override
        public void render(float v) {
            elaspedTime += Gdx.graphics.getDeltaTime();
            game.batch.begin();

            // background
            game.batch.draw(backgroundTexture, 0, 0, 1080, 1080);

            int baseSpeed = 5;

            // Only move if allowed
            if (isMoving) {
                x += xSpeed;
                // Bounce back at edges
                if (x <= 100) {
                    x = 100;
                    xSpeed = baseSpeed;
                }
                else if (x >= 600) {
                    x = 600;
                    xSpeed = -baseSpeed;
                }
            }
            if (Gdx.input.justTouched()) {
                Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());

                if (touchPos.x >= x && touchPos.x <= x + ALIEN_WIDTH &&
                    touchPos.y >= y && touchPos.y <= y + ALIEN_HEIGHT) {
                    isMoving = !isMoving;
                    long currentTime = TimeUtils.millis();
                    lastTapTime = currentTime;
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                jump();
            }
                else {
                    resetJump();
                }

            if (!isMoving) {
                game.batch.draw(petIdle, x, y, 537, 798);
            } else {
                if (xSpeed > 0) {
                    game.batch.draw(petRight, x, y, 303, 717);
                } else {
                    game.batch.draw(petRight, x + 303, y ,-303, 717);
                }
            }



            //jump when clicked mechanic
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
            }

            //ball game button

            float BallGameButtonX = Blub_Blub.WIDTH - 100;
            float BallGameButtonY = Blub_Blub.HEIGHT - 300;

            if (Gdx.input.getX() > BallGameButtonX && Gdx.input.getX() < BallGameButtonX + BALL_BUTTON_WIDTH &&
                Blub_Blub.HEIGHT - Gdx.input.getY() > BallGameButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
                < BallGameButtonY + BALL_BUTTON_HEIGHT) {

                game.batch.draw(ballButtonActive, BallGameButtonX, BallGameButtonY, BALL_BUTTON_WIDTH, BALL_BUTTON_HEIGHT);

                //Pause button clicked
                if (Gdx.input.isTouched()) {
                    this.dispose();
                    game.setScreen(new BallGameScreen(game)); //Closes (exits) game
                }
            }
            else {
                game.batch.draw(ballButtonInactive, BallGameButtonX, BallGameButtonY, BALL_BUTTON_WIDTH, BALL_BUTTON_HEIGHT);
            }

            game.batch.end();

        }

        private void jump() {
            y += 200;
            game.playJump();
        }
        private void resetJump() {
            y = 100;
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
