package com.blub.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.blub.Blub_Blub;
import com.blub.Screens.Player;
import com.blub.Screens.PetScreen;

public class HygieneScreen implements Screen {
    Blub_Blub game;
    Player player = new Player(); // added for leveling feature. could be changed for saving feature
    float xp = player.getExperience(); // used for leveling feature. when 1 it means enough experience points for a level up. experience resets to 0. level plus 1
    private static final float BUTTON_WIDTH = 80;
    private static final float BUTTON_HEIGHT = 80;
    BitmapFont levelFont;
    int level = player.getLevel(); // used for leveling feature, starts at 1
    //Going Back Button
    Texture backButton;
    //XP bar
    Texture progressBar;



    public HygieneScreen(Blub_Blub game) {
        this.game = game;
        backButton = new Texture(Gdx.files.internal("ball.png"));
        progressBar = new Texture("progress-bar.png");
        levelFont = new BitmapFont(Gdx.files.internal("fonts/levelFont.fnt")); // used for level feature
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // if feed button clicked
        // then xp goes up
        //when xp goes up, we update experience from player using the Player.addExperience(0.1)
        //we also check if there is enough experience to level up with the method in player levelUp()
        Gdx.gl.glClearColor(0.9F, 0.9F, 0.9F, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        //Back Button
        float backButtonX = Blub_Blub.WIDTH - 100;
        float backButtonY = Blub_Blub.HEIGHT - 130;

        if (Gdx.input.getX() > backButtonX && Gdx.input.getX() < backButtonX + BUTTON_WIDTH &&
            Blub_Blub.HEIGHT - Gdx.input.getY() > backButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
            < backButtonY + BUTTON_HEIGHT) {

            game.batch.draw(backButton, backButtonX, backButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);

            //Back button clicked
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new PetScreen(game)); //Closes (exits)
            }
        } else {
            game.batch.draw(backButton, backButtonX, backButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        // Progress Bar
        game.batch.draw(progressBar, 0, 1040, (Blub_Blub.WIDTH * xp), 20);

        // level
        level = player.getLevel();
        GlyphLayout levelLayout = new GlyphLayout(levelFont,"LEVEL " + level);
        levelFont.draw(game.batch, levelLayout,Blub_Blub.WIDTH/ 2 - levelLayout.width /2, Blub_Blub.HEIGHT - levelLayout.height - 30);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}
