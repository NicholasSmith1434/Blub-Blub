package com.blub.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.blub.Blub_Blub;

public class PetScreen implements Screen {
    Blub_Blub game;

    int x, y;
    int xSpeed;
    long lastTapTime = 0;
    boolean isMoving = true;

    Player player = new Player(); // added for leveling feature. could be changed for saving feature
    float xp = player.getExperience(); // used for leveling feature. when 1 it means enough experience

    private static final float BUTTON_WIDTH = 80;
    private static final float BUTTON_HEIGHT = 80;
    private static final float ALIEN_WIDTH = 303;
    private static final float ALIEN_HEIGHT = 717;

    float stateTime = 0;

    BitmapFont levelFont;
    int level = player.getLevel(); // used for leveling feature, starts at 1

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
    Texture ballButton;
    //Pause Menu button
    Texture pauseButtonActive;
    Texture pauseButtonInactive;
    //Background
    Texture backgroundTexture;
    SpriteBatch batch;
    Texture alienWalkSheet;
    TextureRegion[] animationFrames;
    float elaspedTime;
    //XP bar
    Texture progressBar;

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
        pauseButtonActive = new Texture(Gdx.files.internal("PauseButtonActive.png"));
        pauseButtonInactive = new Texture(Gdx.files.internal("Pause-Button-PNG.png"));
        backgroundTexture = new Texture("Blub_Blub_Background.png");
        ballButton = new Texture(Gdx.files.internal("ball.png"));
        progressBar = new Texture("progress-bar.png");
        levelFont = new BitmapFont(Gdx.files.internal("fonts/levelFont.fnt")); // used for level feature
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


//        jump when clicked mechanic
//        if (Gdx.input.isTouched()) {
//            game.batch.draw(petIdle, 200, 300);
//            game.playJump();
//        } else {
//            game.batch.draw(petIdle, 200, 100);
//        }

        //Pause Button
        float pauseButtonX = Blub_Blub.WIDTH - 100;
        float pauseButtonY = Blub_Blub.HEIGHT - 130;

        if (Gdx.input.getX() > pauseButtonX && Gdx.input.getX() < pauseButtonX + BUTTON_WIDTH &&
            Blub_Blub.HEIGHT - Gdx.input.getY() > pauseButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
            < pauseButtonY + BUTTON_HEIGHT) {

            game.batch.draw(pauseButtonActive, pauseButtonX, pauseButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);

            //Pause button clicked
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new PauseMenuScreen(game)); //Closes (exits)
            }
        } else {
            game.batch.draw(pauseButtonInactive, pauseButtonX, pauseButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
        // Progress Bar
        game.batch.draw(progressBar, 0, 1040, (Blub_Blub.WIDTH * xp), 20);

        // level
        level = player.getLevel();
        GlyphLayout levelLayout = new GlyphLayout(levelFont,"LEVEL " + level);
        levelFont.draw(game.batch, levelLayout,Blub_Blub.WIDTH/ 2 - levelLayout.width /2, Blub_Blub.HEIGHT - levelLayout.height - 30);


        //ball game button

        float BallGameButtonX = Blub_Blub.WIDTH - 100;
        float BallGameButtonY = Blub_Blub.HEIGHT - 300;

        if (Gdx.input.getX() > BallGameButtonX && Gdx.input.getX() < BallGameButtonX + BUTTON_WIDTH &&
            Blub_Blub.HEIGHT - Gdx.input.getY() > BallGameButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
            < BallGameButtonY + BUTTON_HEIGHT) {

            game.batch.draw(ballButton, BallGameButtonX, BallGameButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);

            //ball button clicked
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new BallScreen(game));  //Opens ball game
            }
        }
        else {
            game.batch.draw(ballButton, BallGameButtonX, BallGameButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        //cleaning game button
        float CleaningGameButtonX = Blub_Blub.WIDTH - 100;
        float CleaningGameButtonY = Blub_Blub.HEIGHT - 400;

        if (Gdx.input.getX() > CleaningGameButtonX && Gdx.input.getX() < CleaningGameButtonX + BUTTON_WIDTH &&
            Blub_Blub.HEIGHT - Gdx.input.getY() > CleaningGameButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
            < CleaningGameButtonY + BUTTON_HEIGHT) {

            game.batch.draw(ballButton, CleaningGameButtonX, CleaningGameButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);

            //clean button clicked
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new HygieneScreen(game)); //Opens cleaning game
            }
        }
        else {
            game.batch.draw(ballButton, CleaningGameButtonX, CleaningGameButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
        }


        //feeding game button
        float FeedingGameButtonX = Blub_Blub.WIDTH - 100;
        float FeedingGameButtonY = Blub_Blub.HEIGHT - 500;

        if (Gdx.input.getX() > FeedingGameButtonX && Gdx.input.getX() < FeedingGameButtonX + BUTTON_WIDTH &&
            Blub_Blub.HEIGHT - Gdx.input.getY() > FeedingGameButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
            < FeedingGameButtonY + BUTTON_HEIGHT) {

            game.batch.draw(ballButton, FeedingGameButtonX, FeedingGameButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);

            //feed button clicked
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new FeedScreen(game)); //Opens feeding game
            }
        }
        else {
            game.batch.draw(ballButton, FeedingGameButtonX, FeedingGameButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
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
    public void resize(int width, int height) {

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
