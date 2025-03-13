package com.blub.lwjgl3.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.blub.lwjgl3.Blub_Blub;

public class PauseMenuScreen implements Screen {

    Blub_Blub game;

    Texture saveButtonActive;
    Texture saveButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture incVolumeActive;
    Texture incVolumeInactive;
    Texture decVolumeActive;
    Texture decVolumeInactive;

    public PauseMenuScreen(Blub_Blub game) {
        this.game = game;

        saveButtonActive = new Texture(Gdx.files.internal("blank"));
        saveButtonInactive = new Texture(Gdx.files.internal("blank"));
        exitButtonActive = new Texture(Gdx.files.internal("blank"));
        exitButtonInactive = new Texture(Gdx.files.internal("blank"));
        incVolumeActive = new Texture(Gdx.files.internal("blank"));
        incVolumeInactive = new Texture(Gdx.files.internal("blank"));
        decVolumeActive = new Texture(Gdx.files.internal("blank"));
        decVolumeInactive = new Texture(Gdx.files.internal("blank"));
    }

    @Override
    public void show() {
        //System.out.print
    }

    @Override
    public void render(float v) {

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
