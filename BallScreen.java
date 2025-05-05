package com.blub.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.blub.Blub_Blub;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class BallScreen implements Screen {
    OrthographicCamera camera;
    Blub_Blub game;
    float x,y;
    float spritex,spritey;
    //buttons
    private static final float PAUSE_BUTTON_WIDTH = 80;
    private static final float PAUSE_BUTTON_HEIGHT = 80;
    private static final float BUTTON_WIDTH = 80;
    private static final float BUTTON_HEIGHT = 80;
    //assets
    Sprite petIdle;
    Texture backgroundTexture;
    Texture pauseButton;
    Sprite ball;
    Sprite petAura;
    //hitbox
    Rectangle petAuraHitbox;
    Rectangle ballHitbox;
    float xSpeed, ySpeed;
    Player player = new Player(); // added for leveling feature. could be changed for saving feature
    float xp = player.getExperience(); // used for leveling feature. when 1 it means enough experience points for a level up. experience resets to 0. level plus 1

    BitmapFont levelFont;
    BitmapFont scoreFont;
    int level = player.getLevel(); // used for leveling feature, starts at 1
    //Going Back Button
    Texture leaveButton;
    //XP bar
    Texture progressBar;
    //counter
    int clickCount = 0;
    boolean gameOver = false;
    private static final float SCORE_COUNTER = 80;



    public BallScreen(Blub_Blub game) {
        this.game = game;
        petIdle = new Sprite(new Texture("Alien Fella Front.png"));
        ball = new Sprite(new Texture("ball.png"));
        backgroundTexture = new Texture("outdoorBackground.png");
        pauseButton = new Texture(Gdx.files.internal("PauseButton.png"));
        petAura = new Sprite(new Texture("alienAura.png"));
        petAuraHitbox = new Rectangle();
        ballHitbox = new Rectangle();
        spritex = 0;
        spritey = 0;
        petAura.setPosition(spritex, spritey);
        ball.setPosition(x, y);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1080, 1080);
        leaveButton = new Texture(Gdx.files.internal("LeaveButton.png"));
        progressBar = new Texture("progress-bar.png");
        levelFont = new BitmapFont(Gdx.files.internal("fonts/levelFont.fnt")); // used for level feature
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/levelFont.fnt")); // used for level feature
        xSpeed = 10;
        ySpeed = 1;
        x = 900;
        y = 0;

    }

    @Override
    public void show() {
        petAura.setPosition(0,0);
        ball.setPosition(0,800);
    }
    //ball game class
    public void ballMove() {
        //makes the ball able to not go out of bounds of the screen
        final int SCREEN_SIZE = 1080;
        final int BALL_SIZE = 200;
        if (x > SCREEN_SIZE - BALL_SIZE) xSpeed = -Math.abs(xSpeed);  // Right edge
        if (x < 0) xSpeed = Math.abs(xSpeed);                         // Left edge
        if (y > SCREEN_SIZE - BALL_SIZE) ySpeed = -Math.abs(ySpeed);  // Top edge
        if (y < 0) ySpeed = Math.abs(ySpeed);
        // Update position
        x += xSpeed;
        y += ySpeed;
    }



    @Override
    public void render(float v) {
        // if feed button clicked
        // then xp goes up
        //when xp goes up, we update experience from player using the Player.addExperience(0.1)
        //we also check if there is enough experience to level up with the method in player levelUp()
        Gdx.gl.glClearColor(0.9F, 0.9F, 0.9F, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, 1080, 1080);

        //calls the ball to move
        ballMove();
        petAuraHitbox.set(petAura.getX(), petAura.getY(), petAura.getWidth(), petAura.getHeight());
        ballHitbox.set(x,y,150,150);


        //detects mouse input to make the ball move inverse once clicked
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos); // Convert to game-world coordinates

            if (touchPos.x >= x && touchPos.x <= x + ball.getWidth() && touchPos.y >= y &&
                touchPos.y <= y + ball.getHeight()) {
                xSpeed *=-1;
                game.playBounce();
                clickCount++;
            }
            if(clickCount % 10 == 0 && clickCount != 0){
                player.addExperience(0.1F);// then xp goes up, when xp goes up, we update experience from player using the Player.addExperience(0.1)
                xp = player.getExperience();
                game.batch.draw(progressBar, 0, 1040, (Blub_Blub.WIDTH * xp), 20);
                // level
                player.levelUp();
                level = player.getLevel();
            }
            //debugging
            System.out.println(touchPos + "touched");

        }

        petAura.draw(game.batch);
        game.batch.draw(ball, x, y, 150, 150);


        System.out.println("Ball Hitbox: " + ballHitbox);
        System.out.println("PetAura Hitbox: " + petAuraHitbox);
        System.out.println("overlaps: " + ballHitbox.overlaps(petAuraHitbox));

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
                game.setScreen(new PauseMenuScreen(game, Blub_Blub.BALL)); //Closes (exits) game
            }
        } else {
            game.batch.draw(pauseButton, pauseButtonX, pauseButtonY, PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT);
        }
        //counter
        float counterX = pauseButtonX -200 ; // Right-aligned
        float counterY = pauseButtonY; // Near top
        GlyphLayout counterLayout = new GlyphLayout();
        counterLayout.setText(scoreFont, "Clicks: " + clickCount);
        scoreFont.draw(game.batch, "Clicks: " + clickCount, counterX, counterY);

        //Back Button
        float backButtonX = pauseButtonX - 950;
        float backButtonY = pauseButtonY;

        if (Gdx.input.getX() > backButtonX && Gdx.input.getX() < backButtonX + BUTTON_WIDTH &&
            Blub_Blub.HEIGHT - Gdx.input.getY() > backButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
            < backButtonY + BUTTON_HEIGHT) {

            game.batch.draw(leaveButton, backButtonX, backButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);

            //Back button clicked
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new PetScreen(game)); //Closes (exits)
            }
        } else {
            game.batch.draw(leaveButton, backButtonX, backButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        // Progress Bar
        game.batch.draw(progressBar, 0, 1040, (Blub_Blub.WIDTH * xp), 20);


        GlyphLayout levelLayout = new GlyphLayout(levelFont,"LEVEL " + level);
        levelFont.draw(game.batch, levelLayout,Blub_Blub.WIDTH/ 2 - levelLayout.width /2, Blub_Blub.HEIGHT - levelLayout.height - 30);



        game.batch.end();
        if (!gameOver && ballHitbox.overlaps(petAuraHitbox)) {
            gameOver = true;
            clickCount = 0;
            game.setScreen(new GameOverScreen(game));
        }
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
        game.batch.dispose();
    }
}


//package com.blub.Screens;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.GlyphLayout;
//import com.badlogic.gdx.Input.Keys;
//import com.blub.UserInputProcessor;
//import com.blub.Blub_Blub;
//
//
//public class BallScreen implements Screen {
//    Blub_Blub game;
//    Player player = new Player(); // added for leveling feature. could be changed for saving feature
//    float xp = player.getExperience(); // used for leveling feature. when 1 it means enough experience points for a level up. experience resets to 0. level plus 1
//    private static final float BUTTON_WIDTH = 80;
//    private static final float BUTTON_HEIGHT = 80;
//    BitmapFont levelFont;
//    int level = player.getLevel(); // used for leveling feature, starts at 1
//    UserInputProcessor inputProcessor = new UserInputProcessor();
//
//    //Back Button
//    Texture backButton;
//    //Pause Menu button
//    Texture pauseButtonActive;
//    Texture pauseButtonInactive;
//    //Throw Ball Button
//    Texture throwBallButton;
//    //XP bar
//    Texture progressBar;
//    //Background
//    Texture background;
//
//
//
//    public BallScreen(Blub_Blub game) {
//        this.game = game;
//        throwBallButton = new Texture(Gdx.files.internal("ball.png"));
//        backButton = new Texture(Gdx.files.internal("Back_Arrow.png"));
//        pauseButtonActive = new Texture(Gdx.files.internal("PauseButtonActive.png"));
//        pauseButtonInactive = new Texture(Gdx.files.internal("Pause-Button-PNG.png"));
//        progressBar = new Texture("progress-bar.png");
//        levelFont = new BitmapFont(Gdx.files.internal("fonts/levelFont.fnt")); // used for level feature
//        background = new Texture("outdoorBackground.png");
//        Gdx.input.setInputProcessor(inputProcessor);
//    }
//    @Override
//    public void show() {
//
//    }
//
//    @Override
//    public void render(float delta) {
//        game.batch.begin();
//        // background
//        game.batch.draw(background, 0, 0, 1080, 1080);
//
//        //Back Button
//        float backButtonX = Blub_Blub.WIDTH - 1050;
//        float backButtonY = Blub_Blub.HEIGHT - 130;
//
//        if (Gdx.input.getX() > backButtonX && Gdx.input.getX() < backButtonX + BUTTON_WIDTH &&
//            Blub_Blub.HEIGHT - Gdx.input.getY() > backButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
//            < backButtonY + BUTTON_HEIGHT) {
//
//            game.batch.draw(backButton, backButtonX, backButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
//
//            //Back button clicked
//            if (Gdx.input.isTouched()) {
//                this.dispose();
//                game.setScreen(new PetScreen(game)); //Closes (exits)
//            }
//        } else {
//            game.batch.draw(backButton, backButtonX, backButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
//        }
//
//        //Pause Button
//        float pauseButtonX = Blub_Blub.WIDTH - 100;
//        float pauseButtonY = Blub_Blub.HEIGHT - 130;
//
//        if (Gdx.input.getX() > pauseButtonX && Gdx.input.getX() < pauseButtonX + BUTTON_WIDTH &&
//            Blub_Blub.HEIGHT - Gdx.input.getY() > pauseButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
//            < pauseButtonY + BUTTON_HEIGHT) {
//
//            game.batch.draw(pauseButtonActive, pauseButtonX, pauseButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
//
//            //Pause button clicked
//            if (Gdx.input.isTouched()) {
//                this.dispose();
//                game.setScreen(new PauseMenuScreen(game, Blub_Blub.BALL)); //Closes (exits)
//            }
//        } else {
//            game.batch.draw(pauseButtonInactive, pauseButtonX, pauseButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
//        }
//
//        // Throwing Ball Button
//        float throwButtonX = Blub_Blub.WIDTH - 1000;
//        float throwButtonY = Blub_Blub.HEIGHT - 1000;
//
//        if (Gdx.input.getX() > throwButtonX && Gdx.input.getX() < throwButtonX + BUTTON_WIDTH &&
//            Blub_Blub.HEIGHT - Gdx.input.getY() > throwButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
//            < throwButtonY + BUTTON_HEIGHT) {
//
//            game.batch.draw(throwBallButton, throwButtonX, throwButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
//
//            //Throw ball button clicked
//            if (Gdx.input.isTouched()) { // if throw ball button clicked
//                player.addExperience(0.1F);// then xp goes up, when xp goes up, we update experience from player using the Player.addExperience(0.1)
//                xp = player.getExperience();
//                game.batch.draw(progressBar, 0, 1040, (Blub_Blub.WIDTH * xp), 20);
//            }
//        } else {
//            game.batch.draw(throwBallButton, throwButtonX, throwButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
//        }
//
//        // Progress Bar
//        game.batch.draw(progressBar, 0, 1040, (Blub_Blub.WIDTH * xp), 20);
//
//        // level
//        player.levelUp();
//        level = player.getLevel();
//        GlyphLayout levelLayout = new GlyphLayout(levelFont,"LEVEL " + level);
//        levelFont.draw(game.batch, levelLayout,Blub_Blub.WIDTH/ 2 - levelLayout.width /2, Blub_Blub.HEIGHT - levelLayout.height - 30);
//
//        game.batch.end();
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        // Resize your screen here. The parameters represent the new window size.
//    }
//
//    @Override
//    public void pause() {
//        // Invoked when your application is paused.
//    }
//
//    @Override
//    public void resume() {
//        // Invoked when your application is resumed after pause.
//    }
//
//    @Override
//    public void hide() {
//        // This method is called when another screen replaces this one.
//    }
//
//    @Override
//    public void dispose() {
//        // Destroy screen's assets here.
//    }
//}
