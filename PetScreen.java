package com.blub.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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

    private static final float PAUSE_BUTTON_WIDTH = 80;
    private static final float PAUSE_BUTTON_HEIGHT = 80;
    private static final float BALL_BUTTON_WIDTH = 80;
    private static final float BALL_BUTTON_HEIGHT = 80;
    private static final float HYGIENE_BUTTON_WIDTH = 80;
    private static final float HYGIENE_BUTTON_HEIGHT = 80;
    private static final float FEED_BUTTON_WIDTH = 80;
    private static final float FEED_BUTTON_HEIGHT = 80;
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
    //Mini-game buttons
    Texture ballButton;
    Texture hygieneButton;
    Texture feedButton;
    //Pause Menu button
    Texture pauseButton;
    //Background
    Texture backgroundTexture;
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
        pauseButton = new Texture(Gdx.files.internal("PauseButton.png"));
        backgroundTexture = new Texture("Blub_Blub_Background.png");
        ballButton = new Texture("ball.png");
        hygieneButton = new Texture(Gdx.files.internal("soap.png"));
        feedButton = new Texture(Gdx.files.internal("bread.png"));
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

        //Background
        game.batch.draw(backgroundTexture, 0, 0, 1080, 1080);

        int baseSpeed = 2;

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

        if (Gdx.input.getX() > pauseButtonX && Gdx.input.getX() < pauseButtonX + PAUSE_BUTTON_WIDTH &&
            Blub_Blub.HEIGHT - Gdx.input.getY() > pauseButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
            < pauseButtonY + PAUSE_BUTTON_HEIGHT) {

            game.batch.draw(pauseButton, pauseButtonX, pauseButtonY, PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT);

            //Pause button clicked
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.playClick();
                game.setScreen(new PauseMenuScreen(game, Blub_Blub.PET)); //Sends current screen to Pause for saving
            }
        } else {
            game.batch.draw(pauseButton, pauseButtonX, pauseButtonY, PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT);
        }

        // Progress Bar
        game.batch.draw(progressBar, 0, 1040, (Blub_Blub.WIDTH * xp), 20);

        //Level
        level = player.getLevel();
        GlyphLayout levelLayout = new GlyphLayout(levelFont,"LEVEL " + level);
        levelFont.draw(game.batch, levelLayout,Blub_Blub.WIDTH/ 2 - levelLayout.width /2, Blub_Blub.HEIGHT - levelLayout.height - 30);


        //Ball Button
        float ballButtonX = Blub_Blub.WIDTH - 100;
        float ballButtonY = Blub_Blub.HEIGHT - 300;

        if (Gdx.input.getX() > ballButtonX && Gdx.input.getX() < ballButtonX + BALL_BUTTON_WIDTH &&
            Blub_Blub.HEIGHT - Gdx.input.getY() > ballButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
            < ballButtonY + BALL_BUTTON_HEIGHT) {

            game.batch.draw(ballButton, ballButtonX, ballButtonY, BALL_BUTTON_WIDTH, BALL_BUTTON_HEIGHT);

            //Pause button clicked
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.playClick();
                game.setScreen(new BallScreen(game)); //Closes (exits) game
            }
        } else {
            game.batch.draw(ballButton, ballButtonX, ballButtonY, BALL_BUTTON_WIDTH, BALL_BUTTON_HEIGHT);
        }

        //Hygiene Button
        float hygieneButtonX = Blub_Blub.WIDTH - 100; //Same as pause button (100px from right)
        float hygieneButtonY = Blub_Blub.HEIGHT - 400;

        if (Gdx.input.getX() > hygieneButtonX && Gdx.input.getX() < hygieneButtonX + HYGIENE_BUTTON_WIDTH &&
            Blub_Blub.HEIGHT - Gdx.input.getY() > hygieneButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
            < hygieneButtonY + HYGIENE_BUTTON_HEIGHT) {

            game.batch.draw(hygieneButton, hygieneButtonX, hygieneButtonY, HYGIENE_BUTTON_WIDTH, HYGIENE_BUTTON_HEIGHT);

            // Bath button clicked
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.playClick();
                game.setScreen(new HygieneScreen(game));
            }
        } else {
            game.batch.draw(hygieneButton, hygieneButtonX, hygieneButtonY, HYGIENE_BUTTON_WIDTH, HYGIENE_BUTTON_HEIGHT);
        }

        //Feed Button
        float feedButtonX = Blub_Blub.WIDTH - 100; //Same as pause button (100px from right)
        float feedButtonY = Blub_Blub.HEIGHT - 500;

        if (Gdx.input.getX() > feedButtonX && Gdx.input.getX() < feedButtonX + FEED_BUTTON_WIDTH &&
            Blub_Blub.HEIGHT - Gdx.input.getY() > feedButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
            < feedButtonY + FEED_BUTTON_HEIGHT) {

            game.batch.draw(feedButton, feedButtonX, feedButtonY, FEED_BUTTON_WIDTH, FEED_BUTTON_HEIGHT);

            //Feed button clicked
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.playClick();
                game.setScreen(new FeedScreen(game));
            }
        } else {
            game.batch.draw(feedButton, feedButtonX, feedButtonY, FEED_BUTTON_WIDTH, FEED_BUTTON_HEIGHT);
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
