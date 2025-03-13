package com.blub.lwjgl3.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.blub.lwjgl3.Blub_Blub;

public class PetScreen implements Screen {

    Blub_Blub game;
    float x, y;

    Texture img;

    //Pet Standing idle, clicked on head, clicked on belly, clicked on face
    Texture petIdle;
    Texture petPatted;
    Texture petPoked;
    Texture petBooped;
    //Mini-game buttons
    Texture playButtonActive;
    Texture playButtonInactive;
    Texture cleanButtonActive;
    Texture cleanButtonInactive;
    Texture feedButtonActive;
    Texture feedButtonInactive;
    //Pause Menu button
    Texture pauseButtonActive;
    Texture pauseButtonInactive;


    public PetScreen(Blub_Blub game) {
        this.game = game;

        petIdle = new Texture(Gdx.files.internal("blank"));
        petPatted = new Texture(Gdx.files.internal("blank"));
        petPoked = new Texture(Gdx.files.internal("blank"));
        petBooped = new Texture(Gdx.files.internal("blank"));
        playButtonActive = new Texture(Gdx.files.internal("blank"));
        playButtonInactive = new Texture(Gdx.files.internal("blank"));
        cleanButtonActive = new Texture(Gdx.files.internal("blank"));
        cleanButtonInactive = new Texture(Gdx.files.internal("blank"));
        feedButtonActive = new Texture(Gdx.files.internal("blank"));
        feedButtonInactive = new Texture(Gdx.files.internal("blank"));
        pauseButtonActive = new Texture(Gdx.files.internal("blank"));
        pauseButtonInactive = new Texture(Gdx.files.internal("blank"));
    }

    @Override
    public void show() {
        img = new Texture("badlogic.jpg");
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(img, x, y);
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
