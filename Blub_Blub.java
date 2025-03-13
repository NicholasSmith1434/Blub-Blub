package com.blub.lwjgl3;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blub.lwjgl3.Screens.MainMenuScreen;

public class Blub_Blub extends Game {

    //Constants of screen size
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 1080;

    public SpriteBatch batch;

    //Loads Main Menu off the start
    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
        //System.out.print("" + Gdx.graphics.getDeltaTime()); --> Used to count delta time
    }
}

//Delta time should be around 1/FPS (FPS = 30) so roughly 0.033 repeating
